import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager; 

public class Index {
	
	VoiceManager vm;
	Voice voice;
	
	public Index() {
		
		System.setProperty("mbrola.base", "C:/xampp/htdocs/rlo/java/mbrola/");
		System.setProperty("freetts.voices", "de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");
		
		vm = VoiceManager.getInstance();
	    voice = vm.getVoice("mbrola_us1");
	    voice.allocate();
	    
	    speak();
	}
	
	public void speak() {
		
		System.out.println("Name: " + voice.getName());
	    System.out.println("Description: " + voice.getDescription());
	    System.out.println("Organization: " + voice.getOrganization());
	    System.out.println("Age: " + voice.getAge());
	    System.out.println("Gender: " + voice.getGender());
	    System.out.println("Rate: " + voice.getRate());
	    System.out.println("Pitch: " + voice.getPitch());
	    System.out.println("Style: " + voice.getStyle());
	    System.out.println();
	    
	    voice.speak("An old man lived in the village.");
	}

	public static void main(String[] args) {
		
		new Index();
		
	}
}
