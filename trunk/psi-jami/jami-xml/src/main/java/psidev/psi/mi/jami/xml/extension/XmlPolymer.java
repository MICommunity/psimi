package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xml implementation of Polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class XmlPolymer extends XmlInteractor implements Polymer{

    public XmlPolymer() {
    }

    public XmlPolymer(String name, CvTerm type) {
        super(name, type);
    }

    public XmlPolymer(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlPolymer(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlPolymer(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlPolymer(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlPolymer(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlPolymer(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlPolymer(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlPolymer(String name) {
        super(name);
    }

    public XmlPolymer(String name, String fullName) {
        super(name, fullName);
    }

    public XmlPolymer(String name, Organism organism) {
        super(name, organism);
    }

    public XmlPolymer(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlPolymer(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlPolymer(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlPolymer(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlPolymer(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(Polymer.POLYMER, Polymer.POLYMER_MI));
    }
}
