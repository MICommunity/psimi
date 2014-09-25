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
package org.hupo.psi.mi.example.xml;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlVersion;
import psidev.psi.mi.xml.PsimiXmlWriter;
import psidev.psi.mi.xml.model.*;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * This example reads a PSI-MI XML file with only one <entry> element that contains data from multiple publications.
 * The class will create one entry per publication by grouping the interactions from the same publication.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class CreateEntryPerPublication {

    public static void main(String[] args) throws Exception {
        // will read this inputFile
        final PsimiXmlVersion xmlVersion = PsimiXmlVersion.VERSION_254;
        final File inputFile = new File("d:/Downloads/imex-mpidb.xml");
        final File outputFile = new File("d:/Downloads/lala.xml");

        // action!

        // We will use a multimap (from the google collections library) to store
        // the interactions grouped by publication id
        Multimap<String, Interaction> publicationMap = HashMultimap.create();

        // Read the file
        PsimiXmlReader reader = new PsimiXmlReader(xmlVersion);
        EntrySet entrySet = reader.read(inputFile);

        // Iterate through the entries
        for (Entry entry : entrySet.getEntries()) {
            for (Interaction interaction : entry.getInteractions()) {
                String publicationId = findPublicationId(interaction);
                publicationMap.put(publicationId, interaction);
            }
        }

        // now create an Entry per interaction
        EntrySet newEntrySet = new EntrySet(xmlVersion);

        // get first source from the original inputFile
        Source source = entrySet.getEntries().iterator().next().getSource();

        // iterating through the multimap, we get the grouped interactions
        for (Map.Entry<String, Collection<Interaction>> pubInteractions : publicationMap.asMap().entrySet()) {
            Entry entry = new Entry(pubInteractions.getValue());
            entry.setSource(source);

            newEntrySet.getEntries().add(entry);
        }

        // write the output file
        PsimiXmlWriter psimiXmlWriter = new PsimiXmlWriter(xmlVersion);
        psimiXmlWriter.write(newEntrySet, outputFile);
    }

    /**
     * This method assumes that the publication id is the primary bib reference of the experiment
     * @param interaction
     * @return
     */
    private static String findPublicationId(Interaction interaction) {
        ExperimentDescription expDesc = interaction.getExperiments().iterator().next();

        return expDesc.getBibref().getXref().getPrimaryRef().getId();
    }
}
