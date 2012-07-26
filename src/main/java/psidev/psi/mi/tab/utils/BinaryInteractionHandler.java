/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.tab.utils;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.BinaryInteractionImpl;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.Interactor;

import java.util.List;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BinaryInteractionHandler extends AbstractBinaryInteractionHandler<BinaryInteraction> {

    public BinaryInteraction newBinaryInteraction(Interactor i1, Interactor i2) {
        return new BinaryInteractionImpl(i1, i2);
    }

    public Interactor newInteractor(List<CrossReference> identifiers) {
        return new Interactor(identifiers);
    }
}
