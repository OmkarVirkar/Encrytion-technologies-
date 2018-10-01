/*
Algorithm
1. CT = ( PT + KEY ) MOD 26
2. PT = ( CT - KEY ) MOD 26
*/


import java.io.*;
import java.util.Scanner;
import java.lang.*;

public class ceaser
{
	
	public static void main(String[] as) throws IOException
	{

		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   		int i;
		Scanner sc = new Scanner(System.in);
		String text = new String();
		String p_text = new String();
		String c_text = new String();		
	
		System.out.println("\nEnter the Text to be encrypt");
		text = sc.nextLine();
		int key = 0;

		System.out.println("\nEnter Key i.e. integer value less than 52 ");
		key = sc.nextInt();
		p_text = text;

//Encryption 
		for( i = 0; i < text.length(); i++ )
		{

                    int charPosition = str.indexOf(p_text.charAt(i));

	            int keyVal = (key + charPosition) % 52;

		    char replaceVal = str.charAt(keyVal);

	            c_text = c_text + replaceVal;

		}
	
		System.out.println("\nText before encryption is : "+text);
		System.out.println("\n Key is : "+key);			
		System.out.println("\nYour Cipher Text is : "+c_text);
//Encryption End

//DEcryption
	
		String p1_text = new String();

		for ( i = 0; i < c_text.length(); i++)
		{

			int charPosition = str.indexOf(c_text.charAt(i));

			int keyVal = (charPosition-key) % 52;

			if (keyVal < 0)
			{

				keyVal = str.length() + keyVal;
			}

			char replaceVal = str.charAt(keyVal);

			p1_text = p1_text + replaceVal;
		
		}

		System.out.println("\nYour Plain Text is : "+p1_text);

//Decryption End

	}//main
}//class

/***************************************OUTPUT*********************************
[root@rahul PE]# javac ceaser.java 
[root@rahul PE]# java ceaser 

Enter the Text to be encrypt
HiIamRahul

Enter Key i.e. integer value less than 52 
43

Text before encryption is : HiIamRahul

 Key is : 43

Your Cipher Text is : yZzRdIRYlc

Your Plain Text is : HiIamRahul
[root@rahul PE]# 
*********************************************************************************/
