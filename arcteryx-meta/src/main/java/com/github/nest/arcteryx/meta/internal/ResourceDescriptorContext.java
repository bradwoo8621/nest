/**
 * 
 */
package com.github.nest.arcteryx.meta.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.nest.arcteryx.meta.IConfigurationInitializer;
import com.github.nest.arcteryx.meta.IOperatorProvider;
import com.github.nest.arcteryx.meta.IOperatorProviderRegistry;
import com.github.nest.arcteryx.meta.IResourceDescriptor;
import com.github.nest.arcteryx.meta.IResourceDescriptorContext;
import com.github.nest.arcteryx.meta.ResourceDescriptorContextRepository;
import com.github.nest.arcteryx.meta.util.ClassUtils;

/**
 * resource descriptor context
 * 
 * @author brad.wu
 */
public class ResourceDescriptorContext implements IResourceDescriptorContext {
	private String name = null;
	private Map<Class<?>, IResourceDescriptor> map = new HashMap<Class<?>, IResourceDescriptor>();
	private Map<String, IResourceDescriptor> identifiedMap = new HashMap<String, IResourceDescriptor>();
	private IOperatorProviderRegistry OperatorProviderRegistry = null;
	@SuppressWarnings("rawtypes")
	private List<IConfigurationInitializer> initializers = null;
	private Map<String, Object> initializerResult = new HashMap<String, Object>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getName()
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
		String oldName = this.name;
		ResourceDescriptorContextRepository.unregister(oldName);
		this.name = name;
		ResourceDescriptorContextRepository.register(this);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#afterContextInitialized()
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void afterContextInitialized() {
		for (IConfigurationInitializer initializer : this.getConfigurationInitializers()) {
			this.initializerResult.put(initializer.getReturnValueKey(), initializer.initialize(this));
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getInitializedData(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getInitializedData(String key) {
		return (T) this.initializerResult.get(key);
	}

	/**
	 * @return the initializers
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<IConfigurationInitializer> getConfigurationInitializers() {
		return (List<IConfigurationInitializer>) (initializers == null ? Collections.emptyList() : this.initializers);
	}

	/**
	 * set initializers
	 * 
	 * @param initializers
	 *            the initializers to set
	 */
	@SuppressWarnings("rawtypes")
	public void setConfigurationInitializers(List<IConfigurationInitializer> initializers) {
		this.initializers = initializers;
	}

	/**
	 * add initializer
	 * 
	 * @param initializer
	 */
	@SuppressWarnings("rawtypes")
	public void addConfigurationInitializer(IConfigurationInitializer initializer) {
		if (this.initializers == null) {
			synchronized (this) {
				if (this.initializers == null) {
					this.initializers = new LinkedList<IConfigurationInitializer>();
				}
			}
		}
		this.initializers.add(initializer);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IResourceDescriptor> T get(Object resource) {
		assert resource != null : "Resource instance cannot be null.";

		Class<?> clazz = resource.getClass();
		IResourceDescriptor descriptor = get(clazz);
		return (T) descriptor;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getRecursive(java.lang.Object)
	 */
	@Override
	public <T extends IResourceDescriptor> List<T> getRecursive(Object resource) {
		assert resource != null : "Resource instance cannot be null.";

		Class<?> clazz = resource.getClass();
		return getRecursive(clazz);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#get(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IResourceDescriptor> T get(Class<?> resourceClass) {
		assert resourceClass != null : "Resource class cannot be null.";

		IResourceDescriptor descriptor = map.get(resourceClass);
		if (descriptor != null) {
			return (T) descriptor;
		} else {
			return null;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getRecursive(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IResourceDescriptor> List<T> getRecursive(Class<?> resourceClass) {
		assert resourceClass != null : "Resource class cannot be null.";
		List<T> list = new LinkedList<T>();
		List<Class<?>> ancestors = ClassUtils.getAncestorClasses(resourceClass);
		for (Class<?> clazz : ancestors) {
			T descriptor = (T) this.map.get(clazz);
			if (descriptor != null) {
				list.add(descriptor);
			}
		}
		return list;
	}

	/**
	 * will set/clear context if the resource descriptor is instance of
	 * {@linkplain ResourceDescriptor}
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#register(com.github.nest.arcteryx.meta.IResourceDescriptor)
	 */
	@Override
	public IResourceDescriptor register(IResourceDescriptor descriptor) {
		assert descriptor != null : "Resource descriptor cannot be null.";

		synchronized (this.map) {
			IResourceDescriptor old = map.put(descriptor.getResourceClass(), descriptor);
			if (old != null) {
				old.setContext(null);
			}
			descriptor.setContext(this);
			return old;
		}
	}

	/**
	 * set mapping, return old mapping.<br>
	 * will set/clear context if the resource descriptor is instance of
	 * {@linkplain ResourceDescriptor}
	 * 
	 * @param descriptors
	 * @return old configuration
	 */
	public Collection<IResourceDescriptor> setDescriptors(Collection<IResourceDescriptor> descriptors) {
		assert descriptors != null : "Resource descriptor collection cannot be null.";

		synchronized (this.map) {
			List<IResourceDescriptor> oldDescriptors = new LinkedList<IResourceDescriptor>();
			oldDescriptors.addAll(this.map.values());

			this.map.clear();

			for (IResourceDescriptor oldDescriptor : oldDescriptors) {
				oldDescriptor.setContext(null);
			}
			for (IResourceDescriptor descriptor : descriptors) {
				descriptor.setContext(this);
				this.map.put(descriptor.getResourceClass(), descriptor);
			}

			return oldDescriptors;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getDescriptors()
	 */
	@Override
	public Collection<IResourceDescriptor> getDescriptors() {
		List<IResourceDescriptor> list = new ArrayList<IResourceDescriptor>(map.size());
		list.addAll(map.values());
		return list;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getDescriptors(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getDescriptors(Class<T> descriptorClass) {
		List<T> list = new ArrayList<T>(map.size());
		for (IResourceDescriptor descriptor : map.values()) {
			if (descriptorClass.isAssignableFrom(descriptor.getClass())) {
				list.add((T) descriptor);
			}
		}
		return list;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#get(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IResourceDescriptor> T get(String descriptorIdentity) {
		assert StringUtils.isNotBlank(descriptorIdentity) : "Identity of descriptor cannot be null or empty string.";

		return (T) this.identifiedMap.get(descriptorIdentity);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#register(com.github.nest.arcteryx.meta.IResourceDescriptor,
	 *      java.lang.String)
	 */
	@Override
	public IResourceDescriptor register(IResourceDescriptor descriptor, String identity) {
		assert StringUtils.isNotBlank(identity) : "Identity of descriptor cannot be null or empty string.";
		assert descriptor != null : "Resource descriptor cannot be null.";

		synchronized (this.identifiedMap) {
			IResourceDescriptor old = identifiedMap.put(identity, descriptor);
			if (old != null) {
				old.setContext(null);
			}
			descriptor.setContext(this);
			return old;
		}
	}

	/**
	 * set identified descriptors
	 * 
	 * @param descriptors
	 */
	public void setIdentifiedDescriptors(Map<String, IResourceDescriptor> descriptors) {
		assert descriptors != null : "Resource descriptor map cannot be null.";

		synchronized (this.identifiedMap) {
			this.identifiedMap.clear();
			for (Map.Entry<String, IResourceDescriptor> entry : descriptors.entrySet()) {
				this.register(entry.getValue(), entry.getKey());
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getIdentitiesOfDescriptor()
	 */
	@Override
	public Set<String> getIdentitiesOfDescriptor() {
		return this.identifiedMap.keySet();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#getOperatorProviderRegistry()
	 */
	@Override
	public IOperatorProviderRegistry getOperatorProviderRegistry() {
		if (this.OperatorProviderRegistry == null) {
			synchronized (this) {
				if (this.OperatorProviderRegistry == null) {
					this.OperatorProviderRegistry = createOperatorProviderRegistry();
				}
			}
		}
		return this.OperatorProviderRegistry;
	}

	/**
	 * create default resource operator provider
	 * 
	 * @return
	 */
	protected IOperatorProviderRegistry createOperatorProviderRegistry() {
		return new OperatorProviderRegistry();
	}

	/**
	 * set operator provider registry
	 * 
	 * @param operatorProviderRegistry
	 * 
	 */
	public void setOperatorProviderRegistry(IOperatorProviderRegistry operatorProviderRegistry) {
		this.OperatorProviderRegistry = operatorProviderRegistry;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IResourceDescriptorContext#registerOperatorProvider(com.github.nest.arcteryx.meta.IOperatorProvider,
	 *      java.lang.String)
	 */
	@Override
	public void registerOperatorProvider(IOperatorProvider provider, String code) {
		getOperatorProviderRegistry().register(code, provider);
	}

	/**
	 * set default operator providers
	 * 
	 * @param providers
	 */
	public void setOperatorProviders(Map<String, IOperatorProvider> providers) {
		assert providers != null : "Providers map cannot be null.";

		IOperatorProviderRegistry registry = this.getOperatorProviderRegistry();
		synchronized (registry) {
			for (Map.Entry<String, IOperatorProvider> entry : providers.entrySet()) {
				assert entry.getKey() != null : "Code of provider cannot be null.";
				assert entry.getValue() != null : "Provider cannot be null.";

				registry.register(entry.getKey(), entry.getValue());
			}
		}
	}
}
