package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of InteractionTypeRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class InteractionTypeRuleTest extends AbstractRuleTest {

    public InteractionTypeRuleTest() throws OntologyLoaderException {
        super(InteractorTypeRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void test_interaction_one_interaction_type() throws ValidatorException {
        InteractionEvidence interaction = buildInteractionDeterministic();

        CvTerm type = CvTermUtils.createMICvTerm("physical association","MI:0914");
        interaction.setInteractionType(type);

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_interaction_one_interaction_type_no_psi() throws ValidatorException {
        InteractionEvidence interaction = buildInteractionDeterministic();

        CvTerm type = CvTermUtils.createMICvTerm("physical association",null);
        interaction.setInteractionType(type);

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_interaction_one_interaction_no_type() throws ValidatorException {
        InteractionEvidence interaction = buildInteractionDeterministic();

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
