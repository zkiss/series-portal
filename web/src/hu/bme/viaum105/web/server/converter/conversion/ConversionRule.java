package hu.bme.viaum105.web.server.converter.conversion;

public abstract class ConversionRule {

    public final String property;

    ConversionRule(String property) {
	this.property = property;
    }

}
