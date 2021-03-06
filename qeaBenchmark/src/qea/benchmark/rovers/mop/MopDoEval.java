package qea.benchmark.rovers.mop;

import qea.benchmark.rovers.DoEval;
import qea.benchmark.rovers.DoWork;

public class MopDoEval extends DoEval<String> {

	@Override
	public DoWork<String> makeWork() {
		return new MopDoWork();
	}

	public static void main(String[] args) {

		do_the_eval();

	}

	public static void do_the_eval() {

		MopDoEval eval = new MopDoEval();
		DoEval.hardest_only = true;
		DoEval.csv_mode = true;

		eval.eval_for_GrantCancel("GrantCancel", "GrantCancel");

		eval.eval_for_ResourceLifecycle("ResourceLifecycle",
				"ResourceLifecycle");

		eval.eval_for_RespectConflicts("RespectConflicts", "RespectConflicts");

		// eval.eval_for_IncreasingCommand("IncreasingCommand","IncreasingCommand");

		// eval.eval_for_AcknowledgeCommands("AcknowledgeCommands",
		// "AcknowledgeCommands");

		// eval.eval_for_ReleaseResource("ReleaseResource","ReleaseResource");

		eval.eval_for_NestedCommand("NestedCommand", "NestedCommand");

	}

	public static class MopDoWork extends DoWork<String> {

		public boolean print = false;

		@Override
		public void run_with_spec(String spec, String name, int[] args) {
			// Doesn't do anything as we use aspects
			dowork(name, args);
		}

		/*
		 * THE EMPTY METHODS THAT THE ASPECTJ GRABS ONTO!!
		 */
		public static String last_event = "";

		@Override
		public void command_int(int x) {
			last_event = "command";
		}

		@Override
		public void request(Object o) {
			last_event = "request";
			if (print) {
				System.err.println(last_event + " "
						+ System.identityHashCode(o));
			}
		}

		@Override
		public void deny(Object o) {
			last_event = "deny";
			if (print) {
				System.err.println(last_event + " "
						+ System.identityHashCode(o));
			}
		}

		@Override
		public void rescind(Object o) {
			last_event = "rescind";
			if (print) {
				System.err.println(last_event + " "
						+ System.identityHashCode(o));
			}
		}

		@Override
		public void succeed(Object o) {
			last_event = "succeed";
		}

		@Override
		public void command(Object o) {
			last_event = "command";
		}

		@Override
		public void set_ack_timeout(int x) {
			last_event = "set_ack_timeout";
		}

		@Override
		public void command(Object a, Object b, Object c, int d) {
			last_event = "command";
		}

		@Override
		public void ack(Object o, int x) {
			last_event = "ack";
		}

		@Override
		public void grant_rr(Object a, Object b) {
			last_event = "grant";
		}

		@Override
		public void grant_gc(Object a, Object b) {
			last_event = "grant";
		}

		@Override
		public void cancel_gc(Object a, Object b) {
			last_event = "cancel";
		}

		@Override
		public void cancel_rr(Object a, Object b) {
			last_event = "cancel";
		}

		@Override
		public void schedule(Object a, Object b) {
			last_event = "schedule";
		}

		@Override
		public void finish(Object o) {
			last_event = "finish";
		}

		@Override
		public void conflict(Object a, Object b) {
			last_event = "conflict";
		}

		@Override
		public void ping(Object a, Object b) {
			last_event = "ping";
		}

		@Override
		public void ack(Object a, Object b) {
			last_event = "ack";
		}

		@Override
		public void ack(Object a, Object b, int c) {
			last_event = "ack";
		}

		@Override
		public void send(Object a, Object b, int c) {
			last_event = "send";
		}

		@Override
		public void fail(Object o) {
			last_event = "fail";
		}

		@Override
		public void priority(Object a, Object b) {
			last_event = "priority";
		}

		@Override
		public void grant_rl(Object o) {
			last_event = "grant";
			if (print) {
				System.err.println(last_event + " "
						+ System.identityHashCode(o));
			}
		}

		@Override
		public void grant_rc(Object o) {
			last_event = "grant";
		}

		@Override
		public void grant_rp(Object o) {
			last_event = "grant";
		}

		@Override
		public void cancel_rl(Object o) {
			last_event = "cancel";
			if (print) {
				System.err.println(last_event + " "
						+ System.identityHashCode(o));
			}
		}

		@Override
		public void cancel_rc(Object o) {
			last_event = "cancel";
		}

		@Override
		public void cancel_rp(Object o) {
			last_event = "cancel";
		}
	}

}
