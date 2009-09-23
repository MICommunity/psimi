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
package psidev.psi.mi.tab.utils;

import psidev.psi.mi.tab.model.Flippable;

import java.util.Collection;

/**
 * Utilities for BinaryInteractions.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public final class BinaryInteractionUtils {

    private BinaryInteractionUtils() {}

    public static void flip(Collection<? extends Flippable> flippables,
                                                  Flipper flipper) {
        for (Flippable flippable : flippables) {
            flipper.flip(flippable);
        }
    }

}
