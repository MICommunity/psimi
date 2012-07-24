package psidev.psi.mi.tab.model;

import java.util.Scanner;


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
    private Double factor;

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
        this.base = 10;
        this.exponent = 0;

        this.value = value;

        // value using scientific notation
        try {
            this.factor = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            this.factor = Double.NaN;
        }
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
        this.base = 10;
        this.exponent = 0;

        this.value = value;

// value using scientific notation
//		try {
//			this.factor = Double.parseDouble(value);
//		} catch (NumberFormatException e) {
//			this.factor = Double.NaN;
//		}

        Scanner scanner = new Scanner(value);
        if (scanner.hasNextDouble()) {
            factor = scanner.nextDouble();
            if (scanner.hasNextInt()) {
                base = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    exponent = scanner.nextInt();

                }
            }
        }
    }

    /**
     * Builds a parameter.
     *
     * @param type  the type of the parameter.
     * @param factor the factor of the parameter.
     * @param base the base of the parameter.
     * @param exponent the exponent of the parameter.
     * @param unit  the unit of the parameter.
     */
    public ParameterImpl(String type, double factor, int base, int exponent, String unit) {
        this.type = type;
        this.factor = factor;
        this.base = base;
        this.exponent = exponent;
        this.unit = unit;

        this.value = String.valueOf(factor);
        if (exponent != 0) {
            value = value + " x " + base + "^" + exponent;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getType() {

        this.value = String.valueOf(factor);

        if (exponent != 0 && base != 10) {
            value = factor + " x " + base + "^" + exponent;
        }
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
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public void setValue(String value) {
        this.value = value;

        Scanner scanner = new Scanner(value);
        if (scanner.hasNextDouble()) {
            factor = scanner.nextDouble();
            if (scanner.hasNextInt()) {
                base = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    exponent = scanner.nextInt();

                }
            }
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

