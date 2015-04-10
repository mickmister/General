package Main;

public class Permutations 
{
	static int[] initialPermutation = new int[] 
	{
		57,	49,	41,	33,	25,	17,	9,	1,	59,	51,	43,	35,	27,	19,	11,	3,
		61,	53,	45,	37,	29,	21,	13,	5,	63,	55,	47,	39,	31,	23,	15,	7,
		56,	48,	40,	32,	24,	16,	8,	0,	58,	50,	42,	34,	26,	18,	10,	2,
		60,	52,	44,	36,	28,	20,	12,	4,	62,	54,	46,	38,	30,	22,	14,	6
	};
	
	static int[] initialPermutationInverse = new int[]
	{
		39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30,
		37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28,
		35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26,
		33, 1, 41,  9, 49, 17, 57, 25, 32, 0, 40,  8, 48, 16, 56, 24 
	};
	
	static int[] keyPermutationInitial = new int[]
	{
		49,	42,	35,	28,	21,	14,	7,	0,	50,	43,	36,	29,	22,	15,
		8,	1,	51,	44,	37,	30,	23,	16,	9,	2,	52,	45,	38,	31,
		55,	48,	41,	34,	27,	20,	13,	6,	54,	47,	40,	33,	26,	19,
		12,	5,	53,	46,	39,	32,	25,	18,	11,	4,	24,	17,	10,	3
	};
	
	static int[] keyPermutationFinal = new int[]
	{
		13,	16,	10,	23,	0,	4,	2,	27,	14,	5,	20,	9,
		22,	18,	11,	3,	25,	7,	15,	6,	26,	19,	12,	1,
		40,	51,	30,	36,	46,	54,	29,	39,	50,	44,	32,	47,
		43,	48,	38,	55,	33,	52,	45,	41,	49,	35,	28,	31
	};
	
	static int[] sboxFinalPermutation = new int[]
	{
		15,	6,	19,	20,	28,	11,	27,	16,	0,	14,	22,	25,	4,	17,	30,	9,
		1,	7,	23,	13,	31,	26,	2,	8,	18,	12,	29,	5,	21,	10,	3,	24
	};
	
	static String performPermutation(String toPermutate, int[] pattern)
	{
		StringBuilder builder = new StringBuilder();
		int length = pattern.length;
		for(int i = 0; i < length; i++)
		{
			int index = pattern[i];
			builder.append(toPermutate.charAt(index));
		}
		return builder.toString();
	}
	
	static String performInitialPermutation(String input)
	{
		return performPermutation(input, initialPermutation);
	}
	
	static String performInitialPermutationInverse(String input)
	{
		return performPermutation(input, initialPermutationInverse);
	}
	
	static String performKeyPermutationInitial(String key)
	{
		return performPermutation(key, keyPermutationInitial);
	}
	
	static String performKeyPermutationFinal(String key)
	{
		return performPermutation(key, keyPermutationFinal);
	}
	
	static String performSBoxFinalPermutation(String input)
	{
		return performPermutation(input, sboxFinalPermutation);
	}
	
}
