package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of NegativeInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/04/11</pre>
 */

public class NegativeInteractionRuleTest extends AbstractRuleTest {

    @Test
    public void check_ok() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();
        Assert.assertFalse(interaction.isNegative());

        NegativeInteractionRule rule = new NegativeInteractionRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_negative() throws ValidatorException {
        Interaction interaction = buildInteractionDeterministic();
        interaction.setNegative(true);
        Assert.assertTrue(interaction.isNegative());

        NegativeInteractionRule rule = new NegativeInteractionRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
