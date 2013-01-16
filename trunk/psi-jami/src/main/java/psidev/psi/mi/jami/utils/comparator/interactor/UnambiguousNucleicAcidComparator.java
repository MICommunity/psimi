package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Unambiguous nucleic acids comparator.
 * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
 * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use UnambiguousInteractorBaseComparator to compare the interactor properties
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousNucleicAcidComparator extends NucleicAcidComparator {

    private static UnambiguousNucleicAcidComparator unambiguousNucleicAcidComparator;

    /**
     * Creates a new UnambiguousNucleicAcidComparator. It will uses a UnambiguousInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousNucleicAcidComparator() {
        super(new UnambiguousInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
     * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use UnambiguousInteractorBaseComparator to compare the interactor properties
     */
    public int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2) {
        return super.compare(nucleicAcid1, nucleicAcid2);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousNucleicAcidComparator to know if two nucleic acids are equals.
     * @param nucleicAcid1
     * @param nucleicAcid2
     * @return true if the two nucleic acids are equal
     */
    public static boolean areEquals(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2){
        if (unambiguousNucleicAcidComparator == null){
            unambiguousNucleicAcidComparator = new UnambiguousNucleicAcidComparator();
        }

        return unambiguousNucleicAcidComparator.compare(nucleicAcid1, nucleicAcid2) == 0;
    }
}
