package psidev.psi.mi.jami.enricher.cvterm;

import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.util.CollectionUtilsExtra;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 14:19
 */
public class MinimumCvTermEnricher
        extends AbstractCvTermEnricher{

    public MinimumCvTermEnricher() throws EnrichmentException {
    }

    /**
     * Enrichment of a single CvTerm.
     * If enrichment takes place, the ToEnrich will be edited.
     *
     * @param cvTermToEnrich  a CvTerm to enrich
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws EnrichmentException{

        //Get the enriched form
        CvTerm cvTermEnriched = getEnrichedForm(cvTermToEnrich);
        if(cvTermEnriched == null){
            //log.debug("The enriched protein was null");
            return;
        }
        //Enrich
        enrichCvTerm(cvTermToEnrich, cvTermEnriched);
        //Find mismatches
        compareForMismatches(cvTermToEnrich, cvTermEnriched);
        //Fire the report
        fireEnricherEvent(enricherEvent);
    }

    /**
     * Compares two CvTerms and updates the ToEnrich with any fields that it is missing.
     * Only full name and synonyms are considered.
     * @param cvTermToEnrich      The cvTerm to be updated
     * @param cvTermEnriched    The cvTerm containing the data to update the ToEnrich with.
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich, CvTerm cvTermEnriched)
            throws EnrichmentException{

        //Todo report obsolete
        //Add full name
        if(cvTermToEnrich.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermToEnrich.setFullName(cvTermEnriched.getFullName());
            addAdditionReport(new AdditionReport("FullName", cvTermToEnrich.getFullName()));
        }

        //Add identifiers
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getIdentifiers(),
                cvTermToEnrich.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref x: subtractedIdentifiers){
            cvTermToEnrich.getIdentifiers().add(x);
            addAdditionReport(new AdditionReport("Identifier", x.getId()));
        }

        //Add synonyms
        Collection<Alias> subtractedSynonyms = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getSynonyms(),
                cvTermToEnrich.getSynonyms(),
                new DefaultAliasComparator());

        for(Alias x: subtractedSynonyms){
            cvTermToEnrich.getSynonyms().add(x);
            addAdditionReport(new AdditionReport("Synonym", "Name: " + x.getName() + ", Type: " + x.getType()));
        }
    }

    /**
     * Compares the ToEnrich and enriched form for mismatches.
     * The full name and the short name are compared between the ToEnrich and enriched forms.
     * @param cvTermToEnrich
     * @param cvTermEnriched
     */
    public void compareForMismatches(CvTerm cvTermToEnrich, CvTerm cvTermEnriched){
        //Check full name
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermToEnrich.getFullName().equals(cvTermEnriched.getFullName())){
                addMismatchReport(new MismatchReport(
                        "FullName", cvTermToEnrich.getFullName(), cvTermEnriched.getFullName()));
            }
        }

        //Overwrite shortname
        if(!cvTermToEnrich.getShortName().equals(cvTermEnriched.getShortName())){
            addMismatchReport(new MismatchReport(
                    "ShortName", cvTermToEnrich.getShortName(), cvTermEnriched.getShortName()));
        }
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
