/*
 *  Copyright 2012 kbreuer.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package psidev.psi.mi.calimocho.solr.converter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author kbreuer
 */
public class ConverterSuite extends TestCase {
    
    public ConverterSuite(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("ConverterSuite");
        suite.addTest(SingleBooleanFieldConverterTest.suite());
        suite.addTest(XrefFieldConverterTest.suite());
        suite.addTest(TextFieldConverterTest.suite());
        suite.addTest(TextToBooleanFieldConverterTest.suite());
        suite.addTest(ConverterTest.suite());
        suite.addTest(AnnotationFieldConverterTest.suite());
        suite.addTest(DateFieldConverterTest.suite());
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
