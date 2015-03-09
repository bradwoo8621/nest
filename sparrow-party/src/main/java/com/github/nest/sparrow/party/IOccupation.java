/**
 * 
 */
package com.github.nest.sparrow.party;

import java.io.Serializable;

/**
 * occupation
 * 
 * @author brad.wu
 */
public interface IOccupation extends Serializable {
	/**
	 * get code
	 * 
	 * @return
	 */
	String getCode();

	/**
	 * get name
	 * 
	 * @return
	 */
	String getName();
}
