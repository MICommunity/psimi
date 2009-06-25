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

import java.io.File;

/**
 * This example reads a PSI-MI XML file with only one <entry> element that contains data from multiple publications.
 * The class will create one entry per publication by grouping the interactions from the same publication.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class CreateEntryPerPublication {

    public static void main(String[] args) throws Exception {
        // will read this file
        File file = new File("/homes/baranda/My Downloads/imex-mpidb.xml");

        

    }
}
