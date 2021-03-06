/**
 * 
 */
package com.github.nest.arcteryx.data.internal.codes;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import com.github.nest.arcteryx.data.codes.CodesRuntimeException;
import com.github.nest.arcteryx.data.codes.ICodeItem;
import com.github.nest.arcteryx.data.codes.ICodeItemFilter;
import com.github.nest.arcteryx.data.codes.ICodeTable;
import com.github.nest.arcteryx.data.codes.ICodeTableContentProvider;
import com.github.nest.arcteryx.data.codes.ICodeTableFilter;
import com.github.nest.arcteryx.data.codes.ICodeTableRegistry;
import com.github.nest.arcteryx.data.internal.codes.annotation.AnnotationUtil;

/**
 * code table
 * 
 * @author brad.wu
 */
public abstract class AbstractCodeTable implements ICodeTable {
	private static final long serialVersionUID = -3226999765206954279L;

	private String name = null;
	private ICodeTableContentProvider contentProvider = null;
	private ICodeTableRegistry registry = null;

	public AbstractCodeTable() {
		initialize();
	}

	/**
	 * initialize
	 */
	protected void initialize() {
		this.initializeName();
		this.initializeContentProvider();
	}

	/**
	 * load items
	 * 
	 * @return
	 */
	protected void initializeContentProvider() {
		this.setContentProvider(this.createContentProvider());
		this.getContentProvider().initialize(this);
	}

	/**
	 * @return the contentProvider
	 */
	protected ICodeTableContentProvider getContentProvider() {
		return contentProvider;
	}

	/**
	 * @param contentProvider
	 *            the contentProvider to set
	 */
	protected void setContentProvider(ICodeTableContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * create content provider
	 * 
	 * @return
	 * @throws CodesRuntimeException
	 */
	protected ICodeTableContentProvider createContentProvider() throws CodesRuntimeException {
		ICodeTableContentProvider provider = AnnotationUtil.createContentProvider(getClass());
		return provider == null ? createDefaultContentProvider() : provider;
	}

	/**
	 * create default content provider
	 * 
	 * @return
	 */
	protected ICodeTableContentProvider createDefaultContentProvider() {
		return new DefaultCodeTableContentProvider();
	}

	/**
	 * initialize name
	 * 
	 * @return
	 */
	protected void initializeName() {
		this.setName(AnnotationUtil.getRegistrationName(getClass()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#getRegistry()
	 */
	@Override
	public ICodeTableRegistry getRegistry() {
		return this.registry;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#setRegistry(com.github.nest.arcteryx.data.codes.ICodeTableRegistry)
	 */
	@Override
	public void setRegistry(ICodeTableRegistry registry) {
		this.registry = registry;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	protected void setName(String name) {
		assert StringUtils.isNotBlank(name) : "Name of code table cannot be blank.";

		this.name = name;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String code) {
		return this.getContentProvider().contains(code);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#getItem(java.lang.String)
	 */
	@Override
	public ICodeItem getItem(String code) {
		return this.getContentProvider().getItem(code);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#iterator()
	 */
	@Override
	public Iterator<ICodeItem> iterator() {
		return this.getContentProvider().iterator();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#getItems()
	 */
	@Override
	public Collection<ICodeItem> getItems() {
		return this.getContentProvider().getItems();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#getItems(com.github.nest.arcteryx.data.codes.ICodeItemFilter)
	 */
	@Override
	public Collection<ICodeItem> getItems(ICodeItemFilter filter) {
		return this.getContentProvider().getItems(filter);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.data.codes.ICodeTable#getItems(com.github.nest.arcteryx.data.codes.ICodeTableFilter)
	 */
	@Override
	public Collection<ICodeItem> getItems(ICodeTableFilter filter) {
		return this.getContentProvider().getItems(filter);
	}
}
