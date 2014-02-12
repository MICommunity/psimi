package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ExperimentalEntityChangeListener;
import psidev.psi.mi.jami.model.ExperimentalEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ParticipantEvidenceEnricherListener<P extends ExperimentalEntity> extends ParticipantEnricherListener<P>, ExperimentalEntityChangeListener<P> {

}
