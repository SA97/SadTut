import java.net.*;
import java.io.*;
import helperPack.*;
import java.util.*;
 
public class Server
{

    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    private DataOutputStream out = null;
 

    public Server(int port)
    {

        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
 

            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());
 
            String line = "";
 

            while (true)
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);
                    if(line.equals("Over") || line.equals("over"))
                        break;
                    int result = cal(line);
                    System.out.println(result);
                    out.writeUTF(Integer.toString(result));
 
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
 

            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public int cal(String exp){
        char[] tokens = exp.toCharArray();

        Stack<Integer> val = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();

        for(int i=0;i<tokens.length;i++){
            if(tokens[i] == ' ')
                continue;

            if(tokens[i] >= '0' && tokens[i] <= '9'){
                StringBuffer sbuf = new StringBuffer();
                while(i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                val.push(Integer.parseInt(sbuf.toString()));
            }

            else if(tokens[i] == '(')
                ops.push(tokens[i]);

            else if(tokens[i] == ')'){
                while(ops.peek() != '(')
                    val.push(applyop(ops.pop(), val.pop(), val.pop()));
                ops.pop();
            }

            else if(tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/'){
                while(!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    val.push(applyop(ops.pop(), val.pop(), val.pop()));
                ops.push(tokens[i]);
            }
        }
        while(!ops.empty())
            val.push(applyop(ops.pop(), val.pop(), val.pop()));

        return val.pop();
    }

    public boolean hasPrecedence(char op1, char op2){
        if(op2 == '(' || op2 == ')')
            return false;
        if((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static int applyop(char op, int b, int a){
        Add ad = new Add();
        Sub sb = new Sub();
        Mul ml = new Mul();
        Div dv = new Div();
        switch(op){
            case '+': return ad.add(a,b);
            case '-': return sb.sub(a,b);
            case '*': return ml.mul(a,b);
            case '/': if(b == 0)
                        throw new UnsupportedOperationException("Cannot divide by zero");
                      return dv.div(a,b);
        }
        return 0;
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}
