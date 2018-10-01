/* Algorithm 
*/
import java.util.Scanner;
import java.util.Random;
import java.math.BigInteger;

public class mil
{
	public boolean isPrime(long n, int iteration)
	{
		if (n == 0 || n == 1)	/** base case **/
		return false;
	
		if (n == 2)	/** base case - 2 is prime **/
		return true;

		if (n % 2 == 0)	/** an even number other than 2 is composite **/
		return false;
		
		long s = n - 1;
		while (s % 2 == 0)
		s /= 2;
		
		Random rand = new Random();
		for (int i = 0; i < iteration; i++)
		{
			long r = Math.abs(rand.nextLong());            
			long a = r % (n - 1) + 1, temp = s;
			long mod = modPow(a, temp, n);
			while (temp != n - 1 && mod != 1 && mod != n - 1)
			{
				mod = mulMod(mod, mod, n);
				temp *= 2;
			}
		if (mod != n - 1 && temp % 2 == 0)
		return false;
		}
	return true;        

	}

	public long modPow(long a, long b, long c)     /** Function to calculate (a ^ b) % c **/
	{
		long res = 1;
		for (int i = 0; i < b; i++)
		{
			res *= a;
			res %= c; 
		}
		return res % c;
	}

	public long mulMod(long a, long b, long mod) /** Function to calculate (a * b) % c **/
	{
		return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
	}
	
	public static void main (String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Miller Rabin Primality Algorithm Test\n");
		
		mil mr = new mil();
	
		System.out.println("Enter number\n");
		long num = scan.nextLong();
	
	        boolean prime = mr.isPrime(num, 50);
		if (prime)
		{
			System.out.println("\n"+ num +" is prime");
		}
        	else
		{
			System.out.println("\n"+ num +" is composite");
		}

	}//main
}//class

/********************************************OUTPUT**************************************************
[root@rahul PR]# javac mil.java 
[root@rahul PR]# java mil 
Miller Rabin Primality Algorithm Test

Enter number

3307

3307 is prime
[root@rahul PR]# java mil 
Miller Rabin Primality Algorithm Test

Enter number

4717

4717 is composite
[root@rahul PR]# 

******************************************************************************************/
