import java.util.Random;
public class Maze {
    private int[] parent;
    private int[] height;
    private int num;


    public Maze(int n) {
        num = n;
        parent = new int[n];
        height = new int[n];
        for (int i = 1; i < n; i++) {
            parent[i] = i;
            height[i] = 0;
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (height[i] < height[j]) {
            parent[i] = j;
        }
        else if (height[i] > height[j]) {
            parent[j] = i;
        }
        else {
            parent[j] = i;
            height[i]++;
        }
        num--;
    }

    private boolean validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            return false;
        }
        return true;
    }

    public int find(int p) {
        if(validate(p)) {
            while (p != parent[p])
                p = parent[p];
            return p;
        } else {
            return -1;
        }
    }

    public int count() {
        return num;
    }

    public int randomNum(int min, int max) {
        Random rand = new Random();
        int x = max - min;
        int n = rand.nextInt((max - min) + 1) + min;
        return n;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Maze maze = new Maze(n*n);
        System.out.println(maze.count() + " components");
        while(!maze.connected(1, ((n*n)))) {
            int a = maze.randomNum(1, maze.count());
            int b = 0; //1 is up, 2 is right, 3 is down, 4 is left
                int c = -1;
                while(c < 1 || c > (n*n)) {
                    b = maze.randomNum(1, 4);
                    if(b == 1) {
                        c = a - n;
                    } else if (b == 2) {
                        if(a != 15 && a != 30 && a != 45 && a != 60 && a != 75 && a != 90 && a != 105 && a != 120 && a != 135 && a != 150 && a != 165 && a != 180 && a != 195 && a != 210 && a != 225)
                            c = a + 1;
                        else
                            c = a + n;
                    } else if (b == 3) {
                        c = a + n;
                    } else {
                        if(a != 16 && a != 31 && a != 46 && a != 61 && a != 76 && a != 91 && a != 106 && a != 121 && a != 136 && a != 151 && a != 166 && a != 181 && a != 196 && a != 211 && a != 226)
                            c = a - 1;
                        else {
                            c = a - n;
                        }
                    }
                }    
            if(!maze.connected(a, c)) {
                maze.union(a, c);
                System.out.println("Union: " + a + ", " + c);
                System.out.println(maze.find(c));
            }
        }
        System.out.println("done");
    }
}