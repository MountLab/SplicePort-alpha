package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import splicing.Splicesite;

public class SpliceSitePrinter implements Runnable{
	
	
	private BlockingQueue<Splicesite> splicesites;
	private String file;
	private String header;
	private PrintWriter printer;
	private AtomicBoolean done = new AtomicBoolean(false);
	
	public void activate() throws IOException{
		printer = new PrintWriter(new FileWriter(file,true));
		printer.println(header);
	}
	
	public void printHeader(String head){
		printer.println(head);
	}
	
	public void printSplicesite(Splicesite site){
			try {
				site.ready.await(50, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(site.ready.getCount() == 1){
				try {
					System.out.println("problem");
					this.splicesites.put(site);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else{
				String type = "Donor";
				if(site.getType() == 'A'){
					type = "Acceptor";
				}
				printer.println(type+"\t"+site.getLocation()+"\t"+site.getSequence()+"\t"+site.getScore());
			}
	}
	
	
	
	public SpliceSitePrinter(BlockingQueue<Splicesite>  sites, PrintWriter printer){
		this.splicesites = sites;
		this.printer = printer;
		
	}
	
	public static void printResults(List<Splicesite> splicesites, String file, String header) throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(file,true));
		pw.println(header);
		for(Splicesite site: splicesites){
			String type = "Donor";
			if(site.getType() == 'A'){
				type = "Acceptor";
			}
			pw.println(type+"\t"+site.getLocation()+"\t"+site.getSequence()+"\t"+site.getScore());
		}
		pw.close();
		
	}
	
	public void setDone(boolean val){
		this.done.set(val);
	}

	public void run() {
		while(!done.get()){
			try {
				printSplicesite(splicesites.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	

}
