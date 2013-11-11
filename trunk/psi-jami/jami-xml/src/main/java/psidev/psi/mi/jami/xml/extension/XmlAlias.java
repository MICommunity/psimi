package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

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
public class XmlAlias implements Alias, FileSourceContext, Locatable {

    private String name;
    private CvTerm type;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;

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
        return this.type;
    }

    public String getName() {
        if (name == null){
            name = PsiXml25Utils.UNSPECIFIED;
        }
        return name;
    }

    @XmlValue
    public void setJAXBName(String name) {
        this.name = name;
        if (this.name == null){
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onAliasWithoutName(this);
            }
        }
    }

    /**
     * Sets the value of the typeAc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "typeAc")
    public void setJAXBTypeAc(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(PsiXml25Utils.UNSPECIFIED, value);
        }
        else if (this.type != null){
            if (PsiXml25Utils.UNSPECIFIED.equals(this.type.getShortName()) && value == null){
                this.type = null;
            }
            else {
                this.type.setMIIdentifier(value);
            }
        }
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlAttribute(name = "type")
    public void setJAXBTypeName(String value) {
        if (this.type == null && value != null){
            this.type = new DefaultCvTerm(value);
        }
        else if (this.type != null){
            if (this.type.getMIIdentifier() == null && value == null){
                this.type = null;
            }
            else {
                this.type.setShortName(value!= null ? value : PsiXml25Utils.UNSPECIFIED);
            }
        }
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
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
        return "Xml Alias: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }
}

