import javax.swing.SwingWorker;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager; 

public class Teller extends SwingWorker<Void, Void> {
	
	VoiceManager vm;
	Voice voice;
	
	private String text;
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		speak();
		
		return null;
	}
	
	public Teller(String text) {
		
		System.setProperty("mbrola.base", "C:/xampp/htdocs/rlo/java/mbrola/");
		System.setProperty("freetts.voices", "de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");
		
		vm = VoiceManager.getInstance();
	    voice = vm.getVoice("mbrola_us1");
	    voice.allocate();

	    this.text = text;	    
	}
	
	public void speak() {
	    
	    voice.speak(this.text);	    
	}
}
