package deterministic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.management.InvalidAttributeValueException;

import deterministic.Job.Type;

public class GreedyScheduler {
	//all jobs and all machines 
	private final List<Job> jobs = new ArrayList<>();
	private final List<Machine> machines = new ArrayList<>();
	//number of machines
	private final int m;
	//algorithm makespan
	public int minimumMakeSpan;
	
	//excution time
	private long start;
	private long end;
	
	/***
	 * Constructor 1
	 * @param machineCount -- m
	 * @param jobCountLimit -- number of jobs. Types of jobs are randomly chosen
	 * @throws InvalidAttributeValueException
	 */
	public GreedyScheduler(int machineCount, int jobCountLimit) throws InvalidAttributeValueException {
		if (machineCount <= 0 || jobCountLimit == Integer.MAX_VALUE) {
			throw new InvalidAttributeValueException(
					"m must be >=1 and jobCountLimit must not equal to Integer.MAX_VALUE");
		}
		this.m = machineCount;

		initialiseMachines();
		generateJobs(jobCountLimit);
	}
	
	/**
	 * Constructor 2
	 * @param machineCount -- m
	 * @param jobsType -- the different jobs in format {Type.SIMPLE,Type.COMPLEX......TYPE.N} corresponding to job 1 is a simple job while job 2 is a complex job and so on
	 * @throws InvalidAttributeValueException
	 */
	public GreedyScheduler(int machineCount, Type[] jobsType) throws InvalidAttributeValueException {
		if (machineCount <= 0 || jobsType.length < machineCount) {
			throw new InvalidAttributeValueException(
					"m must be >=1 and jobCountLimit must not equal to Integer.MAX_VALUE");
		}
		this.m = machineCount;

		initialiseMachines();
		generateJobs(jobsType);
	}

	/**
	 * Add m number of machines to List<Machine>
	 */
	private void initialiseMachines() {
		for (int i = 1; i <= m; i++) {
			machines.add(new Machine("Machine " + i));
		}
	}
	
	/**
	 * ONLY USED BY Constructor 1
	 * Add jobLimit number of jobs to List<Job> with random types
	 */
	private void generateJobs(int jobLimit) {
		for (int i = 1; i <= jobLimit; i++) {
			jobs.add(randomJob(i));
		}
	}

	/**
	 * ONLY USED BY Constructor 1
	 * Add Job with ID as label to List<Job> with random type
	 * @param ID -- job label
	 * @return Job with ID and random type
	 */
	private Job randomJob(int ID) {
		Random rand = new Random();
		int jobType = rand.nextInt(2);
		return new Job("Job " + ID, jobType);
	}
	
	/**
	 * ONLY USED BY Constructor 2
	 * Add jobs to List<Job> with types specified in jobsType[i]
	 */
	private void generateJobs(Type[] jobsType) {
		for (int i = 0; i < jobsType.length; i++) {
			jobs.add(new Job("Job "+(i+1), jobsType[i].ordinal()));
		}
	}
	
	/**
	 * Main algorithm
	 */
	public void solve() {
		this.start = System.currentTimeMillis();
		
		Machine recipient = null;
		int cycles = 0;
		//for the first m jobs, assign jobs[i] to machines[i]
		for (int i = 0; i < jobs.size() && i < m; i++) {
			recipient = machines.get(i);
			recipient.addJob(jobs.get(i));
			cycles = i;
		}
		//for m..totalJobs, assigned jobs[i] to machine with least work (greedy)
		for (int i = cycles; i < jobs.size(); i++) {
			recipient = getMachineWithLeastWork(); // same as least nextIdleTime
			recipient.addJob(jobs.get(i));
		}
		
		this.end = System.currentTimeMillis();
	}
	
	/**
	 * Get the greedy option i.e. machine with least nextIdleTime
	 * @return Machine that satisfies greedy choice
	 */
	private Machine getMachineWithLeastWork() {
		//go through each machine
		Machine result = machines.get(0);
		for (int i = 1; i < machines.size(); i++) {
			Machine candidate = machines.get(i);
			if (candidate.nextIdleTime < result.nextIdleTime) {
				result = candidate;
			}
		}
		return result;
	}
	
	/**
	 * Print results of algorithm to console
	 */
	public void output() {
		//get makeSpan
		this.minimumMakeSpan = getMachineWithLeastWork().nextIdleTime;
		
		System.out.println("Scheduler finished at "+new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
		//get job assignments
		for (Machine mach : machines) {
			System.out.println(mach.toString());
		}
		System.out.println("Minimum makespan is " + minimumMakeSpan + " minute(s)");
	}
	
	/**
	 * USED FOR TESTING ONLY
	 * @return running time of algorithm in milliseconds
	 */
	public Long runningTime() {
		return end - start;
	}

	public static void main(String[] args) throws InvalidAttributeValueException {		
		//fill array with 30 jobs, 50% simple and 50% complex
		Job.Type[] jobs = new Job.Type[30];
		for (int i = 0; i < jobs.length; i++) {
			if(i%2 == 1) jobs[i] = Type.SIMPLE;
			else jobs[i] = Type.COMPLEX;
		}
		//assign to 5 machines
		GreedyScheduler tester = new GreedyScheduler(5, jobs);
		//must call solve and output
		tester.solve();
		tester.output();
		
		//fill array with 30 jobs, 50% simple and 50% complex
		Job.Type[] jobs2 = new Job.Type[30];
		for (int i = 0; i < jobs2.length; i++) {
			if(i%2 == 1) jobs2[i] = Type.SIMPLE;
			else jobs2[i] = Type.COMPLEX;
		}
		//sort first, see if it has impact
		Arrays.sort(jobs2);
		//assign to 5 machines
		GreedyScheduler tester2 = new GreedyScheduler(5, jobs2);
		//must call solve and output
		tester2.solve();
		tester2.output();
	}
}
