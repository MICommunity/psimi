package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Unambiguous exact nucleic acids comparator.
 * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
 * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use UnambiguousExactInteractorBaseComparator to compare the interactor properties
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactNucleicAcidComparator extends NucleicAcidComparator {

    private static UnambiguousExactNucleicAcidComparator unambiguousExactNucleicAcidComparator;

    /**
     * Creates a new DefaultExactNucleicAcidComparator. It will uses a UnambiguousExactInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousExactNucleicAcidComparator() {
        super(new UnambiguousExactInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
     * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use UnambiguousExactInteractorBaseComparator to compare the interactor properties
     *
     */
    public int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2) {
        return super.compare(nucleicAcid1, nucleicAcid2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousExactNucleicAcidComparator to know if two nucleic acids are equals.
     * @param nucleicAcid1
     * @param nucleicAcid2
     * @return true if the two nucleic acids are equal
     */
    public static boolean areEquals(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2){
        if (unambiguousExactNucleicAcidComparator == null){
            unambiguousExactNucleicAcidComparator = new UnambiguousExactNucleicAcidComparator();
        }

        return unambiguousExactNucleicAcidComparator.compare(nucleicAcid1, nucleicAcid2) == 0;
    }
}
