#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "task2.h"

int main(void) {

   /* Fil-pekere */
   FILE *fInput = NULL;
   FILE *fOutput = NULL;
   
   /* Struct som holder på all metadata for ett tall */
   struct TASK2_NUMBERS_METADATA stEntry;
   
   /* Buffer som sørger for sikker lesing av fil.
      Stor nok for største int, linjeskift og nullterminator */
   char szBuf[32];
   
   /* Teller for sekvensnummer, starter på 0 og økes til 1 for første skriving */
   int iIndex = 0;
   
   /* Brukes til å fjerne \r og \n */
   int iLen = 0;
   
   /* Åpne input-fil for lesing - fopen returnerer NULL hvis filen ikke finnes */
   fInput = fopen("pgexam26_test.txt", "r");
   if (fInput == NULL) {
      printf("Feil: Kunne ikke åpne inputfil\n");
      return 1;
   }
   
   /* "wb" = write binary - skriver rå bytes */
   fOutput = fopen("pgexam26_output.bin", "wb");
   if (fOutput == NULL) {
      printf("Feil: Kunne ikke skrive til outputfil\n");
      fclose(fInput);
      return 1;
   }
   
   /* Jeg bruker fgets for ekstra sikkerhet, slik at den leser maks
      sizeof(szBuf) tegn og hindrer buffer overflow */
   while (fgets(szBuf, sizeof(szBuf), fInput) != NULL) {
   
      /* Bruker strlen siden szBuf alltid nulltermineres med fgets */
      iLen = strlen(szBuf);
      while (iLen > 0 && (szBuf[iLen-1] == '\r' || szBuf[iLen-1] == '\n')) {
         szBuf[iLen-1] = '\0';
         iLen--;
      }
      
      /* Hopper over tomme linjer */
      if (iLen == 0) {
         continue;
      }
      
      /* Nullstiller structen for hver iterasjon - unngår verdier fra forrige
         runde som blir liggende i minnet */
      memset(&stEntry, 0, sizeof(stEntry));
      
      /* atoi konverterer tekststrengen til 1055\n til heltallet 1055 */
      stEntry.iNumber = atoi(szBuf);
      stEntry.iIndex = iIndex;
      
      iIndex++;
 
      /* Kaller hver funksjon */
      stEntry.bIsFibonacci = isFibonacci(stEntry.iNumber);
      stEntry.bIsPrimeNumber = isPrime(stEntry.iNumber);
      stEntry.bIsSquareNumber = isSquareNumber(stEntry.iNumber);
      stEntry.bIsCubeNumber = isCubeNumber(stEntry.iNumber);
      stEntry.bIsPerfectNumber = isPerfectNumber(stEntry.iNumber);
      stEntry.bIsAbundantNumber = isAbundantNumber(stEntry.iNumber);
      stEntry.bIsDeficientNumber = isDeficientNumber(stEntry.iNumber);
      stEntry.bIsOddNumber = isOdd(stEntry.iNumber);
      
      /* Skriver hele structen som rå binærdata - ikke tekst 
         &stEntry = adressen til structen i minnet
         sizeof(stEntry) = 40 bytes (10 int-felt x 4 bytes)*/
      fwrite(&stEntry, sizeof(stEntry), 1, fOutput);
   }
   
   /* Lukker filene for å tømme bufferen */
   fclose(fInput);
   fclose(fOutput);
   return 0;
} /* main Slutt
----------------------------------------------------------------------------- */



