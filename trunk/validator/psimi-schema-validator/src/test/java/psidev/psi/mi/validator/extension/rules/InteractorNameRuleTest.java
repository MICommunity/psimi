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
package psidev.psi.mi.validator.extension.rules;

import org.junit.Test;
import org.junit.Assert;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.ValidatorException;

import java.util.Collection;
import java.util.Iterator;

/**
 * InteractorNameRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class InteractorNameRuleTest extends AbstractRuleTest {

    public InteractorNameRuleTest() {
        super();
    }

    @Test
    public void testCheck() throws ValidatorException {
        InteractorNameRule rule = new InteractorNameRule( ontologyMaganer );
        final Interaction interaction = buildInteractionDeterministic();
        final Iterator<Participant> participantIterator = interaction.getParticipants().iterator();
        final Interactor interactor1 = participantIterator.next().getInteractor();
        final Interactor interactor2 = participantIterator.next().getInteractor();

        interactor1.getNames().setShortLabel( null );
        interactor1.getNames().setFullName( null );

        interactor2.getNames().setShortLabel( "" );
        interactor2.getNames().setFullName( "" );
        
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        // should about 1 protein not having name
        Assert.assertEquals( 2, messages.size() );
    }
}
