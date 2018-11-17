import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TilePuzzle implements IProblem
{
	public enum MOVECOST {STANDARD, INCREASING}

	int[][] 			_tilePuzzle;	// Problem instance
	int					_size;			// Puzzle size
	int 				_zeroRow;		// The row of the blank
	int					_zeroCol;		// The column of the blank
	IHeuristic			_heuristic;		// The problem heuristic

	public TilePuzzle()
	{
		_heuristic = new TilePuzzleHeuristic();
	}
	
	public TilePuzzle
	(
		String problemName
	)
	{
		importInstance(problemName);
		_heuristic = new TilePuzzleHeuristic();
	}
	
	
	@Override
	public IProblemState getProblemState() 
	{
		TilePuzzleState root = new TilePuzzleState(this, _tilePuzzle, _zeroRow, _zeroCol, null);
		return root;
	}
	
	@Override
	public IHeuristic getProblemHeuristic()
	{
		return _heuristic;
	}

	@Override
	public boolean performMove
	(
		IProblemMove move
	) 
	{
		int tempRow = _zeroRow; 
		int tempCol = _zeroCol;
		if (move instanceof TilePuzzleMove)
		{
			TilePuzzleMove tilePuzzleMove = (TilePuzzleMove)move;
			
			// Find the moving cell 
			if (tilePuzzleMove._move == TilePuzzleMove.MOVE.UP)
				tempRow --;
			else if (tilePuzzleMove._move == TilePuzzleMove.MOVE.DOWN)
				tempRow ++;
			else if (tilePuzzleMove._move == TilePuzzleMove.MOVE.LEFT)
				tempCol --;
			else if (tilePuzzleMove._move == TilePuzzleMove.MOVE.RIGHT)
				tempCol ++;
			
			// Validation
			if (outOfBoundaries(tempRow, tempCol))
				return false;
			
			// Perform the action
			_tilePuzzle[_zeroRow][_zeroCol] = _tilePuzzle[tempRow][tempCol];
			_tilePuzzle[tempRow][tempCol]	= 0;
			_zeroRow 						= tempRow;
			_zeroCol 						= tempCol;
			
			return true;
		}			
		return false;
	}

	private void importInstance
	(
		String problemName
	)
	{
		// Get the file
		File file 	= new File(problemName); 
	    Scanner sc;
		try 
		{
			sc = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		} 
	    
		// Read the file
	    while (sc.hasNextLine()) 
	    {
	    	String cuurentLine = sc.nextLine();
	    	if (cuurentLine.contains("Size:"))					// Puzzle size
	    	{
	    		cuurentLine = sc.nextLine();
	    		_size 		= Integer.parseInt(cuurentLine);
	    		_tilePuzzle = new int [_size][_size];
	    	}
	    	else if (cuurentLine.contains("Puzzle:"))			// Puzzle instance
	    	{
	    		for (int row = 0; row < _size; row ++)
	    		{
	    			cuurentLine 	= sc.nextLine();
	    			String[] tokens = cuurentLine.split("\\|");
	    			for (int col = 0; col < _size; col ++)
	    			{
	    				_tilePuzzle[row][col] = Integer.parseInt(tokens[col]);
	    				if (_tilePuzzle[row][col] == 0)
	    				{
	    					_zeroRow = row;
	    					_zeroCol = col;
	    				}
	    			}
	    		}
	    	}
	    }
	    sc.close();
	}
	
	private boolean outOfBoundaries
	(
		int row,
		int col
	)
	{
		if (row < 0			||
			row >= _size	||
			col < 0			||
			col >= _size)
			return true;
		return false;
	}

}
