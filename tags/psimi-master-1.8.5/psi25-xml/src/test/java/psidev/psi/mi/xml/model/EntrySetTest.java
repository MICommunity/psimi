/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * EntrySet Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class EntrySetTest {

    public EntrySet buildEntrySet() {
        EntrySet entrySet = new EntrySet();
        assertNotNull( entrySet );

        entrySet.setLevel( 2 );
        entrySet.setVersion( 5 );
        entrySet.setMinorVersion( 3 );

        return entrySet;
    }

    @Test
    public void getEntry() {
        EntrySet entrySet = new EntrySet();
        assertNotNull( entrySet.getEntries() );
        assertTrue( entrySet.getEntries().isEmpty() );
    }

    @Test
    public void getLevel() {
        EntrySet es = buildEntrySet();
        assertEquals( 2, es.getLevel() );
    }

    @Test
    public void getMinorVersion() {
        EntrySet es = buildEntrySet();
        assertEquals( 3, es.getMinorVersion() );
    }

    @Test
    public void getVersion() {
        EntrySet es = buildEntrySet();
        assertEquals( 5, es.getVersion() );
    }
}
