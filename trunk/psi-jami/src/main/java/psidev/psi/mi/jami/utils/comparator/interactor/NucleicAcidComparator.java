package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Basic nucleic acids comparator.
 * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
 * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use Comparator<Interactor> to compare the interactor properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class NucleicAcidComparator implements Comparator<NucleicAcid> {

    protected Comparator<Interactor> interactorComparator;
    protected OrganismTaxIdComparator organismComparator;

    /**
     * Creates a bew NucleicAcidComparator. It needs a Comparator<Interactor> to compares interactor properties and it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public NucleicAcidComparator(Comparator<Interactor> interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare nucleic acids. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        this.organismComparator = new OrganismTaxIdComparator();

    }

    /**
     * Creates a bew NucleicAcidComparator. It needs a Comparator<Interactor> to compares interactor properties and a OrganismComparator
     * to compare the sequence and organism. If the organism comparator is null,it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     * @param organismComparator : comparator for organism
     */
    public NucleicAcidComparator(Comparator<Interactor> interactorComparator, OrganismTaxIdComparator organismComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare nucleic acids. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        if (organismComparator == null){
            this.organismComparator = new OrganismTaxIdComparator();
        }
        else {
            this.organismComparator = organismComparator;
        }
    }

    /**
     *  * It will look first for DDBJ/EMBL/Genbank identifier if both are set. If the DDBJ/EMBL/Genbank identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the sequence/organism.
     * If the properties of a nucleic acid were not enough to compare the nucleic acids, it will use Comparator<Interactor> to compare the interactor properties
     *
     * @param nucleicAcid1
     * @param nucleicAcid2
     * @return
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
            // first compares DDBJ/EMBL/Genbank identifiers
            String ddbjEmblGenbank1 = nucleicAcid1.getDdbjEmblGenbank();
            String ddbjEmblGenbank2 = nucleicAcid1.getDdbjEmblGenbank();

            if (ddbjEmblGenbank1 != null && ddbjEmblGenbank2 != null){
                return ddbjEmblGenbank1.compareTo(ddbjEmblGenbank2);
            }

            // compares Refseq if at least one Refseq identifier is not set
            String refseq1 = nucleicAcid1.getRefseq();
            String refseq2 = nucleicAcid2.getRefseq();

            if (refseq1 != null && refseq2 != null){
                return refseq1.compareTo(refseq2);
            }

            // compares sequences if at least one Refseq is not set
            String seq1 = nucleicAcid1.getSequence();
            String seq2 = nucleicAcid2.getSequence();

            if (seq1 != null && seq2 != null){
                int comp = seq1.compareTo(seq2);
                // if sequences are equal, look at the organism before saying that the nucleic acids are equals.
                if (comp == 0){
                    comp = organismComparator.compare(nucleicAcid1.getOrganism(), nucleicAcid2.getOrganism());
                }
                return comp;
            }

            // compares the interactor properties if the nucleic acid properties are not enough
            return interactorComparator.compare(nucleicAcid1, nucleicAcid2);
        }
    }

    public Comparator<Interactor> getInteractorComparator() {
        return interactorComparator;
    }

    public OrganismTaxIdComparator getOrganismComparator() {
        return organismComparator;
    }
}
