/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.nest.arcteryx.meta.beans.ConstraintApplyTo;
import com.github.nest.arcteryx.meta.beans.ConstraintSeverity;
import com.github.nest.arcteryx.meta.beans.constraints.AssertValidConstraint;

/**
 * assert valid
 * 
 * @author brad.wu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Constraint(constraintClass = AssertValidConstraint.class)
public @interface AssertValid {
	/**
	 * name of constraint
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * message template
	 * 
	 * @return
	 */
	String messageTemplate() default "";

	/**
	 * error code
	 * 
	 * @return
	 */
	String errorCode() default "";

	/**
	 * profiles
	 * 
	 * @return
	 */
	String[] profiles() default {};

	/**
	 * severity
	 * 
	 * @return
	 */
	ConstraintSeverity severity() default ConstraintSeverity.ERROR;

	/**
	 * when
	 * 
	 * @return
	 */
	String when() default "";

	/**
	 * applies to
	 * 
	 * @return
	 */
	ConstraintApplyTo appliesTo() default ConstraintApplyTo.VALUES;

	/**
	 * target
	 * 
	 * @return
	 */
	String target() default "";

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Constraints
	public @interface List {
		AssertValid[] values();
	}
}
