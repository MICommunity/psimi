package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml implementation of Parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "parameterType", propOrder = {
        "experimentRefList"})
public class XmlParameter implements Parameter, ModelledParameter, Serializable{

    private Collection<Publication> publications;
    private Collection<Integer> experimentRefList;
    private CvTerm type;
    private BigDecimal uncertainty;
    private CvTerm unit;
    private ParameterValue value;

    public XmlParameter() {
    }

    public XmlParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
        this.type = type;
        this.value = value;
        this.uncertainty = uncertainty;
        this.unit = unit;
    }

    @XmlTransient
    public CvTerm getType() {
        if (this.type == null){
            this.type = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED);
        }
        return this.type;
    }

    @XmlAttribute(name = "uncertainty")
    public BigDecimal getUncertainty() {
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
    public void setUncertainty(BigDecimal value) {
        this.uncertainty = value;
    }

    @XmlTransient
    public CvTerm getUnit() {
        return this.unit;
    }

    @XmlTransient
    public ParameterValue getValue() {
        if (this.value == null){
            this.value = new ParameterValue(new BigDecimal(0));
        }
        return this.value;
    }

    @XmlTransient
    public Collection<Publication> getPublications() {
        if (publications == null){
            publications = new ArrayList<Publication>();
        }
        return publications;
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
    public String getTerm() {
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
    public void setTerm(String value) {
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
    public String getTermAc() {
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
    public void setTermAc(String value) {
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
    public short getBase() {
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
    public void setBase(Short value) {
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
    public short getExponent() {
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
    public void setExponent(Short value) {
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
    public BigDecimal getFactor() {
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
    public void setFactor(BigDecimal value) {
        this.value = new ParameterValue(value != null ? value : new BigDecimal(0), getValue().getBase(), getValue().getExponent());
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElementWrapper(name="experimentRefList")
    @XmlElement(name="experimentRef")
    public Collection<Integer> getExperimentRefList() {
        return experimentRefList;
    }

    /**
     * Sets the value of the experimentRefList property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setExperimentRefList(Collection<Integer> value) {
        this.experimentRefList = value;
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
