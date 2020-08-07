import java.util.Random;
import java.util.*;


public class Game {
	private int size;
	private int numMines;
	private boolean[][] board;
	private int[][] touchBoard;
	private int minesLeft;
	private int flags;
	private ArrayList<int[]> notMines= new ArrayList<int[]>();
	private boolean[][] boardInv;
	
	
	public Game(int size, int numMines)
	{
		this.size = size;
		this.numMines = numMines;
		this.flags = numMines;
		this.board = new boolean[size][size];
		this.boardInv = new boolean[size][size];
		this.touchBoard = new int[size][size];
		for (int i = 0; i<size;i++)
		{
			for( int j = 0;j< size;j++)
			{
				board[i][j] = false;
				notMines.add(new int[] {i,j});
			}
		}
		
		
		makeBoard();
		
	}
	
	public void makeBoard ()
	{
		int remaining = numMines;
		
		Random r = new Random();
		
		while (remaining > 0)
		{
			int x = r.nextInt(notMines.size());
			
			int[] temp= notMines.get(x);
			notMines.remove(x);
			board[temp[0]][temp[1]] = true;
			remaining--;
		}
		for (int i = 0; i<size;i++)
		{
			for( int j = 0;j< size;j++)
			{
				touchBoard[i][j] = minesTouching(i,j);
			}
		}
	}
	
	public void makeBoard (int m, int x, int y)
	{
		int remaining = m;
		
		Random r = new Random();
		
		while (remaining > 0)
		{
			int a= r.nextInt(notMines.size());
			int[] temp = notMines.get(a);
			int row = temp[1];
			int col = temp[0];
			notMines.remove(a);
			board[temp[0]][temp[1]] = true;
			remaining--;
			if (col>0 && row>0)
			{
				touchBoard[col-1][row-1]++;
			}
			if (col > 0) 
			{
			
				touchBoard[col-1][row]++;

				if (row < size-1)
				{
					touchBoard[col-1][row+1]++;
						
				}
			}
			if (col<size-1 && row<size-1)
			{
				touchBoard[col+1][row+1]++;
					
			}
			if (col < size-1)
			{
				touchBoard[col+1][row]++;
					
				if (row > 0)
				{
					touchBoard[col+1][row-1]++;
						
				}
				
			}
			if (row > 0)
			{
				touchBoard[col][row-1]++;
					
			}
			if (row < size-1)
			{
				touchBoard[col][row+1]++;
					
			}
		}
		
		for (int i = 0; i<size;i++)
		{
			for( int j = 0;j< size;j++)
			{
				if (board[i][j])
				{
					boardInv[i][j] = false;
					
				}
				else
				{
					boardInv[i][j] = true;
				}
				touchBoard[i][j] = minesTouching(i,j);
			}
		}
		
	}
	
	public void makeSafeSpace(int x, int y)
	{
		int numChanged = 0;
		if (board[x][y])
		{
			numChanged++;
			board[x][y] = false;
		}
		else
		{
			notMines.remove(new int[] {x,y});
		}
		
		if (x>0 && y>0)
		{
			if(board[x-1][y-1])
			{
				numChanged++;
				board[x-1][y-1] = false;
			}
			else
			{
				int[] temp = new int[] {x-1,y-1};
				notMines.remove(temp);
			}
			
		}
		if (x > 0)
		{
			if(board[x-1][y])
			{
				numChanged++;
				board[x-1][y] = false;
			}
			else
			{
				int[] temp = new int[] {x-1,y};
				notMines.remove(temp);
			}

			if (y < size-1)
			{
				if(board[x-1][y+1])
				{
					numChanged++;
					board[x-1][y+1] = false;
				}
				else
				{
					int[] temp = new int[] {x-1,y+1};
					notMines.remove(temp);
				}
			}
		}
		if (x<size-1 && y<size-1)
		{
			if(board[x+1][y+1])
			{
				numChanged++;
				board[x+1][y+1] = false;
			}
			else
			{
				int[] temp = new int[] {x+1,y+1};
				notMines.remove(temp);
			}
		}
		if (x < size-1)
		{
			if(board[x+1][y])
			{
				numChanged++;
				board[x+1][y] = false;
			}
			else
			{
				int[] temp = new int[] {x+1,y};
				notMines.remove(temp);
			}
			
			if (y > 0)
			{
				if(board[x+1][y-1])
				{
					numChanged++;
					board[x+1][y-1] = false;
				}
				else
				{
					int[] temp = new int[] {x+1,y-1};
					notMines.remove(temp);
				}
			}
			
		}
		if (y > 0)
		{
			if(board[x][y-1])
			{
				numChanged++;
				board[x][y-1] = false;
			}
			else
			{
				int[] temp = new int[] {x,y-1};
				notMines.remove(temp);
			}
		}
		if (y < size-1)
		{
			if(board[x][y+1])
			{
				numChanged++;
				board[x][y+1] = false;
			}
			else
			{
				int[] temp = new int[] {x,y+1};
				notMines.remove(temp);
			}
		}
		makeBoard(numChanged,x,y);
	}
	
	public int minesTouching (int x, int y)
	{
		int num = 0;
		if (x>0 && y>0)
		{
			if(board[x-1][y-1])
				num++;
		}
		if (x > 0)
		{
			if(board[x-1][y])
				num++;

			if (y < size-1)
			{
				if(board[x-1][y+1])
					num++;
			}
		}
		if (x<size-1 && y<size-1)
		{
			if(board[x+1][y+1])
				num++;
		}
		if (x < size-1)
		{
			if(board[x+1][y])
				num++;
			if (y > 0)
			{
				if(board[x+1][y-1])
					num++;
			}
			
		}
		if (y > 0)
		{
			if(board[x][y-1])
				num++;
		}
		if (y < size-1)
		{
			if(board[x][y+1])
				num++;
		}
		return num;
	}
	
	public void changeFlag(int x)
	{
		flags += x;
	}
	
	public int getFlags()
	{
		return flags;
	}
	
	public int getMinesLeft()
	{
		return minesLeft;
	}
	
	public int[][] getTouchBoard()
	{
		return touchBoard;
	}
	public int getSize()
	{
		return size;
	}
	
	public boolean[][] getBoard()
	{
		return board;
	}
	
	public boolean[][] getBoardInv()
	{
		return boardInv;
	}
}
