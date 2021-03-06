/**
 * 
 */
package com.github.nest.arcteryx.meta.beans.internal.validators.oval;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.oval.Check;
import net.sf.oval.Validator;
import net.sf.oval.configuration.pojo.elements.ClassConfiguration;
import net.sf.oval.configuration.pojo.elements.FieldConfiguration;
import net.sf.oval.configuration.pojo.elements.MethodConfiguration;
import net.sf.oval.configuration.pojo.elements.MethodReturnValueConfiguration;
import net.sf.oval.configuration.pojo.elements.ObjectConfiguration;
import net.sf.oval.localization.locale.LocaleProvider;
import net.sf.oval.localization.message.MessageResolver;
import net.sf.oval.localization.value.MessageValueFormatter;
import net.sf.oval.logging.LoggerFactory;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

import com.github.nest.arcteryx.meta.IResourceDescriptorContext;
import com.github.nest.arcteryx.meta.beans.IBeanConstraint;
import com.github.nest.arcteryx.meta.beans.IBeanConstraintReorganizer;
import com.github.nest.arcteryx.meta.beans.IBeanDescriptor;
import com.github.nest.arcteryx.meta.beans.IBeanPropertyConstraint;
import com.github.nest.arcteryx.meta.beans.IBeanPropertyConstraintReorganizer;
import com.github.nest.arcteryx.meta.beans.IBeanPropertyDescriptor;
import com.github.nest.arcteryx.meta.beans.IConstraint;
import com.github.nest.arcteryx.meta.beans.IValidationConfigurationInitializer;
import com.github.nest.arcteryx.meta.beans.internal.IValidationConfiguration;
import com.github.nest.arcteryx.meta.beans.internal.validators.BeanConstraintReorganizer;
import com.github.nest.arcteryx.meta.beans.internal.validators.BeanPropertyConstraintReorganizer;
import com.github.nest.arcteryx.meta.beans.internal.validators.BeanValidationException;
import com.github.nest.arcteryx.meta.beans.internal.validators.ValidationConfiguration;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.AssertValidConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.BeanScriptConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.DateRangeConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.EmailConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.LengthConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.NotBlankConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.NotEmptyConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.NotNegativeConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.NotNullConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.NumberFormatConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.PropertyScriptConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.SizeConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.TextFormatConvertor;
import com.github.nest.arcteryx.meta.beans.internal.validators.oval.convertors.TheNumberConvertor;
import com.github.nest.arcteryx.meta.util.ReflectionUtils;
import com.github.nest.arcteryx.validation.oval.localization.message.OvalResourceBundleMessageResolver;

/**
 * OVal configurer initializer
 * 
 * @author brad.wu
 */
public class OValValidationConfigurationInitializer implements IValidationConfigurationInitializer {
	private static final long serialVersionUID = -2221049148470519548L;
	public static final String MESSAGES = "com/github/nest/arcteryx/meta/beans/internal/validators/oval/constraints/Messages";
	/**
	 * when initialize the configuration, use getter method first or not
	 */
	private boolean getterFirst = true;
	/**
	 * OVal validation configurer
	 */
	private IOValConfigurer configurer = null;

	private Map<Class<?>, IOValCheckConvertor<?>> convertors = new HashMap<Class<?>, IOValCheckConvertor<?>>();
	private IBeanConstraintReorganizer beanConstraintReorganizer = null;
	private IBeanPropertyConstraintReorganizer propertyConstraintReorganizer = null;

	public OValValidationConfigurationInitializer() {
		setDefaultMessageResolver();
		// default connect to slf4j
		Validator.setLoggerFactory(new LoggerFactorySLF4JImpl());

		this.configurer = createConfigurer();
		this.setConvertors(createDefaultConvertors());
	}

	/**
	 * set default message resolver
	 */
	private void setDefaultMessageResolver() {
		OvalResourceBundleMessageResolver messageResolver = new OvalResourceBundleMessageResolver();
		messageResolver.addMessageBundle(MESSAGES);
		Validator.setMessageResolver(messageResolver);
	}

