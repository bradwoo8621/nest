package com.github.nest.arcteryx.data.codes.ut06;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.github.nest.arcteryx.common.ut.EnableLogger;
import com.github.nest.arcteryx.context.Context;
import com.github.nest.arcteryx.data.codes.ICodeTableRegistry;

public class UT06Test extends EnableLogger {
	/**
	 * test constanted code table
	 */
	@Test
	public void test() {
		ApplicationContext context = Context.createContextByClassPath("ut06",
				"/com/github/nest/arcteryx/data/codes/ut06/CodeTableA.xml");
		ICodeTableRegistry registry = context.getBean("CodeTableRegistry", ICodeTableRegistry.class);

		CodeTableA codeTable = registry.getCodeTable("CodeTableA");
		assertTrue(codeTable.contains("CodeA"));
		assertTrue(codeTable.contains("CodeB"));
		assertTrue(codeTable.contains("CodeC"));
		assertFalse(codeTable.contains("CodeD"));

		registry.deregister("CodeTableA");
		codeTable = registry.getCodeTable("CodeTableA");
		assertNull(codeTable);
	}
}
