/**
 * 
 */
package com.github.nest.sparrow.party.internal.operator;

import com.github.nest.goose.internal.FileLineCacheProvider;
import com.github.nest.sparrow.party.internal.codes.Industry;

/**
 * country cache provider
 * 
 * @author brad.wu
 */
public class IndustryCacheProvider extends FileLineCacheProvider {
	/**
	 * (non-Javadoc)
	 * @see com.github.nest.goose.internal.FileLineCacheProvider#createBean(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createBean(String line) {
		String[] ss = line.split("[|]");
		Industry industry = new Industry();
		industry.setCode(ss[0]);
		industry.setName(ss[1]);
		return (T) industry;
	}
}
