/**
 * 
 */
package com.github.nest.arcteryx.meta.internal;

import com.github.nest.arcteryx.meta.IResourceDescriptor;
import com.github.nest.arcteryx.meta.IResourceOperator;

/**
 * static code resource operator. code of operator will be created by
 * {@linkplain #createCode()} and cannot be changed.
 * 
 * @author brad.wu
 */
public abstract class AbstractStaticCodeResourceOperator implements IResourceOperator {
	private String code = null;
	private IResourceDescriptor resourceDescriptor = null;

	public AbstractStaticCodeResourceOperator() {
		this.code = createCode();
	}

	/**
	 * create code
	 * 
	 * @return
	 */
	protected abstract String createCode();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceOperator#getCode()
	 */
	@Override
	public final String getCode() {
		return this.code;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceOperator#setResourceDescriptor(com.github.nest.arcteryx.meta.IResourceDescriptor)
	 */
	@Override
	public void setResourceDescriptor(IResourceDescriptor resourceDescriptor) {
		this.resourceDescriptor = resourceDescriptor;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceOperator#getResourceDescriptor()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IResourceDescriptor> T getResourceDescriptor() {
		return (T) this.resourceDescriptor;
	}
}
