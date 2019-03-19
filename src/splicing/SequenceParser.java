package splicing;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;



public class SequenceParser {
	
	private int windowSizeHalf;
	private FeatureSites acceptorFeatures;
	private FeatureSites donorFeatures;
	private List<String> headers;
	private Scanner scan;
	private int numThreads;
	
	private StringBuffer buffer = new StringBuffer("");
	private Semaphore numReady;

	
	public SequenceParser(int windowSize, int numThreads, FeatureSites acceptors, 
			FeatureSites donors, Scanner scanner){
		this.windowSizeHalf = windowSize/2;
		this.acceptorFeatures = acceptors;
		this.donorFeatures = donors;
		this.numReady = new Semaphore(numThreads);
		this.numThreads = numThreads;
		this.scan = scanner;
	}
	
	public void setHeaders(List<String> headers){
		this.headers = headers;
	}
	
	public boolean generateScores(BlockingQueue<Splicesite> splicesites) throws InterruptedException{
		int pos = 0;
		int offset = 0;
		char prev = 'X';
		advanceBuffer(offset);
		String window;
		//System.out.println(this.sequence.getSequenceAsString());
		//List<Splicesite> splicesites = new LinkedList<Splicesite>();
		while(offset<buffer.length()){
			char cur = buffer.charAt(offset);
			if(cur == 'T' && prev == 'G'){
				if(offset>windowSizeHalf && buffer.length()> offset+windowSizeHalf){
					//numReady.acquire();
					window = buffer.substring(offset-windowSizeHalf, offset+windowSizeHalf);
					Splicesite site = new Splicesite('D',pos-1,window.substring(windowSizeHalf-6,this.windowSizeHalf+6 ));
					splicesites.put(site);
					
					SiteScorer scorer = new SiteScorer(site,window, this.donorFeatures, numReady);
					scorer.run();
					//Thread treadScorer = new Thread(scorer);
					//treadScorer.start();
				}
			}
			else if(cur =='G' && prev == 'A'){
				if(offset>windowSizeHalf && buffer.length()> offset+windowSizeHalf){
					//numReady.acquire();
					window = buffer.substring(offset-windowSizeHalf, offset+windowSizeHalf);
					Splicesite site = new Splicesite('A',pos+1,window.substring(windowSizeHalf-6,this.windowSizeHalf+6 ));
					splicesites.put(site);

					SiteScorer scorer = new SiteScorer(site,window, this.acceptorFeatures, numReady);
					scorer.run();
					//Thread treadScorer = new Thread(scorer);
					//treadScorer.start();
				}
				
				
			}
			prev = cur;
			offset++;
			
			pos++;
			if((double)offset>=(((double)buffer.length())*2.0)/3.0){
				offset = advanceBuffer(offset);
				
			}
			
		}	

		
		//long prob =0;
		//numReady.acquire(this.numThreads);
		
		
		return true;
		
		
	}
	private String getNewLine(){
		String addition = "";
		while(scan.hasNextLine() && buffer.length()<244){
			String line = scan.nextLine().toUpperCase();
			if(line.contains(">")){
				headers.add(line);
				break;
			}	
			else addition+=line;
		}
		return addition;
		
	}
	
	
	private int advanceBuffer(int offset){
		int original = buffer.length();
		buffer = buffer.delete(0, buffer.length()/3);
		int after = buffer.length();
		while(scan.hasNextLine() && buffer.length()<windowSizeHalf*3){
			String line = scan.nextLine();
			if(line.contains(">")){
				headers.add(line);
				break;
			}	
			buffer.append(line.toUpperCase());
		}
		return offset = offset-(original-after);
	}
	
	public int min(int val){
		if(val<1){
			return 0;
		}
		return val;
	}
	
	public int max(int val, int length){
		if(val >= length){
			return (length-1);
		}
		return val;
	}

}