	/**
	 * create default convertors
	 * 
	 * @return
	 */
	protected Collection<IOValCheckConvertor<?>> createDefaultConvertors() {
		List<IOValCheckConvertor<?>> convertors = new LinkedList<IOValCheckConvertor<?>>();

		convertors.add(new AssertValidConvertor());
		convertors.add(new DateRangeConvertor());
		convertors.add(new EmailConvertor());
		convertors.add(new LengthConvertor());
		convertors.add(new NotBlankConvertor());
		convertors.add(new NotEmptyConvertor());
		convertors.add(new NotNegativeConvertor());
		convertors.add(new NotNullConvertor());
		convertors.add(new TheNumberConvertor());
		convertors.add(new NumberFormatConvertor());
		convertors.add(new SizeConvertor());
		convertors.add(new PropertyScriptConvertor());
		convertors.add(new TextFormatConvertor());

		convertors.add(new BeanScriptConvertor());

		return convertors;
	}

	/**
	 * create OVal configurer
	 * 
	 * @return
	 */
	protected IOValConfigurer createConfigurer() {
		return new OValConfigurer();
	}

	/**
	 * 
	 * @return the getterFirst
	 */
	public boolean isGetterFirst() {
		return getterFirst;
	}

	/**
	 * @param getterFirst
	 *            the getterFirst to set
	 */
	public void setGetterFirst(boolean getterFirst) {
		this.getterFirst = getterFirst;
	}

	/**
	 * @return the convertors
	 */
	public Collection<IOValCheckConvertor<?>> getConvertors() {
		return convertors.values();
	}

	/**
	 * @param convertors
	 *            the convertors to set
	 */
	@SuppressWarnings("rawtypes")
	public void setConvertors(Collection<IOValCheckConvertor<?>> convertors) {
		assert convertors != null : "Convertor of constraints cannot be null.";

		for (IOValCheckConvertor convertor : convertors) {
			this.convertors.put(convertor.getSupportedConstraintType(), convertor);
		}
	}

