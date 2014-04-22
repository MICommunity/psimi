package psidev.psi.mi.jami.xml.model.extension.xml254;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlBasicInteraction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Xml implementation of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlBasicInteraction extends AbstractXmlBasicInteraction{

    public XmlBasicInteraction() {
        super();
    }

    public XmlBasicInteraction(String shortName) {
        super(shortName);
    }

    public XmlBasicInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }
}
