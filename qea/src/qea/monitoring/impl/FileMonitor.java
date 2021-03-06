package qea.monitoring.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import qea.monitoring.impl.translators.OfflineTranslator;
import qea.structure.impl.other.Verdict;
import qea.structure.intf.QEA;
import qea.exceptions.ShouldNotHappenException;

/**
 * Provides the base implementation for an offline monitor processing a trace in
 * a file
 * 
 * @author Giles Reger
 * @author Helena Cuenca
 */
public abstract class FileMonitor {

	protected BufferedReader trace;
	protected OfflineTranslator translator;


	public FileMonitor(String tracename, QEA qea, OfflineTranslator translator)
			throws FileNotFoundException {

		translator.setMonitor(MonitorFactory.create(qea));
		this.translator = translator;
		trace = new BufferedReader(new FileReader(tracename));
	}

	
	public abstract Verdict monitor() throws IOException;

	public Class getMonitorClass() {
		return translator.getMonitor().getClass();
	}

	protected String format(String arg) {
		return arg.intern();
	}	
	
}
