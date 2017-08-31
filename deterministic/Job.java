package deterministic;

public class Job {
	public final String label;
	public final Type jobType;
	public int startTime;
	public int endTime;
	
	/***
	 * @param label -- Job label e.g. "Job 1"
	 * @param type -- Job type depending on enum Type e.g. 0 for SIMPLEX; 1 for COMPLEX
	 */
	public Job(String label, int type) {
		this.label = label;
		this.jobType = (type == 0) ? Type.SIMPLE : Type.COMPLEX;
	}
	
	/**
	 * Job duration depending on Type
	 * @return
	 */
	public int getDuration() {
		return (this.jobType == Type.SIMPLE) ? 5 : 10;
	}
	
	/**
	 * Possible types of Jobs
	 */
	enum Type {
		SIMPLE, COMPLEX
	}

	@Override
	public String toString() {
		return "[" + label + ", " + jobType + ", s " + startTime + " e " + endTime + "]";
	}
}
