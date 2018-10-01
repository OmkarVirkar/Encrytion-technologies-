/*
Algorithm
1. Find 5*5 Matrix i.e. Key Matrix
2. if two char same insert filler char x
3. if same row replace each letter to right
4. if same column replace each letter to below
5. if different row and column take square of it  
*/

import java.util.Scanner;
public class playfairencry
{
	private String KeyWord = new String();
	private String Key = new String();
	private char matrix_arr[][] = new char[5][5];

 	public void setKey(String k)
	{
		String K_adjust = new String();
		boolean flag = false;
		K_adjust = K_adjust + k.charAt(0);
	
		for (int i = 1; i < k.length(); i++)
		{
			for (int j = 0; j < K_adjust.length(); j++)
			{
				if (k.charAt(i) == K_adjust.charAt(j))
				{
					flag = true;
				}//if
			}//inner for

			if (flag == false)
			K_adjust = K_adjust + k.charAt(i);
			flag = false;
		}//outer for
		KeyWord = K_adjust;
	}//setkey()
	
	public void KeyGen()
	{
		boolean flag = true;
		char current;
		Key = KeyWord;
		
		for (int i = 0; i < 26; i++)
		{
			current = (char) (i + 97);
			if (current == 'j')
			continue;
	
			for (int j = 0; j < KeyWord.length(); j++)
			{
				if (current == KeyWord.charAt(j))
				{
					flag = false;
					break;
				}//if
			}//for
			
			if (flag)
			Key = Key + current;
			flag = true;
		}//outer for
		
		System.out.println(Key);

		matrix();	//matrix() call

	}//keygen()

	private void matrix()
	{
		int counter = 0;
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				matrix_arr[i][j] = Key.charAt(counter);
				System.out.print(matrix_arr[i][j] + " ");
				counter++;
			}//inner for
		
		System.out.println();
		}//outer for
	}//matrix
	
	private String format(String old_text)
	{
		int i = 0;
		int len = 0;
		String text = new String();
		
		len = old_text.length();
		for (int tmp = 0; tmp < len; tmp++)
		{
			if (old_text.charAt(tmp) == 'j')
			{
				text = text + 'i';
			}//if
			else
			text = text + old_text.charAt(tmp);
		}//for
		len = text.length();
		
		for (i = 0; i < len; i = i + 2)
		{
			if (text.charAt(i + 1) == text.charAt(i))
			{
				text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
			}//if
		}//for
		return text;
	}//format()

	private String[] Divid2Pairs(String new_string)
	{
		String Original = format(new_string);
		int size = Original.length();
	
		if (size % 2 != 0)
		{
			size++;
			Original = Original + 'x';
		}//if
				
		String x[] = new String[size / 2];
		
		int counter = 0;
		for (int i = 0; i < size / 2; i++)
		{	
			x[i] = Original.substring(counter, counter + 2);
			counter = counter + 2;
		}//for
		return x;
	}//Divide2Pairs()

	public int[] GetDiminsions(char letter)
	{
		int[] key = new int[2];
		if (letter == 'j')
		letter = 'i';
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (matrix_arr[i][j] == letter)
				{
					key[0] = i;
					key[1] = j;
					break;
				}//if
			}//for
		}//outer for
		return key;
	}//GetDimension()

	public String encryptMessage(String Source)
	{
		String src_arr[] = Divid2Pairs(Source);
		String Code = new String();
		char one;	
		char two;
		int part1[] = new int[2];
		int part2[] = new int[2];
		for (int i = 0; i < src_arr.length; i++)
		{
			one = src_arr[i].charAt(0);
			two = src_arr[i].charAt(1);
			part1 = GetDiminsions(one);
			part2 = GetDiminsions(two);
		
			if (part1[0] == part2[0])
			{
				if (part1[1] < 4)
				part1[1]++;
				else
				part1[1] = 0;
			
				if (part2[1] < 4)
				part2[1]++;
				else
				part2[1] = 0;
			}//if

            		else if (part1[1] == part2[1])
			{
				if (part1[0] < 4)
				part1[0]++;
				else
				part1[0] = 0;
				
				if (part2[0] < 4)
				part2[0]++;
				else
				part2[0] = 0;
			}//elseif
		
			else
			{
				int temp = part1[1];
				part1[1] = part2[1];
				part2[1] = temp;
			}//else
		
		Code = Code + matrix_arr[part1[0]][part1[1]] + matrix_arr[part2[0]][part2[1]];
		}//for
	
		return Code;
	}//encryptMessage()

 

	public static void main(String[] args)
	{
		playfairencry x = new playfairencry();
		Scanner sc = new Scanner(System.in);

        	System.out.println("Enter a keyword:");
		String keyword = sc.next();

        	x.setKey(keyword);
		x.KeyGen();
		
		System.out.println("Enter word to encrypt: (Make sure length of message is even)");
		String key_input = sc.next();

		if (key_input.length() % 2 == 0)
		{
			System.out.println("Encryption: " + x.encryptMessage(key_input));
		}//if
		else
		{
			System.out.println("Message length should be even");
		}//else
	sc.close();
	}//main();

}//class();

/*********************************************OUTPUT***************************************************************
[root@rahul PE]# javac playfairencry.java 
[root@rahul PE]# java playfairencry 
Enter a keyword:
andarsul
andrsulbcefghikmopqtvwxyz
a n d r s 
u l b c e 
f g h i k 
m o p q t 
v w x y z 
Enter word to encrypt: (Make sure length of message is even)


mynameisrahulban
Encryption: qvdntukrsnfbbcnd
[root@rahul PE]# 

****************************************************************************************************************/
