/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors;

import net.sf.oval.Check;

import com.github.nest.arcteryx.meta.beans.constraints.DateRangeConstraint;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.constraints.DateRangeCheck;

/**
 * date range convertor
 * 
 * @author brad.wu
 */
public class DateRangeConvertor extends AbstractOValPropertyCheckConvertor<DateRangeConstraint> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.internal.validators.oval.IOValCheckConvertor#getSupportedConstraintType()
	 */
	@Override
	public Class<DateRangeConstraint> getSupportedConstraintType() {
		return DateRangeConstraint.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.AbstractOValCheckConvertor#createCheck(com.github.nest.arcteryx.meta.beans.IBeanPropertyConstraint)
	 */
	@Override
	protected Check createCheck(DateRangeConstraint constraint) {
		DateRangeCheck check = new DateRangeCheck();
		check.setMax(constraint.getTo());
		check.setExcludeMax(constraint.isExcludeTo());
		check.setMin(constraint.getFrom());
		check.setExcludeMin(constraint.isExcludeFrom());
		check.setTolerance(constraint.getTolerance());
		check.setFormat(constraint.getFormat());
		return check;
	}
}
