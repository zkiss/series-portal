package hu.bme.viaum105.web.server.converter.conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hu.bme.viaum105.web.server.converter.ConverterException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class MethodAccessor extends Accessor {

    private static final Log log = LogFactory.getLog(MethodAccessor.class);

    private final Method setter;

    public MethodAccessor(Method getter, Method setter) {
	super(getter);
	this.setter = setter;
    }

    @Override
    public void setValue(Object target, Object value) throws ConverterException {
	if (MethodAccessor.log.isTraceEnabled()) {
	    MethodAccessor.log.trace("using setter to set value: " + this.setter.getName());
	}
	try {
	    this.setter.invoke(target, value);
	} catch (IllegalArgumentException e) {
	    throw new ConverterException("Could not invoke setter: " + this.setter.getName(), e);
	} catch (IllegalAccessException e) {
	    throw new ConverterException("Could not invoke setter: " + this.setter.getName(), e);
	} catch (InvocationTargetException e) {
	    throw new ConverterException("Could not invoke setter: " + this.setter.getName(), e);
	}
    }

}
