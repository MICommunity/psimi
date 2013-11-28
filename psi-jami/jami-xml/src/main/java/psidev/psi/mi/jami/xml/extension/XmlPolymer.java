package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of Polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlPolymer extends XmlMolecule implements Polymer{

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
        super(name, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlPolymer(String name, String fullName) {
        super(name, fullName, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlPolymer(String name, Organism organism) {
        super(name, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlPolymer(String name, String fullName, Organism organism) {
        super(name, fullName, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlPolymer(String name, Xref uniqueId) {
        super(name, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlPolymer(String name, String fullName, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlPolymer(String name, Organism organism, Xref uniqueId) {
        super(name, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    public XmlPolymer(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())), organism, uniqueId);
    }

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(Polymer.POLYMER, new XmlXref(CvTermUtils.createPsiMiDatabase(),Polymer.POLYMER_MI, CvTermUtils.createIdentityQualifier())));
    }

    public String getSequence() {
        return super.getSequence();
    }

    public void setSequence(String sequence) {
        super.setSequence(sequence);
    }

    @Override
    public String toString() {
        return "Polymer: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }
}
