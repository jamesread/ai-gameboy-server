package vbagamedebugger;

import javax.swing.Action;
import javax.swing.JButton;

public class XButton extends JButton {
	public XButton(String title, Action a) {
		super(a);
		this.setText(title);
	}
}
