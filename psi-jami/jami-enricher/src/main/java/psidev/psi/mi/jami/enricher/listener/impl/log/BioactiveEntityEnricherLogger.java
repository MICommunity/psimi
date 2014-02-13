package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.InteractorEnricherLogger;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * A logging listener. It will display a message when each event is fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class BioactiveEntityEnricherLogger extends InteractorEnricherLogger<BioactiveEntity>
        implements BioactiveEntityEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(BioactiveEntityEnricherLogger.class.getName());
}
