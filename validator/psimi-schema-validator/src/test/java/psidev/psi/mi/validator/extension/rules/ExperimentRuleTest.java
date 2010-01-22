package psidev.psi.mi.validator.extension.rules;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * ExperimentInteractionDetectionMethodRule Tester.
 *
 * @author Marine Dumousseau
 */
public class ExperimentRuleTest extends AbstractRuleTest  {

        public ExperimentRuleTest() {
        super();
    }

    @Test
    public void validate_OneInteractionDetectionMethod() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );

        final Xref intDetMetXref = new Xref();
        intDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0016", "primary-reference", "MI:0358" ) );
        final Names intDetMetName = new Names();
        intDetMetName.setFullName("circular dichroism");

        InteractionDetectionMethod intDetMet = new InteractionDetectionMethod();
        intDetMet.setNames(intDetMetName);
        intDetMet.setXref(intDetMetXref);

        ExperimentDescription exp = new ExperimentDescription( bibref, intDetMet );

        ExperimentInteractionDetectionMethodRule rule = new ExperimentInteractionDetectionMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_OneParticipantDetectionMethod() throws Exception {

        final Xref partDetMetXref = new Xref();
        partDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0396", "primary-reference", "MI:0358" ) );
        final Names partDetMetName = new Names();
        partDetMetName.setFullName("predetermined participant");

        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();
        partDetMet.setNames(partDetMetName);
        partDetMet.setXref(partDetMetXref);

        ExperimentDescription exp = new ExperimentDescription();
        exp.setParticipantIdentificationMethod(partDetMet);

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_WrongInteractionDetectionMethodID() throws Exception {

        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "123", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );

        final Xref intDetMetXref = new Xref();
        intDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0252", "primary-reference", "MI:0358" ) );
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
        
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_WrongParticipantDetectionMethodID() throws Exception {

        final Xref partDetMetXref = new Xref();
        partDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0252", "primary-reference", "MI:0358" ) );
        final Names partDetMetName = new Names();
        partDetMetName.setFullName("predetermined participant");

        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();
        partDetMet.setNames(partDetMetName);
        partDetMet.setXref(partDetMetXref);

        ExperimentDescription exp = new ExperimentDescription();
        exp.setParticipantIdentificationMethod(partDetMet);

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }


    @Test
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
    public void validate_NoParticipantDetectionMethod() throws Exception {

        ExperimentDescription exp = new ExperimentDescription();

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

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

    @Test
    public void validate_NoParticipantDetectionCrossReferences() throws Exception {


        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();

        ExperimentDescription exp = new ExperimentDescription();
        exp.setParticipantIdentificationMethod(partDetMet);

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( exp );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

}
