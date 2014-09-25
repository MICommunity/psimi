/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.xml.PsimiXmlVersion;

import java.io.*;

/**
 * PsimiXmlVersionDetector Tester.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PsimiXmlVersionDetectorTest {

    private PsimiXmlVersionDetector detector;

    @Before
    public void setUp() {
        detector = new PsimiXmlVersionDetector();
    }

    @After
    public void tearDown() {
        detector = null;
    }

    private PushbackReader buildReader( String resourcePath ) {
        InputStream is = PsimiXmlVersionDetectorTest.class.getResourceAsStream( resourcePath );
        return new PushbackReader(new InputStreamReader(is), PsimiXmlVersionDetector.BUFFER_SIZE);
    }

    @Test
    public void detectVersion253() throws Exception {
        Assert.assertEquals( PsimiXmlVersion.VERSION_253,
                             detector.detectVersion( buildReader( "/sample-xml/intact/10320477.253.xml" ) ) );
    }

    @Test
    public void detectVersion254() throws Exception {
        Assert.assertEquals( PsimiXmlVersion.VERSION_254,
                             detector.detectVersion( buildReader( "/sample-xml/intact/10320477.254.xml" ) ) );
    }

    @Test
    public void detectVersion254_2() throws Exception {
        final PushbackReader reader = buildReader( "/sample-xml/intact/10320477.254.xml" );
        Assert.assertEquals( PsimiXmlVersion.VERSION_254,
                             detector.detectVersion( reader ) );

        BufferedReader br = new BufferedReader( reader );
        final String line = br.readLine();

        Assert.assertEquals( "   <entrySet level=\"2\" minorVersion=\"4\" version=\"5\"", line );

    }

    @Test
    public void detectNamespace253() throws Exception {
        final PushbackReader reader = buildReader( "/sample-xml/intact/10320477.253.xml" );
        Assert.assertEquals( "net:sf:psidev:mi",
                            detector.detectNamespace( reader ) );

        BufferedReader br = new BufferedReader( reader );
        final String line = br.readLine();

        Assert.assertEquals( "   <entrySet level=\"2\" minorVersion=\"3\" version=\"5\"", line );

    }

    @Test
    public void detectNamespace254() throws Exception {
        final PushbackReader reader = buildReader( "/sample-xml/intact/10320477.254.xml" );
        Assert.assertEquals( "http://psi.hupo.org/mi/mif",
                            detector.detectNamespace( reader ) );

        BufferedReader br = new BufferedReader( reader );
        final String line = br.readLine();

        Assert.assertEquals( "   <entrySet level=\"2\" minorVersion=\"4\" version=\"5\"", line );

    }
}
