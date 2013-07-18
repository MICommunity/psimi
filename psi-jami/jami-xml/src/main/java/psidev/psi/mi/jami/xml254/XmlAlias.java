package psidev.psi.mi.jami.xml254;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import javax.xml.bind.annotation.*;

/**
 * Xml 2.5.4 implementation of an Alias
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "alias", propOrder = {
        "name"
})
public class XmlAlias implements Alias{

    private String name;
    private CvTerm type;
    public static final String UNSPECIFIED = "unspecified";

    @XmlTransient
    public CvTerm getType() {
        return null;
    }

    public void setType(CvTerm type) {
        this.type = type;
    }

    @XmlValue
    public String getName() {
        if (name == null){
            name = UNSPECIFIED;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of the typeAc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "typeAc")
    public String getTypeAc() {
        return this.type != null ? this.type.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the typeAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTypeAc(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(UNSPECIFIED, value);
        }
        else if (this.type != null){
            this.type.setMIIdentifier(value);
        }
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "type")
    public String getTypeName() {
        return this.type != null ? this.type.getShortName() : null;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTypeName(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(value);
        }
        else if (this.type != null){
            this.type.setShortName(value != null ? value : UNSPECIFIED);
        }
    }
}

