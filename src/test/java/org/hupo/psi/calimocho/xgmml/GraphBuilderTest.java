/**
 * Copyright 2011 The European Bioinformatics Institute, and others.
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
package org.hupo.psi.calimocho.xgmml;

import calimocho.internal.xgmml.Graph;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.key.InteractionKeys;
import org.hupo.psi.calimocho.model.CalimochoDocument;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphBuilderTest {
    
    @Mock 
    private CalimochoDocument calimochoDocument;

    private GraphBuilder graphBuilder = new GraphBuilder();

    @Test
    public void testWriteDocument() throws Exception {
        Field uniprotKbFieldA = mock(Field.class);
        Field uniprotKbFieldB = mock(Field.class);

        when(uniprotKbFieldA.get(CalimochoKeys.KEY)).thenReturn("uniprotkb");
        when(uniprotKbFieldA.get(CalimochoKeys.VALUE)).thenReturn("P12345");

        when(uniprotKbFieldB.get(CalimochoKeys.KEY)).thenReturn("uniprotkb");
        when(uniprotKbFieldB.get(CalimochoKeys.VALUE)).thenReturn("O00001");
        
        Row row = mock(Row.class);

        when(row.getFields(InteractionKeys.KEY_ID_A)).thenReturn(Arrays.asList(uniprotKbFieldA));
        when(row.getFields(InteractionKeys.KEY_ID_B)).thenReturn(Arrays.asList(uniprotKbFieldB));

        when(calimochoDocument.getRows()).thenReturn(Arrays.asList(row));

        final Graph graph = graphBuilder.createGraph(calimochoDocument);

        assertThat(graph.getNodesAndEdges().size(), is(equalTo(3)));

    }
}
