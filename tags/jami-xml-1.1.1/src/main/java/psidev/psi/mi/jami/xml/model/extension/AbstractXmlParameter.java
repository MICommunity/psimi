package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;

/**
 * Abstract Xml implementation of Parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractXmlParameter implements Parameter, FileSourceContext, Locatable{

    private CvTerm type;
    private BigDecimal uncertainty;
    private CvTerm unit;
    private ParameterValue value;
    private PsiXmlLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

    public AbstractXmlParameter() {
    }

    public AbstractXmlParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
        this.type = type;
        this.value = value;
        this.uncertainty = uncertainty;
        this.unit = unit;
    }

    public CvTerm getType() {
        if (this.type == null){
            this.type = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED);
        }
        return this.type;
    }

    public BigDecimal getUncertainty() {
        return this.uncertainty;
    }

    public CvTerm getUnit() {
        return this.unit;
    }

    public ParameterValue getValue() {
        if (this.value == null){
            this.value = new ParameterValue(new BigDecimal(0));
        }
        return this.value;
    }

    /**
     * Sets the value of the term property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "term", required = true)
    public void setJAXBTerm(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(value);
        }
        else if (this.type != null){
            this.type.setShortName(value != null ? value : PsiXmlUtils.UNSPECIFIED);
        }
        if (value == null){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onMissingParameterType(this);
            }
        }
    }

    /**
     * Sets the value of the termAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "termAc")
    public void setJAXBTermAc(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.type != null){
            this.type.setMIIdentifier(value);
        }
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "unit")
    public void setJAXBUnit(String value) {
        if (this.unit == null && value != null){
            this.unit = new DefaultCvTerm(value);
        }
        else if (this.unit != null){
            if (this.unit.getMIIdentifier() == null && value == null){
                this.unit = null;
            }
            else {
                this.unit.setShortName(value!= null ? value : PsiXmlUtils.UNSPECIFIED);
            }
        }
    }

    /**
     * Sets the value of the unitAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "unitAc")
    public void setUnitAc(String value) {
        if (this.unit == null && value != null){
            this.unit = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.unit != null){
            if (PsiXmlUtils.UNSPECIFIED.equals(this.unit.getShortName()) && value == null){
                this.unit = null;
            }
            else {
                this.unit.setMIIdentifier(value);
            }
        }
    }

    /**
     * Sets the value of the base property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    @XmlAttribute(name = "base")
    public void setJAXBBase(Short value) {
        this.value = new ParameterValue(getValue().getFactor(), value, getValue().getExponent());
    }

    /**
     * Sets the value of the exponent property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    @XmlAttribute(name = "exponent")
    public void setJAXBExponent(Short value) {
        this.value = new ParameterValue(getValue().getFactor(), getValue().getBase(), value);
    }

    /**
     * Sets the value of the factor property.
     *
     * @param value
     *     allowed object is
     *     {@link java.math.BigDecimal }
     *
     */
    @XmlAttribute(name = "factor", required = true)
    public void setJAXBFactor(BigDecimal value) {
        if (value == null){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onMissingParameterValue(this);
            }
        }
        this.value = new ParameterValue(value != null ? value : new BigDecimal(0), getValue().getBase(), getValue().getExponent());
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    /**
     * Sets the value of the uncertainty property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
    @XmlAttribute(name = "uncertainty")
    public void setJAXBUncertainty(BigDecimal value) {
        this.uncertainty = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Parameter)){
            return false;
        }

        return UnambiguousParameterComparator.areEquals(this, (Parameter) o);
    }

    @Override
    public String toString() {
        return "Xml Parameter: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    @Override
    public int hashCode() {
        return UnambiguousParameterComparator.hashCode(this);
    }
}
