import java.io.PrintWriter;


public class ShiftCipher
{
	static int aPosition = 65;
	static int numLetters = 26;
	
	public ShiftCipher(String input)
	{
		System.out.println(input);
		
		String fileName = "shift_decrypts.txt";
		printPossibleDecryptsToFile(input, fileName);
		System.out.println("Done");
		System.out.println(input.length());
	
	}
	
	private static void printPossibleDecryptsToFile(String input, String fileName) 
	{
		PrintWriter writer = null;
		try 
		{
			writer = new PrintWriter(fileName, "UTF-8");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i < numLetters; i++)
		{
			String decrypt =  getDecryptedString(input, i);
			if(!decrypt.equals(""))
			{
				writer.println(decrypt + " Shift: " + i);
			}
			
				
		}
		writer.close();		
	}
	
	private static String getDecryptedString(String input, int shiftOffset) 
	{
		String result = "";
		int eCount = 0;		
		int aCount = 0;
		int tCount = 0;
		for(int i = 0; i < input.length(); i++)
		{
			char newChar = getNewChar(input.charAt(i), shiftOffset);
			result += newChar;
			if(newChar == 'Z')
			{
				aCount++;
			}
			else if(newChar == 'E')
			{
				eCount++;
			}
			else if(newChar == 'T')
			{
				tCount++;
			}
			
		}
		
		if(aCount > 0)
//		if(aCount + eCount + tCount < 11)
		{
			result = "";
		}
		
		return result;
	}
	
	private static char getNewChar(char oldChar, int shiftOffset) 
	{
		int position = oldChar - aPosition;
		int newPosition = (position + shiftOffset) % numLetters;
		return (char) (newPosition + aPosition);
	}
}
