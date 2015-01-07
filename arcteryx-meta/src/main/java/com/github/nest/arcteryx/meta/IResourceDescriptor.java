/**
 * 
 */
package com.github.nest.arcteryx.meta;

import java.io.Serializable;
import java.util.Collection;

/**
 * Resource interface, describe the resource features, properties,
 * relationships, etc.
 * 
 * @author brad.wu
 */
public interface IResourceDescriptor extends Serializable {
	/**
	 * get name of object
	 * 
	 * @return
	 */
	String getName();

	/**
	 * get resource class
	 * 
	 * @return
	 */
	Class<?> getResourceClass();

	/**
	 * get description of object
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * get context of descriptor
	 * 
	 * @return
	 */
	<T extends IResourceDescriptorContext> T getContext();

	/**
	 * get properties which declared in current descriptor and its ancestor's
	 * 
	 * @return
	 */
	Collection<IPropertyDescriptor> getProperties();

	/**
	 * get operator by given code
	 * 
	 * @param code
	 * @return
	 */
	<T extends IResourceOperator> T getOperator(String code);

	/**
	 * get all operators
	 * 
	 * @return
	 */
	Collection<IResourceOperator> getOperators();

	/**
	 * get parent descriptor
	 * 
	 * @return
	 */
	IResourceDescriptor getParent();

	/**
	 * get properties declared in current and parents. if the property is
	 * declared more than twice, ancestor's will be overwritten.
	 * 
	 * @param all
	 *            true then return all properties contains ancestor's. otherwise
	 *            return properties declared in current descriptor
	 * @return
	 */
	Collection<IPropertyDescriptor> getProperties(boolean all);
}
