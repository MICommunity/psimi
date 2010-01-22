package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * InteractionDetectionMethod2BiologicalRuleRule Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2BiologicalRoleDependencyRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2BiologicalRoleDependencyRuleTest extends AbstractRuleTest {

    public InteractionDetectionMethod2BiologicalRoleDependencyRuleTest() throws OntologyLoaderException {
        super( InteractionDetectionMethod2BiologicalRoleDependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks that a phosphotransfer assay is associated with a valid biological role (for instance phosphate acceptor/donor)
     * @throws Exception
     */
    @Test
    public void check_Phosphotransfer_ok() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0841", "phosphotransfer assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0841", "phosphotransfer assay" );

        // set the biological role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0842", "phosphate donor" );
        addParticipant( interaction, "MI:0843", "phosphate acceptor" );

        InteractionDetectionMethod2BiologicalRoleDependencyRule rule =
                new InteractionDetectionMethod2BiologicalRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a phosphotransfer assay is associated with a non valid biological role (for instance self)
     * @throws Exception
     */
    @Test
    public void check_Phosphotransfer_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );
        Organism host = new Organism();

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0841", "phosphotransfer assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0841", "phosphotransfer assay" );

        // set the biological role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0503", "self" );
        addParticipant( interaction, "MI:0842", "phosphate donor" );

        InteractionDetectionMethod2BiologicalRoleDependencyRule rule =
                new InteractionDetectionMethod2BiologicalRoleDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( Interaction interaction,
                                 String bioMi, String bioName ) {

        final Participant participant = new Participant();
        participant.setInteractor( new Interactor());
        participant.setBiologicalRole( buildBiologicalRole( bioMi, bioName ));
        interaction.getParticipants().add( participant );
    }
}
