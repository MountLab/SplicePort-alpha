package io;



import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;



public class IORunner {

	public static void main(String[] args) {

		 try {
			 Options options = new Options();
			options.addOption("a", true, "Input Acceptor file");
			options.addOption("d", true, "Input Donor file");
			options.addOption("w", true, "Window Size");
			options.addOption("oa", true, "Output Accetpor File ");
			options.addOption("od",true, "Output Donor File");
			CommandLineParser cparser = new DefaultParser();
			CommandLine cmd = cparser.parse( options, args);
			OldFeatureTranslator translator = new 
					OldFeatureTranslator(Integer.parseInt(cmd.getOptionValue("w")));
			translator.convertFeatures(cmd.getOptionValue("a"),cmd.getOptionValue("oa"), 'A');
			System.out.println("Acceptor Features Converted");
			translator.convertFeatures(cmd.getOptionValue("d"), cmd.getOptionValue("od"), 'D');
			System.out.println("Donor Features Converted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
