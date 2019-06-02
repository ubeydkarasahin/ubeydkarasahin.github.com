import java.io.*;
import java.util.StringTokenizer;

public class Scanner {
    //KEYWORDS TABLE
    int INT_LIT = 10; //Integer
    int ID = 11; //ID(Character)
    int ADD_OP = 20; // Addition + Operation
    int MNS_OP = 21; // Subtraction - Operation
    int MULT_OP = 22; // Multiplication * Operation
    int DIV_OP = 23; // Division / Operation
    int GRT_OP = 40; // Greater than > Operation
    int GRTEQ_OP = 41; // Greater than and equal to >= Operation
    int LSS_OP = 42; // Less than < Operation
    int LSSEQ_OP = 43; // Less than and equal to <= Operation
    int EQL_OP = 44; // Equal = Operation
    int DUBEQ_OP = 45; // Double equal == Operation
    int NTEQ_OP = 46; // Not equal ~= Operation
    int LPAR_OP = 47; // Left parentheses ( Operation
    int RPAR_OP = 48; // Right parentheses ) Operation
    int PRT_OP = 30; // Print Operation
    int END_OP = 31; // End Operation
    int IF_OP = 32; // If Operation
    int ELS_OP = 33; // Else Operation
    int FNC_OP = 34; // Function Operation
    int THN_OP = 35; // Then Operation
    int RTN_OP = 36; // Repeat Operation
    int scnLines = 0; // Shows which line reader at
    int nextToken = 0; // Index of token
    int count = 0; // Counter for identifier table
    int[] tokenlist = new int[100]; // Token list for identifier table
    int[] scndLines = new int[100]; // Scanned lines for identifier table
    String[] lexemelist = new String[100]; //Lexemes for identifier table
    String lexeme = null;

    //This function checks the read string if it is integer or not
    public static boolean checkMe(String s) {
        boolean amIValid = false;
        try {
            // Checks whether s is a valid integer
            Integer.parseInt(s);
            amIValid = true;
        } catch (NumberFormatException ex) {
            //If it is not an integer, function moves on
        }
        return amIValid;
    }

    public void lexAnalyzer (String fileName){
        FileReader reader = null; // Reader for the file
        try {
            reader = new FileReader( fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader( reader );
        String str = null;
        //The method for reading the file token by token
        StringTokenizer tok;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                tok = new StringTokenizer( str );
                while (tok.hasMoreTokens()) {
                    lookUp( tok.nextToken() );
                    count++;
                }
                scnLines++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println();
        //System.out.println("The scanned lines are : " + scnLines);
    }

    //Lexical Analyzer function, checks tokens
    public void lookUp(String x){
        //Checks the integer tokens
        if (checkMe(x)) {
            nextToken = INT_LIT;
            lexeme = x;
        }
        // Checks IDs(characters) and one character operators
        else if (x.length() == 1) {
            char c = x.charAt(0);
            if (Character.isLetter(c)) {
                nextToken = ID;
                lexeme = String.valueOf( c );
            }
            switch (c) {
                case '-':
                    nextToken = MNS_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '+':
                    nextToken = ADD_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '*':
                    nextToken = MULT_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '/':
                    nextToken = DIV_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '>':
                    nextToken = GRT_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '<':
                    nextToken = LSS_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '(':
                    nextToken = LPAR_OP;
                    lexeme = String.valueOf( c );
                    break;
                case ')':
                    nextToken = RPAR_OP;
                    lexeme = String.valueOf( c );
                    break;
                case '=':
                    nextToken = EQL_OP;
                    lexeme = String.valueOf( c );
                    break;
            }
        }
        //Checks more than one character operators
        else {
            switch (x) {
                case "==":
                    nextToken = DUBEQ_OP;
                    lexeme = x;
                    break;
                case "~=":
                    nextToken = NTEQ_OP;
                    lexeme = x;
                    break;
                case ">=":
                    nextToken = GRTEQ_OP;
                    lexeme = x;
                    break;
                case "<=":
                    nextToken = LSSEQ_OP;
                    lexeme = x;
                    break;
                case "print":
                    nextToken = PRT_OP;
                    lexeme = x;
                    break;
                case "function":
                    nextToken = FNC_OP;
                    lexeme = x;
                    break;
                case "else":
                    nextToken = ELS_OP;
                    lexeme = x;
                    break;
                case "if":
                    nextToken = IF_OP;
                    lexeme = x;
                    break;
                case "then":
                    nextToken = THN_OP;
                    lexeme = x;
                    break;
                case "end":
                    nextToken = END_OP;
                    lexeme = x;
                    break;
                case "return":
                    nextToken = RTN_OP;
                    lexeme = x;
                    break;
            }
        }
        //Storing the checked token in arrays as an identifier table
        tokenlist[count] = nextToken;
        lexemelist[count] = lexeme;
        scndLines[count] = scnLines;
        //System.out.println("Next token is: " +  nextToken + "  Next lexeme is: " + lexeme);
    }
}
