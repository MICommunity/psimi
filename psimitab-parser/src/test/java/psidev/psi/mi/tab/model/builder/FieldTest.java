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

/**
 * MitabField Tester.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class FieldTest {

    @Test
    public void fieldToString() throws Exception {
        Field field1 = new Field( "a", "b", "c" );
        Assert.assertEquals( "a:b(c)", field1.toString() );

        Field field2 = new Field( null, "b", "c" );
        Assert.assertEquals( "b(c)", field2.toString() );

        Field field3 = new Field( "b" );
        Assert.assertEquals( "b", field3.toString() );
    }

    @Test
    public void fieldWithSpecialCharsToString() throws Exception {
        Field field1 = new Field( "a:a", "b|b", "c(lala)" );
        Assert.assertEquals( "\"a:a\":\"b|b\"(\"c(lala)\")", field1.toString());
    }
}
