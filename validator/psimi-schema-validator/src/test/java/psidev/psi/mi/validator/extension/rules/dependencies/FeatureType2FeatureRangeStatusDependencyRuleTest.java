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
 * FeatureType2FeatureRangeStatusRule Tester.
 *
 * @author Marine Dumousseau
 */
public class FeatureType2FeatureRangeStatusDependencyRuleTest extends AbstractRuleTest {

    public FeatureType2FeatureRangeStatusDependencyRuleTest() throws OntologyLoaderException {
        super( FeatureType2FeatureRangeStatusDependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks that a feature type (tag) is associated with valid feature range status (for instance c-terminal)
     * @throws Exception
     */
    @Test
    public void check_Tag_ok() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0507", "tag", "MI:0334", "c-terminal position", "MI:0334", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a feature type (tag) is associated with a non valid feature range status (for instance range)
     * @throws Exception
     */
    @Test
    public void check_Tag_Warning() throws Exception {
        Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0507", "tag", "MI:0338", "range", "MI:0334", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that a warning message appears when a feature type children (biotin tag) is associated with a non valid feature range status (for instance range)
     * @throws Exception
     */
    @Test
    public void check_Tag_Children_Warning() throws Exception {
       Interaction interaction = new Interaction();
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        interaction.getExperiments().add( exp );

        // set the feature type of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "MI:0239", "biotin tag", "MI:0338", "range", "MI:0334", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private void addParticipant( Interaction interaction,
                                 String typeMi, String typeName,
                                 String startMi, String startName,
                                 String endMi, String endName) {

        final Participant participant = new Participant();

        final FeatureType type = new FeatureType();
        type.setXref( new Xref() );
        type.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, typeMi, IDENTITY, IDENTITY_MI_REF ) );
        type.setNames( new Names() );
        type.getNames().setShortLabel( typeName );

        Feature feature = new Feature();
        feature.setFeatureType(type);

        addFeatureRange(feature, startMi, startName, endMi, endName);

        participant.getFeatures().add(feature);

        interaction.getParticipants().add( participant );
    }

    private void addFeatureRange( Feature feature,
                                 String startMi, String startName, String endMi, String endName ) {

        final RangeStatus start = new RangeStatus();
        final RangeStatus end = new RangeStatus();

        start.setXref( new Xref() );
        start.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, startMi, IDENTITY, IDENTITY_MI_REF ) );
        start.setNames( new Names() );
        start.getNames().setShortLabel( startName );

        end.setXref( new Xref() );
        end.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, endMi, IDENTITY, IDENTITY_MI_REF ) );
        end.setNames( new Names() );
        end.getNames().setShortLabel( endName );

        Range range = new Range();
        range.setStartStatus(start);
        range.setEndStatus(end);

        feature.getRanges().add(range);
    }
}
