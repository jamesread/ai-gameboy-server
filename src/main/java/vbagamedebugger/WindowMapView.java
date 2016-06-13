package vbagamedebugger;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import vbagamedebugger.games.pokemon.Map;

public class WindowMapView extends JFrame {
	private final ComponentMap componentMap = new ComponentMap();

	public WindowMapView() {
		this.add(this.componentMap, BorderLayout.CENTER);
		this.setTitle("map view");
		this.setSize(640, 640);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void setMap(Map map) {
		this.componentMap.setMap(map);
	}
}