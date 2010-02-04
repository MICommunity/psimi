package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * ExperimentImexCurationRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class ExperimentImexCurationRuleTest extends AbstractRuleTest {

    public ExperimentImexCurationRuleTest() throws OntologyLoaderException {
        super( ExperimentImexCurationRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void validate_AttributeName() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("MI:0959", "imex curation", ""));

        ExperimentImexCurationRule rule = new ExperimentImexCurationRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_Fail_AttributeName() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("journal", "Science (0036-8075)"));

        ExperimentImexCurationRule rule = new ExperimentImexCurationRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_Fail_AttributeName_FullCoverage() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );

        exp.getAttributes().add(new Attribute("MI:0957", "full coverage", ""));

        ExperimentImexCurationRule rule = new ExperimentImexCurationRule( ontologyMaganer );


        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

}
