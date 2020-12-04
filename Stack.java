public class Stack<Item> {
	private Node first;
	private int n;
	private class Node {
		Item item;
		Node next;
	}
	public boolean isEmpty() {
		return (first == null);
	}
	public int size() {
		return n;
	}
	public void push(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		n++;
	}
	public Item pop() {
		Item item = first.item;
		first = first.next;
		n--;
		return item;
	}
}