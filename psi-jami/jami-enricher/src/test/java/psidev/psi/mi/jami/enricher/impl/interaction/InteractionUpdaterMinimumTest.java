package psidev.psi.mi.jami.enricher.impl.interaction;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantUpdaterMinimum;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 12/07/13
 */
public class InteractionUpdaterMinimumTest {



    private InteractionUpdaterMinimum interactionEnricher;

    @Before
    public void setUp(){
        interactionEnricher = new InteractionUpdaterMaximum();
    }

    @Test
    public void test_default_enrichers_are_same_type(){
        assertTrue(interactionEnricher.getCvTermEnricher() instanceof CvTermUpdaterMinimum);
        assertTrue(interactionEnricher.getParticipantEnricher() instanceof ParticipantUpdaterMinimum
        );
    }
}
