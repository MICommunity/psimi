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
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Sample code showing how to use the PsimiXmlReader.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class PsimiXmlLightweightReaderExample {
    public static void main( String[] args ) throws PsimiXmlReaderException {
        File file = new File( "path/to/intputfile.xml" );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        for ( IndexedEntry indexedEntry : indexedEntries ) {
            Iterator<Interaction> ii = indexedEntry.unmarshallInteractionIterator();
            while ( ii.hasNext() ) {
                Interaction interaction = ii.next();

                System.out.println( interaction.getId() );
            }
        }
    }
}
