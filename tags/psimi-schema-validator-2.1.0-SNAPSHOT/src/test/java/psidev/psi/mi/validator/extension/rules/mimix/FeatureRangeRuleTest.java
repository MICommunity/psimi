package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.psimi.FeatureRangeRule;
import psidev.psi.mi.xml.model.Feature;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.mi.xml.model.Range;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * FeatureRangeRule Tester
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27-Aug-2010</pre>
 */

public class FeatureRangeRuleTest extends AbstractRuleTest {

    public FeatureRangeRuleTest() {
        super();
    }

    @Test
    public void validCertainRange() throws ValidatorException {

        String sequence = "AACPCGGAM";

        Participant p1 = buildParticipantDeterministic();
        p1.getInteractor().setSequence(sequence);

        Feature feature = buildCertainFeature( 1, 4);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( p1 );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void invalid_OutOfSequence_Range_1() throws ValidatorException {

        String sequence = "AACPCGGAM";

        Participant p1 = buildParticipantDeterministic();
        p1.getInteractor().setSequence(sequence);

        Feature feature = buildCertainFeature( 11, 15);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( p1 );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 2, messages.size() );
    }

    @Test
    public void invalid_OutOfSequence_Range_2() throws ValidatorException {

        String sequence = "AACPCGGAM";

        Participant p1 = buildParticipantDeterministic();
        p1.getInteractor().setSequence(sequence);

        Feature feature = buildCertainFeature( 4, 15);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( p1 );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void invalid_OutOfSequence_Range_3() throws ValidatorException {

        String sequence = "AACPCGGAM";

        Participant p1 = buildParticipantDeterministic();
        p1.getInteractor().setSequence(sequence);

        Feature feature = buildCertainFeature( 0, 4);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( p1 );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_Undetermined_Range() throws ValidatorException {

        String sequence = "AACPCGGAM";

        Participant p1 = buildParticipantDeterministic();
        p1.getInteractor().setSequence(sequence);

        Feature feature = buildUndeterminedFeature();
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( p1 );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void invvalid_Undetermined_Range() throws ValidatorException {

        String sequence = "AACPCGGAM";

        Participant p1 = buildParticipantDeterministic();
        p1.getInteractor().setSequence(sequence);

        Feature feature = buildUndeterminedFeature();
        p1.getFeatures().add(feature);

        Range range = feature.getFeatureRanges().iterator().next();
        range.setBegin(new psidev.psi.mi.xml.model.Position(0));
        range.setEnd(new psidev.psi.mi.xml.model.Position(3));

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( p1 );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
