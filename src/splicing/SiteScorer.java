package splicing;



import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

import features.Conjunctive;
import features.Feature;
import features.Interval;
import features.Kpos;


public class SiteScorer implements Runnable {

	private String window;
	private Splicesite site;
	private FeatureSites feats;
	private Semaphore ready;
	SiteScorer(Splicesite site, String window, FeatureSites featSites, Semaphore ready){
		this.site = site;
		this.window = window;
		this.feats = featSites;
		this.ready = ready;
	}
	
	public void run() {
		/*try {
			ready.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		
		HashMap<Feature,Boolean> foundMap = feats.produceFoundMap();
		
		for(Interval interval: feats.inList){
		
			String sub = window.substring(interval.getStart(), interval.getStop());
			if(sub.contains((interval.getSequence())))
				foundMap.put(interval, true);

		}
			
		for(Kpos kpos : feats.getKposList()){
			//System.out.println("stuck2?"+System.currentTimeMillis());
			if(kpos.getSequence().length() + kpos.getStart() < window.length() ){
				if(window.substring(kpos.getStart(), kpos.getStart()+
						kpos.getSequence().length()).equals(kpos.getSequence())){
					foundMap.put(kpos,true);
				}
			}
		}

		for(Feature feat: foundMap.keySet()){
			if(feat instanceof Conjunctive ){
				if (((Conjunctive)feat).isFound(foundMap)){
					site.addToScore(feat.getScore());
				}	
			}
			else if(foundMap.get(feat)){
				site.addToScore(feat.getScore());
			}	
		}
		
		//ready.release();
		site.setToReady();
		
		
	}
	

}
