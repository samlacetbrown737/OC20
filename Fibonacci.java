import java.util.ArrayList;
public class Fibonacci {
    public static long fibonacci(int n)
    {
        ArrayList<Integer> sequence = new ArrayList<Integer>();
        sequence.add(0);
        sequence.add(1);
        for(int i = 2; i < n; i++) {
            sequence.add(sequence.get(i-1) + sequence.get(i-2));
        }
        return sequence.get(n-1);
    }
    public static void main(String[] args) {
        System.out.println(args[0] + "th element is " + fibonacci(Integer.parseInt(args[0])));
    }
}