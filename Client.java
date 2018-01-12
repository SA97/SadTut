import java.net.*;
import java.io.*;
 
public class Client
{
    
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private DataInputStream in       = null;
 
    
    public Client(String address, int port)
    {
    
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
 
    
            input  = new DataInputStream(System.in);
 
    
            out    = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
 
    
        String line = "";
        char ch;
    
        while(true)
        {
            try
            {   
                System.out.println("Enter Expression...");
                line = input.readLine();
                out.writeUTF(line);
                line = in.readUTF();
                System.out.println(line + "\nContinue (Y/N)?:");
                ch = input.readLine().charAt(0);
                if(ch == "No" || ch == "no")
                    out.write("Over");
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
 
    
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}
