package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Exact nucleic acids comparator.
 * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
 * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use ExactInteractorComparator to compare the interactor properties
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class ExactNucleicAcidComparator extends NucleicAcidComparator{

    public ExactNucleicAcidComparator(ExactInteractorComparator interactorComparator) {
        super(interactorComparator, new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
     * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use ExactInteractorComparator to compare the interactor properties
     */
    public int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2) {
        return super.compare(nucleicAcid1, nucleicAcid2);
    }

    @Override
    public ExactInteractorComparator getInteractorComparator() {
        return (ExactInteractorComparator) this.interactorComparator;
    }
}
