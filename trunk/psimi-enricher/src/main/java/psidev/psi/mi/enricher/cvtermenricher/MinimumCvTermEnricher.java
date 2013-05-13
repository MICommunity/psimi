package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.enricher.cvtermenricher.exception.EnrichmentException;
import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

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

    public void enrichCvTerm(CvTerm cvTermMaster) throws EnrichmentException{
        EnrichmentReport report = new EnrichmentReport();
        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster, report);

        //Todo report obsolete

        if(cvTermMaster.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermMaster.setFullName(cvTermEnriched.getFullName());
            fireAdditionEvent(new AdditionEvent(
                    report.getIdentity(), report.getIdentityType(),
                    "FullName", cvTermMaster.getFullName()));
        }
         /*
        if(cvTermMaster.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermMaster.setFullName(cvTermEnriched.getFullName());
            fireAdditionEvent(new AdditionEvent(report.getIdentity(), report.getMethod(), "FullName", cvTermMaster.getFullName()));
        }   */


        //Add identifiers
        for(Xref e: cvTermEnriched.getIdentifiers()){
            boolean included = false;
            for(Xref m: cvTermMaster.getIdentifiers()){
                if(m.equals(e)){
                    included = true;
                    break;
                }
            }
            if(!included){
                cvTermMaster.getIdentifiers().add(e);
                fireAdditionEvent(new AdditionEvent(
                        report.getIdentity(), report.getIdentityType(),
                        "Identifier", e.getId()));
            }
        }

        //Add synonyms
        for(Alias e: cvTermEnriched.getSynonyms()){
            boolean included = false;
            for(Alias m: cvTermMaster.getSynonyms()){
                if(m.equals(e)){
                    included = true;
                    break;
                }
            }
            if(!included){
                cvTermMaster.getSynonyms().add(e);
                fireAdditionEvent(new AdditionEvent(
                        report.getIdentity(), report.getIdentityType(),
                        "Synonym", "Name: "+e.getName()+", Type: "+e.getType()));
            }
        }
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
