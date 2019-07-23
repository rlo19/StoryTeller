
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame {

	JFrame frame;
	JButton btnRead, btnUpload;
	JTextArea textArea;
	JPanel panel;
	JScrollPane scrollPane;
	
	Teller teller;
	Writer writer; 
	
	public Frame() {
		
		frame = new JFrame("StoryTeller");
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		textArea.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
				
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(29, 11, 369, 170);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		btnRead = new JButton("Read");
		btnRead.setBounds(88, 195, 89, 23);
		frame.getContentPane().add(btnRead);
		btnRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = btnRead.getText();				
				
				if(name == "Read") {					
					read();		
					textArea.setEditable(false);
				} else {					
					stop();
					textArea.setEditable(true);
				}				
			}
		});
		
		btnUpload = new JButton("Upload");
		btnUpload.setBounds(230, 195, 89, 23);
		frame.getContentPane().add(btnUpload);
		btnUpload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fileChooser.setFileFilter(filter);
				
				int returnValue = fileChooser.showOpenDialog(null);
				
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		        	
		          File selectedFile = fileChooser.getSelectedFile();
		          
		          if(selectedFile.canRead()) {
		        	  
		        	 writer = new Writer(selectedFile);
		        	 
		        	 writer.execute();
		          }		          		          
		        }
			}
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void read() {
		
		teller = new Teller(textArea.getText());
		teller.execute();
		
		btnRead.setText("Stop");
	}
	
	private void stop() {
		
		teller.cancel(true);		
		teller = new Teller("");
		
		btnRead.setText("Read");
	}
	
	private class Writer extends SwingWorker<Void, String> {

		File file;
		FileInputStream inputStream = null;
		Scanner sc = null;		
		
		@Override
		protected Void doInBackground() throws Exception {
			
			write();
			
			return null;
		}
		
		protected Writer(File file) {
			
			this.file = file;
		}
		
		private void write() throws IOException {

			String book = "";
			
			try {
				
				inputStream = new FileInputStream(this.file.getPath());
				
				sc = new Scanner(inputStream, "UTF-8");
				
				while(sc.hasNextLine()) {

					String line = sc.nextLine();
					
					if(line.isEmpty()) {
						line = "\n\n";						
					}
					
					book += line;

					Thread.sleep(50);
				}
				
				publish(book);
				
				if (sc.ioException() != null) {
			        throw sc.ioException();
			    }
				
			} catch (Exception e) {
				
				System.out.println("err");
				System.err.println(e);
				
			} finally {
				
			    if (inputStream != null) {
			    	
			        inputStream.close();		        
			    }
			    
			    if (sc != null) {
			    	
			        sc.close();
			    }
			}
		}
		
		@Override
		protected void process(List<String> chunks) { 
            
			String val = chunks.get(chunks.size()-1); 

            textArea.append(String.valueOf(val));
        } 
		
		@Override
		protected void done() {
			
			System.out.println("Done writing");	
			
		}
	}
}