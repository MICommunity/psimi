/*
 * Copyright 2001-2008 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
* @version $Id$
*/
public class NullCrossReference implements CrossReference {

    private String identifier;

    public NullCrossReference(BinaryInteraction binaryInteraction) {
        this.identifier = binaryInteraction.getInteractorA().getIdentifiers().iterator().next()
                                +"_"+binaryInteraction.getInteractorB().getIdentifiers().iterator().next();
    }

    public String getDatabase() {
        return "unknown";
    }

    public void setDatabase(String database) {
        throw new UnsupportedOperationException();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        throw new UnsupportedOperationException();
    }

    public String getText() {
        return null;
    }

    public void setText(String text) {
        throw new UnsupportedOperationException();
    }

    public boolean hasText() {
        return false;
    }
}