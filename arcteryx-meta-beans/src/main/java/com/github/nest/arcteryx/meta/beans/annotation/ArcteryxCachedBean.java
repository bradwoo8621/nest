/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nest.arcteryx.meta.annotation.ArcteryxResource;
import com.github.nest.arcteryx.meta.beans.annotation.scan.CachedBeanDescriptorGenerator;
import com.github.nest.arcteryx.meta.beans.internal.CachedBeanDescriptor;

/**
 * cached bean parameters.<br>
 * when {@linkplain ArcteryxBean#descriptorClass()} is
 * {@linkplain CachedBeanDescriptor}, add this annotation to type.
 * 
 * @author brad.wu
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArcteryxResource(generator = CachedBeanDescriptorGenerator.class)
public @interface ArcteryxCachedBean {
	/**
	 * bean name in bean context. keep unique.
	 * 
	 * @return
	 */
	String name();

	/**
	 * description of bean, optional
	 * 
	 * @return
	 */
	String description() default "";

	/**
	 * cache name
	 * 
	 * @return
	 */
	String cacheName();

	/**
	 * default sorter code, optional
	 * 
	 * @return
	 */
	String defaultSorterCode() default "";
}
