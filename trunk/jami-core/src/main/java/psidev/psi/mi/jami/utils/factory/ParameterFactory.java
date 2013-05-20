package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParameter;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Factory for Parameters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ParameterFactory {

    private static Pattern PARAMETER_PATTERN = Pattern.compile("([-+]?[0-9]+\\.?[0-9]*)+x?([-+]?[0-9]*\\.?[0-9]*)?\\^?([-+]?[0-9]*\\.?[0-9]*)?~?([-+]?[0-9]*\\.?[0-9]*)?");

    public static Parameter createParameterFromString(String type, String value) throws IllegalParameterException {

        if (value == null){
            throw new IllegalParameterException("The parameter value cannot be null");
        }
        String newValue = value.replaceAll(" ", "");

        Matcher matcher = PARAMETER_PATTERN.matcher(newValue);
        try {
            BigDecimal uncertainty = null;
            short exponent = 0;
            short base = 10;
            BigDecimal factor = null;
            if (matcher.matches()) {
                switch (matcher.groupCount()) {
                    case 4:
                        if (!matcher.group(4).isEmpty()) {
                             uncertainty = new BigDecimal(matcher.group(4));
                        }
                    case 3:
                        if (!matcher.group(3).isEmpty()) {
                            exponent = Short.parseShort(matcher.group(3));
                        }
                    case 2:
                        if (!matcher.group(2).isEmpty()) {
                            base = Short.parseShort(matcher.group(2));
                        }
                    case 1:
                        if (!matcher.group(1).isEmpty()) {
                            factor = new BigDecimal(matcher.group(1));
                        }
                    default:
                        break;
                }
            }

            ParameterValue parameterValue = new ParameterValue(factor, base, exponent);

            return new DefaultParameter(new DefaultCvTerm(type), parameterValue, uncertainty);
        } catch (Exception e) {
            throw new IllegalParameterException("The value of the parameter is bad formatted: " + value + " Exception: " + e);
        }
    }

    public static Parameter createParameterFromString(CvTerm type, String value) throws IllegalParameterException {

        if (value == null){
            throw new IllegalParameterException("The parameter value cannot be null");
        }
        String newValue = value.replaceAll(" ", "");

        Matcher matcher = PARAMETER_PATTERN.matcher(newValue);
        try {
            BigDecimal uncertainty = null;
            short exponent = 0;
            short base = 10;
            BigDecimal factor = null;
            if (matcher.matches()) {
                switch (matcher.groupCount()) {
                    case 4:
                        if (!matcher.group(4).isEmpty()) {
                            uncertainty = new BigDecimal(matcher.group(4));
                        }
                    case 3:
                        if (!matcher.group(3).isEmpty()) {
                            exponent = Short.parseShort(matcher.group(3));
                        }
                    case 2:
                        if (!matcher.group(2).isEmpty()) {
                            base = Short.parseShort(matcher.group(2));
                        }
                    case 1:
                        if (!matcher.group(1).isEmpty()) {
                            factor = new BigDecimal(matcher.group(1));
                        }
                    default:
                        break;
                }
            }

            ParameterValue parameterValue = new ParameterValue(factor, base, exponent);

            return new DefaultParameter(type, parameterValue, uncertainty);
        } catch (Exception e) {
            throw new IllegalParameterException("The value of the parameter is bad formatted: " + value + " Exception: " + e);
        }
    }

    public static Parameter createParameterFromString(String type, String value, String unit) throws IllegalParameterException {

        if (value == null){
            throw new IllegalParameterException("The parameter value cannot be null");
        }
        String newValue = value.replaceAll(" ", "");

        Matcher matcher = PARAMETER_PATTERN.matcher(newValue);
        try {
            BigDecimal uncertainty = null;
            short exponent = 0;
            short base = 10;
            BigDecimal factor = null;
            if (matcher.matches()) {
                switch (matcher.groupCount()) {
                    case 4:
                        if (!matcher.group(4).isEmpty()) {
                            uncertainty = new BigDecimal(matcher.group(4));
                        }
                    case 3:
                        if (!matcher.group(3).isEmpty()) {
                            exponent = Short.parseShort(matcher.group(3));
                        }
                    case 2:
                        if (!matcher.group(2).isEmpty()) {
                            base = Short.parseShort(matcher.group(2));
                        }
                    case 1:
                        if (!matcher.group(1).isEmpty()) {
                            factor = new BigDecimal(matcher.group(1));
                        }
                    default:
                        break;
                }
            }

            ParameterValue parameterValue = new ParameterValue(factor, base, exponent);

            if (unit != null){
                return new DefaultParameter(new DefaultCvTerm(type), parameterValue, new DefaultCvTerm(unit), uncertainty);
            }
            else {
                return new DefaultParameter(new DefaultCvTerm(type), parameterValue, uncertainty);
            }
        } catch (Exception e) {
            throw new IllegalParameterException("The value of the parameter is bad formatted: " + value + " Exception: " + e);
        }
    }

    public static Parameter createParameterFromString(CvTerm type, String value, CvTerm unit) throws IllegalParameterException {

        if (value == null){
            throw new IllegalParameterException("The parameter value cannot be null");
        }
        String newValue = value.replaceAll(" ", "");

        Matcher matcher = PARAMETER_PATTERN.matcher(newValue);
        try {
            BigDecimal uncertainty = null;
            short exponent = 0;
            short base = 10;
            BigDecimal factor = null;
            if (matcher.matches()) {
                switch (matcher.groupCount()) {
                    case 4:
                        if (!matcher.group(4).isEmpty()) {
                            uncertainty = new BigDecimal(matcher.group(4));
                        }
                    case 3:
                        if (!matcher.group(3).isEmpty()) {
                            exponent = Short.parseShort(matcher.group(3));
                        }
                    case 2:
                        if (!matcher.group(2).isEmpty()) {
                            base = Short.parseShort(matcher.group(2));
                        }
                    case 1:
                        if (!matcher.group(1).isEmpty()) {
                            factor = new BigDecimal(matcher.group(1));
                        }
                    default:
                        break;
                }
            }

            ParameterValue parameterValue = new ParameterValue(factor, base, exponent);

            return new DefaultParameter(type, parameterValue, unit, uncertainty);

        } catch (Exception e) {
            throw new IllegalParameterException("The value of the parameter is bad formatted: " + value + " Exception: " + e);
        }
    }
}
