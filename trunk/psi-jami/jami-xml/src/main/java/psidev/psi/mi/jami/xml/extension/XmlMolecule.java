package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Molecule;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xml implementation of molecule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "")
public class XmlMolecule extends XmlInteractor implements Molecule{

    public XmlMolecule() {
    }

    public XmlMolecule(String name, CvTerm type) {
        super(name, type);
    }

    public XmlMolecule(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlMolecule(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlMolecule(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlMolecule(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlMolecule(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlMolecule(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlMolecule(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlMolecule(String name) {
        super(name);
    }

    public XmlMolecule(String name, String fullName) {
        super(name, fullName);
    }

    public XmlMolecule(String name, Organism organism) {
        super(name, organism);
    }

    public XmlMolecule(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlMolecule(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlMolecule(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlMolecule(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlMolecule(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }
}
