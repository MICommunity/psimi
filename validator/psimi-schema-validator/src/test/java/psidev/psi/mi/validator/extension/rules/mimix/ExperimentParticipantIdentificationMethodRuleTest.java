package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * ExperimentParticipantIdentificationMethodRule Tester.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */

public class ExperimentParticipantIdentificationMethodRuleTest extends AbstractRuleTest {

    public ExperimentParticipantIdentificationMethodRuleTest() throws OntologyLoaderException {
        super( ExperimentParticipantIdentificationMethodRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void validate_OneParticipantDetectionMethod() throws Exception {

        final Xref partDetMetXref = new Xref();
        partDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0396", "identical object", "MI:0356" ) );
        final Names partDetMetName = new Names();
        partDetMetName.setFullName("predetermined participant");

        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();
        partDetMet.setNames(partDetMetName);
        partDetMet.setXref(partDetMetXref);

        ExperimentDescription exp = new ExperimentDescription();
        exp.setParticipantIdentificationMethod(partDetMet);

        Interaction interaction = new Interaction();
        interaction.getExperiments().add(exp);

        populatesParticipants(interaction);

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_WrongParticipantDetectionMethodID() throws Exception {

        final Xref partDetMetXref = new Xref();
        partDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0252", "identical object", "MI:0356" ) );
        final Names partDetMetName = new Names();
        partDetMetName.setFullName("predetermined participant");

        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();
        partDetMet.setNames(partDetMetName);
        partDetMet.setXref(partDetMetXref);

        ExperimentDescription exp = new ExperimentDescription();
        exp.setParticipantIdentificationMethod(partDetMet);

        Interaction interaction = new Interaction();
        interaction.getExperiments().add(exp);

        populatesParticipants(interaction);

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void validate_NoParticipantDetectionMethod() throws Exception {

        ExperimentDescription exp = new ExperimentDescription();

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        Interaction interaction = new Interaction();
        interaction.getExperiments().add(exp);

        populatesParticipants(interaction);

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 2, messages.size() );
    }

    @Test
    public void validate_OneParticipantDetectionMethodAtTheParticipantLevel() throws Exception {

        final Xref partDetMetXref = new Xref();
        partDetMetXref.setPrimaryRef( new DbReference( "psi-mi", "MI:0488", "MI:0396", "identical object", "MI:0356" ) );
        final Names partDetMetName = new Names();
        partDetMetName.setFullName("predetermined participant");

        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();
        partDetMet.setNames(partDetMetName);
        partDetMet.setXref(partDetMetXref);

        ExperimentDescription exp = new ExperimentDescription();

        Interaction interaction = new Interaction();
        interaction.getExperiments().add(exp);

        populatesParticipants(interaction);

        for (Participant p : interaction.getParticipants()){
            p.getParticipantIdentificationMethods().add(partDetMet);
        }

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void validate_NoParticipantDetectionCrossReferences() throws Exception {


        ParticipantIdentificationMethod partDetMet = new ParticipantIdentificationMethod();

        ExperimentDescription exp = new ExperimentDescription();
        exp.setParticipantIdentificationMethod(partDetMet);

        ExperimentParticipantIdentificationMethodRule rule = new ExperimentParticipantIdentificationMethodRule( ontologyMaganer );

        Interaction interaction = new Interaction();
        interaction.getExperiments().add(exp);

        populatesParticipants(interaction);

        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    private void populatesParticipants(Interaction interaction){
        Participant p1 = new Participant();
        Names name1 = new Names();
        name1.setShortLabel("p1");
        p1.setNames(name1);

        Participant p2 = new Participant();
        Names name2 = new Names();
        name2.setShortLabel("p2");
        p2.setNames(name2);

        interaction.getParticipants().add(p1);
        interaction.getParticipants().add(p2);
    }
}