	/**
	 * @return the configurer
	 */
	public IOValConfigurer getConfigurer() {
		return configurer;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IConfigurationInitializer#getReturnValueKey()
	 */
	@Override
	public String getReturnValueKey() {
		return KEY;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nest.arcteryx.meta.IConfigurationInitializer#initialize(com.github.nest.arcteryx.meta.IResourceDescriptorContext)
	 */
	@Override
	public IValidationConfiguration initialize(IResourceDescriptorContext context) {
		for (IBeanDescriptor descriptor : context.getDescriptors(IBeanDescriptor.class)) {
			initialize(descriptor);
		}
		return new ValidationConfiguration(getConfigurer());
	}

	/**
	 * initialize validation configuration of given descriptor
	 * 
	 * @param descriptor
	 */
	protected void initialize(IBeanDescriptor descriptor) {
		ClassConfiguration classConfiguration = new ClassConfiguration();
		classConfiguration.type = descriptor.getBeanClass();

		generateTypeLevelConstraints(descriptor, classConfiguration);
		generatePropertyLevelConstraints(descriptor, classConfiguration);

		if (hasConstraint(classConfiguration)) {
			this.getConfigurer().addClassConfiguration(classConfiguration);
		}
	}

	/**
	 * generate property level constraints
	 * 
	 * @param descriptor
	 * @param classConfiguration
	 */
	protected void generatePropertyLevelConstraints(IBeanDescriptor descriptor, ClassConfiguration classConfiguration) {
		// initialize field and method configurations. method configurations
		// only will be used when getterFirst is true.
		classConfiguration.fieldConfigurations = new HashSet<FieldConfiguration>();
		if (this.isGetterFirst()) {
			classConfiguration.methodConfigurations = new HashSet<MethodConfiguration>();
		}

		// get all bean properties
		for (IBeanPropertyDescriptor property : descriptor.getBeanProperties()) {
			buildPropertyConstraint(property, classConfiguration);
		}
	}

	/**
	 * generate type level constraints
	 * 
	 * @param descriptor
	 * @param classConfiguration
	 */
	@SuppressWarnings("rawtypes")
	protected void generateTypeLevelConstraints(IBeanDescriptor descriptor, ClassConfiguration classConfiguration) {
		// find constraints of parents
		IBeanConstraintReorganizer reorganizer = descriptor.getConstraintReorganizer();
		if (reorganizer == null) {
			reorganizer = getBeanConstraintReorganizer();
		}
		List<IBeanConstraint> list = reorganizer.getEffectiveConstraints(descriptor);

		// add into object configuration
		if (list.size() > 0) {
			classConfiguration.objectConfiguration = new ObjectConfiguration();
			classConfiguration.objectConfiguration.checks = convertToChecks(list);
		}
	}

	/**
	 * get default bean constraint reorganizer
	 * 
	 * @return
	 */
	protected IBeanConstraintReorganizer getBeanConstraintReorganizer() {
		if (this.beanConstraintReorganizer == null) {
			synchronized (this) {
				if (this.beanConstraintReorganizer == null) {
					this.beanConstraintReorganizer = createDefaultBeanConstraintReorganizer();
				}
			}
		}
		return this.beanConstraintReorganizer;
	}

	/**
	 * create default bean constraint reorganizer
	 * 
	 * @return
	 */
	protected IBeanConstraintReorganizer createDefaultBeanConstraintReorganizer() {
		return new BeanConstraintReorganizer() {
			private static final long serialVersionUID = -8928033238330327986L;

			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nest.arcteryx.meta.beans.internal.validators.AbstractConstraintReorganizer#getEffectiveConstraints(com.github.nest.arcteryx.meta.beans.IConstraintContainer)
			 */
			@SuppressWarnings("rawtypes")
			@Override
			public List<IBeanConstraint> getEffectiveConstraints(IBeanDescriptor descriptor) {
				return this.getAllConstraints(descriptor);
			}
		};
	}

	/**
	 * check the configuration has constriant or not
	 * 
	 * @param config
	 * @return
	 */
	protected boolean hasConstraint(ClassConfiguration config) {
		return (config.fieldConfigurations != null && config.fieldConfigurations.size() != 0)
				|| (config.methodConfigurations != null && config.methodConfigurations.size() != 0)
				|| (config.constructorConfigurations != null && config.constructorConfigurations.size() != 0)
				|| (config.objectConfiguration != null);
	}

	/**
	 * build constraint for property
	 * 
	 * @param property
	 * @param classConfiguration
	 */
	@SuppressWarnings("rawtypes")
	protected void buildPropertyConstraint(IBeanPropertyDescriptor property, ClassConfiguration classConfiguration) {
		IBeanDescriptor bean = property.getBeanDescriptor();
		Class<?> beanClass = bean.getBeanClass();

		// get property name
		String propertyName = property.getName();

		// get constraint recursive. exclude the constraint collection object
		IBeanPropertyConstraintReorganizer reorganizer = property.getConstraintReorganizer();
		if (reorganizer == null) {
			reorganizer = getPropertyConstraintReorganizer();
		}
		List<IBeanPropertyConstraint> constraints = reorganizer.getEffectiveConstraints(property);
		List<Check> checkList = convertToChecks(constraints);

		if (this.isGetterFirst()) {
			MethodConfiguration mc = null;
			Method method = ReflectionUtils.getGetterRecursive(beanClass, propertyName);
			if (method != null) {
				mc = new MethodConfiguration();
				mc.name = method.getName();
				mc.isInvariant = Boolean.TRUE;
				MethodReturnValueConfiguration mrvc = new MethodReturnValueConfiguration();
				mrvc.checks = checkList;
				mc.returnValueConfiguration = mrvc;
				classConfiguration.methodConfigurations.add(mc);
			}
			if (mc == null) {
				FieldConfiguration fc = new FieldConfiguration();
				fc.name = propertyName;
				fc.checks = checkList;
				classConfiguration.fieldConfigurations.add(fc);
			}
		} else {
			FieldConfiguration fc = null;
			Field field = ReflectionUtils.getFieldRecursive(beanClass, propertyName);
			if (field != null) {
				fc = new FieldConfiguration();
				fc.name = propertyName;
				fc.checks = checkList;
				classConfiguration.fieldConfigurations.add(fc);
			}
			if (fc == null) {
				Method method = ReflectionUtils.getGetterRecursive(beanClass, propertyName);
				MethodConfiguration mc = new MethodConfiguration();
				mc.name = method.getName();
				mc.isInvariant = Boolean.TRUE;
				MethodReturnValueConfiguration mrvc = new MethodReturnValueConfiguration();
				mrvc.checks = checkList;
				mc.returnValueConfiguration = mrvc;
				classConfiguration.methodConfigurations.add(mc);
			}
		}
	}

	/**
	 * get property constraint reorganizer
	 * 
	 * @return
	 */
	protected IBeanPropertyConstraintReorganizer getPropertyConstraintReorganizer() {
		if (this.propertyConstraintReorganizer == null) {
			synchronized (this) {
				if (this.propertyConstraintReorganizer == null) {
					this.propertyConstraintReorganizer = createDefaultPropertyConstraintReorganizer();
				}
			}
		}
		return this.propertyConstraintReorganizer;
	}

	/**
	 * create default property constraint reorganizer
	 * 
	 * @return
	 */
	protected IBeanPropertyConstraintReorganizer createDefaultPropertyConstraintReorganizer() {
		return new BeanPropertyConstraintReorganizer() {
			private static final long serialVersionUID = 6288191233653612290L;

			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nest.arcteryx.meta.beans.internal.validators.AbstractConstraintReorganizer#getEffectiveConstraints(com.github.nest.arcteryx.meta.beans.IConstraintContainer)
			 */
			@SuppressWarnings("rawtypes")
			@Override
			public List<IBeanPropertyConstraint> getEffectiveConstraints(IBeanPropertyDescriptor descriptor) {
				return this.getAllConstraints(descriptor);
			}
		};
	}

	/**
	 * convert to OVal checks
	 * 
	 * @param constraints
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<Check> convertToChecks(List<?> constraints) {
		List<Check> checkList = new LinkedList<Check>();
		for (int index = 0, count = constraints.size(); index < count; index++) {
			IConstraint constraint = (IConstraint) constraints.get(index);
			// convert constraint to OVal check
			IOValCheckConvertor convertor = convertors.get(constraint.getClass());
			if (convertor == null) {
				throw new BeanValidationException("Convertor of [" + constraint + "] not found.");
			}

			Check check = convertor.convert(constraint);
			checkList.add(check);
		}
		return checkList;
	}

	/**
	 * set additional message bundles. name is class name or property file
	 * name.will replace the customized message resolver which set by
	 * {@linkplain #setMessageResolver(MessageResolver)}.
	 * 
	 * @param bundles
	 */
	public void setMessageBundles(String... bundles) {
		assert bundles != null && bundles.length > 0 : "At least one bundle is set";

		this.setDefaultMessageResolver();
		OvalResourceBundleMessageResolver resolver = (OvalResourceBundleMessageResolver) Validator.getMessageResolver();
		for (String bundle : bundles) {
			if (bundle.indexOf('.') != -1) {
				resolver.addMessageBundle(bundle.replace('.', '/'));
			} else {
				resolver.addMessageBundle(bundle);
			}
		}
	}

	/**
	 * set message resolver
	 * 
	 * @param resolver
	 */
	public void setMessageResolver(MessageResolver resolver) {
		Validator.setMessageResolver(resolver);
	}

	/**
	 * set message value formatter, not publish to public now.
	 * 
	 * @param formatter
	 */
	protected void setMessageValueFormatter(MessageValueFormatter formatter) {
		Validator.setMessageValueFormatter(formatter);
	}

	/**
	 * set logger factory
	 * 
	 * @param loggerFactory
	 */
	public void setLoggerFactory(LoggerFactory loggerFactory) {
		Validator.setLoggerFactory(loggerFactory);
	}

	/**
	 * set locale provider, not publish to public now.
	 * 
	 * @param localeProvider
	 */
	protected void setLocaleProvider(LocaleProvider localeProvider) {
		Validator.setLocaleProvider(localeProvider);
	}
}
