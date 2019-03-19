package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import features.Conjunctive;
import features.Feature;
import features.Interval;
import features.Kpos;

public class FeatureReader {
	
	public static int convertToAbsolute(int pos, int window, char type){
		int middle = window/2;
		if(type =='D'){
			if(pos<0){
				return (middle+pos-1);
			}
			else{
				return (middle+pos-2);
			}
		}
		else{
			if(pos<0){
				return (middle+pos+1);
			}
			else{
				return (middle+pos);
			}	
		}
		
	}
	
	public static int convertToRelative(int value, int window, char type){
		int middle =  window/2;
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
	public static void handleInterval(String line, HashMap<Integer, Feature> features, int window ,char type){
		String[] parts = line.split("\t");
		int id = Integer.parseInt(parts[0]);
		String seq = parts[2];
		int start = convertToAbsolute(Integer.parseInt(parts[3]),window, type);
		int end = convertToAbsolute(Integer.parseInt(parts[4]), window, type);
		double score = Double.parseDouble(parts[5]);
		features.put(id,new Interval(seq,start,end,score,id));
	}
	public static void handleConj(String line, HashMap<Integer, Feature> features, int window, char type){
		String[] parts = line.split("\t");
		LinkedList<Feature> featureList = new LinkedList<Feature>();
		int id = Integer.parseInt(parts[0]);
		String[] feats = parts[2].split(",");
		for(String feat:feats){
			featureList.add(features.get(Integer.parseInt(feat)));
		}
		double score = Double.parseDouble(parts[3]);
		features.put(id,new Conjunctive(featureList,score,id));

	}
	public static void handleKpos(String line, HashMap<Integer, Feature> features, int window,char type){
		String[] parts = line.split("\t");
		int id = Integer.parseInt(parts[0]);
		String seq = parts[2];
		int start = convertToAbsolute(Integer.parseInt(parts[3]),window, type);
		double score = Double.parseDouble(parts[4]);
		features.put(id,new Kpos(seq,start,score,id));
	}
	public static List<Feature> readFile(String filename, int windowSize, char type) throws FileNotFoundException{
		
		System.out.println(filename);
		HashMap<Integer, Feature> features = new HashMap<Integer, Feature>();
		Scanner scan = new Scanner(new File(filename));
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			if(line.contains("Kpos"))
				handleKpos(line,features, windowSize,type);
			else if(line.contains("Conj"))
				handleConj(line,features,windowSize, type);
			else if (line.contains("Interval"))
				handleInterval(line, features, windowSize, type);
			}
		scan.close();
		LinkedList<Feature> featureList = new LinkedList<Feature>();
		featureList.addAll(features.values());
		return featureList;
	}

}
