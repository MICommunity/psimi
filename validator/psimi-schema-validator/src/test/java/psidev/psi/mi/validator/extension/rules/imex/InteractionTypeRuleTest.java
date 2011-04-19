package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.InteractionType;
import psidev.psi.mi.xml.model.Xref;
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
        Interaction interaction = buildInteractionDeterministic();

        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        // association interaction type
        ref.setPrimaryRef(new DbReference(RuleUtils.PSI_MI, RuleUtils.PSI_MI_REF, "MI:0914", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF));
        type.setXref(ref);

        interaction.getInteractionTypes().add(type);

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    // the rule doesn't check anymore if the term is valid as a controlled vocabulary rule does it
    /*@Test
    public void test_interaction_one_interaction_type_wrong_psi() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        // association interaction type
        ref.setPrimaryRef(new DbReference(RuleUtils.PSI_MI, RuleUtils.PSI_MI_REF, "MI:xxxx", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF));
        type.setXref(ref);

        interaction.getInteractionTypes().add(type);

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }*/

    @Test
    public void test_interaction_one_interaction_type_no_psi() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        InteractionType type = new InteractionType();

        interaction.getInteractionTypes().add(type);

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_interaction_one_interaction_no_type() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();

        InteractionTypeRule rule =  new InteractionTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(interaction);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
