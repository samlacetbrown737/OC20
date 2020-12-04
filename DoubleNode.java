import java.util.*;
import java.lang.Iterable;

public class DoubleNode {
	public static void main(String args[]) {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		//System.out.println("Add at beginning");
		//list.addAt("6", 0);
		//list.printListF();
		System.out.println("Add 3, 7, 6, 1 and 2 at end");
		list.add(3);
		list.add(7);
		list.add(6);
		list.add(1);
		list.add(2);
		list.printListF();
		System.out.println("Remove from beginning");
		list.remove(0);
		list.printListF();
		System.out.println("Remove from end");
		list.remove(1);
		list.printListF();
		System.out.println("Add 9 before 3rd node");
		list.addAt(9, 2);
		list.printListF();
		System.out.println("Add 5 after 3rd node");
		list.addAt(5, 3);
		list.printListF();
		System.out.println("Remove 3rd node");
		list.remove(3);
		list.printListF();
		System.out.println("Move 5 to front");
		list.newTail(5, 3);
		list.printListF();
		System.out.println("Move 6 to end");
		list.newHead(6, 3);
		list.printListF();
	}
}

class DoubleLinkedList<Item> {
	int nodes = 0;
	Node head = null;
	Node tail = null;
	Node temp = null;

	class Node {
		Item val;
		Node next;
		Node previous;
		public Node(Item val) {
			this.val = val;
			previous = null;
			next = null;
		}
	}

	public void printListF() {
		temp = tail;
		while(temp != null) {
			System.out.println(temp.val);
			temp = temp.previous;
		}
	}

	public void printLisB() {
		temp = head;
		while(temp != null) {
			System.out.println(temp.val);
			temp = temp.next;
		}
	}

	public void add(Item val) {
		nodes++;
		if(head == null) {
			temp = new Node(val);
			head = temp;
			tail = temp;
		} else {
			temp = new Node(val);
			temp.next = head;
			head.previous = temp;
			head = temp;
		}
	}

	public void addAt(Item val, int index) {
		nodes++;
		Node newnode = new Node(val);
		temp = tail;
		int count = 1;
		while(temp.previous != null && (count != index)) {
			count++;
			temp = temp.previous;
		}
		newnode.previous = temp.previous;
		newnode.next = temp;
		temp.previous = newnode;
	}

	public void remove(int index) {
		nodes--;
		if(index == 0) {
			temp = tail.previous;
			tail.next = temp.next;
			tail = temp;
		} else if (index == 1) {
			temp = head.next;
			temp.previous = null;
			head = temp;
		} else {
			int count = 1;
			temp = tail;
			while(temp.previous != null && (count != index-1)) {
				count++;
				temp = temp.previous;
			}
			temp.previous = temp.previous.previous;
			temp.previous.next = temp;
		}
	}

	public int size() {
		return nodes;
	}

	public void newTail(Item val, int index) {	
		remove(index);
		temp = new Node(val);
		temp.previous = tail;
		tail = temp;
	}

	public void newHead(Item val, int index) {
		remove(index);
		temp = new Node(val);	
		head.previous = temp;
	}
}