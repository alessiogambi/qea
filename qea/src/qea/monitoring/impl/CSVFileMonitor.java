package qea.monitoring.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import qea.monitoring.impl.translators.OfflineTranslator;
import qea.structure.impl.other.Verdict;
import qea.structure.intf.QEA;

/*
 * We expect events in the form
 * 
 * name,arg0=A,arg1=B
 * 
 * where we ignore the names ar0,arg1.. so this becomes name(A,B)
 * 
 * we assume that all event names (in string form) begin with a lower case character
 * 
 */

public class CSVFileMonitor extends FileMonitor {

	public CSVFileMonitor(String tracename, QEA qea,
			OfflineTranslator translator) throws FileNotFoundException {
		super(tracename, qea, translator);
	}

	@Override
	public Verdict monitor() throws IOException {

		String line;
		int events = 0;
		boolean not_failed = true;
		while (not_failed && (line = trace.readLine()) != null) {
			events++;
			
			 //if (events % 10000 == 0) {
				 
			 //System.err.println(events/10000+" x 10k");
			// System.err.println(translator.getMonitor());
			 //}
			// System.err.println(events+":"+line);
			Verdict verdict = step(line);
			if (verdict == Verdict.FAILURE) {
				System.err.println("Failure on " + events + ":" + line);
				not_failed = false;
			}
		}
		System.err.println(events + " events");
		return translator.getMonitor().end();
	}

	private Verdict step(String line) {

		String[] parts = line.split(",\\s?|\\s?=\\s?");
		// int name = translate(parts[0]);
		if (parts.length == 3) {
			return translator.translateAndStep(parts[0],
					new String[] { format(parts[1]) },
					new String[] { format(parts[2]) });
		} else if (parts.length == 5) {
			return translator.translateAndStep(parts[0], new String[] {
					format(parts[1]), format(parts[3]) }, new String[] {
					format(parts[2]), format(parts[4]) });
		} else {
			int noargs = (parts.length - 1) / 2;
			String[] args = new String[noargs];
			String[] argNames = new String[noargs];
			for (int i = 2, j = 0; j < noargs; i += 2, j++) {
				argNames[j] = format(parts[i - 1]);
				args[j] = format(parts[i]);
			}
			return translator.translateAndStep(parts[0], argNames, args);
		}
	}
}
