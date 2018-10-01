/*Algorithm 
1. Key generation
	1. Select prime number q, 160 bit long. Select prime number p, which is L-bit long 
	   such that 512 <= L <= 1024. p = q k + 1
	2. Select h where 1 < h < p-1 and g = h ^ k mod p > 1
	3. select Kpr x, 0 < x < q compute y = g ^ x mod p
	4. Kpb = (p, q, g, x)
2.Sign Generation
	1. select secret number k, 0 < k < q
	2. compute r and s
		r = ( g ^ k mod p ) mod q and	
		s = (k ^ -1( SHA-1(m) + x * r )) mod q
	3. if r == 0 or s == 0 reapeat above procedure
	4. DS for msg m is (r, s)
3.Sign verification
	1. compute w = (s ^ -1) mod q
	2. compute u1= (SHA-1(m) * w ) mod q
	3. compute u2 = ( r * w) mod q
	4. compute v = ( ( g ^ u1 * y ^ u2 ) mod p ) mod q
	5. if v == r then verified
*/	

import java.io.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class DSA
{

	int primeCenterie = 20;
	BigInteger q;
	BigInteger p;
	BigInteger g;
	BigInteger y;
	BigInteger x;
	BigInteger k;
	Random rand = new Random();

	DSA() 
	{
	} //constructor 

	public void generateKey() 
	{
		q = new BigInteger(160, primeCenterie, rand);
		p = generateP(q, 512);				//generateP() calls
		g = generateG(p, q);				//generateG() calls
		k = generateK(q); 					//generateK() calls
		do 
		{
			x = new BigInteger(q.bitCount(), rand);
		}while (x.compareTo(BigInteger.ZERO) != 1 && x.compareTo(q) != -1);
    
		y = g.modPow(x, p);

		System.out.println("\nValue of Q : "+q);
		System.out.println("\nValue of P : "+p);
		System.out.println("\nValue of G : "+g);
		System.out.println("\nValue of X : "+x);
		System.out.println("\nValue of Y : "+y);
		System.out.println("\nValue of K : "+k);
	}//generatekey();

	private BigInteger generateP(BigInteger q, int l) 
	{
		if (l % 64 != 0) 
		{
			throw new IllegalArgumentException("L value is wrong");
		}//if
    
		BigInteger pTemp;
		BigInteger pTemp2;
		do 
		{
			pTemp = new BigInteger(l, primeCenterie, rand);
			pTemp2 = pTemp.subtract(BigInteger.ONE);
			pTemp = pTemp.subtract(pTemp2.remainder(q));
		}while (!pTemp.isProbablePrime(primeCenterie) || pTemp.bitLength() != l);
	return pTemp;
	}//generate P

	private BigInteger generateG(BigInteger p, BigInteger q) 
	{
		BigInteger aux = p.subtract(BigInteger.ONE);
		BigInteger pow = aux.divide(q);
		BigInteger gTemp;
		do 
		{
			gTemp = new BigInteger(aux.bitLength(), rand);
		} while (gTemp.compareTo(aux) != -1 && gTemp.compareTo(BigInteger.ONE) != 1);
		return gTemp.modPow(pow, p);
	}//Generate G

	public BigInteger generateK(BigInteger q) 
	{
		BigInteger tempK;
		do 
		{
			tempK = new BigInteger(q.bitLength(), rand);
		} while (tempK.compareTo(q) != -1 && tempK.compareTo(BigInteger.ZERO) != 1);
	return tempK;
	}//Generate K

	public BigInteger generateR() 
	{
		k = generateK(q);
		BigInteger r = g.modPow(k, p).mod(q);
		return r;
	}//Generate R

	public BigInteger generateS(BigInteger r, byte[] data) 
	{
		MessageDigest md;
		BigInteger s = BigInteger.ONE;
		try 
		{
			md = MessageDigest.getInstance("SHA-1");
			md.update(data);
			BigInteger hash = new BigInteger(md.digest());
			s = (k.modInverse(q).multiply(hash.add(x.multiply(r)))).mod(q);
		}//try
		catch (NoSuchAlgorithmException ex) 
		{
			Logger.getLogger(DSA.class.getName()).log(Level.SEVERE, null, ex);
		}//catch
		return s;
	}//generate S

	boolean verify(byte[] data, BigInteger r, BigInteger s) 
	{
		if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(q) >= 0) 
		{
			return false;
		}//if
		
		if (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(q) >= 0) 
		{
			return false;
		}//if
    		
		MessageDigest md;
		BigInteger v = BigInteger.ZERO;
		try 
		{
			md = MessageDigest.getInstance("SHA-1");
			md.update(data);
			BigInteger hash = new BigInteger(md.digest());
			BigInteger w = s.modInverse(q);
			BigInteger u1 = hash.multiply(w).mod(q);
			BigInteger u2 = r.multiply(w).mod(q);
			v = ((g.modPow(u1, p).multiply(y.modPow(u2, p))).mod(p)).mod(q);
			System.out.println("\nValue of V : "+v);	
		}//try
		catch (NoSuchAlgorithmException ex) 
		{
			Logger.getLogger(DSA.class.getName()).log(Level.SEVERE, null, ex);
		}//catch
    		return v.compareTo(r) == 0;
	} //verify

	public static void main(String[] as)
	{
		DSA obj = new DSA();
		obj.generateKey();
	
		BigInteger r = obj.generateR();
		System.out.println("\nValue of R : "+r);
	
		System.out.println("\nEnter message ");	
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		byte[] by = str.getBytes();
	
		BigInteger s = obj.generateS(r,by);
		System.out.println("\nValue of S : "+s);
	
		if(obj.verify(by, r, s))
		{
			System.out.println("\nVerified");	
		}
		else
		{
			System.out.println("\nNot Verified");	
		}

	}//main

}//class

/************************************************output********************************************************************
[root@rahul DS]# javac DSA.java 
[root@rahul DS]# java DSA 

Value of Q : 783862039878032468091117449215694467072276991087

Value of P : 11685594692406571261774627287275232554188145212919277075738860561032017557648566638648907375708530965661318785719
509025513173058627945062092039788250671417

Value of G : 366195653892957513912864058944634665916179230361834704278280831492404837345830714467979736287803226057543021154331053030079443
1220378533620160776094244242

Value of X : 24876111662827858287812

Value of Y : 253807012214340957749381255260252395537067981931430774865797305338089873815793251196393416152888097744722838811151038351951091
3092411477945756371895044656

Value of K : 1415973162197281186657538125934214216830294917114

Value of R : 142764075199088725382942253709634850131196674011

Enter message 
Rahul ...

Value of S : 472847654551077693991735194317850012964418201074

Value of V : 142764075199088725382942253709634850131196674011

Verified
[root@rahul DS]# 

***********************************************************************************************************************************/
