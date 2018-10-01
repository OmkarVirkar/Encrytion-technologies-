/*Algorithm DSA
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

public class DSA_MILLER
 {

   int primeCenterie = 20;
   BigInteger q;
   BigInteger p;
   BigInteger g;
   BigInteger y;
   BigInteger x;
   BigInteger k;
   Random rand = new Random();


DSA_MILLER() 
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
    } while (x.compareTo(BigInteger.ZERO) != 1 && x.compareTo(q) != -1);
    
    y = g.modPow(x, p);

	
	System.out.println("\nValue of Q : "+q);
	System.out.println("\nValue of P : "+p);
	System.out.println("\nValue of G : "+g);
	System.out.println("\nValue of X : "+x);
	System.out.println("\nValue of Y : "+y);
	System.out.println("\nValue of K : "+k);
//-------------------------------------------------------MILLER RABIN CALLS()
// Testing of number p
		if (MillerRabin(p, 50))
		{
			System.out.println("Number P [ :" + p + " ]is Prime");
		}
		else
		{
			System.out.println("Number  P [ " + p + " ] is Composite");
		}

		// Testing of number q
		if (MillerRabin(p, 50))
		{
			System.out.println("Number Q [ :" + q +" ] is Prime");
		}
		else
		{
			System.out.println("Number  Q [ " + q + " ] is Composite");
		}
//--------------------------------------------------






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
    } while (!pTemp.isProbablePrime(primeCenterie) || pTemp.bitLength() != l);
  
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
        Logger.getLogger(DSA_MILLER.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(DSA_MILLER.class.getName()).log(Level.SEVERE, null, ex);
    }//catch
    
   return v.compareTo(r) == 0;
} //verify


//-----------------------------------------PRIMALITY TESTING------------------------------------
public static boolean FermatTest(BigInteger n, Random r) {
		
		BigInteger temp = BigInteger.ZERO;
		do {
			temp = new BigInteger(n.bitLength()-1, r);
		} while (temp.compareTo(BigInteger.ONE) <= 0);
		
		BigInteger ans = temp.modPow(n.subtract(BigInteger.ONE), n);
		
		return (ans.equals(BigInteger.ONE));
	}
	
	private static boolean MyMillerRabin(BigInteger n, Random r) {
		
		BigInteger temp = BigInteger.ZERO;
		do {
			temp = new BigInteger(n.bitLength()-1, r);
		} while (temp.compareTo(BigInteger.ONE) <= 0);
		
		if (!n.gcd(temp).equals(BigInteger.ONE)) return false;
				
		BigInteger base = n.subtract(BigInteger.ONE);
		BigInteger TWO = new BigInteger("2");
		
		int k=0;
		while ( (base.mod(TWO)).equals(BigInteger.ZERO)) {
			base = base.divide(TWO);
			k++;
		}
				
		BigInteger curValue = temp.modPow(base,n);
		
		if (curValue.equals(BigInteger.ONE))
			return true;
			
		for (int i=0; i<k; i++) {
			
			if (curValue.equals(n.subtract(BigInteger.ONE)))
				return true;
				
			else
				curValue = curValue.modPow(TWO, n);
		}

		return false;
	}
	
	public static boolean MillerRabin(BigInteger n, int numTimes) {
		
		Random r = new Random();
		
		for (int i=0; i<numTimes; i++) 
			if (!MyMillerRabin(n,r)) return false;
		return true;
	}

//---------------------------------PRIMALITY TESTING-----------------------------




public static void main(String[] as)
{
	DSA_MILLER obj = new DSA_MILLER();
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

		
		

}//main()

}//class

/***********************output*******************************************************
[root@rahul DM]# javac DSA_MILLER.java 
[root@rahul DM]# java DSA_MILLER 

Value of Q : 1161780946180818354063950744246064897100660982549

Value of P : 882382730918989757431826752307037851709702660871291620454759557169275698649770243459139305422199692781520396728861202411929
0301445518882479311824997068193

Value of G : 841973552163992824053961756845203457700337541970516720739505087071930541721348299533966371218938880462944214948658582423797
665154654488822815965549665514

Value of X : 71756768680419342349651

Value of Y : 58004834532442461381895407702927893615322118923360015398231226836144453046948017816066063045178467282693162093899492411801628332745406792
72067523570013043

Value of K : 239407064475669609382235734804431326857624830842
Number P [ :88238273091898975743182675230703785170970266087129162045475955716927569864977024345913930542219969278152039672886120241192903014455188
82479311824997068193 ]is Prime
Number Q [ :1161780946180818354063950744246064897100660982549 ] is Prime

Value of R : 1007283662162143264377725963441974475294351773777

Enter message 
Rahul Bankar 9822472959

Value of S : 475252227160590041796673213163849869207875708374

Value of V : 1007283662162143264377725963441974475294351773777

Verified
[root@rahul DM]# 

**********************************************************************************************************************/

