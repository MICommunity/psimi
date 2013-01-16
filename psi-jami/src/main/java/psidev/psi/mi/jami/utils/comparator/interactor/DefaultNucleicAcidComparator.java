package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default nucleic acids comparator.
 * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
 * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use DefaultInteractorBaseComparator to compare the interactor properties
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultNucleicAcidComparator extends NucleicAcidComparator {

    private static DefaultNucleicAcidComparator defaultNucleicAcidComparator;

    /**
     * Creates a new DefaultNucleicAcidComparator. It will uses a DefaultInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultNucleicAcidComparator() {
        super(new DefaultInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
     * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use DefaultInteractorBaseComparator to compare the interactor properties
     *
     */
    public int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2) {
        return super.compare(nucleicAcid1, nucleicAcid2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorComparator() {
        return (DefaultInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultNucleicAcidComparator to know if two nucleic acids are equals.
     * @param nucleicAcid1
     * @param nucleicAcid2
     * @return true if the two nucleic acids are equal
     */
    public static boolean areEquals(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2){
        if (defaultNucleicAcidComparator == null){
            defaultNucleicAcidComparator = new DefaultNucleicAcidComparator();
        }

        return defaultNucleicAcidComparator.compare(nucleicAcid1, nucleicAcid2) == 0;
    }
}
