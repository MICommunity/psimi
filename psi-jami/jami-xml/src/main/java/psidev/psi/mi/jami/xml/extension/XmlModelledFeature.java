package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.model.ModelledFeature;

import javax.xml.bind.annotation.*;

/**
 * The xml implementation of a modelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledFeature extends AbstractXmlFeature<ModelledEntity, ModelledFeature> implements ModelledFeature {

    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlModelledFeature() {
    }

    public XmlModelledFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public XmlModelledFeature(CvTerm type) {
        super(type);
    }

    public XmlModelledFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public XmlModelledFeature(String shortName, String fullName, String interpro) {
        super(shortName, fullName, interpro);
    }

    public XmlModelledFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public XmlModelledFeature(String shortName, String fullName, CvTerm type, String interpro) {
        super(shortName, fullName, type, interpro);
    }

    @Override
    @XmlElement(name = "names")
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    @Override
    @XmlElement(name = "xref")
    public void setJAXBXref(FeatureXrefContainer value) {
        super.setJAXBXref(value);
    }

    @Override
    @XmlElement(name = "featureType", type = XmlCvTerm.class)
    public void setJAXBType(CvTerm type) {
        super.setJAXBType(type);
    }

    @Override
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
    }

    @Override
    @XmlElement(name="featureRangeList", required = true)
    public void setJAXBRangeWrapper(JAXBRangeWrapper jaxbRangeWrapper) {
        super.setJAXBRangeWrapper(jaxbRangeWrapper);
    }

    /**
     * Gets the value of the id property.
     *
     */
    @Override
    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int id) {
        super.setJAXBId(id);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getJAXBId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getJAXBId()));
        }
    }
}
