package psidev.psi.mi.jami.xml.model.extension.xml253;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlComplex;

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
@XmlRootElement(name = "interaction", namespace = "net:sf:psidev:mi")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlComplex extends AbstractXmlComplex {

    public XmlComplex() {
    }

    public XmlComplex(String name, CvTerm type) {
        super(name, type);
    }

    public XmlComplex(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlComplex(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public XmlComplex(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlComplex(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlComplex(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlComplex(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlComplex(String name, Organism organism) {
        super(name, organism);
    }

    public XmlComplex(String name, String fullName) {
        super(name, fullName);
    }

    public XmlComplex(String name) {
        super(name);
    }

    public XmlComplex(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlComplex(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlComplex(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlComplex(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlComplex(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlComplex(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }
}
