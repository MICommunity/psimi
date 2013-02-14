/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.io.impl;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05-Jul-2006</pre>
 */
public class RoundtripTest extends XMLTestCase {

    /////////////////////
    // Private methods

    private void checkIdentical( File file1, File file2 ) throws Exception {

        Reader reader1 = new FileReader( file1 );
        Reader reader2 = new FileReader( file2 );

        Diff myDiff = new Diff( reader1, reader2 );

        assertTrue( "pieces of XML are similar " + myDiff, myDiff.similar() );

        assertFalse( "but are they identical? " + myDiff, myDiff.identical() );
    }

    private void checkAllDifferences( File file1, File file2 ) throws Exception {

        Reader reader1 = new FileReader( file1 );
        Reader reader2 = new FileReader( file2 );

        DetailedDiff myDiff = new DetailedDiff( compareXML( reader1, reader2 ) );
        List allDifferences = myDiff.getAllDifferences();

        // display detailed differences
        for ( Iterator iterator = allDifferences.iterator(); iterator.hasNext(); ) {
            Object d = iterator.next();
            System.out.println( "d = " + d );
        }

        assertEquals( myDiff.toString(), 3, allDifferences.size() );
    }

    ///////////////
    // Tests

    public void testIdentical() throws Exception {
//        checkIdentical( new File( "C:\\cygwin\\home\\Samuel\\projects\\psi25\\samples\\10393239.xml" ),
//                        new File( "C:\\cygwin\\home\\Samuel\\projects\\psi25\\samples\\10393239.out.xml" ) );
    }

    public void testAllDIfferences() throws Exception {
//        checkAllDifferences( new File( "C:\\cygwin\\home\\Samuel\\projects\\psi25\\samples\\10393239.xml" ),
//                             new File( "C:\\cygwin\\home\\Samuel\\projects\\psi25\\samples\\10393239.out.xml" ) );
    }
}