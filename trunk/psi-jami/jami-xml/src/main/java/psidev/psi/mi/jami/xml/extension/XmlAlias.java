package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;

/**
 * Xml implementation of an Alias.
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "alias", propOrder = {
        "name"
})
public class XmlAlias implements Alias, FileSourceContext {

    private String name;
    private CvTerm type;
    private PsiXmLocator sourceLocator;

    public XmlAlias() {
    }

    public XmlAlias(String name, CvTerm type) {
        if (name == null){
            throw new IllegalArgumentException("The alias name cannot be null.");
        }
        this.name = name;
        this.type = type;
    }

    public XmlAlias(String name) {
        if (name == null){
            throw new IllegalArgumentException("The alias name cannot be null.");
        }
        this.name = name;
    }

    public CvTerm getType() {
        return null;
    }

    @XmlValue
    public String getName() {
        if (name == null){
            name = PsiXmlUtils.UNSPECIFIED;
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
    public String getJAXBTypeAc() {
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
    public void setJAXBTypeAc(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(PsiXmlUtils.UNSPECIFIED, value);
        }
        else if (this.type != null){
            if (PsiXmlUtils.UNSPECIFIED.equals(this.type.getShortName()) && value == null){
                this.type = null;
            }
            else {
                this.type.setMIIdentifier(value);
            }
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
    public String getJAXBTypeName() {
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
    public void setJAXBTypeName(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(value);
        }
        else if (this.type != null){
            if (this.type.getMIIdentifier() == null && value == null){
                this.type = null;
            }
            else {
                this.type.setShortName(value!= null ? value : PsiXmlUtils.UNSPECIFIED);
            }
        }
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Alias)){
            return false;
        }

        return UnambiguousAliasComparator.areEquals(this, (Alias) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousAliasComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return getName() + (type != null ? "("+type.toString()+")" : "");
    }
}

