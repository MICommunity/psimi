package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Xml implementation of ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlType(name = "defaultModelledInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledInteraction extends AbstractXmlModelledInteraction implements Serializable {

    public XmlModelledInteraction() {
    }

    public XmlModelledInteraction(String shortName) {
        super(shortName);
    }

    public XmlModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }
}
