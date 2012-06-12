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
import psidev.psi.mi.tab.model.builder.ParseUtils;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ParseUtilsTest {

    @Test
    public void quoteAwareSplit1() throws Exception {
        String str = "a:b(c)";
        final String[] splitted = ParseUtils.quoteAwareSplit(str, new char[]{':', '(', ')'}, true);
        Assert.assertEquals("a", splitted[0]);
        Assert.assertEquals("b", splitted[1]);
        Assert.assertEquals("c", splitted[2]);
    }

    @Test
    public void quoteAwareSplit2() throws Exception {
        String str = "a:\"(+)b\"(c)";
        final String[] splitted = ParseUtils.quoteAwareSplit(str, new char[]{':', '(', ')'}, true);
        Assert.assertEquals("a", splitted[0]);
        Assert.assertEquals("(+)b", splitted[1]);
        Assert.assertEquals("c", splitted[2]);
    }

}
