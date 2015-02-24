package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.model.impl.DefaultRange;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

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

        FeatureEvidence feature = buildFeature( "MI:0507", "tag", "MI:1039", "c-terminal position", "MI:1039", "c-terminal position" );

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

        FeatureEvidence feature = buildFeature( "MI:0507", "tag", "MI:0338", "range", "MI:1039", "c-terminal position" );

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

        FeatureEvidence feature = buildFeature( "MI:0239", "biotin tag", "MI:0338", "range", "MI:1039", "c-terminal position" );

        FeatureType2FeatureRangeDependencyRule rule =
                new FeatureType2FeatureRangeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    private FeatureEvidence buildFeature( String typeMi, String typeName,
                                 String startMi, String startName,
                                 String endMi, String endName) {


        final CvTerm type = CvTermUtils.createMICvTerm(typeName, typeMi);

        FeatureEvidence feature = new DefaultFeatureEvidence();
        feature.setType(type);

        addFeatureRange(feature, startMi, startName, endMi, endName);

        return feature;
    }

    private void addFeatureRange( FeatureEvidence feature,
                                 String startMi, String startName, String endMi, String endName ) {

        final CvTerm start = CvTermUtils.createMICvTerm(startName, startMi);
        final CvTerm end = CvTermUtils.createMICvTerm(endName, endMi);

        Range range = new DefaultRange(new DefaultPosition(start, (long)1), new DefaultPosition(end, (long)1));

        feature.getRanges().add(range);
    }
}
