/*
 * Jacob Collins
 * 
 * Purpose:
 * Demostrates the flyweight class by 
 * instantiating a large amount of fine-grained
 * objects, in this case being strobe lights that 
 * have a rectangle and oval drawn with a new color 
 * in a separate thread each sleep cycle. The sleep 
 * cycles are defined by the main , so are the number
 * of strobe lights to be displaye. 
 * 
 * 4-9-18
 * */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Demo {
	public static void main(String[] args) {
		//number of strobe lights
		int size = 10;
		//milliseconds between color changes
		int pause = 100;

		Frame frame = new Frame("Strobe Lights");
		frame.setLayout(new GridLayout(size, size));
		
		//add the new strobe lights to the frame
		for (int i = 0; i < size * size; i++) {
			frame.add(new FlyweightColorBox(pause));
		}

		//set up the frame for display
		frame.setSize(500, 500);
		frame.setBackground(Color.red);
		frame.setVisible(true);
		//exit the thread when the window closes
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

//class that can instantiate multiple instances easily 
//making it a flyweight
class FlyweightColorBox extends Canvas implements Runnable {
	static int count = 0;
	static double average = 0;
	private static final long serialVersionUID = 1L;
	private int pause;
	private Color backColor = getColor();
	private Color foreColor = getColor();
	private static Color[] colors = { Color.black, Color.blue, Color.cyan, 
			Color.darkGray, Color.gray, Color.green, Color.lightGray, 
			Color.orange, Color.white, Color.yellow, Color.red, };

	//Constructor will start a new thread for each new instance
	public FlyweightColorBox(int p) {
		pause = p;
		new Thread(this).start();
	}

	//returns a random color from the colors array
	private static Color getColor() {
		return colors[(int) (Math.random() * 1000) % colors.length];
	}

	//implements the runnable
	public void run() {

		while (true) {
			//returns two colors from the color array
			foreColor = getColor();
			backColor = getColor();
			//repaints the canvas upon each run
			repaint();

			//puts this thread to sleep until the next run
			//making it less strenuous on the system
			try {
				Thread.sleep(pause);
			} catch (InterruptedException ignored) {
			}
		}
	}

	//the canvas paint method that will draw the rect and oval
	@Override
	public void paint(Graphics g) {
		g.setColor(backColor);
		g.fill3DRect(1, 1, getWidth(), getHeight(), true);
		g.setColor(foreColor);
		g.fillOval(0, 0, getWidth(), getHeight());
	}
}
