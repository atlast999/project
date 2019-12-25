package executing;

import java.awt.EventQueue;
import java.util.ArrayList;

import io.FileInteract;

import userInterface.ui;
import userInterface.uiLogin;
import model.Class;
import model.Student;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uiLogin frame = new uiLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
