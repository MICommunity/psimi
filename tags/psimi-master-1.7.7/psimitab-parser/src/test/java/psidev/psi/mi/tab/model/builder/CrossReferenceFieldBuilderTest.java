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

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class CrossReferenceFieldBuilderTest {

    private CrossReferenceFieldBuilder builder;

    @Before
    public void before() {
        builder = new CrossReferenceFieldBuilder();
    }

    @After
    public void after() {
        builder = null;
    }

    @Test
    public void createField_string() throws Exception {

        Assert.assertEquals(new Field("a", "b", "c"), builder.createField("a:b(c)"));
        Assert.assertEquals(new Field("a", "b", "c"), builder.createField("a:\"b\"(c)"));
        Assert.assertEquals(new Field("a(a:a)a", "b(b:b)b", "c(c:c)c"), builder.createField("\"a(a:a)a\":\"b(b:b)b\"(\"c(c:c)c\")"));
        Assert.assertEquals(new Field("a(a:a)a", "b", "c(c:c)c"), builder.createField("\"a(a:a)a\":b(\"c(c:c)c\")"));
        Assert.assertEquals(new Field("a(a:a)a", "b\"b:b\"b", "c(c:c)c"), builder.createField("\"a(a:a)a\":\"b\\\"b:b\\\"b\"(\"c(c:c)c\")"));
        Assert.assertEquals(new Field("a", "b"), builder.createField("a:b"));
        Assert.assertEquals(new Field("b"), builder.createField("b"));

        // line return removal 
        Assert.assertEquals(new Field("l a l a"), builder.createField("l\na\nl\na"));

        Assert.assertEquals(new Field("a", "(+-)b", "c"), builder.createField("a:\"(+-)b\"(c)"));
        
        Assert.assertNull(builder.createField(String.valueOf( Column.EMPTY_COLUMN )));
    }
    
    @Test
    public void fixedMI() throws Exception {
        Assert.assertEquals(new Field("psi-mi", "MI:0012", "blah"), builder.createField("MI:0012(blah)"));
    }
}
