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

import org.junit.Test;
import org.junit.Assert;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabEscapeUtilsTest {

    @Test
    public void escapeColumn() {
        Assert.assertNull(MitabEscapeUtils.escapeColumn(null));
        Assert.assertEquals("abc", MitabEscapeUtils.escapeColumn("abc"));
        Assert.assertEquals("ab c", MitabEscapeUtils.escapeColumn("ab\tc"));
    }

    @Test
    public void escapeElement() {
        Assert.assertNull(MitabEscapeUtils.escapeFieldElement(null));
        Assert.assertEquals("abc", MitabEscapeUtils.escapeFieldElement("abc"));
        Assert.assertEquals("ab c", MitabEscapeUtils.escapeFieldElement("ab\tc"));
        Assert.assertEquals("\"a:b(c)\"", MitabEscapeUtils.escapeFieldElement("a:b(c)"));
        Assert.assertEquals("\"abb\\\"b\\\"bc\"", MitabEscapeUtils.escapeFieldElement("abb\"b\"bc"));
        Assert.assertEquals("\"1) and 2)\"", MitabEscapeUtils.escapeFieldElement("1) and 2)"));
    }
}
