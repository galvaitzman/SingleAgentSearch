import java.util.ArrayList;
import java.util.List;

public class TilePuzzleState implements IProblemState
{
	TilePuzzle 		_problem;		// The original problem
	int[][] 		_tilePuzzle;	// Current state
	int 			_zeroRow;		// Current row of the blank
	int				_zeroCol;		// Current column of the blank
	TilePuzzleMove 	_lastMove;		// The last move made
	
	
	public TilePuzzleState
	(
		TilePuzzle 		problem,
		int[][]			tilePuzzle,
		int				zeroRow,
		int				zeroCol,
		TilePuzzleMove 	lastMove
	)
	{
		_problem	= problem;
		_tilePuzzle	= tilePuzzle;
		_zeroRow	= zeroRow;
		_zeroCol	= zeroCol;
		_lastMove	= lastMove;
	}

	@Override
	public List<IProblemState> getNeighborStates() 
	{
		List<IProblemState>		neighborStates 	= new ArrayList<IProblemState>();
		List<TilePuzzleMove> 	legalMoves 		= getLegalMoves();
		
		for (TilePuzzleMove move : legalMoves)
		{
			IProblemState newState = performMove(move);
			neighborStates.add(newState);
		}
		return neighborStates;
	}
	
	@Override
	public IProblem getProblem()
	{
		return _problem;
	}
	
	@Override
	public boolean isGoalState() 
	{
		int size = _problem._size;
		
		for (int row = 0; row < size; row ++)
			for (int col = 0; col < size; col ++)
				if (_tilePuzzle[row][col] != 0 						&&
					_tilePuzzle[row][col] != row * size + col + 1)
					return false;
		return true;
	}

	@Override
	public IProblemMove getStateLastMove() 
	{
		return _lastMove;
	}
	
	@Override
	public double getStateLastMoveCost() 
	{
		if (_lastMove._move == TilePuzzleMove.MOVE.DOWN)
			return _tilePuzzle[_zeroRow + 1][_zeroCol];
		if (_lastMove._move == TilePuzzleMove.MOVE.UP)
			return _tilePuzzle[_zeroRow - 1][_zeroCol];
		if (_lastMove._move == TilePuzzleMove.MOVE.RIGHT)
			return _tilePuzzle[_zeroRow][_zeroCol + 1];
		if (_lastMove._move == TilePuzzleMove.MOVE.LEFT)
			return _tilePuzzle[_zeroRow][_zeroCol - 1];
		else
			return 0;
	}
	
	@Override
	public boolean equals
	(
		Object obj
	)
	{
		if (obj instanceof TilePuzzleState)
		{
			TilePuzzleState otherState = (TilePuzzleState)obj;
			if (java.util.Arrays.deepEquals(_tilePuzzle, otherState._tilePuzzle))
				return true;
		}
		return false;
	}
	
	public IProblemState performMove
	(
		IProblemMove move
	)
	{		
		if (!(move instanceof TilePuzzleMove))
			return null;
		TilePuzzle 		newProblem		= _problem;
		int[][] 		newTilePuzzle 	= getTilePuzzleCopy();
		int 			newZeroRow 		= _zeroRow; 
		int 			newZeroCol 		= _zeroCol;
		
		TilePuzzleMove	tilePuzzleMove 	= (TilePuzzleMove)move;
				
		// Find the moving cell 
		if (tilePuzzleMove._move == TilePuzzleMove.MOVE.DOWN)
			newZeroRow --;
		else if (tilePuzzleMove._move == TilePuzzleMove.MOVE.UP)
			newZeroRow ++;
		else if (tilePuzzleMove._move == TilePuzzleMove.MOVE.RIGHT)
			newZeroCol --;
		else if (tilePuzzleMove._move == TilePuzzleMove.MOVE.LEFT)
			newZeroCol ++;
		
		// Validation
		if (outOfBoundaries(newZeroRow, newZeroCol))
			return null;
		
		// Perform the action
		newTilePuzzle[_zeroRow][_zeroCol] 		= newTilePuzzle[newZeroRow][newZeroCol];
		newTilePuzzle[newZeroRow][newZeroCol]	= 0;
		
		// Create new state
		TilePuzzleState newTilePuzzleState 		= new TilePuzzleState(newProblem, newTilePuzzle, newZeroRow, newZeroCol, tilePuzzleMove);
		return newTilePuzzleState;
	}
	
	private List<TilePuzzleMove> getLegalMoves() 
	{
		int size = _problem._size;
		
		List<TilePuzzleMove> moveList = new ArrayList<TilePuzzleMove>();
		if (_zeroRow != 0)
			moveList.add(new TilePuzzleMove(TilePuzzleMove.MOVE.DOWN));
		if (_zeroRow != size - 1)
			moveList.add(new TilePuzzleMove(TilePuzzleMove.MOVE.UP));
		if (_zeroCol != 0)
			moveList.add(new TilePuzzleMove(TilePuzzleMove.MOVE.RIGHT));
		if (_zeroCol != size - 1)
			moveList.add(new TilePuzzleMove(TilePuzzleMove.MOVE.LEFT));
		return moveList;
	}
	
	private boolean outOfBoundaries
	(
		int row,
		int col
	)
	{
		int size = _problem._size;
		
		if (row < 0			||
			row >= size		||
			col < 0			||
			col >= size)
			return true;
		return false;
	}
	
	private int[][] getTilePuzzleCopy()
	{
		int 	size 			= _problem._size;
		int[][] newTilePuzzle	= new int[size][size];
		
		for (int row = 0; row < size; row ++)
			for (int col = 0; col < size; col ++)
			{
				newTilePuzzle[row][col] = _tilePuzzle[row][col];
			}
		return newTilePuzzle;
	}

	@Override
	public String toString()
	{
		int size = _problem._size;
		String toPrint = "";
		for(int row = 0; row < size; row ++)
		{
			for (int col = 0; col<size;col++)
			{
				toPrint += _tilePuzzle[row][col] + "|";
			}
			toPrint += "\n";
		}
		return toPrint;
	}

}
