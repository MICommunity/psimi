/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
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
package org.hupo.psi.mitab.io;

import org.hupo.psi.mitab.definition.IntactDocumentDefinition;
import org.hupo.psi.mitab.definition.Mitab26DocumentDefinition;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabConverterTest {

    @Test
    public void testConvert_intactExtended_to_mitab26() throws Exception {
        InputStream is = MitabReaderTest.class.getResourceAsStream("/META-INF/mitab25/P51578.intact_extended.txt");
        
        MitabConverter converter = new MitabConverter(new IntactDocumentDefinition(), new Mitab26DocumentDefinition(), true);
        converter.convert(is, System.out);

    }
}
