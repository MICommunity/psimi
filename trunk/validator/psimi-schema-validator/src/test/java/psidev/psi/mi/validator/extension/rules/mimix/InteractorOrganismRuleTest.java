package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of InteractorOrganismRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class InteractorOrganismRuleTest extends AbstractRuleTest {

    @Test
    public void check_has_organism() throws Exception {
        final InteractorOrganismRule rule = new InteractorOrganismRule( ontologyMaganer );

        final Interactor protein = buildProtein("P12345");

        final Organism organism = buildOrganism( 9606 );
        protein.setOrganism(organism);

        final Collection<ValidatorMessage> messages = rule.check( protein );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_no_organisms() throws Exception {
        final InteractorOrganismRule rule = new InteractorOrganismRule( ontologyMaganer );

        final Interactor protein = buildProtein("P12345");

        final Collection<ValidatorMessage> messages = rule.check( protein );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
