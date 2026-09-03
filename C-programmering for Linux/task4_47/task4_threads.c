/* This file has been created by EWA, and is part of task 4 on the exam for PG3401 2026*/
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "task4_prime.h"

void tean(unsigned int *const v, unsigned int *const w,
          const unsigned int *const k, int N);

/* Endret size_t til unsigned int for å kjøre programmet */          
unsigned int tcp_checksum(const unsigned char *data, unsigned int length);

/* Størrelse på den krypterte filen i bytes */
#define FILESIZE 56

/* XTEA jobber på blokker med 2 usignerte ints (8 bytes) */
#define XTEA_BLOCK_BYTES 8
#define XTEA_BLOCK_INTS 2

/* Antall sykluser for xtea - negativ betyr dekryptering */
#define XTEA_CYCLES (-32)

/* De første 5 tegnene i dekryptert fil skal være BENGT */
#define MAGIC "BENGT"
#define MAGIC_LEN 5

/* -----------------------------------------------------------------------------
   - Struct for node i den lenkede listen som holder på ett primtall
   - Hver node lagrer ett primtall og peker til neste node
----------------------------------------------------------------------------- */
struct PRIMENUMBER {
   unsigned int uiPrime;
   struct PRIMENUMBER* next;
};


/* -----------------------------------------------------------------------------
   - Struct som brukes til å sende all delt data inn i hver worker thread
   - Dette erstatter de opprinnelige globale variablene
        - pPrimeList og fFileInput er nå i main() og sendes via struct
        
   Lagt til i PART II:
      - paucEncrypted: den krypterte filen i minnet
      - iFound: flagg som signaliserer at riktig nøkkel er funnet
      - pMutexFound: beskytter iFound-flagget
----------------------------------------------------------------------------- */
struct THREADARGS {
   /* Part I - fillesning og lenket liste */
   FILE *fInputFile;
   struct PRIMENUMBER **ppPrimeList;
   pthread_mutex_t *pMutexFile;  /* beskytter fInputFile reads */
   pthread_mutex_t *pMutexList;  /* beskytter pPrimeList writes */
   
   /* Part II - dekryptering og signalisering */
   unsigned char *paucEncrypted;   /* kryptert data i minnet */
   int *piFound;  /* 1 = riktig nøkkel funnet */
   pthread_mutex_t *pMutexFound;   /* beskytter piFound */
};

