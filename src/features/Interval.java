package features;

public class Interval implements Feature{
	
	private final String sequence;
	private final int start;
	private final int stop;
	private boolean found = false;
	private final double score;
	private final int id;
	
	public Interval(String seq, int begin, int end, double val, int id){
		this.sequence = seq;
		this.start = begin;
		this.stop = end;
		this.score = val;
		this.id = id;
	}
	
	
	
	public String toString(){
		String printout = ""+id+"\tInterval\t";
		printout+= this.sequence+"\t";
		printout+= this.start+"\t";
		printout+= this.stop+"\t";
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
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the stop
	 */
	public int getStop() {
		return stop;
	}

	public void activate(){
		this.found = true;
	}

	public boolean isFound() {
		return this.found;
	}


	public double getScore() {
		return this.score;
	}

}
