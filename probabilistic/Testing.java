package probabilistic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.management.InvalidAttributeValueException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class Testing {
	// helper vars
	private static int currentHashID = 1;
	private static HashMap<Integer, ArrayList<Long>> times = new HashMap<>();
	private static HashMap<Integer, ArrayList<Integer>> makespans = new HashMap<>();
	private static HashMap<Integer, String> names = new HashMap<>();
	private static final int LOOP = 15;

	@Rule 
	public TestName name = new TestName();
	
	@Before
	public void makeArray() {
		times.put(currentHashID, new ArrayList<Long>());
		makespans.put(currentHashID, new ArrayList<Integer>());
	}

	@After
	public void incrementID() {
		currentHashID++;
	}
	
	@AfterClass
	public static void printTimes() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("probabilistic_test_results.csv"));
        StringBuffer buf = new StringBuffer();
        buf.append("id,name,times\n");
        
		for(Map.Entry<Integer, ArrayList<Long>> entry: times.entrySet()){
			buf.append(entry.getKey()+","+names.get(entry.getKey())+",");
			for (long l : entry.getValue()) {
				buf.append(l+",");
			}
			buf.append('\n');
		}
		
		pw.write(buf.toString());
        pw.close();
        
        pw = new PrintWriter(new File("probabilistic_makesapan_results.csv"));
        buf = new StringBuffer();
        buf.append("id,name,makespans\n");
        
		for(Map.Entry<Integer, ArrayList<Integer>> entry: makespans.entrySet()){
			buf.append(entry.getKey()+","+names.get(entry.getKey())+",");
			for (long l : entry.getValue()) {
				buf.append(l+",");
			}
			buf.append('\n');
		}
		
		pw.write(buf.toString());
        pw.close();
	}
	
	@Test
	public void testM10JobBy100() throws InvalidAttributeValueException {
		names.put(currentHashID,name.getMethodName());
		
		int M = 10;
		for (int i = 0; i < LOOP; i++) {
			ProbabilisticScheduler ps = new ProbabilisticScheduler(M, M*10);
			ps.solve();
			times.get(currentHashID).add(ps.runningTime());
			ps.output();
			makespans.get(currentHashID).add(ps.minimumMakeSpan);
		}
	}
	
	@Test
	public void testM30JobBy100() throws InvalidAttributeValueException {
		names.put(currentHashID,name.getMethodName());
		
		int M = 30;
		for (int i = 0; i < LOOP; i++) {
			ProbabilisticScheduler ps = new ProbabilisticScheduler(M, M*100);
			ps.solve();
			times.get(currentHashID).add(ps.runningTime());
			ps.output();
			makespans.get(currentHashID).add(ps.minimumMakeSpan);
		}
	}
	
	@Test
	public void testM50JobBy100() throws InvalidAttributeValueException {
		names.put(currentHashID,name.getMethodName());
		
		int M = 50;
		for (int i = 0; i < LOOP; i++) {
			ProbabilisticScheduler ps = new ProbabilisticScheduler(M, M*100);
			ps.solve();
			times.get(currentHashID).add(ps.runningTime());
			ps.output();
			makespans.get(currentHashID).add(ps.minimumMakeSpan);
		}
	}
	
	@Test
	public void testM70JobBy100() throws InvalidAttributeValueException {
		names.put(currentHashID,name.getMethodName());
		
		int M = 70;
		for (int i = 0; i < LOOP; i++) {
			ProbabilisticScheduler ps = new ProbabilisticScheduler(M, M*100);
			ps.solve();
			times.get(currentHashID).add(ps.runningTime());
			ps.output();
			makespans.get(currentHashID).add(ps.minimumMakeSpan);
		}
	}
	
	@Test
	public void testM90JobBy100() throws InvalidAttributeValueException {
		names.put(currentHashID,name.getMethodName());
		
		int M = 90;
		for (int i = 0; i < LOOP; i++) {
			ProbabilisticScheduler ps = new ProbabilisticScheduler(M, M*100);
			ps.solve();
			times.get(currentHashID).add(ps.runningTime());
			ps.output();
			makespans.get(currentHashID).add(ps.minimumMakeSpan);
		}
	}
}
