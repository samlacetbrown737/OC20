import java.util.*;
import java.lang.Iterable;

public class ArithmeticExpressionEvaluator {
	public static void main(String args[]) {
		String infix = args[0];
		String postfix = InfixToPostfix(infix);
		String result = EvaluatePostfix(postfix);
		System.out.println("Infix: " + infix);
		System.out.println("Postfix: " + postfix);
		System.out.println("Result: " + result);
	}

	private static int precedence(char sc) {
		if(sc == '(' || sc == ')') {
			return 1;
		}
		if(sc == '+' || sc == '-') {
			return 2;
		}

		if(sc == '*' || sc == '/') {
			return 3;
		}
		return 0;
	}

	public static String InfixToPostfix(String infix) {
		String postfix = "";
        Stack<Character> operator = new ArrayStack<Character>();
        char popped;

        for (int i = 0; i < infix.length(); i++) {
            char get = infix.charAt(i);
            if (!(precedence(get) > 0)) {
                postfix += get;
            }
            else if (get == ')')
                while ((popped = operator.pop()) != '(') {
                    postfix += popped;
                }
            else {
                while (!operator.isEmpty() && get != '(' && precedence(operator.peek()) >= precedence(get)) {
                    postfix += operator.pop();
                }
                operator.push(get);
            }
        }
        while (!operator.isEmpty()) {
            postfix += operator.pop();
        }
        return postfix;
    }

    public static String EvaluatePostfix(String postfix) {
    	String result = "";
    	int x = 0;
    	Stack<Integer> nums = new ArrayStack<Integer>();
    	for (int i = 0; i < postfix.length(); i++) {
            char get = postfix.charAt(i);
            if(precedence(get) == 0) {
            	int digit = Integer.parseInt("" + get);
            	nums.push(digit);
            } else if (precedence(get) > 1) {
            	int b = nums.pop();
            	int a = nums.pop();
            	if(get == '+') {
            		x = a + b;
            		nums.push(x);
            	} else if (get == '-') {
            		x = a - b;
            		nums.push(x);
            	} else if (get == '*') {
            		x = a * b;
            		nums.push(x);
            	} else if (get == '/') {
            		x = a / b;
            		nums.push(x);
            	}
            }
        }
        result = "" + nums.pop();
        return result;
    }
}

interface Stack<Item> extends Iterable<Item> {
	boolean isEmpty();
	void push(Item item);
	Item pop();
	Item peek();
	int size();
	void resize(int capacity);
}

@SuppressWarnings("unchecked")
class ArrayStack<Item> implements Stack<Item> {
	public int size = 0;
	public Item data[];

	public ArrayStack() {
		data = (Item[]) new Object[3];
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public void push(Item item) {
		resize(size+1);
		data[size] = item;
		size++;
	}

	public Item pop() {
		if(!isEmpty()) {
			size--;
			resize(size);
			return (data[size]);	
		}
		return null;
	}

	public Item peek() {
		if(!isEmpty()) {
			int newSize = size - 1;
			return (data[newSize]);
		}
		return null;
	}

	public int size() {
		return (size--);
	}

	public void resize(int capacity) {
		if(data.length < capacity) {
			data = Arrays.copyOf(data, (data.length * 2));
		}
		if((data.length * 4) < capacity) {
			data = Arrays.copyOf(data, (data.length / 2));
		}
	}

	public Iterator<Item> iterator() {
		return new ArrayStackIterator();
	}

	private class ArrayStackIterator implements Iterator<Item> {
		private int i = size;
		public boolean hasNext() {
			return (i > 0);
		}
		
		public Item next() {
			i--;
			return data[i];
		}

		public void remove() {

		}
	}
}