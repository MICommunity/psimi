package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.imex.ExperimentTaxIdHostOrganismRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * ExperimentTaxIdHostOrganismRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 2.0.0
 * @version $Id$
 */
public class ExperimentTaxIdHostOrganismRuleTest extends AbstractRuleTest {

    public ExperimentTaxIdHostOrganismRuleTest() throws OntologyLoaderException {
        super( ExperimentTaxIdHostOrganismRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void check_valid_taxid() throws Exception {
        final ExperimentTaxIdHostOrganismRule rule = new ExperimentTaxIdHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( 9606 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_invalid_negative_taxid() throws Exception {
        final ExperimentTaxIdHostOrganismRule rule = new ExperimentTaxIdHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( -5 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_invalid_positive_taxid() throws Exception {
        final ExperimentTaxIdHostOrganismRule rule = new ExperimentTaxIdHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( Integer.MAX_VALUE );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_invalid_taxid_zero() throws Exception {
        final ExperimentTaxIdHostOrganismRule rule = new ExperimentTaxIdHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( 0 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}