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
package psidev.psi.mi.validator;

import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Stores syntactic and semantic messages for a run of validation.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class ValidatorReport {

    private int interactionCount;

    private Collection<ValidatorMessage> syntaxMessages;

    private Collection<ValidatorMessage> semanticMessages;

    //////////////////
    // Constructors

    public ValidatorReport() {
        syntaxMessages = new ArrayList<ValidatorMessage>();
        semanticMessages = new ArrayList<ValidatorMessage>();
    }

    ///////////////////////////
    // Getters and Setters

    public int getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }

    public Collection<ValidatorMessage> getSyntaxMessages() {
        return syntaxMessages;
    }

    public void setSyntaxMessages(Collection<ValidatorMessage> syntaxMessages) {
        this.syntaxMessages = syntaxMessages;
    }

    public boolean hasSyntaxMessages() {
        return ! syntaxMessages.isEmpty();
    }

    public Collection<ValidatorMessage> getSemanticMessages() {
        return semanticMessages;
    }

    public void setSemanticMessages(Collection<ValidatorMessage> semanticMessages) {
        this.semanticMessages = semanticMessages;
    }

    public boolean hasSemanticMessages() {
        return ! semanticMessages.isEmpty();
    }

    public void clear(){
        this.getSyntaxMessages().clear();
        this.getSemanticMessages().clear();
        this.setInteractionCount(0);
    }
}
