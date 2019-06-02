public class Parser {
    //Calling scanner
    Scanner sc;
    {
        sc = new Scanner();
        sc.lexAnalyzer( "lua.txt" );
    }

    //Creating identifiers
    int[] tList = sc.tokenlist;
    int[] lines = sc.scndLines;
    String[] lList = sc.lexemelist;
    int n=0;

    //Indexing the next token in array
    public void nextToken(){
        n++;
    }

    //Get the token from array
    public int getToken(){
        return tList[n];
    }

    //Get the line from array
    public int getLine(){
        return lines[n];
    }

    //Get the lexeme from array
    public String getLexeme(){
        return lList[n];
    }

    //Print token sentence
    public void printToken(){
        System.out.println("Next token is: " +  getToken() + "  Next lexeme is: " + getLexeme());
    }

    //Parsing program grammar
    public void program(){
        //If token is function operation, program starts
        if (getToken() == sc.FNC_OP) {
            printToken();
            System.out.println( "Enter <program>" );
            nextToken();
            //Id of the function comes, then function variable comes between left and right parantheses
            Id();
            if (getToken() == sc.LPAR_OP) {
                printToken();
                nextToken();
                Id();
                if (getToken() == sc.RPAR_OP) {
                    printToken();
                    System.out.println( "Enter <function>" );
                    System.out.println( "Enter <block>" );
                    //Enters to block which may contain statements
                    Block();
                    //If the token is finish or identifier table is empty, the program finishs
                    if (getToken() == sc.END_OP) {
                        printToken();
                        System.out.println( "Exit <program>" );
                    } else if (getToken() == 0) {
                        System.out.println( "Exit <function>" );
                        System.out.println( "Exit <program>" );
                    }
//Else give error which shows which line at
                } else error();
            } else error();
        }else error();
    }

    //Parsing block grammar
    public void Block(){
        nextToken();
        //If there exist a statement, get the next token
        while(Statement()){
            nextToken();
        }
    }

    //Parsing statement grammar
    public boolean Statement(){
        boolean statement;
        //If the token is if operator, enter the if statement
        if(getToken() == sc.IF_OP) {
            printToken();
            System.out.println("Enter <if_statement>");
            ifStatement();
            statement = true;
        }
        //If the token is print operator, enter the print statement
        else if(getToken() == sc.PRT_OP) {
            printToken();
            System.out.println("Enter <print_statement>");
            printStatement();
            statement = true;
        }
        //There is no statement exists
        else {
            statement = false;
        }
        return statement;
    }

    //Parsing if statement grammer
    public void ifStatement(){
        //Enter the boolean expression
        System.out.println("Enter <boolean_expression>");
        booleanExpression();
        nextToken();
        System.out.println("Exit <boolean_expression>");
        //If the next token is then operator, enter block grammar
        if (getToken() == sc.THN_OP){
            printToken();
            System.out.println("Enter <block>");
            Block();
            //If the next token is return operator, exit block grammar
            if(getToken() == sc.RTN_OP) {
                printToken();
                returnStatement();
                System.out.println("Exit <block>");
            }
            //If the next token is else operator, enter block grammar
            if (getToken() == sc.ELS_OP){
                elseStatement();
                Block();
                System.out.println("Enter <block>");
                //If the next token is return operator, exit block grammar
                if(getToken() == sc.RTN_OP) {
                    printToken();
                    returnStatement();
                    System.out.println("Exit <block>");
                }
            }
            //If the next token is end operator, exit the statement
            if (getToken() == sc.END_OP){
                printToken();
                System.out.println("Exit <if_statement>");
                Block();
            }
        }
        //Else give error which shows which line at
        else error();
    }

    //Parsing return statement grammar
    public void returnStatement(){
        //Calls arithmetic expression
        arithmeticExpression();
        //Keep calls arithmetic expression until the next token is end or else
        while (getToken() != sc.END_OP && getToken() != sc.ELS_OP) {
            arithmeticExpression();
        }
    }

    //Parsing else statement grammar
    public void elseStatement(){
        printToken();
    }

    //Parsing boolean expression grammar
    public void booleanExpression(){
        //Calls arithmetic expressions and put relational operator between to them to create a boolean expression
        arithmeticExpression();
        relationalOperator();
        arithmeticExpression();
    }

    //Parsing arithmetic expression grammar
    public void arithmeticExpression(){
        nextToken();
        if (getToken() == sc.ID) printToken(); // Checks if the token is ID
        else if (getToken() == sc.INT_LIT) printToken(); // Checks if the token is integer
        else if (getToken() == sc.ADD_OP) printToken(); // Checks if the token is +
        else if (getToken() == sc.MNS_OP) printToken(); // Checks if the token is -
        else if (getToken() == sc.MULT_OP) printToken(); // Checks if the token is *
        else if (getToken() == sc.DIV_OP) printToken(); // Checks if the token is /
        else if (getToken() == sc.LPAR_OP) printToken(); // Checks if the token is (
        else if (getToken() == sc.RPAR_OP) printToken(); // Checks if the token is )
    }

    //Parsing relational operator grammar
    public void relationalOperator(){
        nextToken();
        if (getToken() == sc.GRT_OP) printToken(); // Checks if the token is >
        else if (getToken() == sc.GRTEQ_OP) printToken(); // Checks if the token is >=
        else if (getToken() == sc.LSS_OP) printToken(); // Checks if the token is <
        else if (getToken() == sc.LSSEQ_OP) printToken(); // Checks if the token is <=
        else if (getToken() == sc.DUBEQ_OP) printToken(); // Checks if the token is ==
        else if (getToken() == sc.NTEQ_OP) printToken(); // Checks if the token is ~=
        //Else give error which shows which line at
        else error();
    }

    //Parsing print statement grammar
    public void printStatement(){
        arithmeticExpression();
        //Calls arithmetic expression until the next token is end or else
        while (getToken() != sc.END_OP && getToken() != sc.ELS_OP) {
            arithmeticExpression();
        }
        //If the next token is end operator, exit the statement
        if (getToken() == sc.END_OP){
            printToken();
            System.out.println("Exit <print_statement>");
            Block();
        }
        //Else give error which shows which line at
        else error();
    }

    //Parsing ID grammar
    public void Id (){
        //Checks the token if it is ID or not
        if (getToken() == sc.ID) {
            printToken();
            nextToken();
        }
        //Else give error which shows which line at
        else error();
    }

    //Error statement which shows which line at by getting number form identifier table
    public void error (){
        System.out.println("Missing argument at line " + getLine() + ".");
    }
}
