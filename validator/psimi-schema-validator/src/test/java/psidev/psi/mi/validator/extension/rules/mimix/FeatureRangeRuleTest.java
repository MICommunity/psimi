package psidev.psi.mi.validator.extension.rules.mimix;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.psimi.FeatureRangeRule;
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

        ParticipantEvidence p1 = buildParticipantDeterministic();
        ((Polymer)p1.getInteractor()).setSequence(sequence);

        FeatureEvidence feature = buildCertainFeature( 1, 4);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void invalid_OutOfSequence_Range_1() throws ValidatorException {

        String sequence = "AACPCGGAM";

        ParticipantEvidence p1 = buildParticipantDeterministic();
        ((Polymer)p1.getInteractor()).setSequence(sequence);

        FeatureEvidence feature = buildCertainFeature( 11, 15);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 2, messages.size() );
    }

    @Test
    public void invalid_OutOfSequence_Range_2() throws ValidatorException {

        String sequence = "AACPCGGAM";

        ParticipantEvidence p1 = buildParticipantDeterministic();
        ((Polymer)p1.getInteractor()).setSequence(sequence);

        FeatureEvidence feature = buildCertainFeature( 4, 15);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void invalid_OutOfSequence_Range_3() throws ValidatorException {

        String sequence = "AACPCGGAM";

        ParticipantEvidence p1 = buildParticipantDeterministic();
        ((Polymer)p1.getInteractor()).setSequence(sequence);

        FeatureEvidence feature = buildCertainFeature( 0, 4);
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void valid_Undetermined_Range() throws ValidatorException, IllegalRangeException {

        String sequence = "AACPCGGAM";

        ParticipantEvidence p1 = buildParticipantDeterministic();
        ((Polymer)p1.getInteractor()).setSequence(sequence);

        FeatureEvidence feature = buildUndeterminedFeature();
        p1.getFeatures().add(feature);

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void invvalid_Undetermined_Range() throws ValidatorException, IllegalRangeException {

        String sequence = "AACPCGGAM";

        ParticipantEvidence p1 = buildParticipantDeterministic();
        ((Polymer)p1.getInteractor()).setSequence(sequence);

        FeatureEvidence feature = buildUndeterminedFeature();
        p1.getFeatures().add(feature);

        Range range = feature.getRanges().iterator().next();
        range.setPositions(new DefaultPosition(CvTermUtils.getCertain(), 0), new DefaultPosition(3));

        FeatureRangeRule rule = new FeatureRangeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull( messages );
        System.out.println( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
