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
import org.hupo.psi.mitab.definition.UniprotPairDocumentDefinition;
import org.hupo.psi.mitab.model.Row;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabWriterTest {
    @Test
    public void testWrite_mitab26_to_mitab25() throws Exception {
         String mitab26Line = "uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\t" +
                "uniprotkb:TIM9\tuniprotkb:TEX4\t" +
                "MI:0006(anti bait coip)\t-\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\t" +
                "MI:0218(physical interaction)\tMI:0469(intact)\tintact:EBI-1200556\t-\t" +
                "psi-mi:\"MI:xxxx\"(spoke)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\t" +
                "\tpsi-mi:\"MI:0497\"(bait)\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0326\"(protein\tpsi-mi:\"MI:0326\"(protein)\t" +
                "interpro:IPR004046(GST_C)|interpro:IPR003081(GST_mu)|interpro:IPR004045(GST_N)|interpro:IPR012335(Thioredoxin_fold)\t" +
                "go:\"GO:0004709\"(\"F:MAP kinase kinase kinase act\")|go:\"GO:0007257\"(\"P:activation of JUNK\")\tgo:\"GO:xxxxx\"\t" +
                "caution:AnnotA\tcaution:AnnotB\tdataset:Test\ttaxid:9606(human-293t)\t\t\tkd:2\t2009/03/09\t2010/03/30\t" +
                "seguid:checksumA\tseguid:checksumB\tseguid:checksumI";

        MitabReader mitabReader = new MitabReader(new Mitab25DocumentDefinition());
        Row row = mitabReader.readLine(mitab26Line);

        Writer writer = new StringWriter();
        MitabWriter mitabWriter = new MitabWriter(new Mitab25DocumentDefinition());
        mitabWriter.write(writer, row);

        Assert.assertEquals("uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\tuniprotkb:TIMM9(gene name)\tuniprotkb:TIMM22(gene name)\tuniprotkb:TIM9\tuniprotkb:TEX4\tpsi-mi:\"MI:0006\"(anti bait coip)\t-\tpubmed:14726512\ttaxid:9606(human)\ttaxid:9606(human)\tpsi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-1200556\t-", writer.toString());

    }

    @Test
    public void testWrite_UniprotPairDocumentDefinition_toMitab25() throws Exception {
         String testLine = "\"intact:EBI-1200556\",\"Q9Y5J7\",\"Q9Y584\"";

        MitabReader mitabReader = new MitabReader(new UniprotPairDocumentDefinition());
        Row row = mitabReader.readLine(testLine);

        Writer writer = new StringWriter();
        MitabWriter mitabWriter = new MitabWriter(new Mitab25DocumentDefinition());
        mitabWriter.write(writer, row);

        Assert.assertEquals("uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\tintact:EBI-1200556\t-", writer.toString());

    }

     @Test
    public void testWrite_UniprotPairDocumentDefinition_toMitab26() throws Exception {
         String testLine = "\"intact:EBI-1200556\",\"Q9Y5J7\",\"Q9Y584\"";

        MitabReader mitabReader = new MitabReader(new UniprotPairDocumentDefinition());
        Row row = mitabReader.readLine(testLine);

        Writer writer = new StringWriter();
        MitabWriter mitabWriter = new MitabWriter(new Mitab26DocumentDefinition());
        mitabWriter.write(writer, row);

         String now = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        Assert.assertEquals("uniprotkb:Q9Y5J7\tuniprotkb:Q9Y584\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\tintact:EBI-1200556\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t-\t"+now+"\t"+now+"\t-\t-\t-\tfalse", writer.toString());
    }

    @Test
    public void testReadRows() throws Exception {
        InputStream is = MitabReaderTest.class.getResourceAsStream("/META-INF/mitab25/14726512.txt");

        MitabReader mitabReader = new MitabReader(new Mitab25DocumentDefinition());
        Collection<Row> rows = mitabReader.readRows(is);

        StringWriter stringWriter = new StringWriter();

        MitabWriter mitabWriter = new MitabWriter(new UniprotPairDocumentDefinition());
        mitabWriter.write(stringWriter, rows);

        System.out.println(stringWriter);
    }
}
