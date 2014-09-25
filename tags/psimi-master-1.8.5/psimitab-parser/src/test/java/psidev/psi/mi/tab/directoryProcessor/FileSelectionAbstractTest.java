/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.directoryProcessor;

import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;

import java.io.File;

/**
 * PatternBasedFilenameSelection Tester.
 *
 * @author Magali Michaut
 * @version 1.0
 * @since <pre>01/03/2007</pre>
 */
public abstract class FileSelectionAbstractTest {

    public static final String SLASH = File.separator;
    public static final String TMP_DIR = System.getProperty( "java.io.tmpdir" );

    protected File root = new File( TMP_DIR + SLASH + "psimitab" + SLASH + "patternBasedTest" );
    protected File d1 = new File( root.getAbsolutePath() + SLASH + "dir1" );
    protected File d2 = new File( root.getAbsolutePath() + SLASH + "dir2" );
    protected File d3 = new File( root.getAbsolutePath() + SLASH + "dir3" );

    protected File f1 = new File( root.getAbsolutePath() + SLASH + "a.xml" ),
            f2 = new File( root.getAbsolutePath() + SLASH + "b.xml" ),
            f3 = new File( root.getAbsolutePath() + SLASH + "c.doc" ),
            f4 = new File( root.getAbsolutePath() + SLASH + "d.txt" ),
            f5 = new File( d1.getAbsolutePath() + SLASH + "h1.xml" ),
            f6 = new File( d1.getAbsolutePath() + SLASH + "i1.xml" ),
            f7 = new File( d1.getAbsolutePath() + SLASH + "j1.doc" ),
            f8 = new File( d1.getAbsolutePath() + SLASH + "k1.txt" ),
            f9 = new File( d2.getAbsolutePath() + SLASH + "x2.xml" ),
            f10 = new File( d2.getAbsolutePath() + SLASH + "y2.avi" );

    private void deleteStructure() {
        f10.delete();
        f9.delete();
        f8.delete();
        f7.delete();
        f6.delete();
        f5.delete();
        f4.delete();
        f3.delete();
        f2.delete();
        f1.delete();

        d3.delete();
        d2.delete();
        d1.delete();

        root.delete();
    }

    @Before
    public void setUp() throws Exception {

        // create a simple directory structure
        if ( root.exists() ) {
            System.out.println( "ERROR - test directory already exist, trying to delete it." );
            deleteStructure();
            if ( root.exists() ) {
                System.out.println( "FATAL - Could not delete it, abort." );
                fail();
            }
        }
        root.mkdirs();

        //
        //  psimitab/patternBasedTest/
        //    |
        //    |-- a.xml
        //    |-- b.xml
        //    |-- c.doc
        //    |-- e.txt
        //    |
        //    |-- dir1/
        //    |     |
        //    |     |-- h1.xml
        //    |     |-- i1.xml
        //    |     |-- j1.doc
        //    |     \-- k1.txt
        //    |
        //    |-- dir2/
        //    |     |
        //    |     |-- x2.xml
        //    |     \-- y2.avi
        //    |
        //    \-- dir2/
        //

        f1.createNewFile();
        f2.createNewFile();
        f3.createNewFile();
        f4.createNewFile();

        d1.mkdir();
        f5.createNewFile();
        f6.createNewFile();
        f7.createNewFile();
        f8.createNewFile();

        d2.mkdir();
        f9.createNewFile();
        f10.createNewFile();

        d3.mkdir();
    }

    @After
    public void tearDown() throws Exception {
        deleteStructure();
    }
}