//receiver
package mesaagesha1;
import java.io.*;
import java.net.*;

public class Receiver 
{
	private static Socket socket;
 	public static void main(String[] args)
	{
		try
		{
 			int port = 25000;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port 25000");
 		
			while(true)
			{
				//Reading the message from the client
				socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String Msg = br.readLine();
				System.out.println("Message received  "+Msg);
 			}
		}

        	catch (Exception e)
		{
		}
		
		finally
		{
			try
			{
				socket.close();
			}
			
			catch(Exception e)
			{
			}
		}
	}//main   
}//class
