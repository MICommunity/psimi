package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * InteractionDetectionMethod2ExperimentRoleDependencyRule Tester.
 *
 * @author Marine Dumousseau
 */
public class InteractionDetectionMethod2ExperimentRoleRuleTest extends AbstractRuleTest {

     public InteractionDetectionMethod2ExperimentRoleRuleTest() throws OntologyLoaderException {
        super( InteractionDetectionMethod2ExperimentRoleRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks that a cross linking study is associated with a recommended experimental role (for instance neutral component)
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_ok() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0497", "neutral component" );
        addParticipant( interaction, "MI:0497", "neutral component" );

        InteractionDetectionMethod2ExperimentRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a cross linking study is not associated with a recommended experiment role.
     * (for instance bait) 
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );
        Organism host = new Organism();

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, NEUTRAL_MI_REF, "neutral" );

        InteractionDetectionMethod2ExperimentRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that a warning message appears when a cross linking study children (MI:0430) is not associated with a recommended experiment role.
     * (for instance bait)
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_child_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );
        Organism host = new Organism();

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0430", "nucleic acid uv cross-linking assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0430", "nucleic acid uv cross-linking assay" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, NEUTRAL_MI_REF, "neutral" );

        InteractionDetectionMethod2ExperimentRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }



    private void addParticipant( Interaction interaction,
                                 String expRoleMi, String expRoleName ) {

        final Participant participant = new Participant();
        participant.setInteractor( new Interactor());
        participant.getExperimentalRoles().clear();
        participant.getExperimentalRoles().add( buildExperimentalRole( expRoleMi, expRoleName ));
        interaction.getParticipants().add( participant );
    }
}
