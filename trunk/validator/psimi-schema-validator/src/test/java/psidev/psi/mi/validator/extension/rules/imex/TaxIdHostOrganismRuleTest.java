package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * TaxIdHostOrganismRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 2.0.0
 * @version $Id$
 */
public class TaxIdHostOrganismRuleTest extends AbstractRuleTest {

    public TaxIdHostOrganismRuleTest() throws OntologyLoaderException {
        super( TaxIdHostOrganismRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void check_valid_taxid() throws Exception {
        final TaxIdHostOrganismRule rule = new TaxIdHostOrganismRule( ontologyMaganer );

        final Organism organism = buildOrganism( 9606 );
        final Collection<ValidatorMessage> messages = rule.check( organism );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_invalid_negative_taxid() throws Exception {
        final TaxIdHostOrganismRule rule = new TaxIdHostOrganismRule( ontologyMaganer );

        final Organism organism = buildOrganism( -5 );
        final Collection<ValidatorMessage> messages = rule.check( organism );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_invalid_positive_taxid() throws Exception {
        final TaxIdHostOrganismRule rule = new TaxIdHostOrganismRule( ontologyMaganer );

        final Organism organism = buildOrganism( Integer.MAX_VALUE );
        final Collection<ValidatorMessage> messages = rule.check( organism );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_invalid_taxid_zero() throws Exception {
        final TaxIdHostOrganismRule rule = new TaxIdHostOrganismRule( ontologyMaganer );

        final Organism organism = buildOrganism( 0 );
        final Collection<ValidatorMessage> messages = rule.check( organism );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}