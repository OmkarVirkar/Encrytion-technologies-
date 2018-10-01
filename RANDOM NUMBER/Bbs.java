/*
p = 9887
q = 1723
relative prime : 4559

Algorithm 
1. select large prime number p and q									p = 7, q = 11
2. [ p-3 mod 4 = 0 ] and [ q-3 mod 4 =0 ]								[7-3 mod 4] = 0 and [11-3 mod 4] = 0
3. select n i.e. relative prime where n = p*q								n = p * q = 7 * 11 = 77
4. select random number s where GCD( s, n ) = 1 							GCD( 50, 77 ) = 1
5. x0 = ( s ^ 2 ) mod n  										50^2 mod 77 = 36
6. for i =1 to infinity do										
7. xi = ( xi - 1 ) ^ 2 mod n upto infinity								36^2 mod 77 = 64  64^2 mod 77 = 15  
													15^2 mod 77 = 71  71^2 mod 77 = 36 cycle 														generated
8. take reulted xi for input to xi - 1 at step 7 again and again and generate new random number xi 
*/

import java.io.*;
public class Bbs
{
	public static void main(String args[])throws Exception
	{
		int p,q,n,m,c,d;
		int x[]=new int[100];
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enetr prime number for p");
		p=Integer.parseInt(br.readLine());
		System.out.println("Enetr prime number for q");
		q=Integer.parseInt(br.readLine());	
		
		n=(p-3)%4;
		m=(q-3)%4;
		if(n==0 &&m==0)
		{
			System.out.println("Selecting relative prime number");
			System.out.println("By entering new prime number");
			d=Integer.parseInt(br.readLine());
			c=p*q;	
			System.out.println("C="+c);
			x[0]=(d*d) % c;
			for(int i=1;i<10;i++)
			{
				x[i]=(x[i-1]*x[i-1]) % c;	
			}
			for(int i=0;i<10;i++)
			{
				System.out.println("Random number "+Math.abs(x[i]));		
			}	
		}
	}
	
}

/**************************************OUTPUT*********************************************
[root@rahul RN]# javac Bbs.java 
[root@rahul RN]# java Bbs 
Enetr prime number for p
9887
Enetr prime number for q
1723
Selecting relative prime number
By entering new prime number
4559
C=17035301
Random number 3749180
Random number 4063445
Random number 10416800
Random number 16049659
Random number 4603029
Random number 1644526
Random number 807724
Random number 8121592
Random number 11304996
Random number 13817130
[root@rahul RN]# 

***************************************************************************************/
