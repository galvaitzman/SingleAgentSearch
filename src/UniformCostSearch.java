import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class UniformCostSearch   extends ASearch
{
	// Define lists here ...
	PriorityQueue <ASearchNode> openList ;
	ArrayList<ASearchNode> closeList;


	@Override
	public String getSolverName()
	{
		return "UCS";
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
		closeList = new ArrayList<>();
		openList = new PriorityQueue<>(new Comparator<ASearchNode>() {
			@Override
			public int compare(ASearchNode searchNode1, ASearchNode searchNode2) {
				if(searchNode1.getG() <= searchNode2.getG())
					return -1;
				return 1;
			}
		});
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
		return openList.contains(node);
	}

	@Override
	public boolean isClosed(ASearchNode node)
	{
		return closeList.contains(node);
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
