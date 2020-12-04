import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;

public class StronglyConnected {
	private boolean[] marked;
	private int[] id;
	private int count;
	public StronglyConnected(Digraph G) {
		marked = new boolean[G.V()];
		id = new int[G.V()];
		DepthFirstOrder order = new DepthFirstOrder(G.reverse());
		for(int s : order.reversePost()) {
			if(!marked[s]) {
				dfs(G, s);
				count++;
			}
		}
	}
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		id[v] = count;
		for(int w : G.adj(v)) {
			if(!marked[w]) {
				dfs(G, w);
			}
		}
	}
	public boolean stronglyConnected(int v, int w) {
		return id[v] == id[w];
	}
	public int id(int v) {
		return id[v];
	}
	public int count() {
		return count;
	}
	public static void main(String[] args) throws IOException {
		Digraph x = new Digraph(args[0]);
		StronglyConnected sc = new StronglyConnected(x);
		int n = sc.count();
		System.out.println(n + " components");
		Queue<Integer>[] components;
		components = (Queue<Integer>[]) new Queue[n];
		for(int i = 0; i < n; i++) {
			components[i] = new Queue<Integer>();
		}
		for(int v = 0; v < x.V(); v++) {
			components[sc.id(v)].enqueue(v);
		}
		for(int i = 0; i < n; i++) {
			for (int v : components[i]) {
				System.out.print(v + " ");
			}
			System.out.println();
		}
	}
}

class Digraph {
	private final int V;
	private int E;
	private Bag<Integer>[] adj;
	private int[] indegree;
	public Digraph(int V) {
		this.V = V;
		this.E = 0;
		indegree = new int[V];
		adj = (Bag<Integer>[]) new Bag[V];
		for(int v = 0; v < V; v++) {
			adj[v] = new Bag<Integer>();
		}
	}
	public Digraph(String file) throws IOException {
		Scanner in = new Scanner(new File(file));
		if (in == null) {
			throw new IllegalArgumentException("Argument is null");
		}
        try {
            this.V = in.nextInt();
            if (V < 0) {
            	throw new IllegalArgumentException("Num of vertices is negative");
            }
            indegree = new int[V];
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.nextInt();
            if (E < 0) {
            	throw new IllegalArgumentException("Num of edges is negative");
            }
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                addEdge(v, w); 
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid input", e);
        }
	}

	public Digraph(Digraph G) {
        if (G == null) {
        	throw new IllegalArgumentException("argument is null");
        }
        this.V = G.V();
        this.E = G.E();
        if (V < 0) {
        	throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        }
        indegree = new int[V];
        for (int v = 0; v < V; v++) {
            this.indegree[v] = G.indegree(v);
        }
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }
    public int V() {
        return V;
    }
    public int E() {
        return E;
    }
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (V-1));
        }
    }
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
        E++;
    }
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }
    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }
}

class Bag<Item> implements Iterable<Item> {
	private Node<Item> first;
	private int n;
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}
	public Bag() {
		first = null;
		n = 0;
	}
	public boolean isEmpty() {
		return first == null;
	}
	public int size() {
		return n;
	}
	public void add(Item item) {
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}
	public Iterator<Item> iterator() {
		return new ListIterator(first);
	}
	private class ListIterator implements Iterator<Item> {
		private Node<Item> current;
		public ListIterator(Node<Item> first) {
			current = first;
		}
		public boolean hasNext() {
			return current != null;
		}
		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}

class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre;
    private int[] post;
    private Queue<Integer> preorder;
    private Queue<Integer> postorder;
    private int preCounter;
    private int postCounter;
    public DepthFirstOrder(Digraph G) {
        pre    = new int[G.V()];
        post   = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder  = new Queue<Integer>();
        marked    = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);

        assert check();
    }
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.enqueue(v);
        post[v] = postCounter++;
    }
    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }
    public int post(int v) {
        validateVertex(v);
        return post[v];
    }
    public Iterable<Integer> post() {
        return postorder;
    }
    public Iterable<Integer> pre() {
        return preorder;
    }
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }
    private boolean check() {
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                return false;
            }
            r++;
        }
        r = 0;
        for (int v : pre()) {
            if (pre(v) != r) {
                return false;
            }
            r++;
        }
        return true;
    }
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
class Queue<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;
    private class Node {
        private Item item;
        private Node next;
    }

    public Queue() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public int length() {
        return n;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
class Stack<Item> implements Iterable<Item> {
    private int n;     
    private Node first; 

    private class Node {
        private Item item;
        private Node next;
    }

    public Stack() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
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
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        first = first.next; 
        n--;
        return item; 
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }
    public Iterator<Item> iterator()  { return new ListIterator();  }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}