package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * MissingExperimentInteractionDetectionMethodRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class ExperimentInteractionDetectionMethodRuleTest extends AbstractRuleTest {

    public ExperimentInteractionDetectionMethodRuleTest() throws OntologyLoaderException {
        super( ExperimentInteractionDetectionMethodRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void validate_OneInteractionDetectionMethod() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );

        final Xref intDetMetXref = new Xref();
        intDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0016", "identical object", "MI:0356" ) );
        final Names intDetMetName = new Names();
        intDetMetName.setFullName("circular dichroism");

        InteractionDetectionMethod intDetMet = new InteractionDetectionMethod();
        intDetMet.setNames(intDetMetName);
        intDetMet.setXref(intDetMetXref);

        ExperimentDescription exp = new ExperimentDescription( bibref, intDetMet );

        ExperimentInteractionDetectionMethodRule rule = new ExperimentInteractionDetectionMethodRule( ontologyMaganer );
        
        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    // the rule doesn't check anymore if the term is valid as a controlled vocabulary rule does it
    /*@Test
    public void validate_WrongInteractionDetectionMethodID() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );

        final Xref intDetMetXref = new Xref();
        intDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0252", "identical object", "MI:0356" ) );
        final Names intDetMetName = new Names();
        intDetMetName.setFullName("circular dichroism");

        InteractionDetectionMethod intDetMet = new InteractionDetectionMethod();
        intDetMet.setNames(intDetMetName);
        intDetMet.setXref(intDetMetXref);

        ExperimentDescription exp = new ExperimentDescription( bibref, intDetMet );

        MissingExperimentInteractionDetectionMethodRule rule = new MissingExperimentInteractionDetectionMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        System.out.println(messages);

        Assert.assertEquals( 1, messages.size() );
    }*/

    @Test
    @Ignore
    public void validate_NoInteractionDetectionMethod() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );

        ExperimentDescription exp = new ExperimentDescription( );
        exp.setBibref(bibref);

        ExperimentInteractionDetectionMethodRule rule = new ExperimentInteractionDetectionMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_NoInteractionDetectionCrossReferences() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );

        InteractionDetectionMethod intDetMet = new InteractionDetectionMethod();

        ExperimentDescription exp = new ExperimentDescription( bibref, intDetMet );

        ExperimentInteractionDetectionMethodRule rule = new ExperimentInteractionDetectionMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
