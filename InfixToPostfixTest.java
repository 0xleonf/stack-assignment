public class InfixToPostfixTest {
    public static void main(String[] args) {
        InfixToPostfix.LinkedListStack<Integer> s = new InfixToPostfix.LinkedListStack<>();
        s.push(10);
        s.push(20);
        assert s.peek() == 20;
        assert s.pop() == 20;
        assert s.pop() == 10;
        assert s.isEmpty();

        try {
            s.addLast(99);
            throw new AssertionError("addLast should fail");
        } catch (UnsupportedOperationException expected) {}

        try {
            s.removeLast();
            throw new AssertionError("removeLast should fail");
        } catch (UnsupportedOperationException expected) {}

        assert InfixToPostfix.convert("A+B*C").equals("ABC*+");
        assert InfixToPostfix.convert("(A+B)*(C-D)").equals("AB+CD-*");
        assert InfixToPostfix.convert("a+b*(c^d-e)^(f+g*h)-i").equals("abcd^e-fgh*+^*+i-");

        System.out.println("Tests passed.");
    }
}
