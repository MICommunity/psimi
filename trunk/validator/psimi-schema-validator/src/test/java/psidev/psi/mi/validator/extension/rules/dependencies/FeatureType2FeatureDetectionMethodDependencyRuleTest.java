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
 * FeatureType2FeatureDetectionMethodRule Tester.
 *
 * @author Marine Dumousseau
 */
public class FeatureType2FeatureDetectionMethodDependencyRuleTest extends AbstractRuleTest {

    /**
     *
     * @throws OntologyLoaderException
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
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        final FeatureDetectionMethod detectionMethod = new FeatureDetectionMethod();
        detectionMethod.setXref( new Xref() );
        detectionMethod.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0113", IDENTITY, IDENTITY_MI_REF ) );
        detectionMethod.setNames( new Names() );
        detectionMethod.getNames().setShortLabel( "western blot" );

        exp.setFeatureDetectionMethod( detectionMethod );
        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0117", "binding site" );
        addParticipant( interaction, "MI:0117", "binding site" );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
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
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        final FeatureDetectionMethod detectionMethod = new FeatureDetectionMethod();
        detectionMethod.setXref( new Xref() );
        detectionMethod.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0113", IDENTITY, IDENTITY_MI_REF ) );
        detectionMethod.setNames( new Names() );
        detectionMethod.getNames().setShortLabel( "western blot" );

        exp.setFeatureDetectionMethod( detectionMethod );
        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0117", "binding site" );
        addParticipant( interaction, "MI:0118", "mutation" );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that a warning message appears when a feature type (mutation) is not associated with a feature detection method.
     * @throws Exception
     */
    @Test
    public void check_BindingSite_NoFeatureDetectionMethod_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );

        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0117", "binding site" );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that a warning message appears when a feature type children (necessary binding site) is associated with a non valid feature detection method (for instance inferred by author).
     * @throws Exception
     */
    @Test
    public void check_BindingSite_Children_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        final FeatureDetectionMethod detectionMethod = new FeatureDetectionMethod();
        detectionMethod.setXref( new Xref() );
        detectionMethod.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0363", IDENTITY, IDENTITY_MI_REF ) );
        detectionMethod.setNames( new Names() );
        detectionMethod.getNames().setShortLabel( "inferred by author" );

        exp.setFeatureDetectionMethod( detectionMethod );
        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0429", "necessary binding site" );

        FeatureType2FeatureDetectionMethodDependencyRule rule =
                new FeatureType2FeatureDetectionMethodDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( Interaction interaction,
                                 String typeMi, String typeName ) {

        final Participant participant = new Participant();

        final FeatureType type = new FeatureType();
        type.setXref( new Xref() );
        type.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, typeMi, IDENTITY, IDENTITY_MI_REF ) );
        type.setNames( new Names() );
        type.getNames().setShortLabel( typeName );

        Feature feature = new Feature();
        feature.setFeatureType(type);

        participant.getFeatures().add(feature);

        interaction.getParticipants().add( participant );
    }
}
