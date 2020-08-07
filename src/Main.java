/*
* TO DO:
* - properly annotate code
* X make beginning not super slow
* - add picture for mines instead of black circles
* - add better graphics for game over and victory screen
* - somehow make it so you never have to guess a space (has something to do with how mines are made probably)
* X add a counter for how many flags you have left and then stop you from placing more when you hit the cap
*/

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	private static JFrame f = new JFrame("Minesweeper");

	public static void main(String[] args)
	{
		String difficulty = "easy";
		int[] nums;
		if (difficulty.toLowerCase().equals("hard"))
			nums = new int []{24,99};
		else if (difficulty.toLowerCase().equals("medium"))
			nums = new int []{16,40};
		else
			nums = new int[] {8,10};
		
		
		Game game1 = new Game(nums[0],nums[1]);
		int size = 700;
		
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(size,game1);
            }
        });
		
		
    }
    
    public static void createAndShowGUI(int size, Game game)
    {
    	int trueSize = (size/game.getSize()) * game.getSize();
		GameWindow p = new GameWindow(trueSize,game);
        
        f.setResizable(false);
        f.setBackground(Color.WHITE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setVisible(true);
    
	}
   
	public static void createAndShowGUI(GameWindow z, int size, Game game)
	{
        f.remove(z);
        int trueSize = (size/game.getSize()) * game.getSize();
		GameWindow p = new GameWindow(trueSize,game);
        
        f.setResizable(false);
        f.setBackground(Color.WHITE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setVisible(true);
    
	}
}
