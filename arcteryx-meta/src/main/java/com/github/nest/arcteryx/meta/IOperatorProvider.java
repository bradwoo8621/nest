package com.github.nest.arcteryx.meta;

/**
 * default operator provider
 * 
 * @author brad.wu
 *
 */
public interface IOperatorProvider {
	/**
	 * create default operator of given descriptor
	 * 
	 * @param descriptor
	 * @return
	 */
	public <T extends IResourceOperator> T createDefaultOperator(IResourceDescriptor descriptor);
}