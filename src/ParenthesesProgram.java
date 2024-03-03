import java.util.Scanner;
import java.util.Stack;

public class ParenthesesProgram {

    public static void main(String[] args){

        System.out.println("Input a String to check for balanced Parentheses:");
        Scanner scn = new Scanner(System.in);
        if(scn.hasNext()){
            String input = scn.next().replaceAll("[^\\[\\{\\(\\)\\}\\]]", "");
            System.out.println("Input taken:\n"+input);
            boolean output = validate(input);
            System.out.println("Output:\n"+output);
        }
        scn.close();
    }

    private static boolean validate(String input){
        if(input.length()%2 != 0){
            return false;
        }

        Stack<Character> s = new Stack<>();
        for (char c : input.toCharArray()){
            if(c == '(' || c == '{' || c == '['){
                s.push(c);
            } else if((c == ')' && (s.isEmpty() || s.pop() != '(')) ||
                    (c == '}' && (s.isEmpty() || s.pop() != '{')) ||
                    (c == ']' && (s.isEmpty() || s.pop() != '['))){
                return false;
            }
        }
        return s.isEmpty();
    }
}
