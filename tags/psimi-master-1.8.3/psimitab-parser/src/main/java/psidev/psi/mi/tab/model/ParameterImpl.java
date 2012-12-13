package psidev.psi.mi.tab.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class ParameterImpl implements Parameter {
    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 8967236664673865804L;

    /////////////////////////
    // Instance variables

    /**
     * Type of the parameter.
     */
    private String type;

    /**
     * Factor of the value in scientific notation.
     */
    private Double factor = 0.0;

    /**
     * Base of the value in scientific notation. The default value is 10.
     */
    private Integer base = 10;

    /**
     * Exponent of the value in scientific notation. The default value is 0.
     */
    private Integer exponent = 0;

    /**
     * String representation of the parameter in scientific notation.
     */
    private String value;

    /**
     * Unit for the parameter.
     */
    private String unit;

    /**
     * Uncertainty for the parameter.
     */
    private Double uncertainty = 0.0;

    //////////////////////
    // Constructors


    //TODO Review the behaviour

    /**
     * Builds a parameter.
     *
     * @param type  the type of the parameter.
     * @param value double value.
     */
    public ParameterImpl(String type, String value) {
        this.type = type;

        setValue(value);

//        // value using scientific notation
//        try {
//            this.factor = Double.parseDouble(value);
//        } catch (NumberFormatException e) {
//            this.factor = Double.NaN;
//        }
    }

    /**
     * Builds a parameter.
     *
     * @param type  the type of the parameter.
     * @param value string representation of the parameter value. The syntax is:
     *              factor x base ^ exponent.
     * @param unit  the unit of the parameter.
     */
    public ParameterImpl(String type, String value, String unit) {
        this.type = type;
        this.unit = unit;

        setValue(value);

// value using scientific notation
//		try {
//			this.factor = Double.parseDouble(value);
//		} catch (NumberFormatException e) {
//			this.factor = Double.NaN;
//		}

    }

    /**
     * Builds a parameter.
     *
     * @param type     the type of the parameter.
     * @param factor   the factor of the parameter.
     * @param base     the base of the parameter.
     * @param exponent the exponent of the parameter.
     * @param unit     the unit of the parameter.
     */
    public ParameterImpl(String type, double factor, int base, int exponent, double uncertainty, String unit) {
        this.type = type;
        this.factor = factor;
        this.base = base;
        this.exponent = exponent;
        this.unit = unit;
        this.uncertainty = uncertainty;

        this.value = getValue();
    }

    /**
     * {@inheritDoc}
     */
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    public Double getFactor() {
        return factor;
    }

    /**
     * {@inheritDoc}
     */
    public void setFactor(Double factor) {
        this.factor = factor;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getBase() {
        return base;
    }

    /**
     * {@inheritDoc}
     */
    public void setBase(Integer base) {
        this.base = base;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getExponent() {
        return exponent;
    }

    /**
     * {@inheritDoc}
     */
    public void setExponent(Integer exponent) {
        this.exponent = exponent;
    }

    /**
     * {@inheritDoc}
     */
    public String getValue() {
        this.value = String.valueOf(factor);

		if (exponent != 0 || base != 10) {
			value = factor + "x" + base + "^" + exponent;
		}
		if (uncertainty != 0.0) {
            value = value + " ~" + uncertainty;
        }

        return value;
    }

    /**
     * {@inheritDoc}
     */
    public void setValue(String value) {
        this.value = value;

        value = value.replaceAll(" ", "");
        Pattern pattern = Pattern.compile("([-+]?[0-9]+\\.?[0-9]*)+x?([-+]?[0-9]*\\.?[0-9]*)?\\^?([-+]?[0-9]*\\.?[0-9]*)?~?([-+]?[0-9]*\\.?[0-9]*)?");

        Matcher matcher = pattern.matcher(value);
        try {
            if (matcher.matches()) {
//                System.out.println(matcher.groupCount());
                switch (matcher.groupCount()) {
                    case 4:
                        if (!matcher.group(4).isEmpty()) {
                            uncertainty = Double.parseDouble(matcher.group(4));
                        }
                    case 3:
                        if (!matcher.group(3).isEmpty()) {
                            exponent = Integer.parseInt(matcher.group(3));
                        }
                    case 2:
                        if (!matcher.group(2).isEmpty()) {
                            base = Integer.parseInt(matcher.group(2));
                        }
                    case 1:
                        if (!matcher.group(1).isEmpty()) {
                            factor = Double.parseDouble(matcher.group(1));
                        }
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("The value of the parameter is bad formatted: " + value + " Exception: " + e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public String getUnit() {
        return unit;
    }

    /**
     * {@inheritDoc}
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Getter fot property 'uncertainty'.
     *
     * @return a double with the uncertainty.
     */
    public Double getUncertainty() {
        return uncertainty;
    }

    /**
     * Setter for property 'uncertainty'.
     *
     * @param uncertainty a double with the uncertainty
     */
    public void setUncertainty(Double uncertainty) {
        this.uncertainty = uncertainty;
    }

    //////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Parameter");
        sb.append("{type='").append(type).append('\'');
        sb.append(", value='").append(value).append('\'');
        if (unit != null) {
            sb.append(", unit='").append(unit).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ParameterImpl that = (ParameterImpl) o;

        if (!type.equals(that.type)) {
            return false;
        }
        if (!value.equals(that.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = type.hashCode();
        result = 29 * result + value.hashCode();
        return result;
    }
}

