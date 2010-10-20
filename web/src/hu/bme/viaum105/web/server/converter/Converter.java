package hu.bme.viaum105.web.server.converter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import hu.bme.viaum105.web.server.converter.conversion.Conversion;
import hu.bme.viaum105.web.server.converter.conversion.Skip;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Converter {

    private static final Log log = LogFactory.getLog(Converter.class);

    /**
     * Osztálypárok: mi mivé konvertálható
     */
    private static final ArrayList<Conversion> classPairs;

    static {
	classPairs = new ArrayList<Conversion>();
	// TODO entitás osztályok megadása
	// Converter.classPairs.add(new Conversion(Direction.class,
	// DirectionDto.class));
	// Converter.classPairs.add(new Conversion(Portal.class,
	// PortalDto.class, new Skip("name"), new Skip("online")));

	if (Converter.log.isTraceEnabled()) {
	    StringBuilder sb = new StringBuilder("Converter config:");
	    for (Conversion cp : Converter.classPairs) {
		sb.append("\n").append(cp);
	    }
	    Converter.log.trace(sb);
	}
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object source) throws ConverterException {
	if (Converter.log.isTraceEnabled()) {
	    Converter.log.trace("User called convert: " + source);
	}
	return (T) new Converter(source).convert();
    }

    private static Conversion getConversion(Class<?> clazz) {
	Conversion ret = null;
	for (Iterator<Conversion> iterator = Converter.classPairs.iterator(); (ret == null) && iterator.hasNext();) {
	    Conversion conversion = iterator.next();
	    if (conversion.convert(clazz) != null) {
		ret = conversion;
	    }
	}
	return ret;
    }

    private static Field getField(Class<?> clazz, String fieldName) throws SecurityException, NoSuchFieldException {
	Field ret = null;
	try {
	    ret = clazz.getDeclaredField(fieldName);
	} catch (NoSuchFieldException e) {
	    // ősosztályt nézzük
	    if (clazz.getSuperclass() == null) {
		throw e;
	    }
	    ret = Converter.getField(clazz.getSuperclass(), fieldName);
	}
	return ret;
    }

    private static Class<?> getTarget(Class<?> clazz) {
	Class<?> target = null;
	for (Iterator<Conversion> iterator = Converter.classPairs.iterator(); (target == null) && iterator.hasNext();) {
	    Conversion classPair = iterator.next();
	    target = classPair.convert(clazz);
	}
	return target;
    }

    private static String getterToField(String methodName) {
	String ret;
	if (methodName.startsWith("is")) {
	    ret = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
	} else {
	    ret = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
	}
	return ret;
    }

    private final Class<?> targetClass;

    private final Object source;

    private final ConverterMemory memory;

    private final Conversion conversion;

    private Converter(Object source) {
	this(source, new ConverterMemory());
    }

    private Converter(Object source, ConverterMemory memory) {
	this.source = source;
	this.conversion = Converter.getConversion(source.getClass());
	this.targetClass = this.conversion.convert(source.getClass());
	this.memory = memory;
    }

    @SuppressWarnings("unchecked")
    private <T> T convert() throws ConverterException {
	if (Converter.log.isTraceEnabled()) {
	    Converter.log.trace("Converting " + this.source + " to " + this.targetClass);
	}
	try {
	    T ret;
	    if (this.source instanceof Enum<?>) {
		ret = (T) this.convertEnum((Enum<?>) this.source);
	    } else {
		ret = (T) this.convertClass();
	    }
	    if (Converter.log.isTraceEnabled()) {
		Converter.log.trace("converted: " + ret);
	    }
	    return ret;
	} catch (InstantiationException e) {
	    throw new ConverterException("Could not instantiate class", e);
	} catch (IllegalAccessException e) {
	    throw new ConverterException("Could not instantiate class", e);
	} catch (IllegalArgumentException e) {
	    throw new ConverterException("Could not invoke getter", e);
	} catch (InvocationTargetException e) {
	    throw new ConverterException("Could not invoke method", e);
	} catch (SecurityException e) {
	    throw new ConverterException("Could not set value", e);
	} catch (NoSuchFieldException e) {
	    throw new ConverterException("Could not set value", e);
	}
    }

    @SuppressWarnings("unchecked")
    private <T> T convertClass() throws InstantiationException, IllegalAccessException, InvocationTargetException, ConverterException, NoSuchFieldException {
	T ret = (T) this.targetClass.newInstance();
	this.memory.put(this.source, ret);
	for (Method sourceMethod : this.source.getClass().getMethods()) {
	    if (this.isGetter(sourceMethod)) {
		Converter.log.trace("getter found: " + sourceMethod.getName());
		if (!(this.conversion.getRule(Converter.getterToField(sourceMethod.getName())) instanceof Skip)) {
		    AccessibleObject target = this.getPropertyTarget(sourceMethod);
		    Object getterValue = sourceMethod.invoke(this.source);
		    if ((getterValue != null)) {
			Class<?> targetClass;
			if (target instanceof Field) {
			    targetClass = ((Field) target).getType();
			} else {
			    targetClass = ((Method) target).getParameterTypes()[0];
			}
			Object converted = this.convertProperty(getterValue, targetClass);
			this.setProperty(ret, target, converted);
		    } else {
			Converter.log.trace("Value is null, skipping");
		    }
		} else {
		    Converter.log.trace("Skip rule is applied, skipping");
		}
	    }
	}
	return ret;
    }

    private Collection<?> convertCollection(Collection<?> collection, Class<?> targetClass) throws InstantiationException, IllegalAccessException,
	    ConverterException {
	if (Converter.log.isTraceEnabled()) {
	    Converter.log.trace("converting collection: " + collection);
	}
	boolean canIterate = false;
	try {
	    /*
	     * Először megpróbáljuk, működik-e az iteráció. Ha nem működik,
	     * inicializálatlan adatbázisreferenciákkal van dolgunk, amiket
	     * null-ként konvertálunk át
	     */
	    Iterator<?> i = collection.iterator();
	    if (i.hasNext()) {
		i.next();
	    }
	    canIterate = true;
	} catch (Exception e) {
	    Converter.log.trace("Lazy collection, replacing it with null", e);
	}

	Collection<Object> ret;
	if (canIterate) {
	    ret = this.createCollection(targetClass);
	    for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
		Object element = iterator.next();
		if (Converter.getTarget(element.getClass()) != null) {
		    Object converted = new Converter(element, this.memory).convert();
		    this.memory.put(element, converted);
		    ret.add(converted);
		} else {
		    ret.add(element);
		}
	    }
	} else {
	    ret = null;
	}

	return ret;
    }

    @SuppressWarnings("unchecked")
    private <T> T convertEnum(Enum<?> source) {
	/*
	 * Ordinal alapján történik a konverzió
	 */
	Object[] targetEnums = this.targetClass.getEnumConstants();
	return (T) targetEnums[source.ordinal()];
    }

    private Object convertProperty(Object getterValue, Class<?> targetClass) throws InstantiationException, IllegalAccessException, ConverterException {
	Object converted = this.memory.getConvertedValue(getterValue);
	if (converted == null) {
	    if (getterValue instanceof Collection<?>) {
		converted = this.convertCollection((Collection<?>) getterValue, targetClass);
		this.memory.put(getterValue, converted);
	    } else if (Converter.getTarget(getterValue.getClass()) != null) {
		converted = new Converter(getterValue, this.memory).convert();
		this.memory.put(getterValue, converted);
	    } else {
		converted = getterValue;
	    }
	}
	return converted;
    }

    @SuppressWarnings("unchecked")
    private Collection<Object> createCollection(Class<?> collectionClass) throws InstantiationException, IllegalAccessException {
	Collection<Object> ret;
	if (collectionClass.isInterface()) {
	    if (collectionClass.equals(List.class) || collectionClass.equals(Queue.class)) {
		ret = new LinkedList<Object>();
	    } else if (collectionClass.equals(Set.class)) {
		ret = new HashSet<Object>();
	    } else {
		// Collection
		ret = new ArrayList<Object>();
	    }
	} else {
	    ret = (Collection<Object>) collectionClass.newInstance();
	}
	return ret;
    }

    private AccessibleObject getPropertyTarget(Method getterMethod) throws SecurityException, NoSuchFieldException {
	AccessibleObject ret = null;
	try {
	    Method setter = this.getSetterOnTarget(getterMethod);
	    ret = setter;
	} catch (NoSuchMethodException e) {
	    // ha nincs setter, legyen egy field (ID miatt)
	    String fieldName = Converter.getterToField(getterMethod.getName());
	    Field field = Converter.getField(this.targetClass, fieldName);
	    ret = field;
	}
	return ret;
    }

    private Method getSetterOnTarget(Method getter) throws SecurityException, NoSuchMethodException {
	String fieldName = Converter.getterToField(getter.getName());
	String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	Class<?> returnType = Converter.getTarget(getter.getReturnType());
	if (returnType == null) {
	    returnType = getter.getReturnType();
	}
	return this.targetClass.getMethod(setterName, returnType);
    }

    private boolean isGetter(Method method) {
	String methodName = method.getName();

	boolean ret = true;
	Method[] objectMethods = Object.class.getMethods();
	for (int i = 0; ret && (i < objectMethods.length); i++) {
	    ret = !methodName.equals(objectMethods[i].getName());
	}

	if (ret) {
	    ret = (methodName.startsWith("get") && //
		    Character.isUpperCase(methodName.charAt(3)) && //
		    (method.getParameterTypes().length == 0))
		    || (methodName.startsWith("is") && //
			    Character.isUpperCase(methodName.charAt(2)) && //
		    (method.getParameterTypes().length == 0));
	}

	return ret;
    }

    private void setProperty(Object ret, AccessibleObject target, Object convertedValue) throws IllegalAccessException, InvocationTargetException,
	    SecurityException {
	if (target instanceof Method) {
	    Method setter = (Method) target;
	    if (Converter.log.isTraceEnabled()) {
		Converter.log.trace("using setter to set value: " + setter.getName());
	    }
	    setter.invoke(ret, convertedValue);
	} else {
	    // Field
	    Field field = (Field) target;
	    if (Converter.log.isTraceEnabled()) {
		Converter.log.trace("trying to set field value: " + field.getName());
	    }
	    int modifiers = field.getModifiers();
	    if (!Modifier.isPublic(modifiers)) {
		field.setAccessible(true);
	    }
	    field.set(ret, convertedValue);
	    if (!Modifier.isPublic(modifiers)) {
		field.setAccessible(false);
	    }
	}
    }

}
