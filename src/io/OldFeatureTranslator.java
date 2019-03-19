package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import features.Conjunctive;
import features.Feature;
import features.Interval;
import features.Kpos;

public class OldFeatureTranslator {
	private LinkedHashMap<Integer, Feature> featureMap = new LinkedHashMap<Integer, Feature>();
	private AtomicInteger counter = new AtomicInteger(0);
	private int windowsize;
	private char type;
	
	public OldFeatureTranslator(int size){
		this.windowsize = size;
	}
	
	public Feature convertGeneral(String line){
		int id = counter.getAndIncrement();
		String[] split = line.split("\t");
		String sequence = split[0].replaceFirst("GEN-", "");
		double score = Double.parseDouble(split[1]);
		Interval up = new Interval(sequence,0, windowsize/2-1 , score, id  );
		Interval down =  new Interval(sequence,windowsize/2+1, windowsize, score, id);
		LinkedList<Feature> featureList = new LinkedList<Feature>();
		featureList.add(up);
		featureList.add(down);
		return new Interval(sequence,convertToRelative(0), convertToRelative(windowsize) , score, id  );	
	}
	
	public Feature convertUpstream(String line){
		int id = counter.getAndIncrement();
		String[] split = line.split("\t");
		String sequence = split[0].replaceFirst("UP-", "");
		double score = Double.parseDouble(split[1]);
		return new Interval(sequence,convertToRelative(0),convertToRelative(windowsize/2) , score, id  );
	}
	
	public Feature convertDownstream(String line){
		int id = counter.getAndIncrement();
		String[] split = line.split("\t");
		String sequence = split[0].replaceFirst("DOWN-", "");
		double score = Double.parseDouble(split[1]);
		return new Interval(sequence,convertToRelative(windowsize/2), 
				convertToRelative( windowsize) , score, id  );
	}
	
	public List<Feature> converConj(String line){
		
		String[] numstrings = line.replace("CONJ-", "").split("_");
		double value = Double.parseDouble(numstrings[numstrings.length-1].split("\t")[1]);
		numstrings[numstrings.length-1] = numstrings[numstrings.length-1].split("\t")[0];
		LinkedList<Feature> features = new LinkedList<Feature>();
		for(String num: numstrings){
			int index = convertToRelative(Integer.parseInt(num)/4);
			String  base = convertToBase(Integer.parseInt(num)%4);
			features.add(new Kpos(base,index,0,counter.getAndIncrement()));
		}
		Conjunctive conj = new Conjunctive((List<Feature>) features.clone(), value, counter.getAndIncrement());
		features.add(conj);
		return features;

	}
	
	private String convertToBase(int x){
		switch (x) {
			case 0: return "A"; 
			case 1: return "C";
			case 2: return "G";
			case 3: return "T";
			default: return "X";
			
		}
	}
	
	public Feature converKpos(String line){
		int id = counter.getAndIncrement();
		String[] split = line.split("\t");
		String[] dashsplit = split[0].split("-");
		int start = convertToRelative(Integer.parseInt(dashsplit[1]));
		String sequence = dashsplit[3];
		double score = Double.parseDouble(split[1]);
		return new Kpos(sequence,start,score,id);
		
	}
	
	public int convertToRelative(int value){
		int middle =  this.windowsize/2;
		if(type == 'D'){
			if(value<(middle-1)){
				return (value-middle+1);
			}
			else{
				return (value-middle+2);
			}
		}
		else{
			if(value<=middle){
				return ((value-middle)-1);
			}
			else{
				return (value-middle);
			}
		}
	}
	
	private void handleLine(String line, LinkedList<Feature> features){
		if(line.contains("CONJ"))
			features.addAll(this.converConj(line));
		else if(line.contains("GEN"))
			features.add(this.convertGeneral(line));
		else if(line.contains("UP"))
			features.add(this.convertUpstream(line));
		else if(line.contains("DOWN"))
			features.add(this.convertDownstream(line));
		else if(line.contains("KPOS"))
			features.add(this.converKpos(line));
	}
	
	public void convertFeatures(String inputFile, String outputFile, char featureType ) throws FileNotFoundException{
		this.type = featureType;
		LinkedList<Feature> features = new LinkedList<Feature>();
		Scanner scan = new Scanner(new File(inputFile));
		PrintWriter printer = new PrintWriter(outputFile);
		while(scan.hasNextLine()){
			handleLine(scan.nextLine().toUpperCase(), features);
		}
		scan.close();
		for(Feature feat: features){
			printer.println(feat.toString());
		}
		printer.close();
	}
	
	
	
	

}
