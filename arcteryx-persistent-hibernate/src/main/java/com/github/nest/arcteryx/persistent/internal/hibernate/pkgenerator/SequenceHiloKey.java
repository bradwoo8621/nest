/**
 * 
 */
package com.github.nest.arcteryx.persistent.internal.hibernate.pkgenerator;

import org.apache.commons.lang3.StringUtils;

import com.github.nest.arcteryx.persistent.IPrimaryKey;

/**
 * sequence hilo key generator.<br>
 * uses a hi/lo algorithm to efficiently generate identifiers of type long,
 * short or int, given a named database sequence((by default
 * <b>hibernate_sequence</b> respectively)).
 * 
 * @author brad.wu
 */
public class SequenceHiloKey implements IPrimaryKey {
	private static final long serialVersionUID = 5299377166804343069L;
	public static final String DEFAULT_SEQUENCE_NAME = "HIBERNATE_SEQUENCE";
	public static final int DEFAULT_MAX_LOW_VALUE = 100;

	private String sequenceName = null;
	private int maxLowValue = DEFAULT_MAX_LOW_VALUE;

	/**
	 * @return the sequenceName
	 */
	public String getSequenceName() {
		return StringUtils.isBlank(sequenceName) ? DEFAULT_SEQUENCE_NAME : sequenceName;
	}

	/**
	 * @param sequenceName
	 *            the sequenceName to set
	 */
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	/**
	 * @return the maxLowValue
	 */
	public int getMaxLowValue() {
		return maxLowValue;
	}

	/**
	 * @param maxLowValue
	 *            the maxLowValue to set
	 */
	public void setMaxLowValue(int maxLowValue) {
		this.maxLowValue = maxLowValue;
	}
}
