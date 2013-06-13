package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.NucleicAcid;

import java.util.Comparator;

/**
 * Default nucleic acids comparator.
 * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties.
 * If the basic polymer properties are the same, It will look for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set or are identical, it will look at the
 * Refseq identifiers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultNucleicAcidComparator implements Comparator<NucleicAcid> {

    private static DefaultNucleicAcidComparator defaultNucleicAcidComparator;
    protected DefaultPolymerComparator interactorComparator;

    /**
     * Creates a new DefaultNucleicAcidComparator. It will uses a DefaultInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultNucleicAcidComparator() {
        this.interactorComparator = new DefaultPolymerComparator();
    }

    protected DefaultNucleicAcidComparator(DefaultPolymerComparator polymerBaseComparator) {
        this.interactorComparator = polymerBaseComparator != null ? polymerBaseComparator : new DefaultPolymerComparator();
    }

    /**
     * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties.
     * If the basic polymer properties are the same, It will look for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set or are identical, it will look at the
     * Refseq identifiers.
     *
     */
    public int compare(NucleicAcid nucleicAcid1, NucleicAcid nucleicAcid2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (nucleicAcid1 == null && nucleicAcid2 == null){
            return EQUAL;
        }
        else if (nucleicAcid1 == null){
            return AFTER;
        }
        else if (nucleicAcid2 == null){
            return BEFORE;
        }
        else {

            // compares first the basic interactor properties
            int comp = interactorComparator.compare(nucleicAcid1, nucleicAcid2);
            if (comp != 0){
                return comp;
            }

            // then compares DDBJ/EMBL/Genbank identifiers
            String ddbjEmblGenbank1 = nucleicAcid1.getDdbjEmblGenbank();
            String ddbjEmblGenbank2 = nucleicAcid2.getDdbjEmblGenbank();

            if (ddbjEmblGenbank1 != null && ddbjEmblGenbank2 != null){
                comp = ddbjEmblGenbank1.compareTo(ddbjEmblGenbank2);
                if (comp != 0){
                   return comp;
                }
            }

            // compares Refseq
            String refseq1 = nucleicAcid1.getRefseq();
            String refseq2 = nucleicAcid2.getRefseq();

            if (refseq1 != null && refseq2 != null){
                return refseq1.compareTo(refseq2);
            }

            return comp;
        }
    }

    public DefaultPolymerComparator getInteractorComparator() {
        return (DefaultPolymerComparator) this.interactorComparator;
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
