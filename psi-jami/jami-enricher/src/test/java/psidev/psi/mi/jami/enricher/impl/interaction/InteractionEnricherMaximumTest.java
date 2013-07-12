package psidev.psi.mi.jami.enricher.impl.interaction;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEnricherMaximum;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 12/07/13
 */
public class InteractionEnricherMaximumTest {

    private InteractionEnricherMaximum interactionEnricher;

    @Before
    public void setUp(){
        interactionEnricher = new InteractionEnricherMaximum();
    }

    @Test
    public void test_default_enrichers_are_same_type(){
        assertTrue(interactionEnricher.getCvTermEnricher() instanceof CvTermEnricherMaximum);
        assertTrue(interactionEnricher.getParticipantEnricher() instanceof ParticipantEnricherMaximum);
    }

}
