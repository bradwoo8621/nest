/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.internal.validators.hibernate;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;
import static org.hibernate.validator.internal.util.ConcurrentReferenceHashMap.Option.IDENTITY_COMPARISONS;
import static org.hibernate.validator.internal.util.ConcurrentReferenceHashMap.ReferenceType.SOFT;
import static org.hibernate.validator.internal.util.logging.Messages.MESSAGES;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.validation.ParameterNameProvider;

import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.UnconstrainedEntityMetaDataSingleton;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.provider.AnnotationMetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.MetaDataProvider;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;

import com.github.nest.arcteryx.meta.beans.internal.validators.hibernate.BeanMetaDataImpl513.BeanMetaDataBuilder;

/**
 * bean meta data manager. have to extends {@linkplain BeanMetaDataManager}, and
 * copy code from it, change the behavior of create BeanMetaData object.
 * 
 * @author brad.wu
 */
public class BeanMetaDataManager513 extends BeanMetaDataManager {
	/**
	 * The default initial capacity for this cache.
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * The default load factor for this cache.
	 */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * The default concurrency level for this cache.
	 */
	private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

	/**
	 * Additional metadata providers used for meta data retrieval if the XML
	 * and/or programmatic configuration is used.
	 */
	private final List<MetaDataProvider> metaDataProviders;

	/**
	 * Helper for builtin constraints and their validator implementations
	 */
	private final ConstraintHelper constraintHelper;

	/**
	 * Used to cache the constraint meta data for validated entities
	 */
	private final ConcurrentReferenceHashMap<Class<?>, BeanMetaData<?>> beanMetaDataCache;

	/**
	 * Used for resolving type parameters. Thread-safe.
	 */
	private final ExecutableHelper executableHelper;

	/**
	 * Creates a new {@code BeanMetaDataManager}.
	 * {@link DefaultParameterNameProvider} is used as parameter name provider,
	 * no meta data providers besides the annotation-based providers are used.
	 *
	 * @param constraintHelper
	 *            the constraint helper
	 * @param executableHelper
	 *            the executable helper
	 */
	public BeanMetaDataManager513(ConstraintHelper constraintHelper, ExecutableHelper executableHelper) {
		this(constraintHelper, executableHelper, new DefaultParameterNameProvider(), Collections
				.<MetaDataProvider> emptyList());
	}

	/**
	 * Creates a new {@code BeanMetaDataManager}.
	 *
	 * @param constraintHelper
	 *            the constraint helper
	 * @param executableHelper
	 *            the executable helper
	 * @param parameterNameProvider
	 *            the parameter name provider
	 * @param optionalMetaDataProviders
	 *            optional meta data provider used on top of the annotation
	 *            based provider
	 */
	public BeanMetaDataManager513(ConstraintHelper constraintHelper, ExecutableHelper executableHelper,
			ParameterNameProvider parameterNameProvider, List<MetaDataProvider> optionalMetaDataProviders) {
		super(constraintHelper, executableHelper, parameterNameProvider, optionalMetaDataProviders);
		this.constraintHelper = constraintHelper;
		this.metaDataProviders = newArrayList();
		this.metaDataProviders.addAll(optionalMetaDataProviders);
		this.executableHelper = executableHelper;

		this.beanMetaDataCache = new ConcurrentReferenceHashMap<Class<?>, BeanMetaData<?>>(DEFAULT_INITIAL_CAPACITY,
				DEFAULT_LOAD_FACTOR, DEFAULT_CONCURRENCY_LEVEL, SOFT, SOFT, EnumSet.of(IDENTITY_COMPARISONS));

		AnnotationProcessingOptions annotationProcessingOptions = getAnnotationProcessingOptionsFromNonDefaultProviders();
		AnnotationMetaDataProvider defaultProvider = new AnnotationMetaDataProvider(constraintHelper,
				parameterNameProvider, annotationProcessingOptions);
		this.metaDataProviders.add(defaultProvider);
	}

	public boolean isConstrained(Class<?> beanClass) {
		return getOrCreateBeanMetaData(beanClass, true).hasConstraints();
	}

	public <T> BeanMetaData<T> getBeanMetaData(Class<T> beanClass) {
		return getOrCreateBeanMetaData(beanClass, false);
	}

	public void clear() {
		beanMetaDataCache.clear();
	}

	public int numberOfCachedBeanMetaDataInstances() {
		return beanMetaDataCache.size();
	}

	/**
	 * Creates a
	 * {@link org.hibernate.validator.internal.metadata.aggregated.BeanMetaData}
	 * containing the meta data from all meta data providers for the given type
	 * and its hierarchy.
	 *
	 * @param <T>
	 *            The type of interest.
	 * @param clazz
	 *            The type's class.
	 *
	 * @return A bean meta data object for the given type.
	 */
	private <T> BeanMetaDataImpl513<T> createBeanMetaData(Class<T> clazz) {
		BeanMetaDataBuilder<T> builder = BeanMetaDataBuilder.getInstance(constraintHelper, executableHelper, clazz);

		for (MetaDataProvider provider : metaDataProviders) {
			for (BeanConfiguration<? super T> beanConfiguration : provider.getBeanConfigurationForHierarchy(clazz)) {
				builder.add(beanConfiguration);
			}
		}

		return builder.build();
	}

	/**
	 * @return returns the annotation ignores from the non annotation based meta
	 *         data providers
	 */
	private AnnotationProcessingOptions getAnnotationProcessingOptionsFromNonDefaultProviders() {
		AnnotationProcessingOptions options = new AnnotationProcessingOptionsImpl();
		for (MetaDataProvider metaDataProvider : metaDataProviders) {
			options.merge(metaDataProvider.getAnnotationProcessingOptions());
		}

		return options;
	}

	@SuppressWarnings("unchecked")
	private <T> BeanMetaData<T> getOrCreateBeanMetaData(Class<T> beanClass, boolean allowUnconstrainedTypeSingleton) {
		Contracts.assertNotNull(beanClass, MESSAGES.beanTypeCannotBeNull());

		BeanMetaData<T> beanMetaData = (BeanMetaData<T>) beanMetaDataCache.get(beanClass);

		// create a new BeanMetaData in case none is cached
		if (beanMetaData == null) {
			beanMetaData = createBeanMetaData(beanClass);
			if (!beanMetaData.hasConstraints() && allowUnconstrainedTypeSingleton) {
				beanMetaData = (BeanMetaData<T>) UnconstrainedEntityMetaDataSingleton.getSingleton();
			}

			final BeanMetaData<T> cachedBeanMetaData = (BeanMetaData<T>) beanMetaDataCache.putIfAbsent(beanClass,
					beanMetaData);
			if (cachedBeanMetaData != null) {
				beanMetaData = cachedBeanMetaData;
			}
		}

		if (beanMetaData instanceof UnconstrainedEntityMetaDataSingleton && !allowUnconstrainedTypeSingleton) {
			beanMetaData = createBeanMetaData(beanClass);
			beanMetaDataCache.put(beanClass, beanMetaData);
		}

		return beanMetaData;
	}
}