/*------------------------------------------------------------------------------
================================TryDecrypt======================================
 tryDecrypt - forsøk på å dekryptere med ett primtall som nøkkel

   - XTEA bruker en nøkkel som består av 4 unsigned ints
   - Filen er 56 bytes = 7 blokker med 8 bytes
   
   Hvis de 5 første tegnene i dekryptert tekst er "BENGT:
      - Lagrer dekryptert innhold til task4_plain.txt
      - Beregner IP-checksum og lagre til task4_plain.hash
      - Returnerer 1 (funnet), ellers 0"
----------------------------------------------------------------------------- */
int tryDecrypt(unsigned int uiPrime, unsigned char *paucEncrypted) {
   unsigned int auiKey[4];
   unsigned int auiIn[XTEA_BLOCK_INTS];
   unsigned int auiOut[XTEA_BLOCK_INTS];
   unsigned char aucDecrypted[FILESIZE];
   unsigned int uiChecksum = 0;
   FILE *fOut = NULL;
   FILE *fHash = NULL;
   int iBlock = 0;
   int iNumBlocks = 0;
   
   /* Bygger nøkkelen - alle 4 elementer er det samme primtallet */
   auiKey[0] = uiPrime;
   auiKey[1] = uiPrime;
   auiKey[2] = uiPrime;
   auiKey[3] = uiPrime;
   
   /* Dekrypter alle blokkene i filen */
   iNumBlocks = FILESIZE / XTEA_BLOCK_BYTES;
   for (iBlock = 0; iBlock < iNumBlocks; iBlock++) {
      
      /* Kopier 8 bytes i 2 usignerte ints for XTEA */
      memcpy(auiIn, paucEncrypted + (iBlock * XTEA_BLOCK_BYTES),
             XTEA_BLOCK_BYTES);

      /* Dekrypter blokken - XTEA_CYCLES er negativ = dekryptering */
      tean(auiIn, auiOut, auiKey, XTEA_CYCLES);
      
      /* Kopier resultatet til dekryptert buffer */
      memcpy(aucDecrypted + (iBlock * XTEA_BLOCK_BYTES), auiOut, XTEA_BLOCK_BYTES);
   }

   /* Sjekker om de første 5 tegnene er "BENGT" */
   if (memcmp(aucDecrypted, MAGIC, MAGIC_LEN) != 0) {
      return 0;   /* Feil nøkkel - prøv neste */
   }

   /* --- Riktig nøkkel funnet --- */

   /* Lagrer dekryptert innhold til task4_plain.txt */
   fOut = fopen("task4_plain.txt", "wb");
   if (fOut != NULL) {
      fwrite(aucDecrypted, 1, FILESIZE, fOut);   /* Får alle 56 bytes samtidig */
      fclose(fOut);
      fOut = NULL;
   }

   /* Beregner IP-checksum og lagrer i task4_plain.hash */
   uiChecksum = tcp_checksum(aucDecrypted, FILESIZE);
   fHash = fopen("task4_plain.hash", "w");
   if (fHash != NULL) {
      fprintf(fHash, "%u\n", uiChecksum);
      fclose(fHash);
      fHash = NULL;
   }

   printf("Funnet! Nøkkel: %u Checksum: %u\n", uiPrime, uiChecksum);
   return 1;
}


/* -----------------------------------------------------------------------------
=================================ThreadFunction=================================
   - Worker thread på hvert punkt
   - Begge trådene deler samme FILE* og samme linked list peker
   Uten mutexene får man disse problemene:
        1. To tråder kaller på fscanf på samme FILE* samtidig, som medfører
           udefinert oppførsel. Den interne fil-posisjonen kan bli korrupt, så
           tall leses to ganger eller hoppes over.
        2. To tråder som settes inn i den lenkede listen samtidig er en typisk
           race condition: begge leser pPrimeList, begge setter newNode->next
           til samme gamle hode, og deretter overskriver den ene tråden den
           andres oppdatering slik at primtall forsvinner fra listen for alltid.
           
   Med mutex løses det slik:
      - pMutexFile sikrer at bare én tråd om gangen leser filen
      - pMutexList sikrer at bare én tråd om gangen endrer listen
      - isPrime()-kallet gjører bevisst utenfor mutex slik at begge trådene kan
      kjøre priimtallstesten parallelt
      - Kun selve innsettingen i listen er serialisert
----------------------------------------------------------------------------- */

