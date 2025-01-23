import java.util.Random;
class EightQueens {
    public static void main(String[] args) {
        int[][] currState = setup(); 

        while (checkConflicts(currState) != 0) {
            currState = minimizeConflicts(currState);
            printState(currState);  
            if (findQueenRow(currState, 0) == -1) {
                System.out.println("RESTART");
                currState = setup();
            }
        }

        System.out.println("Solution Found!");

    }

// Places 1 Queen Randomly in each col and prints board
    public static int[][] setup(){
    int[][] currState = new int[8][8]; 
    Random random = new Random();
    
        for(int col = 0; col < currState[0].length; col++){
            int row = random.nextInt(currState.length);
            currState[row][col] = 1;
        }
        printState(currState);
    return currState;
    }
// Counts the number of conflicts in the state space
    public static int checkConflicts(int[][] state){
        int size = state.length;
        int conflictCount = 0;
    
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (state[row][col] == 1) {
                    // row conflicts
                    for (int c = 0; c < size; c++) {
                        if (c != col && state[row][c] == 1) {
                            conflictCount++;
                        }
                    }
    
                    // left diagonal conflicts
                    for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
                        if (state[r][c] == 1) {
                            conflictCount++;
                        }
                    }
    
                    // right diagonal conflicts
                    for (int r = row - 1, c = col + 1; r >= 0 && c < size; r--, c++) {
                        if (state[r][c] == 1) {
                            conflictCount++;
                        }
                    }
                }
            }
        }
    
        return conflictCount;    
    }
// Lower the number of conflicts  
public static int[][] minimizeConflicts(int[][] state) {
    int size = state.length;
    int minConflicts = checkConflicts(state);
    int[][] minState = new int[size][size];
    int stateSpacesFound = 0;

    // Iterate over each column
    for (int col = 0; col < size; col++) {
        int originalRow = findQueenRow(state, col); // Find the current row of the queen in the column

        // Iterate over each row in the column, starting from the next row
        for (int row = 0; row < size; row++) {
            // Temporarily remove the queen from the original row
            state[originalRow][col] = 0;

            // Move the queen to the current row
            state[row][col] = 1;

            // Calculate the number of conflicts in the updated state
            int conflicts = checkConflicts(state);

            // If the updated state has fewer conflicts, update the minimum conflicts and the minimum state
            if (conflicts < minConflicts) {
                minConflicts = conflicts;
                stateSpacesFound = 1; // Reset the count and update it for the new minimum conflicts
                // Copy the updated state to the minState
                for (int i = 0; i < size; i++) {
                    System.arraycopy(state[i], 0, minState[i], 0, size);
                }
            } else if (conflicts == minConflicts) {
                stateSpacesFound++; // Increment the count for state spaces with the same minimum conflicts
            }

            // Reset the position to 0 for further iterations
            state[row][col] = 0;
        }

        // Restore the queen to its original row
        state[originalRow][col] = 1;
    }

    System.out.println("Current # of state spaces found with lower conflicts: " + stateSpacesFound);
    return minState;
}

// Helper method to find the row of the queen in a given column
    public static int findQueenRow(int[][] state, int col) {
    int size = state.length;
    for (int row = 0; row < size; row++) {
        if (state[row][col] == 1) {
            return row;
        }
    }
    return -1;
}
// print state
    public static void printState(int[][] state) {
        System.out.println("Current # of conflicts: " +checkConflicts(state) );
        System.out.println("Current State");

        for (int[] row : state) {
            for (int element : row) {
                System.out.print(element + ",");
            }
            System.out.println();
        }
        System.out.println("Setting new current state");
        System.out.println("");
    }
}