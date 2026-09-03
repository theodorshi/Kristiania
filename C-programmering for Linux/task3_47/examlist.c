#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "examlist.h"

/* =============================================================================
===================================AddCandidate ============================= */
EXAM_NODE *AddCandidate(EXAM_NODE **ppHead, EXAM_NODE **ppTail, const char *pszId){

   int i = 0;
   EXAM_NODE *pNew = NULL;
   EXAM_NODE *pCurrent = NULL;
   
   /* Går gjennom listen - Sjekk for å unngå duplikater.
      Bruker strcmp for å sammenligne uten å kopiere. */
   pCurrent = *ppHead;
   while (pCurrent != NULL) {
      if (strcmp(pCurrent->szCandidateId, pszId) == 0) {
         printf("Feil ved forrige input: Kandidat %s finnes allerede!\n", pszId);
         return NULL;   /* Returnerer NUll hvis ingen ny node ble laget */
      }
      pCurrent = pCurrent->pNext;
   }
   
   /* Allokerer minne til en ny node */
   pNew = malloc(sizeof(EXAM_NODE));
   
   if (pNew == NULL) {
      printf("Feil: malloc feilet\n");
      return NULL;   /* Hvis minnet er fullt */
   }
   
   /* Nullstiller hele noden */
   memset(pNew, 0, sizeof(EXAM_NODE));
   
   /* Kopierer kandidat-ID trygt - begrenset lengde med strncpy - unngå overflow */
   strncpy(pNew->szCandidateId, pszId, sizeof(pNew->szCandidateId) -1);
   
   /* Initialiserer alle oppgavepoeng til -1 (ikke vurdert enda) */
   for (i = 0; i < NUM_TASKS; i++) {
      pNew->iPoints[i] = -1;
   }
   
   /* Kobler noden inn i listen */
   if (*ppHead == NULL) {   /* Tom liste: pNew blir både head og tail */
      *ppHead = pNew;
      *ppTail = pNew;
   } else {                 /* Full liste: koble til slutten (dobbelt lenket) */
      pNew->pPrev = *ppTail;
      (*ppTail)->pNext = pNew;
      *ppTail = pNew;
   }
   return pNew;
} /* AddCandidate Slutt 
==============================================================================*/

/* =============================================================================
===================================LoadFromFile============================== */

int LoadFromFile (EXAM_NODE **ppHead, EXAM_NODE **ppTail,
                  const char *pszFileName) {
   FILE *pFile = NULL;
   char szBuf[64];
   int iCount = 0;
   
   /* Åpner fil i lesemodus */
   pFile = fopen(pszFileName, "r");
   if (pFile == NULL) {
      printf("Feil: Kunne ikke åpen filen %s\n", pszFileName);
      return -1;
   }
   
   /* Leser én linje av gangen med fgets */
   while (fgets(szBuf, sizeof(szBuf), pFile) != NULL) {
      /* Fjerner linjeskift som kommer med i fgets '\n' */
      szBuf[strcspn(szBuf, "\n")] = '\0';
      
      /* Hopper over tomme linjer */
      if (szBuf[0] == '\0') {
         continue;
      }
      
      /* Kaller AddCandidate for hver linje */
      if (AddCandidate(ppHead, ppTail, szBuf) != NULL) {
         iCount++;
      }
   }
   fclose(pFile);
   printf("Lastet inn %d kandidater fra %s", iCount, pszFileName);
   return iCount;
} /* LoadFromFile Slutt
============================================================================= */

/* =============================================================================
=====================================GradeTask=============================== */

