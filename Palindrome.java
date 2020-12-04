import java.util.Scanner;

public class Palindrome {
	private long patHash;
	private int m;
	private long q;
	private int R = 256;
	private long RM;
	public Palindrome() {
		q = 37243;
	}

	private long hash(String key, int m) {
		long h = 0;
		for(int j = 0; j < m; j++) {
			h = (R * h + key.charAt(j)) % q;
		}
		return h;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int length = 0;
		boolean palin = false;
		String forward = "";
		String backward = "";
		String input = "";
		Palindrome checker = new Palindrome();
		do {
			System.out.println("Next char (q to quit): ");
			length++;
			input = in.nextLine();
			forward = forward + input;
			backward = input + backward;
			if(checker.hash(forward, length) == checker.hash(backward, length)){
				System.out.println("Palindrome");
			} else {
				System.out.println("Not a palindrome");
			}
		} while(input.charAt(0) != 'q');
	}
}

