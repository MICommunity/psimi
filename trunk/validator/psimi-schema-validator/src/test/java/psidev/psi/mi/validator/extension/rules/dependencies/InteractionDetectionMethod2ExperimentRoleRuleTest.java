package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * InteractionDetectionMethod2ExperimentRoleDependencyRule Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ExperimentRoleRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
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
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipantEvidences()){
            messages.addAll(rule.check( p ));

        }
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );
        Organism host = new Organism();

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.setExperiment( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, NEUTRAL_MI_REF, "neutral" );

        InteractionDetectionMethod2ExperimentRoleDependencyRule rule =
                new InteractionDetectionMethod2ExperimentRoleDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipantEvidences()){
            messages.addAll(rule.check( p ));

        }
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
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
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipantEvidences()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }



    private void addParticipant( InteractionEvidence interaction,
                                 String expRoleMi, String expRoleName ) {

        final ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant.setExperimentalRole( buildExperimentalRole( expRoleMi, expRoleName ));
        interaction.addParticipant( participant );
    }
}
