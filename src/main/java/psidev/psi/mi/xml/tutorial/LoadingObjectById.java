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
package psidev.psi.mi.xml.tutorial;

import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.io.File;
import java.util.List;

/**
 * This sample shows how to load data by id without having to load the whole file in memory.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class LoadingObjectById {
    public static void main( String[] args ) throws PsimiXmlReaderException {
        File file = new File( "path/to/intputfile.xml" );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        for ( IndexedEntry indexedEntry : indexedEntries ) {

            Interaction interaction = indexedEntry.unmarshallInteractionById( 43 );
            ExperimentDescription experiment = indexedEntry.unmarshallExperimentById( 21 );
            Interactor interactor = indexedEntry.unmarshallInteractorById( 38 );

            // do something with the data ...
        }
    }
}
