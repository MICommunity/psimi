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
 * InteractionDetectionMethod2ParticipantIdentificationMethodRule Tester.
 *
 * @author Marine Dumousseau
 */
public class InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRuleTest extends AbstractRuleTest {

    public InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRuleTest() throws OntologyLoaderException {
        super( InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks that the protein complementation assay has a valid participant identification method (for instance predetermined participant)
     * @throws Exception
     */
    @Test
    public void check_Protein_Complementation_Ok() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );

        exp.setInteractionDetectionMethod( buildDetectionMethod( PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" );

        // set the identification method of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0396", "predetermined participant" );
        addParticipant( interaction, "MI:0396", "predetermined participant" );

        InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule rule =
                new InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that there is a warning message when protein complementation assay is not associated with a recommended participant
     * detection method (for instance northern blot)
     * @throws Exception
     */
    @Test
    public void check_Protein_Complementation_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        exp.setInteractionDetectionMethod( buildDetectionMethod( PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" );

        // set the identification method of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0396", "predetermined participant" );
        addParticipant( interaction, "MI:0929", "northern blot" );

        InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule rule =
                new InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that a warning message appears when a child of protein complementation assay (ex : 3 hybrid method) is not
     * associated with a recommended participant identification method (for instance northern blot).
     * @throws Exception
     */
    @Test
    public void check_Protein_Complementation_child_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );
        Organism host = new Organism();

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0588", "3 hybrid method" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0588", "3 hybrid method" );

        // set the identification method of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0396", "predetermined participant" );
        addParticipant( interaction, "MI:0929", "northern blot" );

        InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule rule =
                new InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( Interaction interaction,
                                 String partIdMi, String partIdName ) {

        final Participant participant = new Participant();
        participant.setInteractor( new Interactor());
        participant.getParticipantIdentificationMethods().clear();
        participant.getParticipantIdentificationMethods().add( buildParticipantIdentificationMethod( partIdMi, partIdName ));
        interaction.getParticipants().add( participant );
    }

    private ParticipantIdentificationMethod buildParticipantIdentificationMethod( String mi, String name ) {
        final ParticipantIdentificationMethod detectionMethod = new ParticipantIdentificationMethod();
        detectionMethod.setXref( new Xref() );
        detectionMethod.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, mi, IDENTITY, IDENTITY_MI_REF ) );
        detectionMethod.setNames( new Names() );
        detectionMethod.getNames().setShortLabel( name );
        return detectionMethod;
    }
}
