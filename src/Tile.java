import java.util.*;
public class Tile {
	
	private int touching;
	private boolean mine;
	private ArrayList<Tile> adj;
	private int[] xy;
	private boolean flagged;
	
	public Tile(int x, int y)
	{
		this.xy = new int[] {x,y};
		mine=false;
		touching=0;
		flagged=false;
	}
	
	public int[] getXY()
	{
		return xy;
	}
	
	public boolean hasMine()
	{
		return mine;
	}
	
	public ArrayList<Tile> getAdjList()
	{
		return adj;
	}

}
