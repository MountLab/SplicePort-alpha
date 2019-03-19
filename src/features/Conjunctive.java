package features;

import java.util.HashMap;
import java.util.List;

public class Conjunctive implements Feature {
	public final List<Feature> FeatureList;
	private final double score;
	private final int id;

	public Conjunctive(List<Feature> features, double val, int id){
		this.FeatureList = features;
		this.score = val;
		this.id = id;
	}
	
	public String toString(){
		String printout = ""+id+"\tConj\t";
		for(Feature feat: FeatureList){
			printout+= feat.getID()+",";
		}
		printout = printout.substring(0, printout.length()-1);
		printout+="\t"+this.score;
		return printout;
		
		
		
	}

	public boolean isFound(HashMap<Feature,Boolean> featureMap) {
		boolean found = true;
		for(Feature feat: this.FeatureList){
			found = (found && featureMap.get(feat));
		}
		return found;
	}

	@Override
	public double getScore() {
		return this.score;
	}

	/**
	 * 
	 * @return id
	 */
	public int getID() {
		return this.id;
	}
	
}
