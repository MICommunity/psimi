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
package psidev.psi.mi.tab.model;

import org.junit.Test;
import org.junit.Assert;
import psidev.psi.mi.tab.mock.PsimiTabMockBuilder;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BinaryInteractionImplTest {

    @Test
    public void testFlippable() throws Exception {
        PsimiTabMockBuilder mockBuilder = new PsimiTabMockBuilder();

        BinaryInteraction interaction = mockBuilder.createInteractionRandom();

        Interactor interactorA = interaction.getInteractorA();
        Interactor interactorB = interaction.getInteractorB();

        interaction.flip();

        Assert.assertNotSame(interactorA, interactorB);
        Assert.assertNotSame(interaction.getInteractorA(), interaction.getInteractorB());
        Assert.assertEquals(interactorA, interaction.getInteractorB());
        Assert.assertEquals(interactorB, interaction.getInteractorA());
    }

}
