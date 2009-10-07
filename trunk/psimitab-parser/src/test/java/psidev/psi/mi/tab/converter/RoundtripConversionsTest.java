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
package psidev.psi.mi.tab.converter;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.PsimiTabWriter;
import psidev.psi.mi.tab.converter.tab2xml.Tab2Xml;
import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.PsimiXmlWriter;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class RoundtripConversionsTest {

    @Test
    public void rountrip1() throws Exception {
        String line = "entrez gene/locuslink:3069\tentrez gene/locuslink:11260\tentrez gene/locuslink:HDLBP\tentrez gene/locuslink:XPOT\t" +
                "entrez gene/locuslink:FLJ16432|entrez gene/locuslink:HBP|entrez gene/locuslink:PRO2900|entrez gene/locuslink:VGL\tentrez gene/locuslink:XPO3\t" +
                "psi-mi:\"MI:0401\"(biochemical)\tKruse C (2000)\tpubmed:10657246\ttaxid:9606\ttaxid:9606\tpsi-mi:\"MI:0914\"(association)\t" +
                "psi-mi:\"MI:0463\"(GRID)\tsomedb:id1234\tlpr:12|hpr:89|np:3";

        assertCorrectRountrip(line);
    }

    private void assertCorrectRountrip(String line) throws Exception {
        final PsimiTabReader mitabReader = new PsimiTabReader(false);
        final BinaryInteraction binaryInteraction = mitabReader.readLine(line);

        final Tab2Xml tab2Xml = new Tab2Xml();
        final EntrySet entrySet = tab2Xml.convert(Arrays.asList(binaryInteraction));

        final Xml2Tab xml2Tab = new Xml2Tab();
        final Collection<BinaryInteraction> binaryInteractions = xml2Tab.convert(entrySet);

        Assert.assertEquals(1, binaryInteractions.size());

        final BinaryInteraction convertedBinaryInteraction = binaryInteractions.iterator().next();

        final PsimiTabWriter mitabWriter = new PsimiTabWriter(false);
        final Writer outputWriter = new StringWriter();
        mitabWriter.write(convertedBinaryInteraction, outputWriter);

        System.out.println(line);
        System.out.println(outputWriter.toString());

        System.out.println("\n"+new PsimiXmlWriter().getAsString(entrySet));

        Assert.assertEquals(line, outputWriter.toString());

    }
}