int GradeTask(EXAM_NODE *pHead, const char *pszId,
              int iTask, int iPoints, const char *pszReasoning) {
   EXAM_NODE *pCurrent = NULL;
   int i = 0;
   int aiMaxPoints[NUM_TASKS] = {5, 15, 20, 20, 20, 20};
   
   if (iPoints > aiMaxPoints[iTask]){
      printf("Feil: Maks poeng per oppgave %d er %d\n",
             iTask + 1, aiMaxPoints[iTask]);
      return -1;
   }
   
   /* Valider oppgavenummer */
   if (iTask < 0 || iTask >= NUM_TASKS) {
      return -1;
   }
   
   pCurrent = pHead;
   
   while (pCurrent != NULL) {
      /* Sjekker om det er riktig kandidat */
      if (strcmp(pCurrent->szCandidateId, pszId) == 0) {
         
         /* Setter poeng og begrunnelse */
         pCurrent->iPoints[iTask] = iPoints;
         strncpy(pCurrent->szReasoning[iTask], pszReasoning, 63);
         pCurrent->szReasoning[iTask][63] = '\0';
         
         /* Rekalkulerer total score */
         pCurrent->iScore = 0;
         for (i = 0; i < NUM_TASKS; i++) {
            if (pCurrent->iPoints[i] != -1) {
               pCurrent->iScore += pCurrent->iPoints[i];
            }
         }
         return 0; /* Suksess */
      }
      /* Går til neste node */
      pCurrent = pCurrent->pNext;
   }
   return -1;  /* Kandidat ikke funnet */
} /* GradeTask Slutt 
==============================================================================*/

/* =============================================================================
=====================================PrintAll================================ */

void PrintAll(EXAM_NODE *pHead) {
   EXAM_NODE *pCurrent = NULL;
   char cGrade = ' ';
   int bComplete = 1;
   int i = 0;
   
   /* Sjekker om lista er tom */
   if (pHead == NULL) {
      printf("Ingen kandidater i lista");
      return;
   }
   
   /* Skriver ut oversikt */
   printf("%-10s %-4s %-4s %-4s %-4s %-4s %-4s %-7s %s\n", "KandidatID",
          "T1", "T2", "T3", "T4", "T5", "T6", "Total", "Karakter");
   printf("------------------------------------------------------------\n");
   
   /* Traverserer lista */
   pCurrent = pHead;
   while (pCurrent != NULL) {
   
      /* Nullstiller for hver kandidat */
      bComplete = 1;
   
      /* Beregner karakter */
      cGrade = CalcGrade(pCurrent->iScore);
      
      /* Sjekker om alle oppgaver er vurdert */
      for (i = 0; i < NUM_TASKS; i++) {
         if (pCurrent->iPoints[i] == -1) {
            bComplete = 0;
            break;
         }
      }
      
      /* Skriver ut én kandidat-ID */
      printf("%-12s", pCurrent->szCandidateId);
      
      /* Skriver ut poeng per oppgave */
      for (i = 0; i < NUM_TASKS; i++) {
         if (pCurrent->iPoints[i] == -1) {
            printf("%-5s", "-");
         }
         else {
            printf("%-5d", pCurrent->iPoints[i]);
         }
      }
      
      /* Skriver ut snittkarakter */
      if (bComplete) {
         printf("%-7d %c\n", pCurrent->iScore, cGrade);
      }
      else {
         printf("%-7d %s\n", pCurrent->iScore, "?");
      }
      
      /* Går til neste */
      pCurrent = pCurrent->pNext;
   }   
} /* PrintAll Slutt */

/* Hjelpefunksjon: CalcGrade */
char CalcGrade(int iScore) {
   if (iScore >= 93) return 'A';
   if (iScore >= 78) return 'B';
   if (iScore >= 59) return 'C';
   if (iScore >= 51) return 'D';
   if (iScore >= 40) return 'E';
   return 'F';
}/* CalcGrade Slutt 
============================================================================= */

/* =============================================================================
=====================================PrintByGrade============================ */

