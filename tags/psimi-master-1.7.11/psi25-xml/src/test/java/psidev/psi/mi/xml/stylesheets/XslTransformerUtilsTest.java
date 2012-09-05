/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.xml.stylesheets;

import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * XslTransformerUtils Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since TODO artifact version
 */
public class XslTransformerUtilsTest {

    //////////////////////////
    // Initialisation

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    ////////////////////
    // Tests

    @Test
    public void expandPsiMi10() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/stylesheets/psi10/10029528.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10029528.expand10.xml" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.expandPsiMi10( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void compactPsiMi10() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/stylesheets/psi10/10029528.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10029528.compact10.xml" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.compactPsiMi10( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void viewPsiMi10() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/stylesheets/psi10/10029528.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10029528.v10.html" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.viewPsiMi10( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void expandPsiMi25() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/stylesheets/psi25/10029528.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10029528.expand25.xml" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.expandPsiMi25( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void compactPsiMi25() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/stylesheets/psi25/10029528.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10029528.compact25.xml" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.compactPsiMi25( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void viewPsiMi25() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/stylesheets/psi25/10029528.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10029528.v25.html" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.viewPsiMi25( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void viewPsiMi254() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/sample-xml/intact/10320477.254.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10320477.254.html" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.viewPsiMi25( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void jsonPsiMi254() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/sample-xml/intact/10320477.254.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10320477.254.json" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.jsonPsiMi( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }

    @Test
    public void jsonPsiMi253() throws XslTransformException, IOException {
        File input = new File( XslTransformerUtilsTest.class.getResource( "/sample-xml/intact/10320477.253.xml" ).getFile() );
        assertTrue( input.exists() );

        File output = new File( input.getParentFile(), "10320477.254.json" );
        if ( output.exists() ) {
            output.delete();
        }
        assertFalse( output.exists() );

        XslTransformerUtils.jsonPsiMi( input, output );

        assertTrue( input.exists() );
        assertTrue( output.exists() );
        System.out.println( "Resulting file: " + output.getAbsolutePath() );
    }
}
