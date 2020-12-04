import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LRUCache<Item> {
	HashMap<Item, Integer> st;
	LinkedList<Item> ll;
	public static void main(String[] args) {
		LRUCache<String> lc = new LRUCache<String>();
		Scanner input = new Scanner(System.in);
		int action = 0;
		while(action!=3) {
			System.out.print("1 to access, 2 to remove, 3 to quit: ");
			action = input.nextInt();
			if(action==1) {
				String it = input.next();
				lc.access(it);
			} else if (action==2) {
				System.out.println(lc.remove());
			}
		}
	}
	public LRUCache() {
		st = new HashMap<>();
		ll = new LinkedList<Item>();
	}
	public void access(Item item) {
		if(st.containsKey(item)) {
			System.out.println("contained");
			ll.remove(item);
			ll.addFirst(item);
		} else {
			System.out.println("added");
			ll.addFirst(item);
			st.put(item, ll.indexOf(item));
		}
	}

	public Item remove() {
		Item last = ll.getLast();
		System.out.println(last);
		ll.removeLast();
		st.remove(last);
		return last;
	}
}