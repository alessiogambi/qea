package qea.monitoring.impl.monitors;

import qea.monitoring.impl.GarbageMode;
import qea.monitoring.impl.IncrementalMonitor;
import qea.monitoring.impl.RestartMode;
import qea.structure.impl.other.Verdict;
import qea.structure.impl.qeas.Abstr_QVar01_FVar_QEA;
import qea.util.ArrayUtil;

public abstract class Abstr_Incr_QVar1_FVar_QEAMonitor<Q extends Abstr_QVar01_FVar_QEA>
		extends IncrementalMonitor<Q> {

	public Abstr_Incr_QVar1_FVar_QEAMonitor(RestartMode restart,
			GarbageMode garbage, Q q) {
		super(restart, garbage, q);
	}

	@Override
	public Verdict end() {
		return computeVerdict(true);
	}

	/**
	 * Retrieves the different bindings for the quantified variable, according
	 * to the event mask and arguments specified
	 * 
	 * @param eventMask
	 *            Array containing a value <code>true</code> in the positions of
	 *            the quantified variable. The size must be equal to the size of
	 *            <code>args</code>
	 * @param args
	 *            Array of arguments for the event. The size must be equal to
	 *            the size of <code>eventMask</code>
	 * @param numQVarPositions
	 *            Number of positions in the mask for the quantified variable,
	 *            i.e., number of positions in <code>eventMask</code> whose
	 *            value is <code>true</code>
	 * @return An array with the different bindings (values) for the quantified
	 *         variable
	 */
	protected Object[] getDistinctQVarBindings(boolean[] eventMask,
			Object[] args, int numQVarPositions) {

		Object[] bindings = new Object[numQVarPositions];
		int numBindings = 0;
		boolean existingBinding;

		// Iterate over the mask
		for (int i = 0; i < eventMask.length; i++) {

			if (eventMask[i]) { // QVar position found

				// Check if the binding is already in the array
				existingBinding = false;
				for (int j = 0; j < numBindings; j++) {
					if (args[i].equals(bindings[j])) {
						existingBinding = true;
					}
				}

				// If it's a new binding, add it
				if (!existingBinding) {
					bindings[numBindings] = args[i];
					numBindings++;
				}
			}
		}

		return ArrayUtil.resize(bindings, numBindings);
	}

	/**
	 * Retrieves the first binding for the quantified variable, according to the
	 * event mask and arguments specified
	 * 
	 * @param eventMask
	 *            Array containing a value <code>true</code> in the positions of
	 *            the quantified variable. The size must be equal to the size of
	 *            <code>args</code>
	 * @param args
	 *            Array of arguments for the event. The size must be equal to
	 *            the size of <code>eventMask</code>
	 * @return The first binding (value) for the quantified variable or
	 *         <code>null</code> if the array <code>eventMask</code> does not
	 *         contain any mark for the quantified variable
	 */
	protected Object getFirstQVarBinding(boolean[] eventMask, Object[] args) {

		for (int i = 0; i < eventMask.length; i++) {
			if (eventMask[i]) {
				return args[i];
			}
		}
		return null;
	}

	/**
	 * Computes the verdict for this monitor according to the current state of
	 * the binding(s)
	 * 
	 * @param end
	 *            <code>true</code> if all the events have been processed and a
	 *            final verdict is to be computed; <code>false</code> otherwise
	 * 
	 * @return <ul>
	 *         <li>{@link Verdict#SUCCESS} for a strong success
	 *         <li>{@link Verdict#FAILURE} for a strong failure
	 *         <li>{@link Verdict#WEAK_SUCCESS} for a weak success
	 *         <li>{@link Verdict#WEAK_FAILURE} for a weak failure
	 *         </ul>
	 * 
	 *         A strong success or failure means that other events processed
	 *         after this will produce the same verdict, while a weak success or
	 *         failure indicates that the verdict can change
	 */
	protected Verdict computeVerdict(boolean end) {

		boolean universal = qea.isQuantificationUniversal();
		Verdict result = null;

		if (universal && allBindingsInFinalState() || !universal
				&& existsOneBindingInFinalState()) {
			if (end || finalStrongState && !universal) {
				saved = Verdict.SUCCESS;
				result = Verdict.SUCCESS;
			} else {
				result = Verdict.WEAK_SUCCESS;
			}
		} else if (end || nonFinalStrongState && universal) {
			saved = Verdict.FAILURE;
			result = Verdict.FAILURE;
		} else {
			result = Verdict.WEAK_FAILURE;
		}

		if (qea.isNegated()) {
			result = result.negated();
		}
		return result;
	}

}
