package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * FeatureType2FeatureDetectionMethodRule Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FeatureType2FeatureDetectionMethodDependencyRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class FeatureType2FeatureDetectionMethodDependencyRuleTest extends AbstractRuleTest {

    /**
     *
     * @throws psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException
     */
    public FeatureType2FeatureDetectionMethodDependencyRuleTest() throws OntologyLoaderException {
        super( FeatureType2FeatureDetectionMethodDependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks that a feature type (binding site) is associated with a valid feature detection method (for instance western blot).
     * @throws Exception
     */
    @Test
    public void check_BindingSite_ok() throws Exception {
        InteractionEvidence interaction = new DefaultInteractionEvidence();

        final CvTerm detectionMethod = CvTermUtils.createMICvTerm("western blot", "MI:0113");

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0117", "binding site", detectionMethod );
        addParticipant( interaction, "MI:0117", "binding site", detectionMethod );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            for (FeatureEvidence f : p.getFeatures()){
                messages.addAll(rule.check( f ));
            }
        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a feature type (mutation) is associated with a non valid feature detection method (for instance western blot).
     * @throws Exception
     */
    @Test
    public void check_BindingSite_Warning() throws Exception {
        InteractionEvidence interaction = new DefaultInteractionEvidence();

        final CvTerm detectionMethod = CvTermUtils.createMICvTerm("western blot", "MI:0113");

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0117", "binding site", detectionMethod);
        addParticipant( interaction, "MI:0118", "mutation", detectionMethod);

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            for (FeatureEvidence f : p.getFeatures()){
                messages.addAll(rule.check( f ));
            }
        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that no warning message appears when a feature type (mutation) is not associated with a feature detection method.
     * @throws Exception
     */
    @Test
    public void check_BindingSite_NoFeatureDetectionMethod_Warning() throws Exception {
        InteractionEvidence interaction = new DefaultInteractionEvidence();

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0117", "binding site", null );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            for (FeatureEvidence f : p.getFeatures()){
                messages.addAll(rule.check( f ));
            }
        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a feature type children (necessary binding site) is associated with a non valid feature detection method (for instance inferred by author).
     * @throws Exception
     */
    @Test
    public void check_BindingSite_Children_Warning() throws Exception {
        InteractionEvidence interaction = new DefaultInteractionEvidence();

        final CvTerm detectionMethod = CvTermUtils.createMICvTerm("inferred by author", "MI:0363");

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0429", "necessary binding site", detectionMethod );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
        for (ParticipantEvidence p : interaction.getParticipants()){
            for (FeatureEvidence f : p.getFeatures()){
                messages.addAll(rule.check( f ));
            }
        }
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( InteractionEvidence interaction,
                                 String typeMi, String typeName, CvTerm detectionMethod ) {

        final ParticipantEvidence participant = new DefaultParticipantEvidence(new DefaultProtein("test protein"));

        final CvTerm type = CvTermUtils.createMICvTerm(typeName, typeMi);

        FeatureEvidence feature = new DefaultFeatureEvidence();
        feature.setType(type);
        if (detectionMethod != null){
           feature.getDetectionMethods().add(detectionMethod);
        }

        participant.addFeature(feature);

        interaction.addParticipant( participant );
    }
}
