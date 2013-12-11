package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final Experiment exp = new DefaultExperiment(new DefaultPublication());
        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0841", "phosphotransfer assay" ) );
        interaction.setExperimentAndAddInteractionEvidence(exp);

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0841", "phosphotransfer assay" );

        // set the biological role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0842", "phosphate donor" );
        addParticipant( interaction, "MI:0843", "phosphate acceptor" );

        InteractionDetectionMethod2BiologicalRoleDependencyRule rule =
                new InteractionDetectionMethod2BiologicalRoleDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            messages.addAll(rule.check( p ));

        }
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final Experiment exp = new DefaultExperiment(new DefaultPublication());

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0841", "phosphotransfer assay" ) );
        interaction.setExperiment( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0841", "phosphotransfer assay" );

        // set the biological role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0503", "self" );
        addParticipant( interaction, "MI:0842", "phosphate donor" );

        InteractionDetectionMethod2BiologicalRoleDependencyRule rule =
                new InteractionDetectionMethod2BiologicalRoleDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( InteractionEvidence interaction,
                                 String bioMi, String bioName ) {

        final ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant.setBiologicalRole( buildBiologicalRole( bioMi, bioName ));
        interaction.addParticipant(participant);
    }
}
