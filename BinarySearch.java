import java.util.Arrays;
import java.util.Random;
public class BinarySearch
{
	public static int indexOf(int[] a, int key) {
		int lo = 0;
		int hi = a.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if ( key < a[mid]) {
				hi = mid - 1;
			} else if (key > a[mid]) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static int[] digits(int n) {
		int[] digitList = new int[n];
		Random r = new Random();
		for(int i = 0; i < n; i++) {
			digitList[i] = r.nextInt(1000000);
		}
		Arrays.parallelSort(digitList);
		return digitList;
	}

	public static int matches(int[] arrayOne, int[] arrayTwo, int num) {
		int matchNum = 0;
		for(int i = 0; i < num; i++) {
			int match = indexOf(arrayOne, arrayTwo[i]);
			if(match != -1) {
				matchNum++;
			}
		}
		return matchNum;
	}

	public static void main(String[] args) {
		int[] thousOne = digits(1000);
		int[] thousTwo = digits(1000);
		int[] tenThousOne = digits(10000);
		int[] tenThousTwo = digits(10000);
		int[] hundThousOne = digits(100000);
		int[] hundThousTwo = digits(100000);
		int[] millOne = digits(1000000);
		int[] millTwo = digits(1000000);
		int thous = 0;
		int tenThous = 0;
		int hundThous = 0;
		int mill = 0;
		int trials = Integer.parseInt(args[0]);

		for(int i = 0; i < trials; i++) {
			thous = thous + matches(thousOne, thousTwo, 1000);
			tenThous = tenThous + matches(tenThousOne, tenThousTwo, 10000);
			hundThous = hundThous + matches(hundThousOne, hundThousTwo, 100000);
			mill = mill + matches(millOne, millTwo, 1000000);
		}

		thous = thous / trials;
		tenThous = tenThous / trials;
		hundThous = hundThous / trials;
		mill = mill / trials;

		System.out.println("TRIALS: " + trials);
		System.out.println("10^3 (1,000): " + thous);
		System.out.println("10^4 (10,000): " + tenThous);
		System.out.println("10^5 (100,000): " + hundThous);
		System.out.println("10^6 (1,000,000): " + mill);
	}
}