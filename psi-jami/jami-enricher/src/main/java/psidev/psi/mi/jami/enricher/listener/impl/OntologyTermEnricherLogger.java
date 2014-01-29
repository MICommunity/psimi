package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.OntologyTermEnricherListener;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * A logging listener. It will display a message when each event is fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class OntologyTermEnricherLogger extends CvTermEnricherLogger implements OntologyTermEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(OntologyTermEnricherLogger.class.getName());

    public void onAddedParent(OntologyTerm o, OntologyTerm added) {
        log.info(o.toString()+" has parent added "+added.toString());
    }

    public void onRemovedParent(OntologyTerm o, OntologyTerm removed) {
        log.info(o.toString()+" has parent removed "+removed.toString());
    }

    public void onAddedChild(OntologyTerm o, OntologyTerm added) {
        log.info(o.toString()+" has child added "+added.toString());
    }

    public void onRemovedChild(OntologyTerm o, OntologyTerm removed) {
        log.info(o.toString()+" has child removed "+removed.toString());
    }
}
