package hu.bme.viaum105.web.server.converter.conversion;

import java.util.TreeMap;

public class Conversion {

    private final Class<?> c1;

    private final Class<?> c2;

    private final TreeMap<String, ConversionRule> conversionRules;

    public Conversion(Class<?> c1, Class<?> c2, ConversionRule... conversionRules) {
	this.c1 = c1;
	this.c2 = c2;
	this.conversionRules = new TreeMap<String, ConversionRule>();
	for (ConversionRule conversionRule : conversionRules) {
	    this.conversionRules.put(conversionRule.property, conversionRule);
	}
    }

    public Class<?> convert(Class<?> clazz) {
	Class<?> ret = null;
	if (this.c1.equals(clazz)) {
	    ret = this.c2;
	} else if (this.c2.equals(clazz)) {
	    ret = this.c1;
	}
	return ret;
    }

    @Override
    public boolean equals(Object obj) {
	boolean ret;
	if (obj == null) {
	    ret = false;
	} else if (obj == this) {
	    ret = true;
	} else if (obj instanceof Conversion) {
	    Conversion other = (Conversion) obj;
	    ret = (this.c1.equals(other.c1) && this.c2.equals(other.c2)) || // 
		    (this.c1.equals(other.c2) && this.c2.equals(other.c1));
	} else {
	    ret = false;
	}
	return ret;
    }

    public ConversionRule getRule(String property) {
	return this.conversionRules.get(property);
    }

    @Override
    public int hashCode() {
	return this.c1.hashCode() + this.c2.hashCode();
    }

    @Override
    public String toString() {
	return this.c1.getSimpleName() + " <-> " + this.c2.getSimpleName();
    }
}
