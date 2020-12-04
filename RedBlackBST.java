public class RedBlackBST<Key extends Comparable<Key>, Value> {
	private Node root;
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private class Node {
		Key key;
		Value val;
		Node left, right;
		int n;
		boolean color;

		Node(Key key, Value val, int n, boolean color) {
			this.key = key;
			this.val = val;
			this.n = n;
			this.color = color;
		}
	}
	private boolean isRed(Node x) {
		if(x==null) {
			return false;
		}
		return x.color == RED;
	}
	private Node rotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		x.n = h.n;
		h.n = 1 + size(h.left) + size(h.right);
		return x;
	}
	private Node rotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		x.n = h.n;
		h.n = 1 + size(h.left) + size(h.right);
		return x;
	}
	private void flipColors(Node h) {
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}
	private int size(Node x) {
		if(x==null) {
			return 0;
		} else {
			return x.n;
		}
	}
	private void put(Key key, Value val) {
		root = put(root, key, val);
		root.color = BLACK;
	}
	private Node put(Node h, Key key, Value val) {
		if(h==null) {
			return new Node(key, val, 1, RED);
		}
		int cmp = key.compareTo(h.key);
		if (cmp < 0) {
			h.left = put(h.left, key, val);
		} else if (cmp > 0) {
			h.right = put(h.right, key, val);
		} else {
			h.val = val;
		}
		if(isRed(h.right) && !isRed(h.left)) {
			h = rotateLeft(h);
		}
		if(isRed(h.left) && isRed(h.left.left)) {
			h = rotateRight(h);
		}
		if(isRed(h.left) && isRed(h.right)) {
			flipColors(h);
		}
		h.n = size(h.left) + size(h.right) + 1;
		return h;
	}
	private Node moveRedLeft(Node h) {
		flipColors(h);
		if(isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}
	private Node moveRedRight(Node h) {
		flipColors(h);
		if(isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}
	public void deleteMin() {
		if(!isRed(root.left) && !isRed(root.right)) {
			root.color = RED;
		}
		root = deleteMin(root);
		if(root!=null) {
			root.color = BLACK;
		}
	}
	public void deleteMax() {
		if(!isRed(root.left) && !isRed(root.right)) {
			root.color = RED;
		}
		root = deleteMax(root);
		if(root!=null) {
			root.color = BLACK;
		}
	}
	private Node deleteMin(Node h) {
		if(h.left == null) {
			return null;
		}
		if(!isRed(h.left) && !isRed(h.left.left)) {
			h = moveRedLeft(h);
		}
		h.left = deleteMin(h.left);
		return balance(h);
	}
	private Node deleteMax(Node h) {
		if(isRed(h.left)) {
			h = rotateRight(h);
		}
		if(h.right == null) {
			return null;
		}
		if(!isRed(h.right) && !isRed(h.right.left)) {
			h = moveRedRight(h);
		}
		h.right = deleteMax(h.right);
		return balance(h);
	}
	public void delete(Key key) {
		if(!isRed(root.left) && !isRed(root.right)) {
			root.color = RED;
		}
		root = delete(root, key);
		if(root!=null) {
			root.color = BLACK;
		}
	}
	private Node delete(Node h, Key key) {
		if(key.compareTo(h.key) < 0) {
			if(!isRed(h.left) && !isRed(h.left.left)) {
				h = moveRedLeft(h);
			}
			h.left = delete(h.left, key);
		} else {
			if(isRed(h.left)) {
				h = rotateRight(h);
			}
			if(key.compareTo(h.key) == 0 && (h.right == null)) {
				return null;
			}
			if(!isRed(h.right) && !isRed(h.right.left)) {
				h = moveRedRight(h);
			}
			if(key.compareTo(h.key) == 0) {
				Node x = deleteMin(h.right);
				h.key = x.key;
				h.val = x.val;
				h.right = x;
			}
			else {
				h.right = delete(h.right, key);
			}
		}
		return balance(h);
	}
	private Node balance(Node h) {
		if(isRed(h.right)) {
			h = rotateLeft(h);
		}
		if(isRed(h.left) && isRed(h.left.left)) {
			h = rotateRight(h);
		}
		if(isRed(h.left) && isRed(h.right)) {
			flipColors(h);
		}
		h.n = size(h.left) + size(h.right) + 1;
		return h;
	}
}