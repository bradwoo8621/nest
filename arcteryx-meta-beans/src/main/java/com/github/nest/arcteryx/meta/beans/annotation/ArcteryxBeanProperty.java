/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nest.arcteryx.meta.annotation.ArcteryxResourceProperty;
import com.github.nest.arcteryx.meta.beans.annotation.scan.BeanPropertyDescriptorGenerator;

/**
 * bean property annotation. Name of property will be auto generated by method
 * or field which is annotated.
 * 
 * @author brad.wu
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArcteryxResourceProperty(generator = BeanPropertyDescriptorGenerator.class)
public @interface ArcteryxBeanProperty {
	/**
	 * description of property, optional
	 * 
	 * @return
	 */
	String description() default "";
}
