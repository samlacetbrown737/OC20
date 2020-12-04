import java.util.*;
/*public class Heapsort<Key extends Comparable<Key>> {
	public static void main(String[] args) {
		Heapsort heapsort = new Heapsort(50);
	}
	private Key[] pq;
	private int n = 0;
	public Heapsort(int maxN) {
		pq = (Key[]) new Comparable[maxN+1];
	}
	public boolean isEmpty() {
		return n == 0;
	}
	public int size() {
		return n;
	}
	public void insert(Key v) {
		pq[n++] = v;
		swim(n);
	}
	public Key delMax() {
		Key max = pq[1];
		exch(1, n--);
		pq[n+1] = null;
		sink(1);
		return max;
	}
	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}
	private void exch(int i, int j) {
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}
	private void swim(int k) {
		while(k > 1 && less(k/3, k)) {
			exch(k/3, k);
			k = k/3;
		}
	}
	private void sink(int k) {
		while(3*k <= n) {
			int j = 3*k;
			if(j < n && less(j, j+1)) {
				j++;
			}
			if(!less(k, j)) {
				break;
			}
			exch(k, j);
			k = j;
		}
	}
}*/


class Heapsort {
    private Integer[] heap; 
    private int heapSize, capacity;
    public Heapsort(int capacity)
    {    
        this.capacity = capacity + 1;
        heap = new Integer[this.capacity];
        heapSize = 0;
    }
    public void clear()
    {
        heap = new Integer[capacity];
        heapSize = 0;
    }
    public boolean isEmpty()
    {
        return heapSize == 0;
    }
    public boolean isFull()
    {
        return heapSize == capacity - 1;
    }
    public int size()
    {
        return heapSize;
    }
    public void insert(Integer time)
    {
        Integer newJob = time;
        heap[++heapSize] = newJob;
        int pos = heapSize;
		int pos3 = pos/3;
        while (pos != 1 && pos3 != 0 && newJob > heap[pos3])
        {
            heap[pos] = heap[pos/3];
            pos /=3;
        }
        heap[pos] = newJob;    
    }
    public Integer remove() {
        int parent, child;
        Integer item, temp;
        if (isEmpty()) {
            return null;
        }
        item = heap[1];
        temp = heap[heapSize--];
        parent = 1;
        child = 3;
        while (child <= heapSize) {
            if (child < heapSize && heap[child] < heap[child + 1]) {
                child++;
            }
            if (temp >= heap[child]) {
                break;
            }
            heap[parent] = heap[child];
            parent = child;
            child *= 3;
        }
        heap[parent] = temp; 
        return item;
    }
    public static void main(String[] args) {
    	//Random random = new Random();
    	Heapsort heapsort = new Heapsort(50);
    	/*for(int i = 0; i < 50; i++) {
			int value = random.nextInt(1000);
			Integer v = new Integer(value);
			heapsort.insert(v);
		}
		while(!heapsort.isEmpty()) {
			System.out.println(heapsort.remove());
		}*/
    }
}