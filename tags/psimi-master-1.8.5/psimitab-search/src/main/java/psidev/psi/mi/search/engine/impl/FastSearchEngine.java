/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
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
 *  limitations under the License.
 */
package psidev.psi.mi.search.engine.impl;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

import java.io.File;
import java.io.IOException;

/**
 * A Search Engine based on lucene
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 *
 * @deprecated use BinaryInteractionSearchEngine instead.
 */
@Deprecated
public class FastSearchEngine extends BinaryInteractionSearchEngine
{

    public FastSearchEngine(Directory indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public FastSearchEngine(String indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public FastSearchEngine(File indexDirectory) throws IOException {
        super(indexDirectory);
    }

    public FastSearchEngine(Directory indexDirectory, IndexWriter indexWriter) throws IOException {
        super(indexDirectory, indexWriter);
    }

    public FastSearchEngine(String indexDirectory, IndexWriter indexWriter) throws IOException {
        super(indexDirectory, indexWriter);
    }

    public FastSearchEngine(File indexDirectory, IndexWriter indexWriter) throws IOException {
        super(indexDirectory, indexWriter);
    }
}
