/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * Availability Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class AvailabilityTest {

    private Availability buildAvailability() {
        Availability availability = new Availability();

        assertNotNull( availability );

        availability.setId( 1 );
        availability.setValue( "a lenghty copyright" );

        return availability;
    }

    @Test
    public void setGetValue() {
        Availability availability = buildAvailability();
        assertEquals( "a lenghty copyright", availability.getValue() );
    }

    @Test
    public void setGetId() {
        Availability availability = buildAvailability();
        assertEquals( 1, availability.getId() );
    }
}
