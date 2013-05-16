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
        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster);
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
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
