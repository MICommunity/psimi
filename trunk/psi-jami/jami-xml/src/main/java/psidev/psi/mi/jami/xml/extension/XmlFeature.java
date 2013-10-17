package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Simple Xml implementation of a Feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "feature", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBType",
        "JAXBRanges",
        "JAXBAttributes"
})
public class XmlFeature extends AbstractXmlFeature<Entity,Feature>{

    public XmlFeature() {
    }

    public XmlFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public XmlFeature(CvTerm type) {
        super(type);
    }

    public XmlFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public XmlFeature(String shortName, String fullName, String interpro) {
        super(shortName, fullName, interpro);
    }

    public XmlFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public XmlFeature(String shortName, String fullName, CvTerm type, String interpro) {
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
     *     {@link psidev.psi.mi.jami.model.Xref }
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
    @XmlElementRefs({@XmlElementRef(type=XmlRange.class, name="featureRange", required = true)})
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
    @XmlElementRefs({@XmlElementRef(type=XmlAnnotation.class, name="attribute", required = true)})
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

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return super.getSaxLocator();
    }

    public void setSaxLocator(Locator sourceLocator) {
        super.setSaxLocator(sourceLocator);
    }
}