void* threadFunction(void* arg){
   struct THREADARGS* pArgs = NULL;
   unsigned int auiNumbers[10];
   int iNumbersRead = 0;
   int iIndex = 0;
   struct PRIMENUMBER* newNode = NULL;
   int iAlreadyFound = 0;
   
   pArgs = (struct THREADARGS*)arg;
   
   /* Sikkerhetssjekk - unngår å følge NULL-peker */
   if (pArgs == NULL) {
      return NULL;
   }

   while (1) {
      iNumbersRead = 0;
      
      /* --- Mutex påskrudd - beskytter fillesing --- 
         - Jeg låser slik at bare én tråd om gangen kan kalle fscanf
         - Uten denne låsen kan begge tråder flytte filposisjonpekeren
         samtidig, som vil si at det ikke blir korrupte eller duplikate avlesinger */
      pthread_mutex_lock(pArgs->pMutexFile);
      
      /* Sjekk om den andre tråden allerede har funnet nøkkelen
         Jeg gjør dette under fil-mutex for å unngå unødvendig lesing */
      iAlreadyFound = *(pArgs->piFound);
      if (!iAlreadyFound) {
         while (iNumbersRead < 10) {
            if (fscanf(pArgs->fInputFile, "%u",
                &(auiNumbers[iNumbersRead])) == 1) {
               iNumbersRead++;
            }
            else {
               break;
            }
         }
      }
      
      pthread_mutex_unlock(pArgs->pMutexFile);
      /* --- Mutex avskrudd: fillesing --- */
      
      
      /* Avskrudd hvis nøkkel er funnet eller ingen flere tall */
      if (iAlreadyFound || iNumbersRead == 0) {
         break;
      }
      
      /* Tester hvert tall - kjører parallelt (utenfor mutex) */
      for (iIndex = 0; iIndex < iNumbersRead; iIndex++) {
         
         /* Sjekker om nøkkel allerede er funnet av andre tråd */
         pthread_mutex_lock(pArgs->pMutexFound);
         iAlreadyFound = *(pArgs->piFound);
         pthread_mutex_unlock(pArgs->pMutexFound);
         
         if (iAlreadyFound) {
            break;
         }
         
         if (isPrime(auiNumbers[iIndex])) {
            
            /* Legger primtallet til i listen */
            newNode = (struct PRIMENUMBER*)malloc(
                       sizeof(struct PRIMENUMBER));
               if (newNode != NULL) {
                  newNode->uiPrime = auiNumbers[iIndex];
                  newNode->next = NULL;
                  
                  /* --- Mutex påskrudd: beskytter innsetting i listen ---
                  Uten dette kan to tråder overskriuve hverandres
                  hodepeker og et primtall forsvinner fra listen */
                  pthread_mutex_lock(pArgs->pMutexList);
                  newNode->next = *(pArgs->ppPrimeList);
                  *(pArgs->ppPrimeList) = newNode;
                  pthread_mutex_unlock(pArgs->pMutexList);
                  /* --- Mutex: innsetting i listen --- */
                     
                  newNode = NULL;
               }
               
               /* Forsøk på dekryptering med dette primtallet som nøkkel.
                  tryDecrypt() kjører utenfor mutex - det er trygt siden
                  den bare skriver til lokale variabler og filer */
               if (tryDecrypt(auiNumbers[iIndex], pArgs->paucEncrypted)) {
                  
               /* --- Mutex påskrudd: sett iFound-flagget ---
                  Uten mutex kan begge tråder sette flagget samtidig
                  og skrive over hverandres resultatfiler.
                  Med mutex er flagget atomisk sett. */
                  pthread_mutex_lock(pArgs->pMutexFound);
                  *(pArgs->piFound) = 1;
                  pthread_mutex_unlock(pArgs->pMutexFound);
                  /* --- Mutex avskrudd: iFound-flagget --- */
                  
                  return NULL;   /* Avslutter denne tråden umiddelbart */
               }
            }
         }
         
         if (iAlreadyFound) {
            break;
         }
      }
      
      return NULL;
}

