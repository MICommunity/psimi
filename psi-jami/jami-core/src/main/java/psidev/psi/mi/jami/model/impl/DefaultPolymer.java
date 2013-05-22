package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactPolymerComparator;

/**
 * Default implementation for Polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultPolymer extends DefaultMolecule implements Polymer{

    private String sequence;

    public DefaultPolymer(String name, CvTerm type) {
        super(name, type);
    }

    public DefaultPolymer(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultPolymer(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultPolymer(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public DefaultPolymer(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultPolymer(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultPolymer(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultPolymer(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Polymer)){
            return false;
        }

        // use UnambiguousExactPolymer comparator for equals
        return UnambiguousExactPolymerComparator.areEquals(this, (Polymer) o);
    }
}
