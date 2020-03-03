import java.util.Scanner;

public class InfixToPostfix {

    // initializing scanner class
    public static Scanner obj= new Scanner(System.in);

    // initializing required class variables
    static int top = -1,top_evaluate = -1, size = 100;
    static char  stack[] = new char[size];
    static double stack_evaluate[] = new double[size];

    public static void main(String args[]){

        // initializing required variables
        String input;
        int input_length;
        String postfix_expression = "";
        char current_character;
        boolean flag = false;

        while(flag != true) {

            System.out.println("Enter the Infix Expression");

            input = obj.next(); //getting input from user

            // computing the length of the input
            input_length = input.length();
            // creating array to store characters of the input
            char arr[] = new char[input.length()];

            // checking the length of the expression
            if (input_length >= 3 && input_length <= 20)
            {
               // for loop to put character from string into array
               for (int i = 0; i < input_length; i++) {

                current_character = input.charAt(i);

                // checking for the correctness of the expression
                if (operator_precedence(current_character) != 0 || Character.isDigit(current_character) || current_character == '(' || current_character == ')') {
                    arr[i] = current_character;
                } else {
                    flag = true;
                    break;
                }
             }

            } else flag = true;

            if (flag == false) {

                // calling postfix_converter function and storing it
                postfix_expression = postfix_converter(arr, input_length);

                System.out.println("Postfix Expression: " + postfix_expression);

                // calling evaluate function and storing it
                Double result = evaluate(postfix_expression, postfix_expression.length());

                System.out.printf("Result = %.2f", result);
                flag = true;

            } else {
                // printing failure message for incorrect expression
                System.out.println("Invalid input please try again");
                flag = false;
            }
        }

    }

/*
postfix_converter function get in the array and it's length as arguments and converts the
infix expression into postfix and returns as a string
 */
    public static String postfix_converter(char arr[], int input_length){

        // initializing local variable
        String postfix = new String("");

        // loop to iterate through each elements in the array
        for (int i = 0; i<input_length; i++) {

            // checking whether current element is a digit
            if (Character.isDigit(arr[i]))
                // appending it to the postfix string
                postfix += arr[i];

            // checking if the current element is (
            else if (arr[i] == '(')
                //appending it the string
                push(arr[i]);

            // checking for )
            else if (arr[i] == ')')
            {
                // popping out all the elements before ( in the stack and appending it into string
                while (!isEmpty() && getTop() != '(')
                    postfix += pop();

                // discarding (
                 pop();
            }

            else {

                // checking whether the operator precedence of the current element is less than of that present on the top of the stack
                while (operator_precedence(arr[i]) <= operator_precedence(getTop()) )
                    // popping elements in the stack and adding it to string
                    postfix += pop();

                // if the operator precedence of the current element is greater than of that present on the top of the stack the above while loop is skipped and current element is pushed
                push(arr[i]);
            }
        }

        // popping out all the elements from the stacks
        while(!isEmpty()) {
            postfix += pop();
        }

        return postfix;
    }

    // evaluate function gets postfix string and its length as arguments and return the evaluated value of the expression
    public static double evaluate(String postfix, int length){

        // initalizing local variables
        char c;
        double a,b;

        // loop to iterate through each character in the String
        for(int i=0; i< length; i++) {

            c = postfix.charAt(i);

            // checking for digits
            if (Character.isDigit(c))
                //pushing the digit into stack array
                push_evaluate((double) (c - '0'));
            else {
                // popping last 2 elements and assigning the values to variables a and b if a operator is detected
                a = pop_evaluate();
                b = pop_evaluate();

                // if the character is +, a and b are added and pushed back into the stack
                if (c == '+') {
                    push_evaluate(a+b);

                } else
                    // if the character is -, b and a are subtracted and pushed back into the stack
                    if (c == '-') {
                    push_evaluate(b-a);

                } else
                        // if the character is *, a and b are multiplied and pushed back into the stack
                    if (c == '*') {

                    push_evaluate(a*b);

                } else
                        // if the character is /, b and a are divided and pushed back into the stack
                    if (c == '/') {

                    push_evaluate(b/a);

                } else
                        // if the character is +, b to the power of a is calculated and pushed back into the stack
                    if (c == '^'){

                    push_evaluate(Math.pow(b,a));

                }
            }
        }

        // returning the calculated value
        return getTop_evaluate();
    }
    // operator_precedence method gets operator as argument and return the precedence of the operator
    public static int operator_precedence(char operator){
        int value = 0;

        if(operator == '^')
            // if the operator is ^ value 3 is returned
            value = 3;
        else if(operator == '*' || operator == '/')
            // if the operator is * or / value 2 is returned
            value = 2;
        else if(operator == '+' || operator == '-')
            // if the operator is + or - value 1 is returned
            value = 1;

        return value;
    }
    //push method pushes the element in the stack array
    public static void push(char item){

        if(top > size-1 )
            System.out.println("Stack Overflow");
        else {
            top++;
            stack[top] = item;
        }
    }
    // pop element remove and returns the top element of the stack array
    public static char pop(){

        char item;

        if(top < 0 ) {
            System.out.println("Stack underflow");
            return ' ';
        }else {

            item = stack[top];
            stack[top] = ' ';
            top--;
            return item;
        }
    }
    // pop_evaluate removes and return the top element of the stack_evaluate array
    public static double pop_evaluate(){

        double item;

        if(top_evaluate < 0 ) {
            System.out.println("Stack underflow");
            return ' ';
        }else {
            item = stack_evaluate[top_evaluate];
            stack[top_evaluate] = ' ';
            top_evaluate--;
            return item;
        }
    }
    //push method pushes the element into stack_evaluate array
    public static void push_evaluate(double item){

        if(top_evaluate > size-1 )
            System.out.println("Stack Overflow");
        else {
            top_evaluate++;
            stack_evaluate[top_evaluate] = item;
        }
    }
    // getTop function returns the top value of stack array
    public static char getTop(){
        if (top == -1)
            return ' ';
        else
            return stack[top];
    }
    //getTOP_evaluate function returns the top value of stack_evaluate array
    public static double getTop_evaluate(){
        if (top_evaluate == -1)
            return ' ';
        else
        return stack_evaluate[top_evaluate];
    }
    // isEmpty checks whether the stack array is empty
    public static boolean isEmpty(){
        if (top==-1)
            return true;
        else
            return false;
    }
}
