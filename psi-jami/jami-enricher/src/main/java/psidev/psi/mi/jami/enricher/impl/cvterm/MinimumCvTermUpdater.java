package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.Collection;

/**
 * Provides minimum updating of the CvTerm.
 * Will update the short name and full name of CvTerm to enrich.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MinimumCvTermUpdater
        extends AbstractCvTermEnricher
        implements CvTermEnricher{

    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){

        if(cvTermFetched.getShortName() != null
                && ! cvTermFetched.getShortName().equalsIgnoreCase(cvTermToEnrich.getShortName())){
            if (listener != null) listener.onShortNameUpdate(cvTermToEnrich, cvTermToEnrich.getShortName());
            cvTermToEnrich.setShortName(cvTermFetched.getShortName());

        }

        //FullName
        if(cvTermFetched.getFullName() != null
                && ! cvTermFetched.getFullName().equalsIgnoreCase(cvTermToEnrich.getFullName())){
            if (listener != null) listener.onFullNameUpdate(cvTermToEnrich, cvTermToEnrich.getFullName());
            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
        }

        //TOdo - why is this commented
        //Identifiers
        /*Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                cvTermFetched.getIdentifiers(),
                cvTermToEnrich.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref xrefIdentifier: subtractedIdentifiers){
            cvTermToEnrich.getIdentifiers().add(xrefIdentifier);
            if (listener != null) listener.onAddedIdentifier(cvTermToEnrich, xrefIdentifier);
        }  */
    }

}
