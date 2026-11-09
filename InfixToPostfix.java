import java.util.Scanner;

public class InfixToPostfix {

    interface Stack<T> {
        void push(T val);
        T pop();
        T peek();
        boolean isEmpty();
        int size();
    }

    static class MyLinkedList<T> {
        protected static class Node<T> {
            T data;
            Node<T> next;

            Node(T data) {
                this.data = data;
                this.next = null;
            }
        }

        protected Node<T> head;
        protected int count;

        public MyLinkedList() {
            this.head = null;
            this.count = 0;
        }

        public void addFirst(T val) {
            Node<T> node = new Node<>(val);
            node.next = head;
            head = node;
            count++;
        }

        public void addLast(T val) {
            Node<T> node = new Node<>(val);
            if (head == null) {
                head = node;
            } else {
                Node<T> curr = head;
                while (curr.next != null) {
                    curr = curr.next;
                }
                curr.next = node;
            }
            count++;
        }

        public T removeFirst() {
            if (isEmpty()) {
                throw new IllegalStateException("List empty");
            }
            T val = head.data;
            head = head.next;
            count--;
            return val;
        }

        public T removeLast() {
            if (isEmpty()) {
                throw new IllegalStateException("List empty");
            }
            if (head.next == null) {
                T val = head.data;
                head = null;
                count--;
                return val;
            }
            Node<T> curr = head;
            while (curr.next.next != null) {
                curr = curr.next;
            }
            T val = curr.next.data;
            curr.next = null;
            count--;
            return val;
        }

        public T getFirst() {
            if (isEmpty()) {
                throw new IllegalStateException("List empty");
            }
            return head.data;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public int size() {
            return count;
        }
    }

    static class LinkedListStack<T> extends MyLinkedList<T> implements Stack<T> {

        @Override
        public void push(T val) {
            addFirst(val);
        }

        @Override
        public T pop() {
            return removeFirst();
        }

        @Override
        public T peek() {
            return getFirst();
        }

        @Override
        public void addLast(T val) {
            throw new UnsupportedOperationException("addLast unsupported on Stack");
        }

        @Override
        public T removeLast() {
            throw new UnsupportedOperationException("removeLast unsupported on Stack");
        }
    }

    private static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    private static boolean isRightAssociative(char ch) {
        return ch == '^';
    }

    public static String convert(String expr) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new LinkedListStack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (stack.isEmpty() || stack.peek() != '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    int pCurr = precedence(ch);
                    int pTop = precedence(stack.peek());

                    if (pTop > pCurr || (pTop == pCurr && !isRightAssociative(ch))) {
                        result.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            char top = stack.pop();
            if (top == '(' || top == ')') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            result.append(top);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter infix expression: ");
        if (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (!input.trim().isEmpty()) {
                System.out.println("Postfix: " + convert(input));
            }
        }
        sc.close();
    }
}
