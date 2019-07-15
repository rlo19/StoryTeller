import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.SwingWorker;

public class Writer extends SwingWorker<Void, String> {

	File file;
	FileInputStream inputStream = null;
	Scanner sc = null;
	String line;
	
	@Override
	protected Void doInBackground() throws Exception {
		
		write();
		
		return null;
	}
	
	public Writer(File file) {
		
		this.file = file;
	}
	
	private void write() throws IOException {

		try {
			
			inputStream = new FileInputStream(this.file.getPath());
			
			sc = new Scanner(inputStream, "UTF-8");
			
			while(sc.hasNextLine()) {

				line += sc.nextLine();
				
				Thread.sleep(20);
			}
			
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
	protected void done() {
		
		System.out.println("Done writing");
		
		show();
	}
	
	public String show() {
		
		
		return line;		
	}
}
