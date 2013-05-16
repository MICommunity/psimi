package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;
import psidev.psi.mi.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;
import psidev.psi.mi.util.CollectionUtilsExtra;

import java.util.Collection;

/**
 * Date: 13/05/13
 * Time: 14:16
 */
public class MaximumCvTermUpdater
        extends MinimumCvTermEnricher
        implements CvTermEnricher{


    public MaximumCvTermUpdater() throws EnrichmentException {
    }

    public void enrichCvTerm(CvTerm cvTermMaster)
            throws EnrichmentException{

        enricherEvent = new EnricherEvent();
        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster, enricherEvent);
        enrichCvTerm(cvTermMaster, cvTermEnriched);
    }



    public void enrichCvTerm(CvTerm cvTermMaster, CvTerm cvTermEnriched)
            throws EnrichmentException{

        super.enrichCvTerm(cvTermMaster, cvTermEnriched);

        //Check full name
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermMaster.getFullName().equals(cvTermEnriched.getFullName())){
                String oldname =  cvTermMaster.getFullName();
                cvTermMaster.setFullName(cvTermEnriched.getFullName());
                //TODO MISMATCH OR OVERWRITE
                addOverwriteEvent(new OverwriteEvent(
                        "FullName", oldname, cvTermMaster.getFullName()));
            }
        }

        //Overwrite shortname
        if(!cvTermMaster.getShortName().equals(cvTermEnriched.getShortName())){
            String oldname =  cvTermMaster.getShortName();
            cvTermMaster.setShortName(cvTermEnriched.getShortName());
            //TODO MISMATCH OR OVERWRITE
            addOverwriteEvent(new OverwriteEvent(
                    "ShortName", oldname, cvTermMaster.getShortName()));
        }

        /*
        //Add identifiers
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getIdentifiers(),
                cvTermMaster.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref x: subtractedIdentifiers){
            cvTermMaster.getIdentifiers().add(x);

            AdditionEvent e = new AdditionEvent(report);
            e.setAdditionValues("Identifier", x.getId());
            fireAdditionEvent(e);
        }

        //Add synonyms
        Collection<Alias> subtractedSynonyms = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getSynonyms(),
                cvTermMaster.getSynonyms(),
                new DefaultAliasComparator());

        for(Alias x: subtractedSynonyms){
            cvTermMaster.getSynonyms().add(x);

            AdditionEvent e = new AdditionEvent(report);
            e.setAdditionValues( "Synonym", "Name: "+x.getName()+", Type: "+x.getType());
            fireAdditionEvent(e);
        }   */
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
