/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.fail;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.Availability;
import psidev.psi.mi.xml253.jaxb.AvailabilityType;

/**
 * AvailabilityConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/16/2006</pre>
 */
public class AvailabilityConverterTest {

    @Test
    public void fromJaxb() {

        AvailabilityConverter availabilityConverter = new AvailabilityConverter();

        try {
            availabilityConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        AvailabilityType jAvailability = new AvailabilityType();

        jAvailability.setId( 3 );
        jAvailability.setValue( "a copyright" );

        Availability mAvailability = availabilityConverter.fromJaxb( jAvailability );

        Assert.assertNotNull( mAvailability );
        Assert.assertEquals( 3, mAvailability.getId() );
        Assert.assertEquals( "a copyright", mAvailability.getValue() );
    }

    @Test
    public void toJaxb() {

        AvailabilityConverter availabilityConverter = new AvailabilityConverter();

        try {
            availabilityConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        Availability mAvailability = new Availability();

        mAvailability.setId( 3 );
        mAvailability.setValue( "a copyright" );

        AvailabilityType jAvailability = availabilityConverter.toJaxb( mAvailability );

        Assert.assertNotNull( jAvailability );
        Assert.assertEquals( 3, jAvailability.getId() );
        Assert.assertEquals( "a copyright", jAvailability.getValue() );
    }
}