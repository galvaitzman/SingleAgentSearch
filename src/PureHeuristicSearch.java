
public class PureHeuristicSearch  extends ASearch
{
	// Define lists here ...
	
	@Override
	public String getSolverName() 
	{
		return "PHS";
	}

	@Override
	public ASearchNode createSearchRoot
	(
		IProblemState problemState
	) 
	{
		ASearchNode newNode = new HeuristicSearchNode(problemState);
		return newNode;
	}
	
	@Override
	public void initLists() 
	{

	}

	@Override
	public ASearchNode getOpen
	(
		ASearchNode node
	) 
	{
		return null;
	}

	@Override
	public boolean isOpen
	(
		ASearchNode node
	) 
	{
		return false;
	}
	
	@Override
	public boolean isClosed
	(
		ASearchNode node
	) 
	{
		return false;
	}

	

	@Override
	public void addToOpen
	(
		ASearchNode node
	) 
	{

	}

	@Override
	public void addToClosed
	(
		ASearchNode node
	) 
	{

	}

	@Override
	public int openSize() 
	{
		return 0;
	}

	@Override
	public ASearchNode getBest() 
	{
		return null;
	}

}