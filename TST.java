import java.util.*;
import java.io.*;
public class TST {
	private Node root;
	private class Node {
		char c;
		Node left, mid, right;
		int val;
	}

	public int get(String key) {
		Node x = get(root, key, 0);
		if(x == null) {
			return -1;
		}
		return x.val;
	}

	private Node get(Node x, String key, int d) {
		if (x == null) {
			return null;
		}
		char c = key.charAt(d);
		if (c < x.c) {
			return get(x.left, key, d);
		} else if (c > x.c) {
			return get(x.right, key, d);
		} else if (d < key.length() - 1) {
			return get(x.mid, key, d+1);
		} else {
			return x;
		}
	}

	public void put(String key, int val) {
		root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, int val, int d) {
		char c = key.charAt(d);
		if (x == null) {
			x = new Node();
			x.c = c;
		}
		if (c < x.c) {
			x.left = put(x.left, key, val, d);
		} else if(c > x.c) {
			x.right = put(x.right, key, val, d);
		} else if (d < key.length() - 1) {
			x.mid = put(x.mid, key, val, d+1);
		} else {
			x.val = val;
		}
		return x;
	}

	public void addWords() {
		String filePath = "words.txt";
		int i = 0;
		try {
		    BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
		    String lineText = null;
		 
		    while ((lineText = lineReader.readLine()) != null) {
		        put(lineText, i);
		        i++;
		    }
		 
		    lineReader.close();
		} catch (IOException ex) {
		    System.err.println(ex);
		}
	}

	private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
        	return;
        }
        collect(x.left,  prefix, queue);
        if (x.val != -1) {
        	queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

	public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) {
        	return queue;
        }
        if (x.val != -1) {
        	queue.enqueue(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    public void searchWords() {
    	Scanner input = new Scanner(System.in);
		String entered = "";
		Iterable<String> optionList = keysWithPrefix("a");
		int options = 6;
		while(options>1) {
			System.out.println("Next letter: ");
			entered = entered + input.next();
			optionList = keysWithPrefix(entered);
			options = 0;
			for (String s : optionList) {
    			options++;
			}
			System.out.println("Input: " + entered);
			System.out.println("Possible suggestions: " + options);
			if(options<6) {
				System.out.println("Options: ");
				for (String s : optionList) {
    				System.out.println(s);
				}
			}
		}
		if(options>0){
			System.out.println("Word: " + optionList);
		}
    }

	public static void main(String[] args) {
		TST tst = new TST();
		tst.addWords();
		tst.searchWords();
	}
}