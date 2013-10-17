package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A container for aliases, shortname and fullname
 * The JAXB binding is designed to be read-only and is not designed for writing
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "names", propOrder = {
        "JAXBShortLabel",
        "JAXBFullName",
        "JAXBAliases"
})
@XmlSeeAlso({
        ProteinNamesContainer.class
})
public class NamesContainer implements FileSourceContext{

    private String shortLabel;
    private String fullName;
    private Collection<Alias> aliases;

    private PsiXmLocator sourceLocator;

    /**
     * Gets the value of the shortLabel property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "shortLabel")
    public String getJAXBShortLabel() {
        return shortLabel;
    }

    /**
     * Sets the value of the shortLabel property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJAXBShortLabel(String value) {
        this.shortLabel = value;
    }

    /**
     * Gets the value of the fullName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "fullName")
    public String getJAXBFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJAXBFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the alias property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alias property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlias().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlAlias }
     *
     *
     */
    @XmlElement(name = "alias")
    @XmlElementRefs({ @XmlElementRef(type=XmlAlias.class)})
    public Collection<Alias> getJAXBAliases() {
        if (aliases == null) {
            initialiseAliases();
        }
        return this.aliases;
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

    public boolean isEmpty(){
        return (shortLabel == null && fullName == null && getJAXBAliases().isEmpty());
    }

    protected void initialiseAliases(){
        this.aliases = new ArrayList<Alias>();
    }

    protected void initialiseAliasesWith(Collection<Alias> aliases){
        if (aliases == null){
            this.aliases = Collections.EMPTY_LIST;
        }
        else {
            this.aliases = aliases;
        }
    }
}