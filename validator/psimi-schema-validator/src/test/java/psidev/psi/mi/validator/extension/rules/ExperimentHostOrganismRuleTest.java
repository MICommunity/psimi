package psidev.psi.mi.validator.extension.rules;

import org.junit.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.mi.xml.model.*;

import java.io.InputStream;
import java.util.Collection;

/**
 * ExperimentHostOrgRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 2.0.0
 * @version $Id$
 */
public class ExperimentHostOrganismRuleTest extends AbstractRuleTest {

    public ExperimentHostOrganismRuleTest() throws OntologyLoaderException {
        super( ExperimentHostOrganismRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void check_valid_taxid() throws Exception {
        final ExperimentHostOrganismRule rule = new ExperimentHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( 9606 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_invalid_negative_taxid() throws Exception {
        final ExperimentHostOrganismRule rule = new ExperimentHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( -6 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_invalid_positive_taxid() throws Exception {
        final ExperimentHostOrganismRule rule = new ExperimentHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( Integer.MAX_VALUE );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_invalid_taxid_zero() throws Exception {
        final ExperimentHostOrganismRule rule = new ExperimentHostOrganismRule( ontologyMaganer );

        final ExperimentDescription experimentDescription = buildExperiment( 0 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
