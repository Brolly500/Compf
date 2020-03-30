//Стековый компилятор формул.
public class Compf extends Stack{
    //Типы символов (скобки, знаки операций, иное).
    boolean beginOther;
    protected final static int SYM_LEFT = 0,
            SYM_RIGHT = 1,
            SYM_QL = 2,
            SYM_QR = 3,
            SYM_OPER = 4,
            SYM_OTHER = 5;

/*
public Compf(){
//
}
*/

    private int symType(char c){
        switch(c){
            case '(':
                return SYM_LEFT;
            case ')':
                return SYM_RIGHT;
            case '[':
                return SYM_QL;
            case ']':
                return SYM_QR;
            case '+':
            case '-':
            case '*':
            case '/':
                return SYM_OPER;
            default:
                return symOther(c);
        }
    }

    private void processSymbol(char c){
        switch(symType(c)){
            case SYM_LEFT:
                push(c);
                beginOther = false;
                break;
            case SYM_RIGHT:
                processSuspendedSymbols(c);
                pop();
                beginOther = false;
                break;
            case SYM_QL:
                push(c);
                beginOther = false;
                break;
            case SYM_QR:
                processSuspendedSymbolss(c);
                pop();
                beginOther = false;
                break;
            case SYM_OPER:
                processSuspendedSymbols(c);
                push(c);
                beginOther = false;
                break;
            case SYM_OTHER:
                nextOther(c);
                break;
        }
    }

    protected void precede(){

    }

    private void processSuspendedSymbolss(char c){
        while(precedess(top(), c))
            nextOper(pop());
        if(!precedess(top(), c))
            qwer();
    }

    protected void qwer(){
    }

    private boolean precedess(char a, char b){
        if (symType(a) == SYM_QL || symType(a) == SYM_LEFT) return false;
        if (symType(b) == SYM_QR) return true;
        return priority(a) >= priority(b);
    }

    private void processSuspendedSymbols(char c){
        while(precedes(top(), c)){
            char a=pop();
            if(a=='-'&&top()=='('){
                precede();
            }else{
                nextOper(a);
            }

        }
    }


    private int priority(char c){
        return c == '+' || c == '-' ? 1 : 2;
    }

    private boolean precedes(char a, char b){
        if(symType(a) == SYM_LEFT||symType(a) == SYM_QL) return false;
        if(symType(b) == SYM_RIGHT) return true;
        return priority(a) >= priority(b);
    }

    protected int symOther(char c){
        if (c < 'a' || c > 'z'){
            System.out.println("Недопустимый символ: " + c);
            System.exit(0);
        }

        return SYM_OTHER;
    }

    protected void nextOper(char c){
        System.out.print("" + c + " ");
    }

    protected void nextOther(char c){
        nextOper(c);
    }

    public void compile(char[] str){
        processSymbol('(');

        for(int i = 0; i < str.length; i++)
            processSymbol(str[i]);

        processSymbol(')');

        System.out.print("\n");
    }
}