import java.util.Random;
public class MazeMaker {
    private int[] parent;
    private int[] size;
    private int count;


    public MazeMaker(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p) {
        //validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    public boolean connected(int p, int q) {
        // error is with find(q)) which is nn;
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public int randomNum(int min, int max) {
        Random rand = new Random();
        int x = max - min;
        int n = rand.nextInt((max - min) + 1) + min;
        return n;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int nn = n*n;
        MazeMaker maze = new MazeMaker(nn);
        while(!maze.connected(2, (nn-3))) { //set starting/end blocks 
            int a = maze.randomNum(0, (nn-2));
            int b = 0; //1 is up, 2 is right, 3 is down, 4 is left
            int c = -1;
            while(c < 1 || c > (nn-2)) {
                b = maze.randomNum(1, 4);
                if(b == 1) {
                    c = a - n;
                } else if (b == 2) {
                    if((a % n) != 0)
                        c = a + 1;
                    else
                        c = a + n;
                } else if (b == 3) {
                    c = a + n;
                } else {
                    if((a % n) != 1)
                        c = a - 1;
                    else {
                        c = a - n;
                    }
                }
            }
            if(!maze.connected(a,c)) {
                maze.union(a,c);
                System.out.println("Union: " + a + ", " + c);  
                //System.out.println("Connected: " + maze.connected(2, (nn-3)));  
            }
        }
    }
}