package qea.benchmark.competition.java.larva.monitoring;

import qea.properties.Property;
import qea.properties.competition.Larva;
import qea.structure.impl.other.Verdict;
import qea.transactionsystem.Interface;
import qea.benchmark.competition.java.QEAMonitoringAspect;

public aspect LarvaFourAspect extends QEAMonitoringAspect {

	private final int APPROVE = 1;

	public LarvaFourAspect() {
		super(new Larva().make(Property.LARVA_FOUR));
		validationMsg = "Property Larva 4 satisfied";
		violationMsg = "Property Larva 4 violated. An account approved by the administrator may not have the same account number as any other already existing account in the system";
	}

	pointcut approveAccount(String accountId) :
		call(void Interface.ADMIN_approveOpenAccount(Integer, String))
		&& args(*, accountId);

	before(String accountId) : approveAccount(accountId) {
		Verdict verdict = monitor.step(APPROVE, accountId);
		if (verdict == Verdict.FAILURE) {
			System.err.println(violationMsg + " [accountNumber=" + accountId
					+ "]");
			printTimeAndExit();
		}
	};
}
