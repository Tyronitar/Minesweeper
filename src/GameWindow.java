import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

class GameWindow extends JPanel implements ActionListener{
	private int size;
	private int gameSize;
	private int spaceSize;
	private Game game;
	private boolean play;
	private boolean[][] board;
	private boolean[][] revealedBoard;
	private boolean[][] flagBoard;
	private int[][] touchBoard;
	private boolean[][] boardInv;
	private int clicks = 0;
	File img;
	BufferedImage im;
	File img2;
	BufferedImage im2;
	private boolean ohYeah;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	
	public GameWindow(int size, Game game)
	{ 
		this.play = true;
		this.size = size;
		this.game = game;
		this.board = game.getBoard();
		this.boardInv = game.getBoardInv();
		this.gameSize = game.getSize();
		this.touchBoard = game.getTouchBoard();
		this.spaceSize = (int)size/gameSize;
		
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        add(Box.createHorizontalGlue());
		this.b1 = new JButton("Easy");
        this.b2 = new JButton("Medium");
        this.b3 = new JButton("Hard");		
        b1.setMaximumSize(new Dimension(130,80));
		b2.setMaximumSize(new Dimension(130,80));
		b3.setMaximumSize(new Dimension(130,80));
		b1.setActionCommand("easy");
		b2.setActionCommand("medium");
		b3.setActionCommand("hard");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
        b1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        b3.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(b1);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(b2);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(b3);
		
		
		try 
    	{ 
			if (gameSize == 8)
    		{
				this.img = new File("D:\\Programming\\Minesweeper\\src\\flag1.png");
				this.img2 = new File("D:\\Programming\\Minesweeper\\src\\space1.png");
    		}
			else if (gameSize == 16)
    		{
				this.img = new File("D:\\Programming\\Minesweeper\\src\\flag2.png");
				this.img2 = new File("D:\\Programming\\Minesweeper\\src\\space2.png");
    		}
			else if (gameSize == 24)
    		{
				this.img = new File("D:\\Programming\\Minesweeper\\src\\flag3.png");
				this.img2 = new File("D:\\Programming\\Minesweeper\\src\\space3.png");
    		}
    		BufferedImage inputImage = ImageIO.read(img);
    		BufferedImage inputImage2 = ImageIO.read(img2);
    		
    		ohYeah = true;
    		this.im = inputImage;
    		this.im2 = inputImage2;
    	}
    	catch (IOException e) { 
    		System.out.println("Oof");
    		ohYeah = false;
    	
    	} 
		getPreferredSize();
		
		this.revealedBoard = new boolean[gameSize][gameSize];
		this.flagBoard = new boolean[gameSize][gameSize];
		for (int i = 0; i<gameSize;i++)
		{
			for (int j = 0; j<gameSize;j++)
			{
				revealedBoard[i][j] = false;
				flagBoard[i][j] = false;
			}
		}
		
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e)
            {
            	if(play)
            	{
            		if (e.getX() < size && e.getY() < size)
            		{
	            		if (e.getButton()==1)
	            		{
	            			if (clicks == 0)
	            			{
	            				try
	            				{
	            				game.makeSafeSpace(posToGrid(e.getX()),posToGrid(e.getY()));
	            				board = game.getBoard();
	            				boardInv = game.getBoardInv();
	            				}
	            				catch (ArrayIndexOutOfBoundsException l)
	            				{
	            					System.err.println("");
	            				}
	            			}
	            			clicks++;
	            			revealSquare(e.getX(),e.getY());
	            		}
	            		else if (e.getButton()==3)
	            			flag(e.getX(),e.getY());
            		}
            			
            	}
            }
           
        });
	}
	public void actionPerformed(ActionEvent e) { 
		if ("easy".equals(e.getActionCommand()))
		{	
	        Main.createAndShowGUI(this,700,new Game(8,10));
	    }
		else if ("medium".equals(e.getActionCommand()))
		{
	        Main.createAndShowGUI(this,700,new Game(16,40));
	    } 
		else if ("hard".equals(e.getActionCommand()))
		{
	        Main.createAndShowGUI(this,700, new Game(24,99));
	    }
	}
	public void revealSquare(int x, int y)
	{
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		int column =posToGrid(x);
		int row = posToGrid(y);
		try
		{
			revealedBoard[column][row] = true;
			if (board[column][row])
			{
				g2.setColor(new Color(151, 151, 151));
	        	g2.fillRect(gridToPos(column)+1,gridToPos(row)+1,spaceSize-2,spaceSize-2);
	        	g2.setColor(Color.GRAY);
	        	g2.setStroke(new BasicStroke(2));
	        	g2.drawRect(gridToPos(column)+1,gridToPos(row)+1,spaceSize-2,spaceSize-2);
				g2.setColor(Color.BLACK);
				g2.fillOval(gridToPos(column),gridToPos(row),spaceSize,spaceSize);
				for (int r =0; r < gameSize;r++)
				{
					for (int c =0; c < gameSize;c++)
					{
						if (board[c][r])
						{
							g2.setColor(new Color(151, 151, 151));
				        	g2.fillRect(gridToPos(c)+1,gridToPos(r)+1,spaceSize-2,spaceSize-2);
				        	g2.setColor(Color.GRAY);
				        	g2.setStroke(new BasicStroke(2));
				        	g2.drawRect(gridToPos(c)+1,gridToPos(r)+1,spaceSize-2,spaceSize-2);
							g2.setColor(Color.BLACK);
							g2.fillOval(gridToPos(c),gridToPos(r),spaceSize,spaceSize);
						}
					}
				}
				g2.setColor(new Color(114,0,0));
				Font f1 = new Font("helvetica",Font.BOLD,100);
				FontMetrics metrics = g.getFontMetrics(f1);
				String str = "GAME OVER";
				int c = 0 + (size - metrics.stringWidth(str)) / 2;
				int r = 0 + ((size - metrics.getHeight()) / 2) + metrics.getAscent();
				g2.setFont(f1);
				g2.drawString(str, c, r);
				play=false;
				
			}
			else
			{
				if (flagBoard[column][row])
				{
					flagBoard[column][row] = false;
					game.changeFlag(1);
					g2.setColor(Color.LIGHT_GRAY);
		            g2.fillRect(700, 250, 200, 200);
					g2.setColor(Color.BLACK);
					Font f1 = new Font("helvetica",Font.BOLD,20);
					String str = "FLAGS LEFT: \n"+ game.getFlags();
					g2.setFont(f1);
					g2.drawString(str, size+10, 300);
				}
	        	g2.setColor(new Color(151, 151, 151));
	        	g2.fillRect(gridToPos(column)+1,gridToPos(row)+1,spaceSize-2,spaceSize-2);
	        	g2.setColor(Color.GRAY);
	        	g2.setStroke(new BasicStroke(2));
	        	g2.drawRect(gridToPos(column)+1,gridToPos(row)+1,spaceSize-2,spaceSize-2);
	        	if (touchBoard[column][row] != 0)
	        	{
	        		Font f1 = new Font("helvetica",Font.BOLD,(2*spaceSize)/3);
		        	FontMetrics metrics = g.getFontMetrics(f1);
				    // Determine the X coordinate for the text
				    int c = (gridToPos(column)+1) + (spaceSize - metrics.stringWidth(""+ touchBoard[column][row])) / 2;
				    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
				    int r = (gridToPos(row)+1) + ((spaceSize - metrics.getHeight()) / 2) + metrics.getAscent();
				    // Draw the String
				    g2.setFont(f1);
				    if (touchBoard[column][row]==1)
				    	g2.setColor(new Color(14, 53, 153));
				    else if (touchBoard[column][row]==2)
				    	g2.setColor(new Color(14, 153, 80));
				    else if (touchBoard[column][row]==3)
				    	g2.setColor(new Color(153, 14, 14));
				    else if (touchBoard[column][row]==4)
				    	g2.setColor(new Color(93, 15, 153));
				    else
				    	g2.setColor(Color.BLACK);
		        	g.drawString(""+ touchBoard[column][row], c, r);
	        	}
	        	else if (touchBoard[column][row] == 0)
	        	{
		        	if (column > 0 && row > 0)
		        	{
			        	if(!revealedBoard[column-1][row-1])
			        		revealSquare(x-spaceSize,y-spaceSize);
		        	}
		        	if (column > 0)
		    		{
		        		if(!revealedBoard[column-1][row])
		            		revealSquare(x-spaceSize,y);
		
		    			if (row < gameSize-1)
		    			{
		    				if(!revealedBoard[column-1][row+1])
		                		revealSquare(x-spaceSize,y+spaceSize);
		    			}
		    		}
		        	if (column < gameSize-1 && row < gameSize-1)
		        	{
			    		if(!revealedBoard[column+1][row+1])
			        		revealSquare(x+spaceSize,y+spaceSize);
		        	}
		    		
		    		if (column < gameSize-1)
		    		{
		    			if(!revealedBoard[column+1][row])
		            		revealSquare(x+spaceSize,y);
		    			if (row > 0)
		    			{
		    				if(!revealedBoard[column+1][row-1])
		                		revealSquare(x+spaceSize,y-spaceSize);
		    			}
		    			
		    		}
		    		if (row > 0)
		    		{
						if(!revealedBoard[column][row-1])
			        		revealSquare(x,y-spaceSize);
		    		}
					
		    		if (row < gameSize-1)
		    		{
						if(!revealedBoard[column][row+1])
			        		revealSquare(x,y+spaceSize);
		    		}
	        	}
			}
			testWin();
		}
		catch (ArrayIndexOutOfBoundsException l)
		{
			System.err.println("");
		}
	}
	
	public void flag(int x, int y)
	{
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		int column =posToGrid(x);
		int row = posToGrid(y);
		if (!revealedBoard[column][row])
		{
			if (!flagBoard[column][row] && game.getFlags()>0)
			{
				if (ohYeah)
				{
		    		g2.drawImage(im, gridToPos(column), gridToPos(row), null);
				}
				else
				{
				g2.setColor(Color.RED);
	        	g2.fillRect(gridToPos(column)+spaceSize/6,gridToPos(row)+spaceSize/6,(int)(2*spaceSize/3),(int)(2*spaceSize/3));
				}
	        	flagBoard[column][row]=true;
	        	game.changeFlag(-1);
	        	g2.setColor(Color.LIGHT_GRAY);
	            g2.fillRect(700, 250, 200, 200);
				g2.setColor(Color.BLACK);
				Font f1 = new Font("helvetica",Font.BOLD,20);
				String str = "FLAGS LEFT: \n"+ game.getFlags();
				g2.setFont(f1);
				g2.drawString(str, size+10, 300);
	        	
			}
			else if (flagBoard[column][row])
			{
				if (ohYeah)
				{
		    		g2.drawImage(im2, gridToPos(column), gridToPos(row), null);
				}
				else
				{
					g2.setColor(new Color(171, 171, 171));
	        		g2.fill3DRect(gridToPos(column), gridToPos(row), spaceSize, spaceSize,true);
				}
				flagBoard[column][row]=false;
				game.changeFlag(1);
				g2.setColor(Color.LIGHT_GRAY);
		        g2.fillRect(700, 250, 200, 200);
				g2.setColor(Color.BLACK);
				Font f1 = new Font("helvetica",Font.BOLD,20);
				String str = "FLAGS LEFT: \n"+ game.getFlags();
				g2.setFont(f1);
				g2.drawString(str, size+10, 300);
			}
		}
		
	}
	
	public int gridToPos(int x)
	{
		int n = spaceSize*x;
		return n;
	}
	
	public int posToGrid(int x)
	{
		int n = (Math.round(x/spaceSize));
		if (x<(n*spaceSize))
			n--;
		return n;
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(size+200,size);
    }
	
	public void testWin()
	{
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		boolean temp = true;
		for (int i = 0; i < gameSize; i ++)
		{
			for (int j = 0; j < gameSize; j++)
			{
				if (revealedBoard[i][j] != boardInv[i][j])
				{
					temp = false;
					break;
				}
			}
		}
		if (temp)
    	{
    		g2.setColor(new Color(0,114,13));
			Font f1 = new Font("helvetica",Font.BOLD,100);
			FontMetrics metrics = g.getFontMetrics(f1);
			String str = "YOU WIN";
			int c = 0 + (size - metrics.stringWidth(str)) / 2;
			int r = 0 + ((size - metrics.getHeight()) / 2) + metrics.getAscent();
			g2.setFont(f1);
			g2.drawString(str, c, r);
			play=false;
    	}
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        
        this.setBackground(Color.LIGHT_GRAY);
    
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i <= gameSize-1; i ++)
        {
        	for (int j = 0; j <= gameSize-1; j++)
        	{
        		if (ohYeah)
				{
		    		g2.drawImage(im2, gridToPos(i), gridToPos(j), null);
				}
				else
				{
	        		g2.setColor(new Color(171, 171, 171));
	        		g2.fill3DRect(gridToPos(i), gridToPos(j), spaceSize, spaceSize,true);
				}
        	}
        }
        g2.setColor(Color.BLACK);
		Font f1 = new Font("helvetica",Font.BOLD,20);
		String str = "FLAGS LEFT: \n"+ game.getFlags();
		g2.setFont(f1);
		g2.drawString(str, size+10, 300);
        
        
        
}
}
