package monitoring.impl.translators;

import structure.impl.other.Verdict;

/**
 * Translator for the property ZOT+SOLOIST 1
 * 
 * @author Helena Cuenca
 * @author Giles Reger
 */
public class OfflineTranslator_SOLOIST_ONE extends OfflineTranslator {

	private final int WITHDRAW = 1;
	private final int LOGOFF = 2;
	private final String WITHDRAW_STR = "repwidraw";
	private final String LOGOFF_STR = "recvlogoff";

	@Override
	public Verdict translateAndStep(String eventName, String[] params) {
		switch (eventName) {
		case WITHDRAW_STR:
			return monitor.step(WITHDRAW, params[0]);
		case LOGOFF_STR:
			return monitor.step(LOGOFF, params[0]);
		default:
			return null;
		}
	}

	@Override
	public Verdict translateAndStep(String eventName) {
		// No event without parameters is relevant for this property
		return null;
	}

}
