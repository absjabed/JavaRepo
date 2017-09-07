import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;

public class BubblePanel extends JPanel {

	private ArrayList<Bubble> bubbleList;
	private int size = 30;
	private Timer timer;
	private final int DELAY = 33; //ms of delay for 30 fps
	private JTextField textSize;
	private JTextField textSpeed;
	private JCheckBox chkGroup, chkPause;
	private JCheckBox chckbxRectangle;
	
	public BubblePanel() {
		bubbleList = new ArrayList<Bubble>();
		
		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());
		
		timer = new Timer(DELAY, new BubbleListener());
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(720, 400));
		
		JPanel panel = new JPanel();
		//panel.setBackground(Color.GREEN);
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Bubble Size: ");
		panel.add(lblNewLabel);
		
		textSize = new JTextField();
		textSize.setToolTipText("Set Bubble Size");
		textSize.setHorizontalAlignment(SwingConstants.CENTER);
		textSize.setText("30");
		panel.add(textSize);
		textSize.setColumns(3);
		
		JLabel lblNewLabel_1 = new JLabel("Animation Speed (fps): ");
		panel.add(lblNewLabel_1);
		
		textSpeed = new JTextField();
		textSpeed.setText("30");
		textSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textSpeed);
		textSpeed.setColumns(2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int newSize = Integer.parseInt(textSize.getText());
				
				int newSpeed = Integer.parseInt(textSpeed.getText());
				
				size = newSize;
				timer.setDelay(1000/newSpeed);
				repaint(); 
			}
		});
		panel.add(btnUpdate);
		
		JButton btnClear = new JButton("Clear ");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<Bubble>();
				repaint();
			}
		});
		
		chkGroup = new JCheckBox("Group Bubbles");
		panel.add(chkGroup);
		
		chkPause = new JCheckBox("Pause");
		chkPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkPause.isSelected()){
					timer.stop();
				}else{
					timer.start();
				}
			}
		});
		panel.add(chkPause);
		
		chckbxRectangle = new JCheckBox("Rectangle ?");
		panel.add(chckbxRectangle);
		panel.add(btnClear);
		
		timer.start();
	}
	
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
		//drawing bubbles from bubbleList
		for(Bubble bubble:bubbleList){
			page.setColor(bubble.color);
			if(bubble.bubbleOrRectangle == 1){
				page.fillRect(bubble.x - bubble.size/2,
						bubble.y - bubble.size/2,
						bubble.size, 
						bubble.size);	
			}else{
				page.fillOval(bubble.x - bubble.size/2,
						bubble.y - bubble.size/2,
						bubble.size, 
						bubble.size);
			}
			
		}
		
		// write the number of bubbles on screen
		page.setColor(Color.GREEN);
		page.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		page.drawString("#absj4k", getWidth()/16, getHeight()-20);
		page.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		page.drawString(""+bubbleList.size(), getWidth()-40, getHeight()-20);
		
	}

	private class BubbleListener implements MouseListener, 
											MouseMotionListener,
											MouseWheelListener,
											ActionListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			//timer.stop();
			// add to the bubbleList my mouse location
			if(chckbxRectangle.isSelected()){
				bubbleList.add(new Bubble(e.getX(), e.getY(), size,1));	
			}else{
				bubbleList.add(new Bubble(e.getX(), e.getY(), size,0));
			}
			repaint();
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//timer.start();
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// add to the bubbleList my mouse location
			
			if(chckbxRectangle.isSelected()){
				bubbleList.add(new Bubble(e.getX(), e.getY(), size,1));	
			}else{
				bubbleList.add(new Bubble(e.getX(), e.getY(), size,0));
			}
			
			
			if(chkGroup.isSelected()){
				// set xspeed and yspeed of the bubbles to the previous speed
				bubbleList.get(bubbleList.size() - 1).xspeed = 
						bubbleList.get(bubbleList.size() - 2).xspeed;
				
				bubbleList.get(bubbleList.size() - 1).yspeed = 
						bubbleList.get(bubbleList.size() - 2).yspeed;
			}
			
			repaint();
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub 
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			//change the bubble size.....
			size += e.getWheelRotation();
			
			textSize.setText(""+ size);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// update the location of each bubble for the animation
			for(Bubble bubble:bubbleList){
				bubble.update();
			}
			//repaint the screen
			repaint();
			
		}
		
	}

	private class Bubble{
		/*Bubble properties*/
		public int x;
		public int y;
		public int size;
		public int bubbleOrRectangle = 1; // 0 for bubble 1 for rectangle 
		public Color color;
		public int xspeed;
		public int yspeed;
		private final int MAX_SPEED = 5;
			
		public Bubble(int x, int y, int size, int bubbleRectangle) {
			this.x = x;
			this.y = y;
			bubbleOrRectangle = bubbleRectangle;
			this.size = size;
			color = new Color(
					(float)Math.random(),
					(float)Math.random(), 
					(float)Math.random(),
					(float)Math.random());
			xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
			yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
			
			if(xspeed == 0 && yspeed == 0){
				xspeed = 2;
				yspeed = 2;
			}
			
		}
		
		public void update(){
			//y -= 5; //float each bubble up 5 pixels per frame...
			x += xspeed;
			y += yspeed;
			
			// collision detection with the edges of the panel
			if(x < size / 2 || x + size / 2 >= getWidth() | y < size / 2 || y + size / 2 >= getHeight()){
				xspeed = -1*xspeed;
				yspeed = -1*yspeed;
			}
			
		}
		
		
	}
}
