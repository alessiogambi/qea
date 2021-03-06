package qea.test.properties.dacapo;

import static org.junit.Assert.assertEquals;
import static qea.structure.impl.other.Verdict.FAILURE;
import static qea.structure.impl.other.Verdict.SUCCESS;
import qea.monitoring.intf.Monitor;

import org.junit.Before;
import org.junit.Test;

import qea.properties.papers.DaCapo;
import qea.structure.intf.QEA;
import qea.test.TestSettings;

public class PersistentHashesTest {

	private Monitor monitor;
	private QEA qea = DaCapo.makePersistentHashes();

	@Before
	public void setup() {
		monitor = TestSettings.create(qea);
	}

	int ADD = 1;
	int OBSERVE = 2;
	int REMOVE = 3;

	@Test
	public void test_one() {
		Object o = TestSettings.object("o");
		int hash = o.hashCode();

		monitor.step(ADD, o, hash, true);
		monitor.step(ADD, o, hash, true);
		monitor.step(REMOVE, o, hash, true);
		monitor.step(OBSERVE, o, hash);

		assertEquals(SUCCESS, monitor.end());
	}

	@Test
	public void test_two() {
		Object o = TestSettings.object("o");
		int hash = o.hashCode();

		monitor.step(ADD, o, hash, true);
		monitor.step(ADD, o, hash, true);
		monitor.step(REMOVE, o, hash, true);
		monitor.step(OBSERVE, o, hash);
		monitor.step(OBSERVE, o, new Object().hashCode());

		assertEquals(FAILURE, monitor.end());
	}

	@Test
	public void test_three() {
		Object o = TestSettings.object("o");
		int hash = o.hashCode();

		monitor.step(ADD, o, hash, true);
		monitor.step(ADD, o, hash, true);
		monitor.step(REMOVE, o, hash, true);
		monitor.step(OBSERVE, o, hash);
		monitor.step(ADD, o, new Object().hashCode(), true);

		assertEquals(FAILURE, monitor.end());
	}

}
