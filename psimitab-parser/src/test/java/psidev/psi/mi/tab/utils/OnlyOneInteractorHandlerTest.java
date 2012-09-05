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
package psidev.psi.mi.tab.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabParserUtils;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class OnlyOneInteractorHandlerTest {

    private OnlyOneInteractorHandler handler;

    @Before
    public void before() throws Exception {
        handler = new OnlyOneInteractorHandler();
    }

    @After
    public void after() throws Exception {
        handler = null;
    }

    @Test
    public void cloneBinaryInteraction1() throws Exception {
        String line = "uniprotkb:P23367\tuniprotkb:P09184\tgene name:mutL\tgene name:vsr\tlocus name:b4170\tlocus name:b1960\tMI:0018(two hybrid)|MI:0014(adenylate cyclase)\t-\tpubmed:11585365\ttaxid:562(ecoli)\ttaxid:562(ecoli)\tMI:0218(physical interaction)\tEuropean Bioinformatics Institute:MI:0469\t-\t-\t-";
        BinaryInteraction binaryInteraction = MitabParserUtils.buildBinaryInteraction(line.split("\t"));

        BinaryInteraction clone = handler.cloneBinaryInteraction(binaryInteraction);

        Assert.assertEquals(binaryInteraction, clone);
    }

    @Test
    public void merge1() throws Exception {

        String line1 = "uniprotkb:P23367\tuniprotkb:P09184\tgene name:mutL\tgene name:vsr\tlocus name:b4170\tlocus name:b1960\tMI:0018(two hybrid)|MI:0014(adenylate cyclase)\t-\tpubmed:11585365\ttaxid:562(ecoli)\ttaxid:562(ecoli)\tMI:0218(physical interaction)\tEuropean Bioinformatics Institute:MI:0469\t-\t-\t-";
        String line2 = "uniprotkb:P23367\tuniprotkb:P23367\tgene name:mutH\tgene name:mutL\tgene name synonym:mutR|gene name synonym:prv|locus name:b2831\tlocus name:b4170\tMI:0018(two hybrid)|MI:0014(adenylate cyclase)\t-\tpubmed:11585365\ttaxid:562(ecoli)\ttaxid:562(ecoli)\tMI:0218(physical interaction)\tEuropean Bioinformatics Institute:MI:0469\t-\t-\t-";
        BinaryInteraction binaryInteraction1 = MitabParserUtils.buildBinaryInteraction(line1.split("\t"));
        BinaryInteraction binaryInteraction2 = MitabParserUtils.buildBinaryInteraction(line2.split("\t"));

        BinaryInteraction merged = handler.merge(binaryInteraction1, binaryInteraction2);

        Assert.assertNotSame(merged, binaryInteraction1);
        Assert.assertNotSame(merged, binaryInteraction2);

        Assert.assertEquals(merged.getInteractorA(), binaryInteraction1.getInteractorA());
        Assert.assertNotSame(merged.getInteractorB(), binaryInteraction1.getInteractorB());

        Assert.assertEquals(1, merged.getInteractorA().getIdentifiers().size());
        Assert.assertEquals(0, merged.getInteractorB().getIdentifiers().size());
        Assert.assertEquals(1, merged.getInteractorA().getAlternativeIdentifiers().size());
        Assert.assertEquals(0, merged.getInteractorB().getAlternativeIdentifiers().size());
        Assert.assertEquals(1, merged.getInteractorA().getAliases().size());
        Assert.assertEquals(0, merged.getInteractorB().getAliases().size());
    }


}