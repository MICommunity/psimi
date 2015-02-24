package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * InteractionDetectionMethod2ParticipantIdentificationMethodRule Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final Experiment exp = new DefaultExperiment(new DefaultPublication());
        exp.setInteractionDetectionMethod( buildDetectionMethod( PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" ) );
        interaction.setExperimentAndAddInteractionEvidence( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" );

        // set the identification method of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0396", "predetermined participant" );
        addParticipant( interaction, "MI:0396", "predetermined participant" );

        InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule rule =
                new InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            messages.addAll(rule.check( p ));

        }
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final Experiment exp = new DefaultExperiment(new DefaultPublication());

        exp.setInteractionDetectionMethod( buildDetectionMethod( PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" ) );
        interaction.setExperimentAndAddInteractionEvidence( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, PROTEIN_COMPLEMENTATION_MI_REF, "protein complementation assay" );

        // set the identification method of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0396", "predetermined participant" );
        addParticipant( interaction, "MI:0929", "northern blot" );

        InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule rule =
                new InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            messages.addAll(rule.check( p ));

        }
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
        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final Experiment exp = new DefaultExperiment(new DefaultPublication());

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0588", "3 hybrid method" ) );
        interaction.setExperimentAndAddInteractionEvidence( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0588", "3 hybrid method" );

        // set the identification method of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0396", "predetermined participant" );
        addParticipant( interaction, "MI:0929", "northern blot" );

        InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule rule =
                new InteractionDetectionMethod2ParticipantIdentificationMethodDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            messages.addAll(rule.check( p ));

        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( InteractionEvidence interaction,
                                 String partIdMi, String partIdName ) {

        final ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        participant.getIdentificationMethods().add(buildParticipantIdentificationMethod(partIdMi, partIdName));
        interaction.addParticipant(participant);
    }

    private CvTerm buildParticipantIdentificationMethod( String mi, String name ) {
        final CvTerm detectionMethod = CvTermUtils.createMICvTerm(name, mi);
        return detectionMethod;
    }
}
