import java.util.*;
public class Priority {
	public static void main(String[] args) {
		Priority priority = new Priority();
		Random random = new Random();
		List<Integer> jobs = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			int length = random.nextInt(100);
			jobs.add(length);
		}
		System.out.println("Average Turnaround Time, 100 Jobs");
		System.out.println("First In First Out: " + priority.FIFO(jobs) + " units");
		System.out.println("Shortest Job First: " + priority.SJF(jobs) + " units");
		System.out.println("Round Robin (20 max): " + priority.RR(jobs, 20) + " units");
		System.out.println("Round Robin (5 max): " + priority.RR(jobs, 5) + " units");
	}
	public int FIFO(List<Integer> jobs) {
		int time = 0;
		for(int i = 0; i < 100; i++) {
			time = time + jobs.get(i) + ((100 - i) * jobs.get(i));
		}
		time = (time / 100);
		return time;
	}
	public int SJF(List<Integer> jobs) {
		int time = 0;
		PriorityQueue pq = new PriorityQueue(100);
		for(int i = 0; i < 100; i++) {
			pq.insert(jobs.get(i));
		}
		while(!pq.isEmpty()) {
			time = time + (int) pq.remove() + (pq.size() * (int) pq.remove());
		}
		time = time / 100;
		return time;
	}
	public int RR(List<Integer> jobs, int slices) {
		List<Integer> j = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			j.add(jobs.get(i));
		}
		int time = 0;
		int i = 0;
		while(j.size() != 0) {
			int spent = slices;
			if (j.get(i) < slices) {
				spent = j.get(i);
			}
			j.set(i, j.get(i) - spent);
			time = time + spent + (j.size() * spent);
			if(j.get(i) == 0) {
				j.remove(i);
			}
			i++;
			if(i >= j.size()) {
				i = 0;
			}
		}
		time = time / 100;
		return time;
	}
}

class PriorityQueue {
    private Integer[] heap; 
    private int heapSize, capacity;
    public PriorityQueue(int capacity)
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
    public void insert(int time)
    {
        Integer newJob = time;
        heap[++heapSize] = newJob;
        int pos = heapSize;
        while (pos != 1 && newJob > heap[pos/2])
        {
            heap[pos] = heap[pos/2];
            pos /=2;
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
        child = 2;
        while (child <= heapSize) {
            if (child < heapSize && heap[child] < heap[child + 1]) {
                child++;
            }
            if (temp >= heap[child]) {
                break;
            }
            heap[parent] = heap[child];
            parent = child;
            child *= 2;
        }
        heap[parent] = temp; 
        return item;
    }
}