/* =============================================================================
=======================================main=====================================

 main: oppretter tråder, venter på dem, og skriver ut resultater
 
 Endringer fra originalkoden:
   1. pPrimeList og fInputFile er nå lokale variabler i stedet for globale
   2. Filenavnet hentes fra apszArgv[1] (var hardkodet) 
   3. Mutexer initialiseres eksplisitt med pthread_mutex_init()
   4. En args-struct sender delt tilstad inn i hver tråd
   5. (PART II) Leser kryptert fil, send til trådene for dekryptering
==============================================================================*/
int main(int iArgc, char* apszArgv[]){
   pthread_t thread1;
   pthread_t thread2;
   pthread_mutex_t tMutexFile;
   pthread_mutex_t tMutexList;
   pthread_mutex_t tMutexFound;
   struct PRIMENUMBER* pPrimeList = NULL;  /* Er nå lokal */
   struct PRIMENUMBER* pPtr = NULL;
   struct THREADARGS tArgs;
   FILE* fInputFile = NULL;
   FILE* fBinFile = NULL;
   unsigned char aucEncrypted[FILESIZE];
   int iFound = 0;
   int rc = 0;
   int iByte = 0;

   /* Sjekker at brukeren ga oss et filnavn på kommandolinjen */
   if (iArgc < 2) {
      printf("Bruk: %s <filnavn>\n", apszArgv[0]);
      return 1;
   }
   
   /* Åpner primtallsfilen - navn fra kommandolinjen var hardkodet */
   fInputFile = fopen(apszArgv[1], "r");
   if (fInputFile == NULL) {
      printf("Feil: kunne ikke åpne tekst-filen: %s\n", apszArgv[1]);
      return 1;
   }
   
   /* Leser den krypterte filen inn i minnet slik at begge trådene kan bruke den
      uten å konkurrere om en FILE* */
   fBinFile = fopen("task4_code.bin" "rb");
   if (fBinFile == NULL) {
      printf("Feil: kunne ikke åpne binær-filen\n");
      fclose(fInputFile);
      return 1;
   }
   for (iByte = 0; iByte < FILESIZE; iByte++) {
      aucEncrypted[iByte] = (unsigned char)fgetc(fBinFile);
   }
   fclose(fBinFile);
   fBinFile = NULL;
   
   /* Mutex-initialisering */
   pthread_mutex_init(&tMutexFile, NULL);
   pthread_mutex_init(&tMutexList, NULL);
   pthread_mutex_init(&tMutexFound, NULL);
   
   /* Fyller args-structen som begge trådene skal dele */
   tArgs.fInputFile = fInputFile;
   tArgs.ppPrimeList = &pPrimeList;
   tArgs.pMutexFile = &tMutexFile;
   tArgs.pMutexList = &tMutexList;
   tArgs.paucEncrypted = aucEncrypted;
   tArgs.piFound = &iFound;
   tArgs.pMutexFound = &tMutexFound;

   /* Oppretter arbeidstråd 1 */
   rc = pthread_create(&thread1, NULL, threadFunction, (void*)&tArgs);
   if (rc != 0) {
      printf("Feil: kunne ikke opprette tråd 1\n");
      fclose(fInputFile);
      pthread_mutex_destroy(&tMutexFile);
      pthread_mutex_destroy(&tMutexList);
      pthread_mutex_destroy(&tMutexFound);
      return 1;
   }
   
   /* Oppretter arbeidstråd 2 */
   rc = pthread_create(&thread2, NULL, threadFunction, (void*)&tArgs);
   if (rc != 0) {
      printf("Feil: kunne ikke opprette tråd 2\n");
      pthread_join(thread1, NULL);
      fclose(fInputFile);
      pthread_mutex_destroy(&tMutexFile);
      pthread_mutex_destroy(&tMutexList);
      pthread_mutex_destroy(&tMutexFound);
      return 1;
   }

   /* Venter på at begge trådene er ferdige før jeg rører listen */
   pthread_join(thread1, NULL);
   pthread_join(thread2, NULL);

   fclose(fInputFile);
   fInputFile = NULL;

   /* Rydder opp mutexer når jeg er ferdig med dem */
   pthread_mutex_destroy(&tMutexFile);
   pthread_mutex_destroy(&tMutexList);
   
   /* Skriver ut alle primtall som ble funnet */
   printf("\r\nPrimtall funnet : \r\n");
   pPtr = pPrimeList;
   while (pPtr != NULL) {
      printf("%d\r\n", pPtr->uiPrime);
      pPtr = pPtr->next;
   }

   /* Frigjør den lenkede listen */
   while (pPrimeList != NULL) {
      pPtr = pPrimeList;
      pPrimeList = pPrimeList->next;
      free(pPtr);
      pPtr = NULL;
   }

   return 0;
}

