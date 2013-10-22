package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

/**
 * Xml implementation of Parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "parameterType")
@XmlSeeAlso({
        XmlModelledParameter.class
})
public class XmlParameter implements Parameter, FileSourceContext{

    private CvTerm type;
    private BigDecimal uncertainty;
    private CvTerm unit;
    private ParameterValue value;

    private PsiXmLocator sourceLocator;

    public XmlParameter() {
    }

    public XmlParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
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
     * Gets the value of the term property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "term", required = true)
    public String getJAXBTerm() {
        return getType().getShortName();
    }

    /**
     * Sets the value of the term property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJAXBTerm(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(value);
        }
        else if (this.type != null){
            this.type.setShortName(value != null ? value : PsiXmlUtils.UNSPECIFIED);
        }
    }

    /**
     * Gets the value of the termAc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "termAc")
    public String getJAXBTermAc() {
        return getType().getMIIdentifier();
    }

    /**
     * Sets the value of the termAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJAXBTermAc(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.type != null){
            this.type.setMIIdentifier(value);
        }
    }

    /**
     * Gets the value of the unit property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "unit")
    public String getJAXBUnit() {
        return unit != null ? unit.getShortName() : null;
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
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
     * Gets the value of the unitAc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "unitAc")
    public String getUnitAc() {
        return unit != null ? unit.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the unitAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
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
     * Gets the value of the base property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     *
     */
    @XmlAttribute(name = "base")
    public short getJAXBBase() {
        return getValue().getBase();
    }

    /**
     * Sets the value of the base property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    public void setJAXBBase(Short value) {
        this.value = new ParameterValue(getValue().getFactor(), value, getValue().getExponent());
    }

    /**
     * Gets the value of the exponent property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     *
     */
    @XmlAttribute(name = "exponent")
    public short getJAXBExponent() {
        return getValue().getExponent();
    }

    /**
     * Sets the value of the exponent property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    public void setJAXBExponent(Short value) {
        this.value = new ParameterValue(getValue().getFactor(), getValue().getBase(), value);
    }

    /**
     * Gets the value of the factor property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    @XmlAttribute(name = "factor", required = true)
    public BigDecimal getJAXBFactor() {
        return getValue().getFactor();
    }

    /**
     * Sets the value of the factor property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setJAXBFactor(BigDecimal value) {
        this.value = new ParameterValue(value != null ? value : new BigDecimal(0), getValue().getBase(), getValue().getExponent());
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }    }

    @XmlAttribute(name = "uncertainty")
    public BigDecimal getJAXBUncertainty() {
        return this.uncertainty;
    }

    /**
     * Sets the value of the uncertainty property.
     *
     * @param value
     *     allowed object is
     *     {@link Double }
     *
     */
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
        return type.toString() + ": " + value  + (uncertainty != null ? " ~" + uncertainty.toString() : "" + (unit != null ? "("+unit.toString()+")" : ""));
    }

    @Override
    public int hashCode() {
        return UnambiguousParameterComparator.hashCode(this);
    }
}
