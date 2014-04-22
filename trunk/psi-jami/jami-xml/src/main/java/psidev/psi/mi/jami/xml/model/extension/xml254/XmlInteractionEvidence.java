package psidev.psi.mi.jami.xml.model.extension.xml254;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlInteractionEvidence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Xml implementation of InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlInteractionEvidence extends AbstractXmlInteractionEvidence{

    public XmlInteractionEvidence() {
        super();
    }

    public XmlInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }
}
