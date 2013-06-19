package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;

import java.util.Collection;
import java.util.TreeSet;

/**
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 */
public class MaximumCvTermUpdater
        extends MinimumCvTermUpdater
        implements CvTermEnricher {

    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){

        super.processCvTerm(cvTermToEnrich);


        //Add synonyms
        Collection<Alias> subtractedSynonyms = CollectionManipulationUtils.comparatorSubtract(
                cvTermFetched.getSynonyms(),
                cvTermToEnrich.getSynonyms(),
                new DefaultAliasComparator());

        for(Alias aliasSynonym: subtractedSynonyms){
            cvTermToEnrich.getSynonyms().add(aliasSynonym);
            if (listener != null) listener.onAddedSynonym(cvTermToEnrich , aliasSynonym);
        }

    }

}
