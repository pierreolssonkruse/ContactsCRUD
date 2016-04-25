import javax.swing.JFrame;

import View.View;


public class Main {

	public static void main(String[] args) {
		View View = new View();
		View.setVisible(true);
		View.setResizable(false);
		View.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}