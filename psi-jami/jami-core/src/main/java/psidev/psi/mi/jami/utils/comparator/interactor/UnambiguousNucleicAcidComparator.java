package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;

/**
 * Unambiguous nucleic acids comparator.
 * It will first use UnambiguousPolymerComparator to compare the basic interactor properties.
 * If the basic polymer properties are the same, It will look for DDBJ/EMBL/Genbank identifier. If the DDBJ/EMBL/Genbank identifiers are identical, it will look at the
 * Refseq identifiers.
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
        super(new UnambiguousPolymerComparator());
    }

    public UnambiguousPolymerComparator getInteractorComparator() {
        return (UnambiguousPolymerComparator)super.getInteractorComparator();
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
