/**
 * 
 */
package com.github.nest.sparrow.party.internal.codes;

import org.apache.commons.lang3.StringUtils;

import com.github.nest.goose.internal.AbstractCodeBaseBean;
import com.github.nest.sparrow.party.codes.IEducationDegree;

/**
 * education degree
 * 
 * @author brad.wu
 */
public class EducationDegree extends AbstractCodeBaseBean implements IEducationDegree {
	private static final long serialVersionUID = 1937491111383846132L;

	private String code = null;
	private String name = null;

	public EducationDegree() {
	}

	public EducationDegree(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.goose.ICodeBaseBean#getCode()
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		assert StringUtils.isNotBlank(code) : "Code cannot be null or empty string.";

		this.code = code;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.sparrow.party.codes.IEducationDegree#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		assert StringUtils.isNotBlank(name) : "Name cannot be null or empty string.";

		this.name = name;
	}
}
