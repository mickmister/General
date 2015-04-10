package Main;

public class ExpandAndXOR
{
	static int[] expandR = new int[]
	{
		31,	0,	1,	2,	3,	4,	3,	4,	5,	6,	7,	8,
		7,	8,	9,	10,	11,	12,	11,	12,	13,	14,	15,	16,
		15,	16,	17,	18,	19,	20,	19,	20,	21,	22,	23,	24,
		23,	24,	25,	26,	27,	28,	27,	28,	29,	30,	31,	0

	};
	
	static String performExpandRight(String rightString)
	{
		return Permutations.performPermutation(rightString, expandR);
	}
	
	static String xorBinaryStrings(String string1, String string2)
	{
		StringBuilder builder = new StringBuilder();
		int length = string1.length();
		for(int i = 0; i < length; i++)
		{
			if(string1.charAt(i) == string2.charAt(i))
			{
				builder.append('0');
			}
			else
			{
				builder.append('1');
			}
		}
		return builder.toString();
	}

}
