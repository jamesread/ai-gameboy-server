package vbagamedebugger;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import java.util.List;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

class DebugPresetModel implements ListModel {
	class DebugPreset {
		int offset = 0;
		int end = 0;
		String name = "untitled";

		public DebugPreset(String name, int offset, int end) {
			this.name = name;
			this.offset = offset;
			this.end = end;  
		} 
	}
 
	public DebugPresetModel() {
		File debugPresetsDir = new File("debugPresets/rom/");

		for (File f : debugPresetsDir.listFiles()) {
			if (f.isHidden()) {
				continue;
			}
			
			processPresetFile(f);
		}
	}
	
	private int parseInt(String input) {
		if (input.startsWith("0x")) { 
			return Integer.parseInt(input.replace("0x", ""), 16);
		} else {
			return Integer.parseInt(input);
		} 
	}
	
	public void processPresetFile(File f) {
		int offset = 0;
		int end = 0;
		String name = "";
		 
		try {
			for (String line : new String(Files.readAllBytes(f.toPath())).split("\n")) {
				String[] parts = line.split("=", 2);
				
				switch (parts[0]) {
				case "offset":
					offset = parseInt(parts[1]);
					break;
				case "end":
					end = parseInt(parts[1]);
					break; 
				case "name":
					name = parts[1]; 
					break; 
				default:
					System.out.println(f.getName() + " Unrecognized keyword: " + parts[0] + " in file: " + f.getName()); 
				} 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("preset " +  name + " " + offset + " " + end); 
		 
		if (name != null && offset != 0 && end != 0) {
			presets.add(new DebugPreset(name, offset, end));
		}
	}

	public Vector<DebugPreset> presets = new Vector<DebugPreset>();


	public void removeListDataListener(ListDataListener ldl) {	}
	public void addListDataListener(ListDataListener ldl) {	}
	public Object getElementAt(int i) {
		return this.presets.get(i).name;
	}
	
	public DebugPreset get(int i) {
		return this.presets.get(i); 
	}

	public int getSize() {
		return this.presets.size();
	}
}
