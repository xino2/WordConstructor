package screen;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class StartGame extends JFrame implements MouseInputListener {
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private GameDrawPanel panel;
	
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		new StartGame();
	}
	
	public StartGame() throws UnsupportedEncodingException, FileNotFoundException, IOException{
		frame = new JFrame("Word Constructor");
		frame.setSize(800,600);
		frame.setResizable(false);
		panel = new GameDrawPanel();//gamePanel
		panel.addMouseListener(this); //added clicked
		panel.addMouseMotionListener(this);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		//in game
		if(panel.isInGame()){
			panel.checkSelectedArea(e.getX(), e.getY());
			panel.repaint();
			
		} else { //in menu
			
			if(panel.getSelectedOption()==1){
				panel.startGame();
				panel.repaint();
			} else if(panel.getSelectedOption()==2){
				//show options...
				panel.setShowOptions(true);
				panel.setMenuSelectGridSize(e.getX(), e.getY());
				panel.repaint();
			}
			
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub	
		if(!panel.isInGame()){
			panel.setMenuCursor(e.getX(), e.getY());
			panel.repaint();
		}
	}

	
}
