package splicing;

import io.FeatureReader;
import io.SpliceSitePrinter;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;


public class Predictor {

	public static void main(String[] args){
		
		try {
			Options options = new Options();
			options.addOption("f", true, "Input FASTA file");
			options.addOption("a", true, "Input Acceptor file");
			options.addOption("d", true, "Input Donor file");
			options.addOption("w", true, "Window Size");
			options.addOption("t", true, "Number of Threads");
			options.addOption("o",true, "Output File");
			CommandLineParser cparser = new DefaultParser();
			CommandLine cmd = cparser.parse( options, args);
			if(cmd.getOptionValue("f") == null){
				throw new Exception("Please provide a fasta file using the -f flag");
			}
			File sequenceFile = new File(cmd.getOptionValue("f"));
			int numThreads = 2;
			if(cmd.hasOption("t")){
			  numThreads = Integer.parseInt(cmd.getOptionValue("t"));
			}
			System.out.println("ThreadPoolSize : "+numThreads);
			if(cmd.getOptionValue("w") == null){
				throw new Exception("Please provide a window size using the -w flag");
			}
			int windowSize = Integer.parseInt(cmd.getOptionValue("w"));
			Scanner scan = new Scanner(sequenceFile);
			System.out.println("Fasta read in "+ sequenceFile.getName());
			if(cmd.getOptionValue("a") == null){
				scan.close();
				throw new Exception("Please provide an acceptor feature file using the -a flag");
			}
			FeatureSites acceptorFeatures = new  FeatureSites(FeatureReader.readFile(cmd.getOptionValue("a"),
					windowSize, 'A'));
			
			System.out.println("acceptors: "+cmd.getOptionValue("a"));
			System.out.println("donors: "+cmd.getOptionValue("d"));
			System.out.println("windowSize: "+cmd.getOptionValue("w"));
			if(cmd.getOptionValue("d") == null){
				scan.close();
				throw new Exception("Please provide an donor feature file using the -d flag");
			}
			FeatureSites donorFeatures = new  FeatureSites(FeatureReader.readFile(cmd.getOptionValue("d"),
					windowSize, 'D'));
			System.out.println("Features read in");
			SequenceParser parser = new SequenceParser(162, numThreads,
					acceptorFeatures, donorFeatures, scan);
			List<String> headers = new LinkedList<String>();
			parser.setHeaders(headers);
			headers.add(scan.nextLine());
			if(cmd.getOptionValue("o") == null){
				scan.close();
				throw new Exception("Please provide an output file using the -a flag");
			}
			PrintWriter writer = new PrintWriter(cmd.getOptionValue("o"));
			
			writer.print("");
			LinkedBlockingQueue<Splicesite> splicesites = new LinkedBlockingQueue<Splicesite>();
			SpliceSitePrinter sprinter = new SpliceSitePrinter(splicesites,writer);
			Thread pthread = new Thread(sprinter);
			long startTime = System.currentTimeMillis();
			while(!headers.isEmpty()){
				sprinter.printHeader(headers.get(0));
				pthread.start();
				System.out.println("parsing");
				sprinter.setDone(parser.generateScores(splicesites));
				
				//SpliceSitePrinter.printResults(featureList,args[5] , headers.get(0));
				headers.remove(0);
			}
			pthread.join();
			System.out.println("results");
			long runtime = System.currentTimeMillis() - startTime;
			System.out.println("Runtime in milliseconds:"+ runtime);
			writer.close();
			scan.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
