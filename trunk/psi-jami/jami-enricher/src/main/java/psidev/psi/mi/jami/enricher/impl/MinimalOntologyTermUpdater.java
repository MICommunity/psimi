package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.OntologyTermEnricherListener;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * Minimal updater for ontology terms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class MinimalOntologyTermUpdater extends MinimalOntologyTermEnricher{
    public MinimalOntologyTermUpdater(OntologyTermFetcher cvTermFetcher) {
        super(new MinimalCvTermUpdater(cvTermFetcher));
    }

    protected MinimalOntologyTermUpdater(CvTermEnricher cvEnricher) {
        super(cvEnricher);
    }

    @Override
    protected void processChildren(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        mergeOntologyTerms(cvTermToEnrich, cvTermToEnrich.getParents(), cvTermFetched.getParents(), true, true);
    }

    @Override
    protected void processParents(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        mergeOntologyTerms(cvTermToEnrich, cvTermToEnrich.getChildren(), cvTermFetched.getChildren(), true, false);
    }

    @Override
    protected void processDefinition(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) {
        if ((cvTermFetched.getDefinition() != null && !cvTermFetched.getDefinition().equalsIgnoreCase(cvTermToEnrich.getDefinition())
                || (cvTermFetched.getDefinition() == null && cvTermFetched.getDefinition() != null))){
            String oldDef = cvTermToEnrich.getDefinition();
            cvTermToEnrich.setDefinition(cvTermFetched.getDefinition());
            if (getCvTermEnricherListener() instanceof OntologyTermEnricherListener){
                ((OntologyTermEnricherListener)getCvTermEnricherListener()).onDefinitionUpdate(cvTermToEnrich, oldDef);
            }
        }
    }
}
