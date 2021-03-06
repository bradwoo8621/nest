/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.internal;

import com.github.nest.arcteryx.meta.beans.IBeanDestroyer;

/**
 * bean destroyer, singleton, never care the bean descriptor. will do nothing
 * when call {@linkplain #destroy(Object)} or {@linkplain #handle(Object)}, and
 * always return true.
 * 
 * @author brad.wu
 */
public class BeanDestroyer extends AbstractStaticCodeBeanOperator implements IBeanDestroyer {
	/**
	 * singleton, never care the resource descriptor
	 */
	public final static IBeanDestroyer DESTROYER = new BeanDestroyer();

	private BeanDestroyer() {
	}

	/**
	 * do nothing, return true
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDestroyer#destroy(java.lang.Object)
	 */
	@Override
	public boolean destroy(Object resource) {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.internal.AbstractStaticCodeResourceOperator#createCode()
	 */
	@Override
	protected String createCode() {
		return CODE;
	}
}
