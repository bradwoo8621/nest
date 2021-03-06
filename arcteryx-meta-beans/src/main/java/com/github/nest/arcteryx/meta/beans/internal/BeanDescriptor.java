/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.nest.arcteryx.meta.IPropertyDescriptor;
import com.github.nest.arcteryx.meta.beans.IBeanConstraint;
import com.github.nest.arcteryx.meta.beans.IBeanConstraintReorganizer;
import com.github.nest.arcteryx.meta.beans.IBeanCreator;
import com.github.nest.arcteryx.meta.beans.IBeanDescriptor;
import com.github.nest.arcteryx.meta.beans.IBeanDestroyer;
import com.github.nest.arcteryx.meta.beans.IBeanFinder;
import com.github.nest.arcteryx.meta.beans.IBeanPropertyDescriptor;
import com.github.nest.arcteryx.meta.beans.IBeanValidator;
import com.github.nest.arcteryx.meta.beans.constraints.BeanConstraints;
import com.github.nest.arcteryx.meta.internal.ResourceDescriptor;

/**
 * Bean descriptor
 * 
 * @author brad.wu
 */
public class BeanDescriptor extends ResourceDescriptor implements IBeanDescriptor {
	private static final long serialVersionUID = -292539093442024519L;

	@SuppressWarnings("rawtypes")
	private IBeanConstraint constraint = null;
	private IBeanConstraintReorganizer constraintReorganizer = null;
	private Collection<IBeanPropertyDescriptor> propertyDescriptors = null;
	private Collection<IBeanPropertyDescriptor> allPropertyDescriptors = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getBeanClass()
	 */
	@Override
	public Class<?> getBeanClass() {
		return this.getResourceClass();
	}

	/**
	 * @param beanClass
	 *            the beanClass to set
	 */
	public void setBeanClass(Class<?> beanClass) {
		this.setResourceClass(beanClass);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getConstraint()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IBeanConstraint getConstraint() {
		return this.constraint;
	}

	/**
	 * @param constraint
	 *            the constraint to set
	 */
	@SuppressWarnings("rawtypes")
	public void setConstraint(IBeanConstraint constraint) {
		this.constraint = constraint;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getConstraintReorganizer()
	 */
	@Override
	public IBeanConstraintReorganizer getConstraintReorganizer() {
		return this.constraintReorganizer;
	}

	/**
	 * @param constraintReorganizer
	 *            the constraintReplacement to set
	 */
	public void setConstraintReorganizer(IBeanConstraintReorganizer constraintReorganizer) {
		this.constraintReorganizer = constraintReorganizer;
	}

	/**
	 * set constraints
	 * 
	 * @param constraints
	 */
	@SuppressWarnings("rawtypes")
	public void setConstraints(List<IBeanConstraint> constraints) {
		assert constraints != null : "Constraints cannot be null.";

		BeanConstraints all = new BeanConstraints();
		all.setConstraints(constraints);
		setConstraint(all);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getValidator()
	 */
	@Override
	public IBeanValidator getValidator() {
		return this.getOperator(IBeanValidator.CODE);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getCreator()
	 */
	@Override
	public IBeanCreator getCreator() {
		return this.getOperator(IBeanCreator.CODE);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getDestoryer()
	 */
	@Override
	public IBeanDestroyer getDestoryer() {
		return this.getOperator(IBeanDestroyer.CODE);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getFinder()
	 */
	@Override
	public IBeanFinder getFinder() {
		return this.getOperator(IBeanFinder.CODE);
	}

	/**
	 * Will cache the {@linkplain IBeanPropertyDescriptor} at first call, and
	 * never changed again.
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getBeanProperties()
	 */
	@Override
	public Collection<IBeanPropertyDescriptor> getBeanProperties() {
		if (this.allPropertyDescriptors == null) {
			synchronized (this) {
				if (this.allPropertyDescriptors == null) {
					List<IBeanPropertyDescriptor> list = new ArrayList<IBeanPropertyDescriptor>();

					Collection<IPropertyDescriptor> descriptors = this.getProperties();
					for (IPropertyDescriptor descriptor : descriptors) {
						if (descriptor instanceof IBeanPropertyDescriptor) {
							list.add((IBeanPropertyDescriptor) descriptor);
						}
					}
					this.allPropertyDescriptors = list;
				}
			}
		}
		return this.allPropertyDescriptors;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.IBeanDescriptor#getDeclaredBeanProperties()
	 */
	@Override
	public Collection<IBeanPropertyDescriptor> getDeclaredBeanProperties() {
		if (this.propertyDescriptors == null) {
			synchronized (this) {
				if (this.propertyDescriptors == null) {
					List<IBeanPropertyDescriptor> list = new ArrayList<IBeanPropertyDescriptor>();

					Collection<IPropertyDescriptor> descriptors = this.getDeclaredProperties();
					for (IPropertyDescriptor descriptor : descriptors) {
						if (descriptor instanceof IBeanPropertyDescriptor) {
							list.add((IBeanPropertyDescriptor) descriptor);
						}
					}
					this.propertyDescriptors = list;
				}
			}
		}
		return this.propertyDescriptors;
	}
}
