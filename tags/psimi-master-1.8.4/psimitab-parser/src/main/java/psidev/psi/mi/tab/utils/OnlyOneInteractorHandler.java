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
import psidev.psi.mi.tab.model.Interactor;

import java.util.Collections;

/**
 * When the interaction is merged, only one of the interactors (B) is merged. Interactor A
 * is left intact.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class OnlyOneInteractorHandler extends BinaryInteractionHandler {

    @Override
    public BinaryInteraction merge(BinaryInteraction bi1, BinaryInteraction bi2) {
        BinaryInteraction bi = super.merge(bi1, bi2);

        bi.setInteractorB(newInteractor(Collections.EMPTY_LIST));

        return bi;
    }

    @Override
    protected Interactor mergeInteractorA(Interactor i1, Interactor i2) {
        if (i1 != null && i2 != null){
            return cloneInteractor(i1);
        }
        else if (i1 != null){
            return i1;
        }
        else {
            return i2;
        }
    }
}