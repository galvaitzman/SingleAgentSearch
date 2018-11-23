import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main 
{

	public static void main(String [ ] args)
	{
		
		System.out.println("Start!");
		//task1();
		//task2();
		//task3();
		//task4();
		//task5();
		//task6();
		System.out.println("");
		System.out.println("Done!");


		double heuristicNumber = 0;
		int[][] tilePuzzleState = {{2,5,4,7},{9,1,6,8},{0,10,3,11},{13,14,15,12}};
		int expected = 0;
		int size= tilePuzzleState.length;
		for (int row = 0; row < size; row+=1) {
			for (int column = 0; column < size; column+=1) {
				int value = tilePuzzleState[row][column];
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
		System.out.println(heuristicNumber);

	}

	public static void task1()
	{
		System.out.println("---------- Task 1 ----------");
		List<String> ids = IDs.getIDs();
		System.out.print("Students ids: ");
		for (String id : ids)
			System.out.print(id + " | ");
	}
	
	public static void task2()
	{
		System.out.println("---------- Task 2 ----------");
		List<ASearch> 		solvers 	= new ArrayList<ASearch>();
		BreadthFirstSearch 	bfs 		= new BreadthFirstSearch();
		solvers.add(bfs);
		solveInstances(solvers, "tile3x3");
	}
	
	public static void task3()
	{
		System.out.println("---------- Task 3 -----------");
		List<ASearch> 		solvers 	= new ArrayList<ASearch>();
		UniformCostSearch 	ucs 		= new UniformCostSearch();
		solvers.add(ucs);
		solveInstances(solvers, "tile3x3");
	}
	
	public static void task4()
	{
		System.out.println("---------- Task 4 -----------");
		List<ASearch> 		solvers 	= new ArrayList<ASearch>();
		PureHeuristicSearch phs 		= new PureHeuristicSearch();
		solvers.add(phs);
		solveInstances(solvers, "tile3x3");
	}
	
	public static void task5()
	{
		System.out.println("---------- Task 5 -----------");
		List<ASearch> 		solvers 	= new ArrayList<ASearch>();
		AStarSearch 		astar 		= new AStarSearch();
		solvers.add(astar);
		solveInstances(solvers, "tile3x3");
	}
	
	public static void task6()
	{
		System.out.println("---------- Task 6 -----------");
		List<ASearch> 		solvers 	= new ArrayList<ASearch>();
		AStarSearch 		astar 		= new AStarSearch();
		solvers.add(astar);
		solveInstances(solvers, "tile4x4");
	}
	
	public static void solveInstances
	(
		List<ASearch> 	solvers,
		String 			instancesType
	) 
	{
		try 
		{
			long			totalTime = 0;
			List<String> 	instances = getInstances(instancesType);
			for (String instance : instances)
			{
				System.out.println("---- " + instance.substring(instance.indexOf("tile_")) + " ----");
				TilePuzzle 			problem 	= new TilePuzzle(instance);
				for (ASearch solver : solvers)
				{
					System.out.println("Solver: " + solver.getSolverName());
					long 				startTime 	= System.nanoTime();
					List<IProblemMove> 	solution 	= solver.solve(problem);
					long 				finishTime 	= System.nanoTime();
					double 				cost 		= checkSolution(problem, solution);
					if (cost >= 0)		// valid solution
					{
						// printSolution(problem, solution);
						System.out.println("Cost:  " + cost);
						System.out.println("Moves: " + solution.size());
						System.out.println("Time:  " + (finishTime - startTime)/1000000.0 + " ms");
						System.out.println(solution);
						totalTime += (finishTime - startTime)/1000000.0;
					}
					else				// invalid solution
						System.out.println("Invalid solution.");
				}
				System.out.println("");
			}
			System.out.println("Total time:  " + totalTime/60000.0 + " min");
			System.out.println("");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static List<String> getInstances
	(
		String type
	) throws IOException
	{
		List<String> instances = new ArrayList<String>();
		String currentDir = new java.io.File( "." ).getCanonicalPath() + "\\instances\\" + type + "\\";
		File folder = new File(currentDir);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				instances.add(currentDir + listOfFiles[i].getName());
			} 
		}
		return instances;
	}
	
	public static double checkSolution
	(
		IProblem 			instance,
		List<IProblemMove> 	solution
	)
	{
		if (solution == null)
			return -1;
		double cost = 0;
		IProblemState currentState = instance.getProblemState();
		for (IProblemMove move : solution)
		{
			currentState = currentState.performMove((TilePuzzleMove)move);
			if (currentState.getStateLastMove() != null)
				cost += currentState.getStateLastMoveCost();
		}
		if (currentState.isGoalState())
			return cost;
		return -1;
	}
	
	public static void printSolution
	(
		IProblem 			instance,
		List<IProblemMove> 	solution
	)
	{
		IProblemState currentState = instance.getProblemState();
		for (IProblemMove move : solution)
		{
			currentState = currentState.performMove((TilePuzzleMove)move);
			System.out.println(move);
			System.out.println(currentState);
		}
	}


	// helper to get a board in final state
	public static int[][] getFinalState(int size) {
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
	public static int getRow(int[][] a, int value) {
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
	public static int getCol(int[][] a, int value) {
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
