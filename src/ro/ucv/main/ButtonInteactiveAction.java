package ro.ucv.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ro.ucv.main.interactive.PictureGUI;

public class ButtonInteactiveAction implements  ActionListener{
	
	public ButtonInteactiveAction(){
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//create a new gui when button pressed
		PictureGUI p=new PictureGUI();
		
	}

}
