package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * TaxIdHostOrganismRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
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

        final Experiment experimentDescription = buildExperiment( 9606 );
        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_No_HostOrganism() throws Exception {
        final ExperimentHostOrganismRule rule = new ExperimentHostOrganismRule( ontologyMaganer );

        final Publication bibref = new DefaultPublication();
        final Xref xref = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "1234567", "primary-reference", "MI:0358");
        bibref.getIdentifiers().add(xref);
        final Experiment experimentDescription = new DefaultExperiment( bibref);

        final Collection<ValidatorMessage> messages = rule.check( experimentDescription );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}