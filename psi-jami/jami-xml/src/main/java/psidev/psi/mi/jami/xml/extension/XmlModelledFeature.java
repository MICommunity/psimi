package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * The xml implementation of a modelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "modelledFeature", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBType",
        "JAXBRanges",
        "JAXBAttributes"
})
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

    /**
     * Gets the value of the names property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    @Override
    @XmlElement(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link Xref }
     *
     */
    @Override
    @XmlElement(name = "xref")
    public FeatureXrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlElement(name = "featureType", type = XmlCvTerm.class)
    public CvTerm getJAXBType() {
        return super.getType();
    }

    /**
     * Gets the value of the featureRangeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlRange }
     *
     */
    @XmlElementWrapper(name="featureRangeList", required = true)
    @XmlElements({@XmlElement(type=XmlRange.class, name="featureRange", required = true)})
    @Override
    public ArrayList<Range> getJAXBRanges() {
        return super.getJAXBRanges();
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    @XmlElementWrapper(name="attributeList")
    @XmlElements({@XmlElement(type=XmlAnnotation.class, name="attribute", required = true)})
    @Override
    public ArrayList<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    /**
     * Gets the value of the id property.
     *
     */
    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
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
