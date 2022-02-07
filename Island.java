package ir.alikie.ds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Island {
    static ArrayDeque<Node> firstIsland = new ArrayDeque<>();
    static Node secondIsland;
    static int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static int shortestBridge(int[][] grid) {
        ArrayDeque<Node> q = new ArrayDeque<>();

        boolean flag = false;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length && !flag; i++) {
            for (int j = 0; j < grid[i].length && !flag; j++) {
                if (grid[i][j] == 1) {
                    DFS(grid, i, j, visited, q, firstIsland);
                    flag = true;
                }
            }
        }


        int level = -1;

        while (q.size() != 0) {
            int size = q.size();
            level++;

            while (size-- > 0) {
                Node rem = q.removeFirst();

                for (int i = 0; i < dirs.length; i++) {
                    int newrow = rem.x + dirs[i][0];
                    int newcol = rem.y + dirs[i][1];

                    if (newrow >= 0 && newcol >= 0 && newrow < grid.length && newcol < grid[0].length && !visited[newrow][newcol]) {
                        if (grid[newrow][newcol] == 1) {
                            secondIsland = new Node(newrow, newcol);
                            return level;
                        }

                        q.add(new Node(newrow, newcol));
                        visited[newrow][newcol] = true;
                    }
                }
            }
        }
        return -1;
    }


    public static void DFS(int[][] grid, int row, int col, boolean[][] visited, ArrayDeque<Node> q, ArrayDeque<Node> firstIsland) {
        visited[row][col] = true;
        q.add(new Node(row, col));
        firstIsland.add(new Node(row, col));

        for (int i = 0; i < dirs.length; i++) {
            int newrow = row + dirs[i][0];
            int newcol = col + dirs[i][1];

            if (newrow >= 0 && newcol >= 0 && newrow < grid.length && newcol < grid[0].length && !visited[newrow][newcol] && grid[newrow][newcol] == 1) {
                DFS(grid, newrow, newcol, visited, q, firstIsland);
            }
        }

    }


    public static void shortestDistance(int[][] grid) {
        int distance = shortestBridge(grid);
        int size = firstIsland.size();
        int size2 = size;
        ArrayList<Node> nodes = new ArrayList<>();
        double[] check = new double[size];

        System.out.printf("%nDistance = %d%n", distance);


        while (size-- != 0) {
            Node node1 = firstIsland.removeFirst();
            nodes.add(node1);
            int deltaX = Math.abs((node1.x) - (secondIsland.x));
            int deltaY = Math.abs((node1.y) - (secondIsland.y));
            double a = Math.pow(deltaX, 2);
            double b = Math.pow(deltaY, 2);
            check[size] = Math.sqrt(a + b);
        }

        double min = check[0];
        for (int j = 1; j < check.length; j++) {
            if (min > check[j]) {
                min = check[j];
            }
        }

        while (size2-- != 0) {
            Node node1 = nodes.get(size2);
            int deltaX = Math.abs((node1.x) - (secondIsland.x));
            int deltaY = Math.abs((node1.y) - (secondIsland.y));
            double a = Math.pow(deltaX, 2);
            double b = Math.pow(deltaY, 2);

            if (Math.sqrt(a + b) == min){
                System.out.printf("%nx1 = %d , y1 = %d, x2 = %d , y2 = %d",
                        node1.x, node1.y, secondIsland.x, secondIsland.y);
            }

        }

    }


    public static void main(String[] args) {

        Scanner intInput = new Scanner(System.in);
        Scanner stringInput = new Scanner(System.in);
        int row = intInput.nextInt();
        int col = intInput.nextInt();

        int[][] island = new int[row][col];


        for (int i = 0; i < island.length; i++) {
            String[] line = stringInput.nextLine().split(" ");
            for (int j = 0; j < line.length; j++) {
                island[i][j] = Integer.parseInt(line[j]);
            }
        }

        shortestDistance(island);

    }


}


// Input: grid = [[0,1],[1,0]]
// Input: grid = [[0,1,0],[0,0,0],[0,0,1]]
// Input: grid = [[1,1,0],[0,0,0],[0,1,1]]
// Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]