package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlAnnotation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Xml implementation of an Annotation
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlRootElement(name = "attribute", namespace = "http://psi.hupo.org/mi/mif300")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlAnnotation extends AbstractXmlAnnotation {

    public XmlAnnotation() {
    }

    public XmlAnnotation(CvTerm topic, String value) {
        super(topic, value);
    }

    public XmlAnnotation(CvTerm topic) {
        super(topic);
    }
}
