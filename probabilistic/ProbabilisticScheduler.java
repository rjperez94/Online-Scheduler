package probabilistic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.management.InvalidAttributeValueException;

import probabilistic.Job.Type;

public class ProbabilisticScheduler {
	//all jobs and all machines 
	private final List<Job> jobs = new ArrayList<>();
	private final List<Machine> machines = new ArrayList<>();
	//number of machines
	private final int m;
	//total job duration of all the jobs processed so far is tracked globally
	private int processedSoFar;
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
	public ProbabilisticScheduler(int machineCount, int jobCountLimit) throws InvalidAttributeValueException {
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
	public ProbabilisticScheduler(int machineCount, Type[] jobsType) throws InvalidAttributeValueException {
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
			jobs.add(new Job("Job " + (i + 1), jobsType[i].ordinal()));
		}
	}
	
	/**
	 * Main algorithm
	 */
	public void solve() {
		this.start = System.currentTimeMillis();
		
		//for all jobs
		for (int i = 0; i < jobs.size(); i++) {
			//generate random limit
			//assign job [i] to recipient machine
			getRandomMachine(new Random().nextDouble()).addJob(jobs.get(i));
			//increment processedSoFar by job duration
			processedSoFar += jobs.get(i).getDuration();
		}
		
		this.end = System.currentTimeMillis();
	}
	
	/**
	 * Get a random machine provided that it has a lower 'proportion limit'
	 * @param limit -- the random proportion limit
	 * @return random Machine that has a lower 'proportion limit'
	 */
	private Machine getRandomMachine(double limit) {
		boolean hit = false;
		Machine recipient = null;
		Random rand = new Random();
		double candidateProbability = 0;
		
		do {
			if(hit)System.out.println("HIT");
			//pick random machine from List<Machine>
			recipient = machines.get(rand.nextInt(machines.size()));
			//inspect if nextIdleTime/processedSoFar < limit....
			candidateProbability = (this.processedSoFar == 0) ? 0 : recipient.nextIdleTime / this.processedSoFar;
			
			hit = true;
			//....if it is, then pick random Machine again
		} while (candidateProbability >= limit);
		
		return recipient;
	}
	
	/**
	 * Print results of algorithm to console
	 */
	public void output() {
		//get makeSpan
		getMakeSpan();

		System.out.println("Scheduler finished at " + new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
		//get job assignments
		for (Machine mach : machines) {
			System.out.println(mach.toString());
		}
		System.out.println("Minimum makespan is " + minimumMakeSpan + " minute(s)");
	}
	
	/**
	 * Get makespan of algorithm
	 */
	private void getMakeSpan() {
		//go through each machine
		Machine result = machines.get(0);
		for (int i = 1; i < machines.size(); i++) {
			Machine candidate = machines.get(i);
			//get the least idle time
			if (candidate.nextIdleTime > result.nextIdleTime) {
				result = candidate;
			}
		}
		this.minimumMakeSpan = result.nextIdleTime;
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
			if (i % 2 == 1)
				jobs[i] = Type.SIMPLE;
			else
				jobs[i] = Type.COMPLEX;
		}
		//assign to 5 machines
		ProbabilisticScheduler tester = new ProbabilisticScheduler(5, jobs);
		//must call solve and output
		tester.solve();
		tester.output();
		
		//fill array with 30 jobs, 50% simple and 50% complex
		Job.Type[] jobs2 = new Job.Type[30];
		for (int i = 0; i < jobs2.length; i++) {
			if (i % 2 == 1)
				jobs2[i] = Type.SIMPLE;
			else
				jobs2[i] = Type.COMPLEX;
		}
		//sort first, see if it has impact
		Arrays.sort(jobs2);
		//assign to 5 machines
		ProbabilisticScheduler tester2 = new ProbabilisticScheduler(5, jobs2);
		//must call solve and output
		tester2.solve();
		tester2.output();
	}
}