void PrintByGrade(EXAM_NODE *pHead, char cGrade) {
   char cCalcGrade = ' ';
   EXAM_NODE *pCurrent = NULL;
   
   /* Sjekker om lista er tom */
   if (pHead == NULL) {
      printf("Ingen kandidater i lista");
      return;
   }
   
   /* Skriver ut header (med litt ekstra rom mellom kolonnene) */
   printf("%-15s %-10s %-6s\n", "KandidatID", "Poeng", "Karakter");
   printf("----------------------------\n");
   
   /* Traverser lista */
   pCurrent = pHead;
   while (pCurrent != NULL) {
   
      /* Beregner karakter */
      cCalcGrade = CalcGrade(pCurrent->iScore);
      
      if (cCalcGrade == cGrade) {
         printf("%-15s %-10d %-6c\n",
         pCurrent->szCandidateId,
         pCurrent->iScore,
         cCalcGrade);
      }     
      
      /* Går til neste */
      pCurrent = pCurrent->pNext;
   }
} /* PrintByGrade Slutt 
============================================================================= */

/* =============================================================================
=====================================NonEvaluated============================ */

void NonEvaluated(EXAM_NODE *pHead) {
   EXAM_NODE *pCurrent = NULL;
   int i = 0;
   int iGraded = 0;
   
   pCurrent = pHead;
   while (pCurrent != NULL) {
   
      /* Nullstiller for hver kandidat */
      iGraded = 0;
      
      /* Teller antall vurderte oppgaver */
      for (i = 0; i < NUM_TASKS; i++) {
         if (pCurrent->iPoints[i] != -1) {
            
            iGraded++;
         }
      }
      
      /* Status på uvurderte kandidater: Ikke vurdert/delvis vurdert */
      if (iGraded == 0) {
         printf("%-15s IKKE VURDERT\n",
         pCurrent->szCandidateId);
      }
      else if (iGraded < NUM_TASKS) {
         printf("%-15s DELVIS VURDERT (%d/%d)\n",
         pCurrent->szCandidateId,
         iGraded, NUM_TASKS);
      }
      pCurrent = pCurrent->pNext;
   }
} /* NonEvaluated Slutt
============================================================================= */

/* =============================================================================
=====================================PrintDetails============================ */

void PrintDetails(EXAM_NODE *pHead, const char *pszId) {
   EXAM_NODE *pCurrent = NULL;
   int i = 0;
   char cGrade = ' ';
   
   pCurrent = pHead;
   
   while (pCurrent != NULL) {
      /* Sjekker om det er riktig kandidat */
      if (strcmp(pCurrent->szCandidateId, pszId) == 0) {
      
         /* Beregner karakter */
         cGrade = CalcGrade(pCurrent->iScore);
         
         /* Skriver ut kandidatinfo */
         printf("Kandidat: %s\n", pCurrent->szCandidateId);
         printf("Total score: %d\n", pCurrent->iScore);
         printf("Karakter: %c\n", cGrade);
         
         /* Skriver ut detaljer per oppgave */
         for (i = 0; i < NUM_TASKS; i++) {
            printf("Oppgave %d: %d poeng\n",
                    i + 1, pCurrent->iPoints[i]);
            printf("Begrunnelse: %s\n",
                    pCurrent->szReasoning[i]);
         }
         return; /* Ferdig - Ikke let videre i lista */
      }
      /* Går til neste node */
      pCurrent = pCurrent->pNext;
   }
   /* Kandidat ikke funnet */
   printf("Kandidat %s ikke funnet\n", pszId);
} /* PrintDetails Slutt
============================================================================= */

/* =============================================================================
======================================FreeList=============================== */

void FreeList(EXAM_NODE **ppHead) {
   /* pNext lagrer neste node før jeg frigjør pCurrent */
   EXAM_NODE *pCurrent = NULL;
   EXAM_NODE *pNext = NULL;
   
   pCurrent = *ppHead;
   
   /* Går gjennom listen og frigjør hver node */
   while (pCurrent != NULL) {
      pNext = pCurrent->pNext;
      free(pCurrent);
      pCurrent = pNext;
   }
   /* Setter head til NULL etter frigjøring - unngår dangling pointer */
   *ppHead = NULL;
} /* FreeList Slutt
============================================================================= */


