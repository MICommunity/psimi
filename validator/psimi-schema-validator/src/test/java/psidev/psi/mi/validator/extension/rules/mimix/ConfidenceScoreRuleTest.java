/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.validator.extension.rules.mimix;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * ConfidenceScoreRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class ConfidenceScoreRuleTest extends AbstractRuleTest {

    public ConfidenceScoreRuleTest() {
        super();
    }

    @Test
    public void check_ok_without_confidence() throws ValidatorException {
        final InteractionEvidence interaction = buildInteractionDeterministic();
        interaction.getAnnotations().clear();
        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_ok_with_confidence() throws ValidatorException {
        final InteractionEvidence interaction = buildInteractionDeterministic();

        interaction.getAnnotations().add( AnnotationUtils.createAnnotation( AUTHOR_CONFIDENCE,
                                                        AUTHOR_CONFIDENCE_MI_REF,
                                                        "0.6" ) );

        final Experiment exp = interaction.getExperiment();
        exp.getAnnotations().add(AnnotationUtils.createAnnotation( CONFIDENCE_MAPPING,
                                                CONFIDENCE_MAPPING_MI_REF,
                                                "blah blah blah..." ) );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_ok_with_confidence_multiple_experiments() throws ValidatorException {
        final InteractionEvidence interaction = buildInteractionDeterministic();

        interaction.getAnnotations().add( AnnotationUtils.createAnnotation( AUTHOR_CONFIDENCE,
                                                        AUTHOR_CONFIDENCE_MI_REF,
                                                        "0.6" ) );

        // add a second experiment
        final Experiment exp2 = buildExperiment( 12345 );
        interaction.setExperimentAndAddInteractionEvidence( exp2);
        exp2.getAnnotations().add( AnnotationUtils.createAnnotation( CONFIDENCE_MAPPING,
                                                CONFIDENCE_MAPPING_MI_REF,
                                                "blah blah blah 2..." ) );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_with_multiple_experiments() throws ValidatorException {
        final InteractionEvidence interaction = buildInteractionDeterministic();

        interaction.getAnnotations().add( AnnotationUtils.createAnnotation( AUTHOR_CONFIDENCE,
                                                        AUTHOR_CONFIDENCE_MI_REF,
                                                        "0.6" ) );

        final Experiment exp = interaction.getExperiment();

        // add a second experiment
        final Experiment exp2 = buildExperiment( 12345 );
        interaction.setExperimentAndAddInteractionEvidence( exp2);

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_ok_with_partial_confidence() throws ValidatorException {
        // Note: it is fine to have a confidence mapping without an author confidence on the interaction.
        final InteractionEvidence interaction = buildInteractionDeterministic();

        final Experiment exp = interaction.getExperiment();
        exp.getAnnotations().add( AnnotationUtils.createAnnotation( CONFIDENCE_MAPPING,
                                                CONFIDENCE_MAPPING_MI_REF,
                                                "blah blah blah..." ) );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_incomplete_confidence() throws ValidatorException {
        final InteractionEvidence interaction = buildInteractionDeterministic();

        interaction.getAnnotations().add( AnnotationUtils.createAnnotation(AUTHOR_CONFIDENCE,
                AUTHOR_CONFIDENCE_MI_REF,
                "0.6") );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
