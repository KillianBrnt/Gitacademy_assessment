import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class App {
    // set default value
    private static final int N = 23, MAP_SIZE = 10000;

    // creating 2d data structure
    private static class Coordinate {
        private int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    public static void main(String[] args) {
        int[][] graph = new int[MAP_SIZE * 2][MAP_SIZE * 2]; // maintains a record of the places visited
        Deque<Coordinate> robotPos = new ArrayDeque<>(); // keep track of the last coordinates that the character
                                                         // visited
        List<Coordinate> moves = new ArrayList<>(Arrays.asList( // possible movements
                new Coordinate(-1, 0), new Coordinate(1, 0), new Coordinate(0, -1), new Coordinate(0, 1)));

        // intialize the starting point of the character in the graph
        robotPos.push(new Coordinate(0, 0)); // starting coordinate in the view of a real-world graph
        graph[0 + MAP_SIZE][0 + MAP_SIZE] = 1; // the actual coordinate of the origin point in our graph

        // init counter
        int area = 1; // character is already at the origin, so include the origin in the count

        while (!robotPos.isEmpty()) {
            Coordinate lastLocation = robotPos.pop(); // fetch and remove the last location that the character was in

            for (Coordinate move : moves) {
                // move the character
                int newX = lastLocation.getX() + move.getX(); // new x coordinate in the real-world graph after moving
                int newY = lastLocation.getY() + move.getY(); // new y coordinate in the real-world graph after moving
                int x = newX + MAP_SIZE; // the actual x coordinate in our graph
                int y = newY + MAP_SIZE; // the actual y coordinate in our graph

                // check if this coordinate in our graph has been visited before
                if (graph[x][y] == 0) {
                    boolean isSafe = checkSafe(newX, newY);

                    // check if it's safe
                    if (isSafe) {
                        graph[x][y] = 1; // move the character to the new coordinates, and mark as visited
                        area += 1; // increment count since this is a safe position
                        robotPos.push(new Coordinate(newX, newY)); // push the current location
                    } else if (!isSafe) {
                        graph[x][y] = -1; // set mined place -1 to avoid rechecking them
                    }
                }
            }
        }

        System.out.println("For N = " + N + " result is : " + area); // display the result
    }

    // check if coordinate is safe
    private static boolean checkSafe(int x, int y) {
        // get the absolute value of x and y
        int tempX = Math.abs(x), tempY = Math.abs(y);

        // add x and y digits sums and compare it to N
        return getValue(tempX) + getValue(tempY) <= N;
    }

    // get the sum of the coordinates digits
    private static int getValue(int val) {
        int ret = 0;

        // split each digit of the value and add it in a storage value
        while (val > 0) {
            ret += val % 10;
            val /= 10;
        }
        return ret;
    }
}