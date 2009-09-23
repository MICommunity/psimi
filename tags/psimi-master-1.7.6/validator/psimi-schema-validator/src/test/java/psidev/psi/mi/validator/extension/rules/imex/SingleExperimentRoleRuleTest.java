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
package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.UNIPROTKB_MI_REF;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.ValidatorException;
import org.junit.Test;

import java.util.Collection;

import junit.framework.Assert;

/**
 * SingleExperimentRoleRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class SingleExperimentRoleRuleTest extends AbstractRuleTest {

    public SingleExperimentRoleRuleTest() {
        super();
    }

    @Test
    public void check_ok() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();
        for ( Participant p : interaction.getParticipants() ) {
            Assert.assertTrue( p.getExperimentalRoles().size() == 1 );
        }
        SingleExperimentRoleRule rule = new SingleExperimentRoleRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_0_role() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();
        for ( Participant p : interaction.getParticipants() ) {
            Assert.assertTrue( p.getExperimentalRoles().size() == 1 );
        }
        interaction.getParticipants().iterator().next().getExperimentalRoles().clear();
        SingleExperimentRoleRule rule = new SingleExperimentRoleRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_fail_many_roles() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();
        for ( Participant p : interaction.getParticipants() ) {
            Assert.assertTrue( p.getExperimentalRoles().size() == 1 );
        }

        // add an extra role
        final Collection<ExperimentalRole> roles = interaction.getParticipants().iterator().next().getExperimentalRoles();
        final Names names = new Names();
        names.setShortLabel( "extra role" );
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "MI:xxxx", "database" ) );
        roles.add( new ExperimentalRole( names, xref ) );

        interaction.getParticipants().iterator().next().getExperimentalRoles().clear();
        SingleExperimentRoleRule rule = new SingleExperimentRoleRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
