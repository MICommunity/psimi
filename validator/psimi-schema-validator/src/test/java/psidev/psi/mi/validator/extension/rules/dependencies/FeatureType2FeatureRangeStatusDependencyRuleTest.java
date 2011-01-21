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
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: FeatureType2FeatureRangeStatusDependencyRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
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

        Feature feature = buildFeature( "MI:0507", "tag", "MI:0334", "c-terminal position", "MI:0334", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( feature );
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

        Feature feature = buildFeature( "MI:0507", "tag", "MI:0338", "range", "MI:0334", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( feature );
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
       
        Feature feature = buildFeature( "MI:0239", "biotin tag", "MI:0338", "range", "MI:0334", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private Feature buildFeature( String typeMi, String typeName,
                                 String startMi, String startName,
                                 String endMi, String endName) {


        final FeatureType type = new FeatureType();
        type.setXref( new Xref() );
        type.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, typeMi, IDENTITY, IDENTITY_MI_REF ) );
        type.setNames( new Names() );
        type.getNames().setShortLabel( typeName );

        Feature feature = new Feature();
        feature.setFeatureType(type);

        addFeatureRange(feature, startMi, startName, endMi, endName);

        return feature;
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
