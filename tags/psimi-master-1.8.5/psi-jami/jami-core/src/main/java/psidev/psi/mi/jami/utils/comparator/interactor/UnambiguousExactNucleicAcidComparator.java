package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;

/**
 * Unambiguous exact nucleic acids comparator.
 * It will first use UnambiguousExactPolymerBaseComparator to compare the basic interactor properties.
 * If the basic polymer properties are the same, It will look for DDBJ/EMBL/Genbank identifier. If the DDBJ/EMBL/Genbank identifiers are identical, it will look at the
 * Refseq identifiers.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactNucleicAcidComparator extends UnambiguousNucleicAcidComparator {

    private static UnambiguousExactNucleicAcidComparator unambiguousExactNucleicAcidComparator;

    /**
     * Creates a new DefaultExactNucleicAcidComparator. It will uses a UnambiguousExactInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousExactNucleicAcidComparator() {
        super(new UnambiguousExactPolymerComparator());
    }

    @Override
    /**
     * It will first use UnambiguousExactPolymerBaseComparator to compare the basic interactor properties.
     * If the basic polymer properties are the same, It will look for DDBJ/EMBL/Genbank identifier. If the DDBJ/EMBL/Genbank identifiers are identical, it will look at the
     * Refseq identifiers.
     */
    public int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2) {
        return super.compare(nucleicAcid1, nucleicAcid2);
    }

    @Override
    public UnambiguousExactPolymerComparator getInteractorComparator() {
        return (UnambiguousExactPolymerComparator) this.interactorComparator;
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
