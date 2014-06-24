package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * The Xml implementation of Interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "defaultInteractor")
public class XmlInteractor extends AbstractXmlInteractor  {

    public XmlInteractor() {
    }

    public XmlInteractor(String name, CvTerm type) {
        super(name, type);
    }

    public XmlInteractor(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlInteractor(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlInteractor(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlInteractor(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlInteractor(String name) {
        super(name);
    }

    public XmlInteractor(String name, String fullName) {
        super(name, fullName);
    }

    public XmlInteractor(String name, Organism organism) {
        super(name, organism);
    }

    public XmlInteractor(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlInteractor(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlInteractor(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlInteractor(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlInteractor(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }
}
