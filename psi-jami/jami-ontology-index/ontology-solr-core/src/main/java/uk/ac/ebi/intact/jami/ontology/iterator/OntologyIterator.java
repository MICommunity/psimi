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
package uk.ac.ebi.intact.jami.ontology.iterator;

import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Iterator;

/**
 * This class iterates through <code>OntologyTerm</code>s and is used to get terms from
 * different sources. 
 *
 */
public interface OntologyIterator extends Iterator<OntologyTerm> {

}
