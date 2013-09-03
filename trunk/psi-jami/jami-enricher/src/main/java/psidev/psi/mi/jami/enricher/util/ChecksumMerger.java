package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.comparator.checksum.DefaultChecksumComparator;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  Defines the behaviour of merged checksum lists.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 11/07/13
 */
public class ChecksumMerger {

    boolean fetchedCRC64 = false;
    boolean fetchedROGID = false;
    Collection<Checksum> fetchedToAdd = new ArrayList<Checksum>();
    Collection<Checksum> toRemove = new ArrayList<Checksum>();


    public Collection<Checksum> getFetchedToAdd(){
        return fetchedToAdd;
    }

    public Collection<Checksum> getToRemove(){
        return toRemove;
    }

    public void merge(Collection<Checksum> toEnrichChecksums , Collection<Checksum> fetchedChecksums){
        fetchedCRC64 = false;
        fetchedROGID = false;

        fetchedToAdd.clear();
        fetchedToAdd.addAll(fetchedChecksums);

        toRemove.clear();

        // Check all of the checksums which are to be enriched
        for(Checksum checksumToEnrich : toEnrichChecksums){
            boolean isInFetchedList = false;

            // Remove from the list of checksums to add, all which are already present.
            for(Checksum checksumToAdd : fetchedToAdd){
                if(DefaultChecksumComparator.areEquals(checksumToEnrich, checksumToAdd)){
                    isInFetchedList = true;
                    fetchedToAdd.remove(checksumToAdd); // No need to add it as it is already there
                    break;
                }
            }

            // If it's not in the fetched list, remove it
            if(! isInFetchedList){
                toRemove.add(checksumToEnrich);
            }
        }
    }
}