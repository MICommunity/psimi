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

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ParseUtilsTest {

    @Test
    public void quoteAwareSplit() throws Exception {
        Assert.assertEquals(3, ParseUtils.quoteAwareSplit("a:b(c)", new char[]{':', '(', ')'}, true).length);
        
        Assert.assertEquals(2, ParseUtils.quoteAwareSplit("aaaaa|bbbb", new char[] {'|'}, true).length);
        Assert.assertEquals(2, ParseUtils.quoteAwareSplit("\"aaa|aaa\"|bbbbb", new char[] {'|'}, true).length);
        Assert.assertEquals(2, ParseUtils.quoteAwareSplit("\"a\\aa|aaa\"|bbbbb", new char[] {'|'}, true).length);
        Assert.assertEquals(2, ParseUtils.quoteAwareSplit("\"aa\\\"a|a\\\"aa\"|bbbbb", new char[] {'|'}, true).length);
        Assert.assertEquals(2, ParseUtils.quoteAwareSplit("\"a(a:a)a\":\"b(b:b)b\"(\"c(c:c)c\")"+
                                                          "|"+
                                                          "\"a(\\\"a:a)a\":\"b(\\\"b:b\\\")b\"(\"c(c:c)\\\"c\")", new char[] {'|'}, true).length);
    }

    @Test(expected = NullPointerException.class)
    public void quoteAwareSplit_no_str() throws Exception {
          ParseUtils.quoteAwareSplit( null, new char[] {':', '(', ')'}, true );
    }

    @Test(expected = IllegalArgumentException.class)
    public void quoteAwareSplit_empty_str() throws Exception {
          ParseUtils.quoteAwareSplit( "", new char[] {':', '(', ')'}, true );
    }

    @Test(expected = IllegalArgumentException.class)
    public void quoteAwareSplit_empty_delimiter() throws Exception {
          ParseUtils.quoteAwareSplit( "lala", new char[] {}, true );
    }

    @Test(expected = NullPointerException.class)
    public void quoteAwareSplit_null_delimiter() throws Exception {
          ParseUtils.quoteAwareSplit( "lala", null, true );
    }

    @Test
    public void createColumnFromString_xref() throws Exception {
        Column column = ParseUtils.createColumnFromString("lala:\"5291:333\"|drugbank:DB00619|intact:DGI-71779", new CrossReferenceFieldBuilder());

        Assert.assertEquals(3, column.getFields().size());

        Iterator<Field> iterator = column.getFields().iterator();

        Field field0 = iterator.next();
        Assert.assertEquals("lala", field0.getType());
        Assert.assertEquals("5291:333", field0.getValue());

        Field field1 = iterator.next();
        Assert.assertEquals("drugbank", field1.getType());
        Assert.assertEquals("DB00619", field1.getValue());

        Field field2 = iterator.next();
        Assert.assertEquals("intact", field2.getType());
        Assert.assertEquals("DGI-71779", field2.getValue());
    }

    @Test
    public void createColumnFromString_plainText() throws Exception {
        Column column = ParseUtils.createColumnFromString("author 1|author 2|\"author:3\"", new PlainTextFieldBuilder());

        Assert.assertEquals(3, column.getFields().size());

        Iterator<Field> iterator = column.getFields().iterator();

        Field field0 = iterator.next();
        Assert.assertEquals("author 1", field0.getValue());

        Field field1 = iterator.next();
        Assert.assertEquals("author 2", field1.getValue());

        Field field2 = iterator.next();
        Assert.assertEquals("author:3", field2.getValue());
    }
}