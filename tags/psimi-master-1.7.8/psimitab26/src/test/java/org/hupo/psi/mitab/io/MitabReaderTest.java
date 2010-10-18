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

import org.hupo.psi.mitab.definition.Mitab25DocumentDefinition;
import org.hupo.psi.mitab.definition.Mitab26DocumentDefinition;
import org.hupo.psi.mitab.model.BinaryInteraction;
import org.hupo.psi.mitab.model.Row;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabReaderTest {

    @Test
    public void testReadRows() throws Exception {
        InputStream is = MitabReaderTest.class.getResourceAsStream("/META-INF/mitab25/14726512.txt");

        MitabReader mitabReader = new MitabReader(new Mitab25DocumentDefinition());
        Collection<Row> rows = mitabReader.readRows(is);

        Assert.assertEquals("Header should be excluded by default", 8, rows.size());
    }

    @Test
    public void testReadLine1_mitab25() throws Exception {
        String mitab26Line = "uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\t" +
                "uniprotkb:TIM9\tuniprotkb:TEX4\t" +
                "psi-mi:\"MI:0006\"(anti bait coip)\t-\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\t" +
                "psi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-1200556\t-\t" +
                "psi-mi:\"MI:xxxx\"(spoke)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\t" +
                "psi-mi:\"MI:0497\"(bait)\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0326\"(protein)\tpsi-mi:\"MI:0326\"(protein)\t" +
                "interpro:IPR004046(GST_C)\t" +
                "go:\"GO:0004709\"(\"F:MAP kinase kinase kinase act\")\tgo:\"GO:xxxxx\"\t" +
                "caution:AnnotA\tcaution:AnnotB\tdataset:Test\ttaxid:9606(human-293t)\t-\t-\tkd:2\t2009/03/09\t2010/03/30\t" +
                "seguid:checksumA\tseguid:checksumB\tseguid:checksumI\tfalse";

        MitabReader mitabReader = new MitabReader(new Mitab25DocumentDefinition());
        Row row = mitabReader.readLine(mitab26Line);

        Assert.assertEquals("15 columns in MITAB 2.5 - 2 columns without data", 13, row.getColumnKeys().size());

        FieldConverter fieldConverter = new FieldConverter(new Mitab26DocumentDefinition());

        Assert.assertEquals("uniprotkb:Q9Y5J7", fieldConverter.fieldToString(row.getFieldsByColumnKey(Mitab25DocumentDefinition.KEY_ID_A).iterator().next()));
    }

    @Test
    public void testReadLine_mitab26() throws Exception {
        String mitab26Line = "uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\t" +
                "uniprotkb:TIM9\tuniprotkb:TEX4\t" +
                "psi-mi:\"MI:0006\"(anti bait coip)\t-\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\t" +
                "psi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-1200556\t-\t" +
                "psi-mi:\"MI:xxxx\"(spoke)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\t" +
                "psi-mi:\"MI:0497\"(bait)\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0326\"(protein)\tpsi-mi:\"MI:0326\"(protein)\t" +
                "interpro:IPR004046(GST_C)\t" +
                "go:\"GO:0004709\"(\"F:MAP kinase kinase kinase act\")\tgo:\"GO:xxxxx\"\t" +
                "caution:AnnotA\tcaution:AnnotB\tdataset:Test\ttaxid:9606(human-293t)\t-\t-\tkd:2\t2009/03/09\t2010/03/30\t" +
                "seguid:checksumA\tseguid:checksumB\tseguid:checksumI\tfalse";

        MitabReader mitabReader = new MitabReader(new Mitab26DocumentDefinition());
        Row row = mitabReader.readLine(mitab26Line);

        Assert.assertEquals("39 columns in MITAB 2.5 - 5 columns without data", 34, row.getColumnKeys().size());

        FieldConverter fieldConverter = new FieldConverter(new Mitab26DocumentDefinition());

        Assert.assertEquals("uniprotkb:Q9Y5J7", fieldConverter.fieldToString(row.getFieldsByColumnKey(Mitab25DocumentDefinition.KEY_ID_A).iterator().next()));

    }

    @Test
    public void testReadBinaryInteraction_mitab26() throws Exception {
        String mitab26Line = "uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\t" +
                "uniprotkb:TIM9\tuniprotkb:TEX4\t" +
                "psi-mi:\"MI:0006\"(anti bait coip)\t-\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\t" +
                "psi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-1200556\t-\t" +
                "psi-mi:\"MI:xxxx\"(spoke)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\t" +
                "psi-mi:\"MI:0497\"(bait)\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0326\"(protein)\tpsi-mi:\"MI:0326\"(protein)\t" +
                "interpro:IPR004046(GST_C)\t" +
                "go:\"GO:0004709\"(\"F:MAP kinase kinase kinase act\")\tgo:\"GO:xxxxx\"\t" +
                "caution:AnnotA\tcaution:AnnotB\tdataset:Test\ttaxid:9606(human-293t)\t-\t-\tkd:2\t2009/03/09\t2010/03/30\t" +
                "seguid:checksumA\tseguid:checksumB\tseguid:checksumI\tfalse";

        MitabReader mitabReader = new MitabReader(new Mitab26DocumentDefinition());
        BinaryInteraction binaryInteraction = mitabReader.readBinaryInteractionLine(mitab26Line);

        Assert.assertEquals("EBI-1200556",binaryInteraction.getIdentifiers().iterator().next().getValue());
    }
}
