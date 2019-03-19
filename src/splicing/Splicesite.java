package splicing;

import java.util.concurrent.CountDownLatch;

public class Splicesite {
	private final char type;
	private final int location;
	private final String sequence;
	private double score = 0;
	public CountDownLatch ready = new CountDownLatch(1);
	
	public Splicesite(char aord, int loc, String seq){
		this.type = aord;
		this.location = loc;
		this.sequence = seq;
	}
	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void addToScore(double featureScore) {
		this.score += featureScore;
	}
	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}
	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}
	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}
	
	/**
	 * Sets the splicesite as ready to print
	 */
	public void setToReady(){
		this.ready.countDown();
	}
	
	public void await() {
		try {
			this.ready.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
