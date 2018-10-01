//sender
package mesaagesha1;
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;

public class Sender 
{
	private static Socket socket;
	public static void main(String args[])
	{
		try
		{
			String host = "localhost";
			int port = 25000;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);
 
			//Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			BufferedReader brr=new BufferedReader(new InputStreamReader(System.in));
			String s= brr.readLine();
			String sendMessage;
            
			try 
			{
				String encryMsg = MesaageSha1.SHA1(s);
				sendMessage = String.valueOf(encryMsg) + "\n";
				bw.write(sendMessage);
				bw.flush();
				System.out.println("Message sent : " + sendMessage);
			}//try
			catch (UnsupportedEncodingException e) 
			{
				System.out.println(e);
			}//catch
            
			//Get the return message from the server
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			System.out.println("Message received  : " +message);
        	}//try
	
		catch (IOException | NoSuchAlgorithmException exception)	
		{
		}//catch
       
		finally
		{
			//Closing the socket
			try
			{
				socket.close();
			}//try
			
			catch(Exception e)
			{
				e.printStackTrace();
			}//catch
		}//finally
	}//main
}//class
