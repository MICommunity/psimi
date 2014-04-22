package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xml implementation of an Annotation
 *
 * The JAXB binding is designed to be read-only and is not designed for writing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlType(name = "defaultAttribute")
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
