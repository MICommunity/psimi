package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 */
public class MinimumCvTermEnricher
        extends AbstractCvTermEnricher
        implements CvTermEnricher {



    protected void processCvTerm(CvTerm cvTermToEnrich){

        //ShortName not checked - never null

        //FullName
        if(cvTermToEnrich.getFullName() == null
                && cvTermFetched.getFullName() != null){
            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (listener != null) listener.onFullNameUpdate(cvTermToEnrich, null);
        }

        //Identifiers
        Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                cvTermFetched.getIdentifiers(),
                cvTermToEnrich.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref xrefIdentifier: subtractedIdentifiers){
            cvTermToEnrich.getIdentifiers().add(xrefIdentifier);
            if (listener != null) listener.onAddedIdentifier(cvTermToEnrich, xrefIdentifier);
        }
    }

}
