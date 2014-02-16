package test.properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import monitoring.impl.MonitorFactory;
import monitoring.intf.Monitor;
import properties.SampleNonDetQEA;
import structure.intf.QEA;
import static structure.impl.Verdict.*;

import monitoring.impl.monitors.*;

public class SampleNonDetTest {
	
	private Monitor monitor;
	private QEA qea = new SampleNonDetQEA();
	
	static final int a = 1;
	static final int b = 2;
	static final int c = 3;
	static final int d = 4;
	static final int e = 5;
	static final int f = 6;
	
	@Before
	public void setup(){
		monitor = MonitorFactory.create(qea);
	}
	
	private void printMonitor(){
		System.out.println(((IncrementalNonDetQEAMonitor) monitor));
	}
	
	@Test
	public void test_one() {
		// Test reach state 3
		Object x = new Object();
		printMonitor();
		assertEquals(monitor.step(a,x),WEAK_FAILURE);
		printMonitor();
		assertEquals(monitor.step(c,x),WEAK_SUCCESS);
		printMonitor();
	}
	@Test
	public void test_two() {
		// Test reach state 6
		Object x = new Object();
		
		assertEquals(monitor.step(a,x),WEAK_FAILURE);
		assertEquals(monitor.step(d,x),WEAK_FAILURE);
		assertEquals(monitor.step(e,x),WEAK_SUCCESS);
	}	
	@Test
	public void test_three() {
		// Test reach state 8
		Object x = new Object();
		
		assertEquals(monitor.step(a,x),WEAK_FAILURE);
		assertEquals(monitor.step(d,x),WEAK_FAILURE);
		assertEquals(monitor.step(f,x),WEAK_SUCCESS);
	}		

	
}