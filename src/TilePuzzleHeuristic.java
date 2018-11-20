
public class TilePuzzleHeuristic implements IHeuristic {

	@Override
	public double getHeuristic(IProblemState problemState) {
		double heuristicNumber = 0;
		if (problemState.isGoalState()) return heuristicNumber;
		TilePuzzleState tilePuzzleState = (TilePuzzleState) problemState;
		int expected = 0;
		int size= tilePuzzleState._problem._size;
		for (int row = 0; row < size; row+=1) {
			for (int column = 0; column < size; column+=1) {
				int value = tilePuzzleState._tilePuzzle[row][column];
				expected++;
				if (value != 0 && value != expected) {
					// Manhattan distance is the sum of the absolute values of
					// the horizontal and the vertical distance
					heuristicNumber += value * (Math.abs(row
							- getRow(getFinalState(size), value))
							+ Math.abs(column
							- getCol(getFinalState(size), value)));
				}
			}
		}
		return heuristicNumber;
	}

	// helper to get a board in final state
	private int[][] getFinalState(int size) {
		int[][] finalArray = new int[size][size];
		int value = 0;

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				value++;
				if ((col + 1 == size) && (row + 1 == size)) {
					finalArray[row][col] = 0;
				} else {
					finalArray[row][col] = value;
				}
			}
		}
		return  finalArray;
	}
	// helper to get the row of a value.
	private int getRow(int[][] a, int value) {
		for (int row = 0; row < a.length; row++) {
			for (int col = 0; col < a[row].length; col++) {
				if (a[row][col] == value) {
					return row;
				}
			}
		}
		return -1;
	}

	// helper to get the column of a value.
	private int getCol(int[][] a, int value) {
		for (int row = 0; row < a.length; row++) {
			for (int col = 0; col < a[row].length; col++) {
				if (a[row][col] == value) {
					return col;
				}
			}
		}
		return -1;
	}
}


