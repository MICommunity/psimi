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
package psidev.psi.mi.tab.converter;

/**
 * Thread safe implementation to generate identifiers for XML elements.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class IdentifierGenerator {

    private int currentId = 1;

    private static ThreadLocal<IdentifierGenerator> instance = new ThreadLocal<IdentifierGenerator>() {
        @Override
        protected IdentifierGenerator initialValue() {
            return new IdentifierGenerator();
        }
    };

    public static IdentifierGenerator getInstance() {
        return instance.get();
    }

    public int nextId() {
        return currentId++;
    }

    public int currentId() {
        return currentId;
    }

    public void reset() {
        currentId = 0;
    }
}
