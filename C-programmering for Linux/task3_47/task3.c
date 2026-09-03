#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "examlist.h"

/* =============================================================================
======================================== main =============================== */
int main(void) {
   EXAM_NODE *pHead = NULL;
   EXAM_NODE *pTail = NULL;
   char szChoice[8];
   
   /* Meny */
   while (1) {
      printf("\n=== EKSAMEN VURDERINGSSYSTEM ===\n");
      printf("1. Legg til kandidat manuelt\n");
      printf("2. Last inn kandidater fra fil\n");
      printf("3. Vurder en oppgave\n");
      printf("4. Vis alle kandidater\n");
      printf("5. Vis kandidater med bestemt karakter\n");
      printf("6. Vis uvurderte kandidater\n");
      printf("7. Vis detaljer for kandidat\n");
      printf("q. Avslutt\n");
      printf("Valg: ");
      
      fgets(szChoice, sizeof(szChoice), stdin);
      
      /* Switch-case med hovedfunksjonalitet for hvert valg */
      switch (szChoice[0]) {
         case '1': {
            char szId[32];
            printf("Skriv kandidat-ID: ");
            fgets(szId, sizeof(szId), stdin);
            szId[strcspn(szId, "\n")] = '\0';
            AddCandidate(&pHead, &pTail, szId);
            break;
            }
         case '2': {
            char szFileName[64];
            printf("Filnavn (skriv 'candidates.txt'): ");
            fgets(szFileName, sizeof(szFileName), stdin);
            szFileName[strcspn(szFileName, "\n")] = '\0';
            LoadFromFile(&pHead, &pTail, szFileName);
            break;
            }
         case '3': {
            char szId[32];
            char szReasoning[64];
            char szPoints[8];
            char szTask[8];
            int iTask = 0;    /* Brukes til validering av input */
            int iPoints = 0;  /* Brukes til validering av input */
            int aiMaxPoints[NUM_TASKS];
            aiMaxPoints[0] = 5;  /* Task 1 */
            aiMaxPoints[1] = 15;  /* Task 2 */
            aiMaxPoints[2] = 20;  /* Task 3 */
            aiMaxPoints[3] = 20;  /* Task 4 */
            aiMaxPoints[4] = 20;  /* Task 5 */
            aiMaxPoints[5] = 20;  /* Task 6 */
            
            printf("Kandidat-ID: ");
            fgets(szId, sizeof(szId), stdin);
            szId[strcspn(szId, "\n")] = '\0';
            
            printf("Oppgavenummer (1-6): ");
            fgets(szTask, sizeof(szTask), stdin);
            iTask = atoi(szTask);
            szTask[strcspn(szTask, "\n")] = '\0';
            
            /* Validerer at oppgavenummer er 1-6 og et tall */
            if (!isdigit(szTask[0]) || iTask < 1 || iTask > NUM_TASKS) {
               printf("Feil: Oppgavenummer må være 1-6!\n");
               break;
            }
            
            /* Validerer at poeng stemmer med oppgavenummer */
            printf("Poeng (0-%d): ", aiMaxPoints[iTask - 1]);
            fgets(szPoints, sizeof(szPoints), stdin);
            iPoints = atoi(szPoints);
            
            /* Validerer poeng mot riktig maks */
            if (!isdigit(szPoints[0]) || iPoints < 0 || 
                iPoints > aiMaxPoints[iTask - 1]) {
               printf("Feil: Poeng må være 0-%d for oppgave %d!\n",
                      aiMaxPoints[iTask - 1], iTask);
               break;
            }
            
            printf("Begrunnelse: ");
            fgets(szReasoning, sizeof(szReasoning), stdin);
            szReasoning[strcspn(szReasoning, "\n")] = '\0';
            
            GradeTask(pHead, szId, iTask -1, iPoints, szReasoning);
            break;
            }
         case '4': {
            PrintAll(pHead);
            break;
            }
         case '5': {
            char cGrade = ' ';
            char szGrade[8];
            printf("Karakter (A-F): ");
            fgets(szGrade, sizeof(szGrade), stdin);
            
            /* toupper for å tillate både små og store bokstaver i input */
            cGrade = toupper(szGrade[0]);
            
            /* Kun A-F som er godkjente alternativer */
            if (cGrade < 'A' || cGrade > 'F') {
               printf("Ugyldig karakter. Bruk A-F!\n");
               break;
            }
            PrintByGrade(pHead, cGrade);
            break;
            }
         case '6': {
            NonEvaluated(pHead);
            break;
            }
         case '7': {
            char szId[32];
            printf("Kandidat-ID: ");
            fgets(szId, sizeof(szId), stdin);
            szId[strcspn(szId, "\n")] = '\0';
            PrintDetails(pHead, szId);
            break;
            }
         case 'q': {
            FreeList(&pHead);
            return 0;
            }
         default: {
            printf("Ugyldig valg\n");
            break;
            }
      }
   }
   return 0;
}




