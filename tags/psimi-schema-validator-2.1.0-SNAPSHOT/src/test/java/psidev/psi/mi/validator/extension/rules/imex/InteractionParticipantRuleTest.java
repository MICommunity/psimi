package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.psimi.InteractionParticipantRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Unit tester of InteractionParticipantRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/04/11</pre>
 */

public class InteractionParticipantRuleTest extends AbstractRuleTest {

        public InteractionParticipantRuleTest() throws OntologyLoaderException {
        super( InteractionImexPrimaryRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void validate_interaction_with_participants() throws Exception {
        Interaction interaction = buildInteractionDeterministic();

        Assert.assertFalse(interaction.getParticipants().isEmpty());

        InteractionParticipantRule rule = new InteractionParticipantRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);
        Assert.assertTrue(messages.isEmpty());
    }

    @Test
    public void validate_interaction_without_participants() throws Exception {
        Interaction interaction = new Interaction();

        Assert.assertTrue(interaction.getParticipants().isEmpty());

        InteractionParticipantRule rule = new InteractionParticipantRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);
        Assert.assertEquals(1, messages.size());
    }
}
