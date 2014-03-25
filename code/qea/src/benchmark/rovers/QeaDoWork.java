package benchmark.rovers;

import monitoring.impl.MonitorFactory;
import monitoring.intf.Monitor;
import structure.impl.other.Verdict;
import structure.intf.*;

public class QeaDoWork extends DoWork<QEA>{

	  public static boolean print = false;
	
	  public void run_with_spec(QEA qea, String name, int[] args){
		  
		  setup(qea);
		  dowork(name,args);
		  if(print) System.err.println(monitor);
	  }	
	
	  private Monitor monitor;
	  private int[] events;
  	  
	  private void setup(QEA qea){
		  monitor = MonitorFactory.create(qea);
		  events = new int[14];
		  
		  events[0] = qea.get_event_id("command");
		  events[1] = qea.get_event_id("request");
		  events[2] = qea.get_event_id("grant");
		  events[3] = qea.get_event_id("deny");
		  events[4] = qea.get_event_id("rescind");
		  events[5] = qea.get_event_id("cancel");
		  events[6] = qea.get_event_id("succeed");
		  events[7] = qea.get_event_id("set_ack_timeout");
		  events[8] = qea.get_event_id("schedule");
		  events[9] = qea.get_event_id("finish");
		  events[10] = qea.get_event_id("conflict");
		  events[11] = qea.get_event_id("ping");
		  events[12] = qea.get_event_id("send");
		  events[13] = qea.get_event_id("ack");
		  
	  }	  
		  
	  public void handle(Verdict verdict){
		  if(verdict == Verdict.FAILURE)
			  System.err.println("warning: failure");
	  }
	  
	   public void command(int x){ handle(monitor.step(events[0],x)); }
	   public void command(Object o){handle(monitor.step(events[0],o));}	
	   public void command(Object a,Object b,Object c,int d){ handle(monitor.step(events[0],a,b,c,d));}	   
	   
	   public void request(Object o){ handle(monitor.step(events[1],o)); }
	   
	   public void grant(Object o){ handle(monitor.step(events[2],o));}
	   public void grant(Object a, Object b){ handle(monitor.step(events[2],a,b));}
	   
	   public void deny(Object o){handle(monitor.step(events[3],o)); }
	   public void rescind(Object o){handle(monitor.step(events[4],o));}
	   
	   public void cancel(Object o){ handle(monitor.step(events[5],o));}
	   public void cancel(Object a, Object b){ handle(monitor.step(events[5],a,b));}
	   
	   public void succeed(Object o){ handle(monitor.step(events[6],o));}	  
	   public void set_ack_timeout(int x){ handle(monitor.step(events[7],x));}
	   
	   
	   public void schedule(Object a, Object b){ handle(monitor.step(events[8],a,b));}
	   public void finish(Object o){ handle(monitor.step(events[9],o));}
	   public void conflict(Object a, Object b){ handle(monitor.step(events[10],a,b));}
	   public void ping(Object a, Object b){ handle(monitor.step(events[11],a,b));}
	   public void send(Object a, Object b, int c){ handle(monitor.step(events[12],a,b,c));}
	   
	   public void ack(Object a, Object b){ handle(monitor.step(events[13],a,b));;}
	   public void ack(Object a, Object b, int c){ handle(monitor.step(events[13],a,b,c));}
	   public void ack(Object o,int x){ handle(monitor.step(events[13],o,x));}	   
	
}
