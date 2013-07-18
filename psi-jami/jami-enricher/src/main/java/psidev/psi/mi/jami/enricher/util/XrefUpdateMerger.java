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


    /**
     * Takes two lists of Xrefs and produces a list of those to add and those to remove.
     *
     * The Xrefs to add will be those which were fetched, without those which are already in the list being enriched.
     * Those to remove are all the xrefs on the list being enriched with a cvterm that matches the cvterm of a fetched xref,
     * unless the xref has qualifier when use qualifiers is true.
     *
     * For merging identifiers, 'do not use
     *
     * @param fetchedXrefs
     * @param toEnrichXrefs
     * @param useQualifiers
     */
    public void merge(Collection<Xref> fetchedXrefs , Collection<Xref> toEnrichXrefs, boolean useQualifiers){
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
            boolean isInFetchedList = false;

            // Check all the fetched xrefs so as not to add the same xref twice
            for(Xref xrefToAdd : fetchedToAdd){
                if(DefaultXrefComparator.areEquals(xrefToAdd, xrefIdentifier)){
                    isInFetchedList = true;
                    fetchedToAdd.remove(xrefToAdd); // No need to add it as it is already there
                    break;
                }
            }

            // If it's not in the fetched to add,
            // check if its CvTerm marks it for removal
            // (if it has no qualifier or qualifiers are not being used)
            if(! isInFetchedList && (!useQualifiers || xrefIdentifier.getQualifier() == null)){
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
