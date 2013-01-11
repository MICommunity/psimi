package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.checksum.ChecksumComparator;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.*;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class ExactInteractorComparator implements Comparator<Interactor> {

    protected InteractorComparator interactorComparator;
    private OrganismTaxIdComparator organismComparator;
    private AbstractCvTermComparator typeComparator;
    protected ChecksumComparator checksumComparator;

    /**
     * Creates a new ExactInteractorComparator.
     * @param interactorComparator : the interactor comparator to compare basic ids. It is required
     * @param organismComparator : the comparator for organisms. if null will be OrganismTaxIdComparator
     * @param typeComparator : the interactor type comparator. It is required
     * @param checksumComparator : the checksum comparator. It is required
     */
    public ExactInteractorComparator(InteractorComparator interactorComparator, OrganismTaxIdComparator organismComparator,
                                     AbstractCvTermComparator typeComparator, ChecksumComparator checksumComparator){

        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compares interactor basic identifiers. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        if (organismComparator == null){
            this.organismComparator = new OrganismTaxIdComparator();
        }
        else {
            this.organismComparator = organismComparator;
        }
        if (typeComparator == null){
            throw new IllegalArgumentException("The interactor type comparator is required to compares interactor types. It cannot be null");
        }
        this.typeComparator = typeComparator;
        if (checksumComparator == null){
            throw new IllegalArgumentException("The checksum comparator is required to compares interactor checksums. It cannot be null");
        }
        this.checksumComparator = checksumComparator;
    }

    public OrganismTaxIdComparator getOrganismComparator() {
        return organismComparator;
    }

    public InteractorComparator getInteractorComparator() {
        return interactorComparator;
    }

    public AbstractCvTermComparator getTypeComparator() {
        return typeComparator;
    }

    public ChecksumComparator getChecksumComparator() {
        return checksumComparator;
    }

    public int compare(Interactor interactor1, Interactor interactor2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == null && interactor2 == null){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else{
            // compares first interactor types if both are set
            CvTerm type1 = interactor1.getType();
            CvTerm type2 = interactor2.getType();

            int comp=EQUAL;
            if (type1 != null && type2 != null){
                comp = typeComparator.compare(type1, type2);
            }

            if (comp != 0){
                return comp;
            }

            // then compares organism if both are set
            Organism organism1 = interactor1.getOrganism();
            Organism organism2 = interactor2.getOrganism();

            int comp2=EQUAL;
            if (organism1 != null && organism2 != null){
                comp2 = organismComparator.compare(organism1, organism2);
            }

            if (comp2 != 0){
                return comp2;
            }

            // then compares checksum for at least one common checksum
            int comp3 = EQUAL;
            if (!interactor1.getChecksums().isEmpty() && !interactor2.getChecksums().isEmpty()){
                List<Checksum> checksums1 = new ArrayList<Checksum>(interactor1.getChecksums());
                List<Checksum> checksums2 = new ArrayList<Checksum>(interactor2.getChecksums());
                // sort the collections first
                Collections.sort(checksums1, checksumComparator);
                Collections.sort(checksums2, checksumComparator);
                // get an iterator
                Iterator<Checksum> iterator1 = checksums1.iterator();
                Iterator<Checksum> iterator2 = checksums2.iterator();

                // at least one checksum must match
                Checksum checksum1 = iterator1.next();
                Checksum checksum2 = iterator2.next();
                comp3 = checksumComparator.compare(checksum1, checksum2);
                while (comp3 != 0 && checksum1 != null && checksum2 != null){
                    // checksum1 is before checksum2
                    if (comp3 < 0){
                        // we need to get the next element from ids1
                        if (iterator1.hasNext()){
                            checksum1 = iterator1.next();
                            comp3 = checksumComparator.compare(checksum1, checksum2);
                        }
                        // ids 1 is empty, we can stop here
                        else {
                            checksum1 = null;
                        }
                    }
                    // checksum2 is before checksum1
                    else {
                        // we need to get the next element from ids2
                        if (iterator2.hasNext()){
                            checksum2 = iterator2.next();
                            comp3 = checksumComparator.compare(checksum1, checksum2);
                        }
                        // ids 2 is empty, we can stop here
                        else {
                            checksum2 = null;
                        }
                    }
                }

                if (comp2 == 0){
                    return comp2;
                }
            }

            int comp4 = interactorComparator.compare(interactor1, interactor2);
            if (comp4 == 0){
                return comp4;
            }
            else if (comp3 != 0){
                return comp3;
            }
            else {
                return comp4;
            }
        }
    }
}
