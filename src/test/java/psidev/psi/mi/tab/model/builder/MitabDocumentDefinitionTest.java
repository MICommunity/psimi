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
public class MitabDocumentDefinitionTest {

    private MitabDocumentDefinition documentDefinition;

    @Before
    public void before() {
        this.documentDefinition = new MitabDocumentDefinition();
    }

    @After
    public void after() {
        this.documentDefinition = null;
    }

    @Test
    public void interactionFromString1() throws Exception {
        final String mitabLine = "pubchem:23897|drugbank:DB01618|intact:DGI-137909\tuniprotkb:P14416|intact:DGI-35736\tintact:\"(+-)-molindone\"(drug brand name)|intact:Molindona [inn-spanish](drug brand name)|intact:Molindonum [inn-latin](drug brand name)|intact:Molindone(commercial name)|intact:Moban(drug brand name)\t-\t-\tuniprotkb:DRD2\tMI:0045(experimental interac)\t-\tpubmed:18048412\ttaxid:-3(unknown)\ttaxid:9606(human)\tMI:0407(direct interaction)\tpsi-mi:\"MI:1002\"\tintact:DGI-137908\t-";

        BinaryInteraction bi = documentDefinition.interactionFromString(mitabLine);

        System.out.println(bi.getInteractorA().getAlternativeIdentifiers());
        Assert.assertEquals("(+-)-molindone", bi.getInteractorA().getAlternativeIdentifiers().iterator().next().getIdentifier());
    }

}
