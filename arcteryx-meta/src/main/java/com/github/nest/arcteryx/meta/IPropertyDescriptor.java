/**
 * 
 */
package com.github.nest.arcteryx.meta;

import java.io.Serializable;

/**
 * Property of resource interface
 * 
 * @author brad.wu
 */
public interface IPropertyDescriptor extends Serializable {
	/**
	 * get name of property
	 * 
	 * @return
	 */
	String getName();

	/**
	 * get description
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * set resource descriptor
	 * 
	 * @param resourceDescriptor
	 */
	void setResourceDescriptor(IResourceDescriptor resourceDescriptor);

	/**
	 * get resource descriptor
	 * 
	 * @return
	 */
	IResourceDescriptor getResourceDescriptor();

	/**
	 * get resource descriptor by given class
	 * 
	 * @param descriptorClass
	 * @return
	 */
	<T extends IResourceDescriptor> T getResourceDescriptor(Class<T> descriptorClass);
}
