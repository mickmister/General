import java.io.PrintWriter;


public class AffineCipher
{
	static int[] alphaValues = new int[] {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
	static int[] betaValues = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
	static int aPosition = 65;
	static int numLetters = 26;

	public AffineCipher()
	{
		String input = "oborrybgkyxngtyeorybgkyxngtybvggv".toUpperCase();
		
		//System.out.println('B');
		
		printPossibleDecryptsToFile(input);
		System.out.println("Done");

	}

	private static void printPossibleDecryptsToFile(String input) 
	{
		PrintWriter writer = null;
		try 
		{
			writer = new PrintWriter("afffine_decrypts.txt", "UTF-8");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i < alphaValues.length; i++)
		{
			for(int j = 0; j < betaValues.length; j++)
			{
				int alpha = alphaValues[i];
				int beta = betaValues[j];
				String decrypt =  getDecryptedString(input, alpha, beta);
				if(!decrypt.equals("") && alpha >= 13)
				{
					writer.println(decrypt + " A: " + alpha + " B: " + beta);
				}				
			}
		}
		writer.close();		
	}

	private static String getDecryptedString(String input, int alpha, int beta) 
	{
		String result = "";
		int eCount = 0;
		int aCount = 0;
		int tCount = 0;
		boolean iFound = false;
		boolean itFound = false;
		for(int i = 0; i < input.length(); i++)
		{
			char newChar = getNewChar(input.charAt(i), alpha, beta);
			result += newChar;
			if(newChar == 'I')
			{
				iFound = true;
			}
			else if(iFound && newChar == 'S')
			{
				itFound = true;
				iFound = false;
			}
			else
			{
				iFound = false;
			}
			
		}

		if(!itFound)
		{
			result = "";
		}
		return result;
	}

	private static char getNewChar(char oldChar, int alpha, int beta) 
	{
		int position = oldChar - aPosition;
		int newPosition = (position * alpha + beta) % numLetters;
		return (char) (newPosition + aPosition);
	}
}
