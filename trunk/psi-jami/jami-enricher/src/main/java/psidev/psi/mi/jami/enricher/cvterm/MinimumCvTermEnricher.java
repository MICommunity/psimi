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
     * If enrichment takes place, the master will be edited.
     *
     * @param cvTermMaster  a CvTerm to enrich
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermMaster)
            throws EnrichmentException{

        //Get the enriched form
        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster);
        if(cvTermEnriched == null){
            //log.debug("The enriched protein was null");
            return;
        }
        //Enrich
        enrichCvTerm(cvTermMaster, cvTermEnriched);
        //Find mismatches
        compareForMismatches(cvTermMaster, cvTermEnriched);
        //Fire the report
        fireEnricherEvent(enricherEvent);
    }

    /**
     * Compares two CvTerms and updates the master with any fields that it is missing.
     * Only full name and synonyms are considered.
     * @param cvTermMaster      The cvTerm to be updated
     * @param cvTermEnriched    The cvTerm containing the data to update the master with.
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermMaster, CvTerm cvTermEnriched)
            throws EnrichmentException{

        //Todo report obsolete
        //Add full name
        if(cvTermMaster.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermMaster.setFullName(cvTermEnriched.getFullName());
            addAdditionReport(new AdditionReport("FullName", cvTermMaster.getFullName()));
        }

        //Add identifiers
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getIdentifiers(),
                cvTermMaster.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref x: subtractedIdentifiers){
            cvTermMaster.getIdentifiers().add(x);
            addAdditionReport(new AdditionReport("Identifier", x.getId()));
        }

        //Add synonyms
        Collection<Alias> subtractedSynonyms = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getSynonyms(),
                cvTermMaster.getSynonyms(),
                new DefaultAliasComparator());

        for(Alias x: subtractedSynonyms){
            cvTermMaster.getSynonyms().add(x);
            addAdditionReport(new AdditionReport("Synonym", "Name: " + x.getName() + ", Type: " + x.getType()));
        }
    }

    /**
     * Compares the Master and enriched form for mismatches.
     * The full name and the short name are compared between the master and enriched forms.
     * @param cvTermMaster
     * @param cvTermEnriched
     */
    public void compareForMismatches(CvTerm cvTermMaster, CvTerm cvTermEnriched){
        //Check full name
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermMaster.getFullName().equals(cvTermEnriched.getFullName())){
                addMismatchReport(new MismatchReport(
                        "FullName", cvTermMaster.getFullName(), cvTermEnriched.getFullName()));
            }
        }

        //Overwrite shortname
        if(!cvTermMaster.getShortName().equals(cvTermEnriched.getShortName())){
            addMismatchReport(new MismatchReport(
                    "ShortName", cvTermMaster.getShortName(), cvTermEnriched.getShortName()));
        }
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
