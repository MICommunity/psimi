package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ParticipantPoolChangeListener;
import psidev.psi.mi.jami.model.ParticipantPool;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ParticipantPoolEnricherListener<P extends ParticipantPool> extends ParticipantEnricherListener<P>, ParticipantPoolChangeListener<P> {

}
