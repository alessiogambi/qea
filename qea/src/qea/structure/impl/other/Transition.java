package qea.structure.impl.other;

import java.util.Arrays;

import qea.structure.intf.Assignment;
import qea.structure.intf.Guard;

/**
 * This class represents a transition in the transition function for a QEA. It
 * has to be externally associated to a start state and an event name. It
 * contains the information of the parameters for the event, as well as the
 * guard and assignment if applicable.
 * 
 * @author Helena Cuenca
 * @author Giles Reger
 */
public class Transition {

	/**
	 * Array for the variable names that the event to which this transition is
	 * associated receives as parameters. A number greater or equal to zero is
	 * the name of a free variable, while negative integers are names for
	 * quantified variables
	 */
	private int[] variableNames;

	/**
	 * Guard associated to the transition
	 */
	private Guard guard;

	/**
	 * Assignment associated to the transition
	 */
	private Assignment assignment;

	/**
	 * End state for this transition
	 */
	private int endState;

	@Override
	public String toString() {
		String res = Arrays.toString(variableNames);
		if (guard != null) {
			res += "-" + guard;
		}
		if (assignment != null) {
			res += "-" + assignment;
		}
		return res + "->" + endState;
	}

	public Transition(int varName, int endState) {
		variableNames = new int[] { varName };
		this.endState = endState;
	}

	public Transition(int varName, Guard guard, int endState) {
		variableNames = new int[] { varName };
		this.guard = guard;
		this.endState = endState;
	}

	public Transition(int varName, Guard guard, Assignment assignment,
			int endState) {
		variableNames = new int[] { varName };
		this.guard = guard;
		this.assignment = assignment;
		this.endState = endState;
	}

	public Transition(int var1, int var2, int endState) {
		variableNames = new int[] { var1, var2 };
		this.endState = endState;
	}

	public Transition(int var1, int var2, Guard guard, int endState) {
		variableNames = new int[] { var1, var2 };
		this.guard = guard;
		this.endState = endState;
	}

	public Transition(int var1, int var2, Guard guard, Assignment assignment,
			int endState) {
		variableNames = new int[] { var1, var2 };
		this.guard = guard;
		this.assignment = assignment;
		this.endState = endState;
	}

	public Transition(int[] variableNames, Guard guard, int endState) {
		super();
		this.variableNames = variableNames;
		this.guard = guard;
		this.endState = endState;
	}

	public Transition(int[] variableNames, Guard guard, Assignment assignment,
			int endState) {
		super();
		this.variableNames = variableNames;
		this.guard = guard;
		this.assignment = assignment;
		this.endState = endState;
	}

	public Transition(int[] variableNames, int endState) {
		super();
		this.variableNames = variableNames;
		this.endState = endState;
	}

	public int[] getVariableNames() {
		return variableNames;
	}

	public void setVariableNames(int[] variableNames) {
		this.variableNames = variableNames;
	}

	public Guard getGuard() {
		return guard;
	}

	public void setGuard(Guard guard) {
		this.guard = guard;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public int getEndState() {
		return endState;
	}

	public void setEndState(int endState) {
		this.endState = endState;
	}

}
