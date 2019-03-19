package splicing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import features.Feature;
import features.Interval;
import features.Kpos;

public class FeatureSites {
	//private HashMap<Feature, Integer> foundMap = new HashMap<Feature, Integer>();
	private HashMap<Feature, Boolean> foundMap = new HashMap<Feature, Boolean>();
	public HashMap<Integer,List<Kpos>> kposMap = new HashMap<Integer,List<Kpos>>();
	public LinkedList<Kpos> kposList = new LinkedList<Kpos>();
	public LinkedList<Interval> inList = new LinkedList<Interval>();
	public LinkedHashMap<String,List<Interval>> intervalMap = new LinkedHashMap<String,List<Interval>>();
	//private HashMap<Feature, Double> scoreMap = new HashMap<Feature,Double>();
	private String[] intervalStarts;
	private Integer[] kposStarts;
	
	public FeatureSites(List<Feature> featList){
		System.out.println("Feat_List:"+featList.size());
		
		for(Feature feat: featList){
			foundMap.put(feat, false);

			if(feat instanceof Kpos){
				kposList.add((Kpos)feat);
			}
			else if(feat instanceof features.Interval){
				inList.add((Interval) feat);
			}
		}
	}
	
	public HashMap<Feature, Boolean> produceFoundMap(){
		return (HashMap<Feature, Boolean>) foundMap.clone();
	}
	

	
	public List<Kpos> getKposFeatures(int index){
		if(kposMap.containsKey(index)){
			return kposMap.get(index);
		}
		else return null;
	}
	
	public List<Interval> getIntervalFeatures(String index){
		if(intervalMap.containsKey(index)){
			return intervalMap.get(index);
		}
		else return null;
	}
	
	public String[] getIntervalStarts(){
		return intervalStarts;
	}

	/**
	 * @return the kposStarts
	 */
	public Integer[] getKposStarts() {
		return kposStarts;
	}

	/**
	 * @param kposStarts the kposStarts to set
	 */
	public void setKposStarts(Integer[] kposStarts) {
		this.kposStarts = kposStarts;
	}

	/**
	 * @param intervalStarts the intervalStarts to set
	 */
	public void setIntervalStarts(String[] intervalStarts) {
		this.intervalStarts = intervalStarts;
	}
	
	
	public List<Kpos> getKposList(){
		return this.kposList;
	}
	
	
	
	
	
	
	
	
}
