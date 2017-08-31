package deterministic;

import java.util.ArrayList;
import java.util.List;

public class Machine {
	public final String label;
	//jobs assigned to this machine
	public List<Job> jobsInMachine = new ArrayList<>();
	public int nextIdleTime;
	
	/***
	 * @param label -- Machine label e.g. "Machine 1"
	 */
	public Machine(String label) {
		this.label = label;
	}
	
	/**
	 * Adds job to machine
	 * @param next -- the job to add
	 */
	public void addJob(Job next) {
		//start time == idle time
		next.startTime = this.nextIdleTime;
		//add to list of jobs handled by this machine
		this.jobsInMachine.add(next);
		//idle time == duration of job
		this.nextIdleTime += next.getDuration();
		//end time == idle time
		next.endTime = this.nextIdleTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(label);
		builder.append(", jobsInMachine=");
		for (Job job : jobsInMachine)
			builder.append(job);
		builder.append("]");
		return builder.toString();
	}

}
