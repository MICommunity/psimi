package psidev.psi.mi.validator.extension.rules.mimix;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of BiologicalRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class BiologicalRoleRuleTest extends AbstractRuleTest {


    @Test
    public void check_ok() throws ValidatorException {
        final Participant p = buildParticipantDeterministic();
        Assert.assertTrue(p.getBiologicalRole() != null);

        BiologicalRoleRule rule = new BiologicalRoleRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( p );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_bad_role() throws ValidatorException {
        final Participant p = buildParticipantDeterministic();
        Assert.assertTrue(p.getBiologicalRole() != null);
        p.getBiologicalRole().setMIIdentifier(null);
        p.getBiologicalRole().setMODIdentifier("MI:0xxx");
        BiologicalRoleRule rule = new BiologicalRoleRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( p );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
