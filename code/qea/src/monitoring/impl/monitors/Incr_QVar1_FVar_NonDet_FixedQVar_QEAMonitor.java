package monitoring.impl.monitors;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import monitoring.impl.GarbageMode;
import monitoring.impl.RestartMode;
import monitoring.impl.configs.NonDetConfig;
import structure.impl.other.Verdict;
import structure.impl.qeas.QVar1_FVar_NonDet_FixedQVar_QEA;
import util.EagerGarbageHashMap;
import util.IgnoreIdentityWrapper;
import util.IgnoreWrapper;
import util.WeakIdentityHashMap;

/**
 * 
 * @author Helena Cuenca
 * @author Giles Reger
 */
public class Incr_QVar1_FVar_NonDet_FixedQVar_QEAMonitor extends
		Abstr_Incr_QVar1_FVar_QEAMonitor<QVar1_FVar_NonDet_FixedQVar_QEA> {

	private Map<Object, NonDetConfig> bindings;
	private final HashSet<Object> strong;

	public Incr_QVar1_FVar_NonDet_FixedQVar_QEAMonitor(RestartMode restart,
			GarbageMode garbage, QVar1_FVar_NonDet_FixedQVar_QEA qea) {
		super(restart, garbage, qea);
		switch (garbage) {
		case UNSAFE_LAZY:
		case OVERSAFE_LAZY:
		case LAZY:
			bindings = new WeakIdentityHashMap<>();
			break;
		case EAGER:
			bindings = new EagerGarbageHashMap<>();
			break;
		case NONE:
			bindings = new IdentityHashMap<>();
		}
		if (restart == RestartMode.IGNORE && garbage != GarbageMode.EAGER) {
			bindings = new IgnoreIdentityWrapper<>(bindings);
		}
		strong = new HashSet<Object>();
	}

	@Override
	public Verdict step(int eventName, Object[] args) {

		if (saved != null) {
			if (!restart()) {
				return saved;
			}
		}

		boolean existingBinding = false;
		boolean startConfigFinal = false;
		NonDetConfig config;

		// Obtain the value for the quantified variable
		Object qVarValue = args[0];

		// Determine if the value received corresponds to an existing binding
		if (bindings.containsKey(qVarValue)) { // Existing quantified
												// variable binding
			// Get current configuration for the binding
			config = bindings.get(qVarValue);
			// if config=null it means the object is ignored
			// we should stop processing it here
			if (config == null) {
				return computeVerdict(false);
			}

			// Assign flags for counters update
			existingBinding = true;
			startConfigFinal = qea.containsFinalState(config);

		} else { // New quantified variable binding

			// Create configuration for the new binding
			config = new NonDetConfig(qea.getInitialState(), qea.newBinding());
		}

		// Compute next configuration
		config = qea.getNextConfig(config, eventName, args, qVarValue);

		// Update/add configuration for the binding
		bindings.put(qVarValue, config);

		// Determine if there is a final/non-final strong state
		boolean endConfigFinal = checkFinalAndStrongStates(config, qVarValue);

		// Update counters
		updateCounters(existingBinding, startConfigFinal, endConfigFinal);

		return computeVerdict(false);
	}

	private static final Object[] emptyArgs = new Object[] {};

	@Override
	public Verdict step(int eventName) {
		return step(eventName, emptyArgs);
	}

	@Override
	public String getStatus() {
		String ret = "Map:\n";
		Set<Map.Entry<Object, NonDetConfig>> entryset = null;
		if (bindings instanceof EagerGarbageHashMap) {
			entryset = ((EagerGarbageHashMap) bindings).storeEntrySet();
		} else {
			entryset = bindings.entrySet();
		}
		for (Map.Entry<Object, NonDetConfig> entry : entryset) {
			ret += entry.getKey() + "\t->\t" + entry.getValue() + "\n";
		}
		return ret;
	}

	@Override
	protected int removeStrongBindings() {
		int removed = 0;
		for (Object o : strong) {
			NonDetConfig c = bindings.get(o);
			boolean is_final = false;
			for (int s : c.getStates()) {
				is_final |= qea.isStateFinal(s);
			}
			if (is_final == finalStrongState) {
				bindings.remove(o);
				removed++;
			}
		}
		strong.clear();
		return removed;
	}

	@Override
	protected int rollbackStrongBindings() {
		int rolled = 0;
		for (Object o : strong) {
			NonDetConfig c = bindings.get(o);
			boolean is_final = false;
			for (int s : c.getStates()) {
				is_final |= qea.isStateFinal(s);
			}
			if (is_final == finalStrongState) {
				bindings.put(
						o,
						new NonDetConfig(qea.getInitialState(), qea
								.newBinding()));
				rolled++;
			}
		}
		strong.clear();
		return rolled;
	}

	@Override
	protected int ignoreStrongBindings() {
		int ignored = 0;
		for (Object o : strong) {
			NonDetConfig c = bindings.get(o);
			boolean is_final = false;
			for (int s : c.getStates()) {
				is_final |= qea.isStateFinal(s);
			}
			if (is_final == finalStrongState) {
				((IgnoreWrapper) bindings).ignore(o);
				ignored++;
			}
		}
		strong.clear();
		return ignored;
	}

}
