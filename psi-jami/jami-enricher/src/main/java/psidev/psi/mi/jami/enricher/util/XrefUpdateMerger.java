package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/07/13
 */
public class XrefUpdateMerger {

    private Collection<Xref> toRemove = new ArrayList<Xref>();
    private Collection<Xref> fetchedToAdd = new ArrayList<Xref>();

    public Collection<Xref> getToAdd() {
        return fetchedToAdd;
    }

    public Collection<Xref> getToRemove() {
        return toRemove;
    }

    public void merge(Collection<Xref> fetchedXrefs , Collection<Xref> toEnrichXrefs){
        fetchedToAdd.clear();
        fetchedToAdd.addAll(fetchedXrefs); // All the identifiers which were fetched

        Collection<CvTerm> identifierCvTerms = new ArrayList<CvTerm>(); // The list of all the represented databases

        // For each entry, add its CvTerm to the representatives list, if it hasn't been so already.
        for(Xref xref : fetchedToAdd){
            if(! identifierCvTerms.contains(xref.getDatabase())) identifierCvTerms.add(xref.getDatabase());
        }

        toRemove.clear();

        // Check all of the identifiers which are to be enriched
        for(Xref xrefIdentifier : toEnrichXrefs){
            // Ignore if there's a qualifier
            if(xrefIdentifier.getQualifier() == null){
                boolean isInFetchedList = false;
                //If it's in the list to add, mark that it's to add already
                for(Xref xrefToAdd : fetchedToAdd){
                    if(DefaultXrefComparator.areEquals(xrefToAdd, xrefIdentifier)){
                        isInFetchedList = true;
                        //No need to add it as it is already there
                        fetchedToAdd.remove(xrefToAdd);
                        break;
                    }
                }
                // If it's not in the fetched ones to add,
                // check if it's CvTerm is managed and add to removals if it is
                if(! isInFetchedList){
                    boolean managedCvTerm = false;
                    for(CvTerm cvTerm : identifierCvTerms){
                        if(DefaultCvTermComparator.areEquals(cvTerm, xrefIdentifier.getDatabase())){
                            managedCvTerm = true;
                            break;
                        }
                    }
                    if(managedCvTerm) toRemove.add(xrefIdentifier);
                }
            }

        }
    }


}
