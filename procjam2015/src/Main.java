import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Main {

	private static JSONObject filedata;
	private static InputFileHandler inFileHndlr;
	public static void main(String[] args) {
		String filename = args.length >= 1 ? args[0] : null;
		boolean stfu = false;
		boolean needsHelp = args.length < 1 || filename == null;

		for (String s:args) {
			if (s.contains("help") || s.equals("-h") || s.equals("--h")) {
				needsHelp = true;
			}
			
			if (s.equals("-s") || s.equals("--stfu")) {
				stfu = true;
			}
		}
		
		if (needsHelp) {
			System.out.println("Usage: whatevermynameis.jar <inputfile> -[hs])");
			System.out.println("-h | --help     Show this help message");
			System.out.println("-s | --stfu     Print only the moves generated");
			System.exit(0);
		}
		
		filedata = loadData((filename != null) ? filename : "input.txt");
		inFileHndlr = new InputFileHandler();
		inFileHndlr.setData(filedata);
		
		Scanner scanner = new Scanner(System.in);
		if (!stfu) {
			System.out.println("Muhznit's Procedural RPG move generator");
			System.out.println("Made in about a week for procjam2015");
			if (filedata != null) {
				System.out.println("File " + filename + " loaded successfully");
			}
			System.out.println("Press enter to generate a new move! All configuration is handled via file. Type 'q' to exit.");
		}
		
		String cmd = null;
		while(true) {
			cmd = scanner.nextLine();
			if (cmd.length() > 0)
				break;
			MoveDesignSpecifics designSpecifics = new MoveDesignSpecifics();
			designSpecifics.randomize();
			designSpecifics.setType(inFileHndlr.getRandomType());
			
			Move myMove = new Move();
			myMove.setMoveDesignSpecifics(designSpecifics);
			myMove.randomize();
			System.out.println(myMove.toJSONObject().toString(2));
		}

		scanner.close();
		System.exit(0);
	}

	private static JSONObject loadData(String filename){
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			System.out.println("Error loading " + filename + ". Make sure it exists.");
			System.exit(1);
		}
		JSONTokener tokener = new JSONTokener(fr);
		return new JSONObject(tokener);
	}
	
	public static InputFileHandler getInputFileHandler() {
		return inFileHndlr;
	}

}
