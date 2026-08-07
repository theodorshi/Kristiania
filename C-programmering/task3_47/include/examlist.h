#ifndef EXAMLIST_H
#define EXAMLIST_H

#define NUM_TASKS 6

/* Poeng man kan oppnå per oppgave */
#define MAX_POINTS_T1 5
#define MAX_POINTS_T2 15
#define MAX_POINTS_T3 20
#define MAX_POINTS_T4 20
#define MAX_POINTS_T5 20
#define MAX_POINTS_T6 20
#define MAX_SCORE 100

typedef struct _EXAM_NODE {
   struct _EXAM_NODE *pNext;
   struct _EXAM_NODE *pPrev;
   char szCandidateId[32];
   int iPoints[NUM_TASKS];   /* iPoints er initialisert til -1 = ikke vurdert */
   char szReasoning[NUM_TASKS][64];
   int iScore;
} EXAM_NODE;

void PrintAll(EXAM_NODE *pHead);
void PrintByGrade(EXAM_NODE *pHead, char cGrade);
void NonEvaluated(EXAM_NODE *pHead);
void PrintDetails(EXAM_NODE *pHead, const char *pszId);
void FreeList(EXAM_NODE **ppHead);
EXAM_NODE *AddCandidate(EXAM_NODE **ppHead, EXAM_NODE **ppTail, const char *pszId);
int LoadFromFile(EXAM_NODE **ppHead, EXAM_NODE **ppTail,
   const char *pszFileName);
int GradeTask(EXAM_NODE *pHead, const char *pszId, int iTask, int iPoints,
   const char *pszReasoning);
char CalcGrade(int iScore);

#endif
