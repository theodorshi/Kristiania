#ifndef TASK2_H
#define TASK2_H

#include <stdbool.h>

struct TASK2_NUMBERS_METADATA {
   int iIndex;
   int iNumber;
   int bIsFibonacci;
   int bIsPrimeNumber;
   int bIsSquareNumber;
   int bIsCubeNumber;
   int bIsPerfectNumber;
   int bIsAbundantNumber;
   int bIsDeficientNumber;
   int bIsOddNumber;
};

bool isFibonacci(int n);
bool isPrime(int n);
bool isSquareNumber(int n);
bool isCubeNumber(int n);
bool isPerfectNumber(int n);
bool isAbundantNumber(int n);
bool isDeficientNumber(int n);
bool isOdd(int n);

#endif
