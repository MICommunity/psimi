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
package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * InteractionDetectionMethod2ParticipantRolesDependencyRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ParticipantRolesDependencyRuleTest.java 2 2009-06-01 15:46:10Z brunoaranda $
 * @since 2.0
 */
public class InteractionDetectionMethod2ParticipantRolesDependencyRuleTest extends AbstractRuleTest {

    public InteractionDetectionMethod2ParticipantRolesDependencyRuleTest() throws OntologyLoaderException {
        super( InteractionDetectionMethod2ParticipantRolesDependencyRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void check_two_hybrid_ok() throws Exception {
        final Interaction interaction = buildInteractionDeterministic();

        // Set the interaction detection method
        setDetectionMethod( interaction, TWO_HYBRID_MI_REF, "yeast two hybrid" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "P12345",
                        UNSPECIFIED_MI_REF, "unspecified",
                        BAIT_MI_REF, "bait" );
        addParticipant( interaction, "Q98765",
                        UNSPECIFIED_MI_REF, "unspecified",
                        PREY_MI_REF, "prey" );

        InteractionDetectionMethod2ParticipantRolesDependencyRule rule =
                new InteractionDetectionMethod2ParticipantRolesDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_two_hybrid2_ok() throws Exception {
        final Interaction interaction = buildInteractionDeterministic();

        // Set the interaction detection method
        setDetectionMethod( interaction, TWO_HYBRID_MI_REF, "yeast two hybrid" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "P12345",
                        UNSPECIFIED_MI_REF, "unspecified",
                        BAIT_MI_REF, "bait" );
        addParticipant( interaction, "Q98765",
                        UNSPECIFIED_MI_REF, "unspecified",
                        PREY_MI_REF, "prey" );
        addParticipant( interaction, "Q66666",
                        UNSPECIFIED_MI_REF, "unspecified",
                        ANCILARY_MI_REF, "ancilary" );

        InteractionDetectionMethod2ParticipantRolesDependencyRule rule =
                new InteractionDetectionMethod2ParticipantRolesDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_two_hybrid3_error() throws Exception {
        final Interaction interaction = buildInteractionDeterministic();

        // Set the interaction detection method
        setDetectionMethod( interaction, TWO_HYBRID_MI_REF, "yeast two hybrid" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "P12345",
                        UNSPECIFIED_MI_REF, "unspecified",
                        BAIT_MI_REF, "bait" );
        addParticipant( interaction, "Q98765",
                        UNSPECIFIED_MI_REF, "unspecified",
                        PREY_MI_REF, "prey" );
        addParticipant( interaction, "Q66666",
                        ENZYME_MI_REF, "enzyme",
                        ANCILARY_MI_REF, "ancilary" );

        InteractionDetectionMethod2ParticipantRolesDependencyRule rule =
                new InteractionDetectionMethod2ParticipantRolesDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_two_hybrid_array_error() throws Exception {

        // note: the two hybrid method defines bait/prey/ancilary as valid experimental role
        //       the two hybrid array method is a child term of the one above and do not allow ancilary

        final Interaction interaction = buildInteractionDeterministic();

        // Set the interaction detection method
        setDetectionMethod( interaction, TWO_HYBRID_ARRAY_MI_REF, "yeast two hybrid" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "P12345",
                        UNSPECIFIED_MI_REF, "unspecified",
                        BAIT_MI_REF, "bait" );
        addParticipant( interaction, "Q98765",
                        UNSPECIFIED_MI_REF, "unspecified",
                        PREY_MI_REF, "prey" );
        addParticipant( interaction, "Q66666",
                        UNSPECIFIED_MI_REF, "unspecified",
                        ANCILARY_MI_REF, "ancilary" );

        InteractionDetectionMethod2ParticipantRolesDependencyRule rule =
                new InteractionDetectionMethod2ParticipantRolesDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_two_hybrid_array_ok() throws Exception {

        // note: the two hybrid method defines bait/prey/ancilary as valid experimental role
        //       the two hybrid array method is a child term of the one above and do not allow ancilary

        final Interaction interaction = buildInteractionDeterministic();

        // Set the interaction detection method
        setDetectionMethod( interaction, TWO_HYBRID_ARRAY_MI_REF, "yeast two hybrid" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, "P12345",
                        UNSPECIFIED_MI_REF, "unspecified",
                        BAIT_MI_REF, "bait" );
        addParticipant( interaction, "Q98765",
                        UNSPECIFIED_MI_REF, "unspecified",
                        PREY_MI_REF, "prey" );

        InteractionDetectionMethod2ParticipantRolesDependencyRule rule =
                new InteractionDetectionMethod2ParticipantRolesDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertEquals( 0, messages.size() );
    }

    private void addParticipant( Interaction interaction,
                                 String proteinId,
                                 String bioRoleMi, String bioRoleName,
                                 String expRoleMi, String expRoleName ) {

        final Participant participant = new Participant();
        participant.setInteractor( buildProtein( proteinId ));
        participant.getExperimentalRoles().clear();
        participant.getExperimentalRoles().add( buildExperimentalRole( expRoleMi, expRoleName ));
        participant.setBiologicalRole( buildBiologicalRole( bioRoleMi, bioRoleName ));
        interaction.getParticipants().add( participant );
    }
}
