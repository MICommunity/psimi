package psidev.psi.mi.jami.xml.model.extension.xml254;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlModelledInteraction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Xml implementation of ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledInteraction extends AbstractXmlModelledInteraction {

    public XmlModelledInteraction() {
    }

    public XmlModelledInteraction(String shortName) {
        super(shortName);
    }

    public XmlModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }
}
