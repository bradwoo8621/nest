/**
 * 
 */
package com.github.nest.arcteryx.persistent;

import com.github.nest.arcteryx.meta.beans.IBeanDescriptor;

/**
 * reference persistent column, many-to-one relationship.<br>
 * referenced bean can be persistent or in cache. <br>
 * life-cycle of the referenced bean is managed manually.
 * 
 * @author brad.wu
 */
public interface IManyToOnePersistentColumn extends IPersistentColumn {
	/**
	 * get referenced bean. <br>
	 * 
	 * @return
	 */
	IBeanDescriptor getReferencedBean();

	/**
	 * get referenced bean context name. bean can be referenced from another
	 * context.
	 * 
	 * @return
	 */
	String getReferencedBeanContextName();

	/**
	 * get foreign key column name, required.
	 * 
	 * @return
	 */
	String getForeignKeyColumnName();

	/**
	 * get foreign key property name.<br>
	 * if the referenced bean is not a persistent bean, foreign key property
	 * name is necessary. otherwise it is not need, system will find the
	 * property name automatically.
	 * 
	 * @return
	 */
	String getForeignKeyPropertyName();

	/**
	 * check the referenced bean is in same context with this or not
	 * 
	 * @return
	 */
	boolean isInSameContext();
}
