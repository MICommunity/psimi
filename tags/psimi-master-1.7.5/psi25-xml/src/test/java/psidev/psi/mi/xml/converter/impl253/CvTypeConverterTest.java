/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.fail;
import org.junit.Test;
import psidev.psi.mi.xml253.jaxb.CvType;

/**
 * CvTypeConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/19/2006</pre>
 */
public class CvTypeConverterTest {

    @Test
    public void fromJaxb() {

        CvTypeConverter cvTypeConverter = new CvTypeConverter();

        try {
            cvTypeConverter.fromJaxb( null, null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        try {
            cvTypeConverter.fromJaxb( new CvType(), null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test
    public void toJaxb() {

        CvTypeConverter cvTypeConverter = new CvTypeConverter();

        try {
            cvTypeConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }
}
