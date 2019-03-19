package features;

public class Kpos implements Feature {
	private final double score;
	private boolean found;
	private final int start;
	private final String sequence;
	private final int id;

	public Kpos(String seq, int begin, double val, int id){
		this.sequence = seq;
		this.start = begin;
		this.score = val;
		this.id = id;
	}
	
	
	public String toString(){
		String printout = ""+id+"\tKpos\t";
		printout+= this.sequence+"\t";
		printout+= this.start+"\t";
		printout+=this.getScore();
		return printout;
	}
	/**
	 * @return id
	 */
	public int getID(){
		return id;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}
	
	public boolean isFound() {
		return found;
	}

	@Override
	public double getScore() {
		return score;
	}
	
	public void activate(){
		this.found = true;
	}

}
