
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Frame {

	JFrame frame;
	JButton btnRead, btnUpload;
	JTextArea textArea;
	JPanel panel;
	
	Teller teller;
	
	public Frame() {
		
		frame = new JFrame("StoryTeller");
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);			
		
		textArea = new JTextArea();
		textArea.setBounds(25, 27, 381, 135);
		frame.getContentPane().add(textArea);
		
		btnRead = new JButton("Read");
		btnRead.setBounds(88, 195, 89, 23);
		frame.getContentPane().add(btnRead);
		btnRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = btnRead.getText();				
				
				if(name == "Read") {					
					read();					
				} else {					
					stop();
				}				
			}
		});
		
		teller = new Teller("Hello World");
		
		btnUpload = new JButton("Upload");
		btnUpload.setBounds(230, 195, 89, 23);
		frame.getContentPane().add(btnUpload);
		btnUpload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				
				int returnValue = fileChooser.showOpenDialog(null);
				
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		        	
		          File selectedFile = fileChooser.getSelectedFile();
		          
		          if(selectedFile.canRead()) {
		        	  
		        	 Writer writer = new Writer(selectedFile);
		        	 
		        	 writer.execute();
		        	 System.out.println("Frame get " + writer.getPropertyChangeSupport());
		          }		          		          
		        }
			}
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void read() {
		
		teller.execute();
		btnRead.setText("Stop");
	}
	
	private void stop() {
		
		teller.cancel(true);
		btnRead.setText("Read");
		teller = new Teller("Hello World");
	}
}
