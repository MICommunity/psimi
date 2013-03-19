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

import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.mimix.ConfidenceScoreRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.ExperimentDescription;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.ValidatorException;
import junit.framework.Assert;

import java.util.Collection;

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
        final Interaction interaction = buildInteractionDeterministic();
        interaction.getAttributes().clear();
        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_ok_with_confidence() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        interaction.getAttributes().add( new Attribute( AUTHOR_CONFIDENCE_MI_REF,
                                                        AUTHOR_CONFIDENCE,
                                                        "0.6" ) );

        final ExperimentDescription exp = interaction.getExperiments().iterator().next();
        exp.getAttributes().add( new Attribute( CONFIDENCE_MAPPING_MI_REF,
                                                CONFIDENCE_MAPPING,
                                                "blah blah blah..." ) );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_ok_with_confidence_multiple_experiments() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        interaction.getAttributes().add( new Attribute( AUTHOR_CONFIDENCE_MI_REF,
                                                        AUTHOR_CONFIDENCE,
                                                        "0.6" ) );

        final ExperimentDescription exp = interaction.getExperiments().iterator().next();
        exp.getAttributes().add( new Attribute( CONFIDENCE_MAPPING_MI_REF,
                                                CONFIDENCE_MAPPING,
                                                "blah blah blah..." ) );

        // add a second experiment
        final ExperimentDescription exp2 = buildExperiment( 12345 );
        interaction.getExperiments().add( exp2);
        exp2.getAttributes().add( new Attribute( CONFIDENCE_MAPPING_MI_REF,
                                                CONFIDENCE_MAPPING,
                                                "blah blah blah 2..." ) );

        // add a third one without annotation
        final ExperimentDescription exp3 = buildExperiment( 875 );
        interaction.getExperiments().add( exp3);

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_with_multiple_experiments() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        interaction.getAttributes().add( new Attribute( AUTHOR_CONFIDENCE_MI_REF,
                                                        AUTHOR_CONFIDENCE,
                                                        "0.6" ) );

        final ExperimentDescription exp = interaction.getExperiments().iterator().next();

        // add a second experiment
        final ExperimentDescription exp2 = buildExperiment( 12345 );
        interaction.getExperiments().add( exp2);

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_fail_with_no_experiments() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        interaction.getAttributes().add( new Attribute( AUTHOR_CONFIDENCE_MI_REF,
                                                        AUTHOR_CONFIDENCE,
                                                        "0.6" ) );
        interaction.getExperiments().clear();

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_ok_with_partial_confidence() throws ValidatorException {
        // Note: it is fine to have a confidence mapping without an author confidence on the interaction.
        final Interaction interaction = buildInteractionDeterministic();

        final ExperimentDescription exp = interaction.getExperiments().iterator().next();
        exp.getAttributes().add( new Attribute( CONFIDENCE_MAPPING_MI_REF,
                                                CONFIDENCE_MAPPING,
                                                "blah blah blah..." ) );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_incomplete_confidence() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        interaction.getAttributes().add( new Attribute( AUTHOR_CONFIDENCE_MI_REF,
                                                        AUTHOR_CONFIDENCE,
                                                        "0.6" ) );

        ConfidenceScoreRule rule = new ConfidenceScoreRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
