import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch  extends ASearch
{
	// Define lists here ...
	Queue<ASearchNode> openList;
	ArrayList<ASearchNode> closeList;


	@Override
	public String getSolverName()
	{
		return "BFS";
	}

	@Override
	public ASearchNode createSearchRoot(IProblemState problemState)
	{
		ASearchNode newNode = new BlindSearchNode(problemState);
		return newNode;
	}

	@Override
	public void initLists()
	{
		openList = new LinkedList<>();
		closeList = new ArrayList<>();
	}

	@Override
	public ASearchNode getOpen(ASearchNode node)
	{
		if(openList.contains(node))
			return node;
		return null;
	}

	@Override
	public boolean isOpen(ASearchNode node)
	{
		return(openList.contains(node));
	}

	@Override
	public boolean isClosed(ASearchNode node)
	{
		return(closeList.contains(node));
	}

	@Override
	public void addToOpen(ASearchNode node)
	{
		openList.add(node);
	}

	@Override
	public void addToClosed(ASearchNode node)
	{
		closeList.add(node);
	}

	@Override
	public int openSize()
	{
		return openList.size();
	}

	@Override
	public ASearchNode getBest()
	{
		return openList.poll();
	}
}
