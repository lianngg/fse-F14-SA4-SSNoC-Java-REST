package edu.cmu.sv.ws.ssnoc.dto;



import com.google.gson.Gson;

/**
 * This object contains performance data that is responded as part of the REST
 * API request.
 * 
 */
public class PerformanceData {
    private long postPerSecond;
	private long getPerSecond;
	
    public long getPostPerSecond() {
        return postPerSecond;
    }

    public void setPostPerSecond(long postPerSecond) {
        this.postPerSecond = postPerSecond;
    }
    
	public long getGetPerSecond() {
		return getPerSecond;
	}

	public void setGetPerSecond(long getPerSecond) {
		this.getPerSecond = getPerSecond;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
