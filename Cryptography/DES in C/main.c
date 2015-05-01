/*
  Michael Kochell
  CSSE479: Cryptography
  DES assignment
*/

 
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "permutation.h"
#include "sbox.h"
#include "keys.h"
#include <time.h>

/*
  Each iteration runs here
*/
void execute(unsigned long long input, int numIterations, int numRounds);

/*
  f(R, K)
    called each round to calculate new left side
    calls sbox calculation
*/
int mainKeyFunction(unsigned int rightSide, unsigned long long roundKey);

/*
  Used to extratct 64-bit info from file
*/
unsigned long long stringToLong(char *string);

/*
  All round keys stored in this array
*/
unsigned long long roundKeys[16]; 

int main(int argc, char *argv[])
{
  
  //open file
	FILE* fp;
	fp = fopen("desinput.txt", "r+");

	int numIterations, numRounds;
	char plainString[65];
	char keyString[65];
	unsigned long long plain, key;

  // obtain data from file
  int numItemsScanned; //used to supress warnings
	numItemsScanned = fscanf(fp,"%d\n", &numIterations);
	numItemsScanned = fscanf(fp,"%d\n", &numRounds);
	numItemsScanned = fscanf(fp,"%s\n", plainString);
	numItemsScanned = fscanf(fp,"%s\n", keyString);

  // close file
	fclose(fp);


  // extract 64-bit numbers
	plain = stringToLong(plainString);
	key = stringToLong(keyString);

  // prep roundKeys array
	obtainRoundKeys(key, roundKeys, numRounds);

  // run algorithm
  execute(plain, numIterations, numRounds);
	

	return 0;
}

void execute(unsigned long long input, int numIterations, int numRounds)
{
  int currentIteration;
  unsigned int leftSide, rightSide;
  unsigned long long initPerm, cipher = 0;

  // begin iterations
  for(currentIteration = 0; currentIteration < numIterations; currentIteration++)
  {
    // do initial xor for CBC
    cipher = input ^ cipher;

    // get initial permutation for this iteration
    initPerm = performLongToLongPermutation(cipher, initialPerm, initialPermSize);

    // set left and right side
    leftSide = initPerm >> 32;  // shift off right bits
    rightSide = (unsigned int) initPerm; // cutoff left bits


    int currentRound;
    unsigned int tempLeft, fOutput;
    unsigned long long roundKey;

    // time to go through the rounds
    for(currentRound = 0; currentRound < numRounds; currentRound++)
    {
      // retrieve round key from array
      roundKey = roundKeys[currentRound];

      // store right side for left side later
      tempLeft = rightSide;

      // retrieve main key function result
      fOutput = mainKeyFunction(rightSide, roundKey);

      // xor with left side
      rightSide = leftSide ^ fOutput;

      // left side becomes old right side
      leftSide = tempLeft;
    }

    // combine right and left side
    cipher = (((unsigned long long) rightSide) << 32) | leftSide;

    // get final permutation for interation (initial inverse)
    cipher = performLongToLongPermutation(cipher, initialPermInv, initialPermSize);
  }

  // final result printed
  printf("final cipher: %016llX\n", cipher);
}

/*
  Main function takng R_i and the K_i
    where i = the currentRound zero-indexed
*/
int mainKeyFunction(unsigned int rightSide, unsigned long long roundKey)
{
	unsigned long long expandedRight, xorResult;
	int sboxCalculation;

  // expand right side, E(R)
	expandedRight = performIntToLongPermutation(rightSide, expandRight, expandRightSize);
	
  // xor E(R) with round key
	xorResult = expandedRight ^ roundKey;

  // return sbox calculation
	return performSboxCalculation(xorResult);
}

/*
  Used to create data out of input file
*/
static const int longSize = 64; //used for correct bit placement
static const unsigned long long ONE = 0x1; // used for type correction
unsigned long long stringToLong(char *string)
{
  int i;
  unsigned long long result = 0;
  for(i=0; i<longSize; i++)
  {
    //ascii '0' = 0x30, acsii '1' = 0x31
    //longSize - 1 - i gives us current position on output (LSB perspective)
    result = result | ((string[i] & ONE) << (longSize - 1 - i));
  }
  return result;
}
