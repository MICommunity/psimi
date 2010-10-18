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
package psidev.psi.mi.tab.model.builder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabInteractionRowConverterTest {

    private MitabInteractionRowConverter rowConverter;

    @Before
    public void before() {
        rowConverter = new MitabInteractionRowConverter();
    }

    @After
    public void after() {
        rowConverter = null;
    }

    @Test
    public void createRow1() {
        String line =
            "uniprotkb:P23367\tuniprotkb:P06722\tinterpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913\tinterpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:GO:0005515|intact:EBI-545170\tgene name:mutL|locus name:b4170\tgene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831\tadenylate cyclase:MI:0014\t-\tpubmed:11585365\ttaxid:562\ttaxid:562\tphysical interaction:MI:0218\t-\t-\t-";

        DocumentDefinition docDef = new MitabDocumentDefinition();
        final BinaryInteraction binaryInteraction = docDef.interactionFromString(line);

        Row row = rowConverter.createRow(binaryInteraction);
        Assert.assertEquals(15, row.getColumnCount());
    }
}
