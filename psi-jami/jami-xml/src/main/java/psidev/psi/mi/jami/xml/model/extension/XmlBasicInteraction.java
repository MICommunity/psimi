package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Xml implementation of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlType(name = "defaultBasicInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlBasicInteraction extends AbstractXmlBasicInteraction implements Serializable {

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
