package vbagamedebugger;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class WindowMapCol extends JFrame {
	public WindowMapCol() {
		this.add(new ComponentMap(), BorderLayout.CENTER);
		this.setTitle("map col");
		this.setSize(640, 640);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
