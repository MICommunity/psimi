/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * Xref Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class XrefTest {

    public Xref buildXref() {
        Xref xref = new Xref();

        assertNotNull( xref );
        assertNull( xref.getPrimaryRef() );

        assertFalse( xref.hasSecondaryRef() );
        assertNotNull( xref.getSecondaryRef() );
        assertFalse( xref.hasSecondaryRef() );
        assertTrue( xref.getSecondaryRef().isEmpty() );

        DbReference primary = new DbReference();
        primary.setDb( "uniprotkb" );
        primary.setDbAc( "MI:1111" );

        assertEquals( true, true );

        return xref;
    }

    @Test
    public void getPrimaryRef() {
        //TODO: Test of setGetPrimaryRef should go here...
    }

    @Test
    public void secondaryRef() {
        //TODO: Test of getSecondaryRef should go here...
    }
}
