import java.util.*;
import java.io.*;
import helperPack.*;

public class Calculator{

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

    public static void main(String args[]   ){
        Scanner sc = new Scanner(System.in);

        Calculator c = new Calculator();
        char ch;
        private String exp = new String();
        while(true){
        System.out.println("Enter Expression:");
        exp = sc.nextLine();

        int result = c.cal(exp);
        System.out.println("Result: " + result + "\nContinue (Y/N)?");
        ch = sc.nextLine().charAt(0);
        if(ch == 'n' || ch == 'N')
            break;
        }
    }
}