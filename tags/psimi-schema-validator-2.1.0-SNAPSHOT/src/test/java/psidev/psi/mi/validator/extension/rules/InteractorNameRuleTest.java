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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

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
    @Ignore
    public void testCheck() throws ValidatorException {
        InteractorNameRule rule = new InteractorNameRule( ontologyMaganer );

        final Interactor interactor1 = buildProtein( "P12345" );
        final Interactor interactor2 = buildProtein("P12346");

        interactor1.getNames().setShortLabel( null );
        interactor1.getNames().setFullName( null );

        interactor2.getNames().setShortLabel( "" );
        interactor2.getNames().setFullName( "" );
        
        final Collection<ValidatorMessage> messages = rule.check( interactor1 );
        messages.addAll(rule.check( interactor2 ));
        Assert.assertNotNull( messages );
        // should about 1 protein not having name
        Assert.assertEquals( 2, messages.size() );
    }
}
