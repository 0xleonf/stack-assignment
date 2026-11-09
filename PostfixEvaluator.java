public class PostfixEvaluator {

    public static double evaluate(String postfixExpr) {
        InfixToPostfix.Stack<Double> stack = new InfixToPostfix.LinkedListStack<>();

        for (int i = 0; i < postfixExpr.length(); i++) {
            char ch = postfixExpr.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isDigit(ch)) {
                stack.push((double) (ch - '0'));
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expr");
                }
                double b = stack.pop();
                double a = stack.pop();

                switch (ch) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        stack.push(a / b);
                        break;
                    case '^':
                        stack.push(Math.pow(a, b));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + ch);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expr");
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String infix = "2+3*(4^2-1)";
        String postfix = InfixToPostfix.convert(infix);
        double val = evaluate(postfix);

        System.out.println("Infix  : " + infix);
        System.out.println("Postfix: " + postfix);
        System.out.println("Result : " + val);
    }
}
