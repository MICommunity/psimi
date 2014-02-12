package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ExperimentalEntityPoolChangeListener;
import psidev.psi.mi.jami.model.ExperimentalEntityPool;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ExperimentalEntityPoolEnricherListener extends ParticipantEvidenceEnricherListener<ExperimentalEntityPool>, ExperimentalEntityPoolChangeListener, EntityPoolEnricherListener<ExperimentalEntityPool> {

}
