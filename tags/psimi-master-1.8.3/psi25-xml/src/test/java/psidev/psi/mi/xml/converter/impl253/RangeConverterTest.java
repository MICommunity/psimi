/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.fail;
import org.junit.Test;

/**
 * RangeConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/19/2006</pre>
 */
public class RangeConverterTest {

    @Test
    public void fromJaxb() {

        RangeConverter rangeConverter = new RangeConverter();

        try {
            rangeConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test
    public void toJaxb() {

        RangeConverter rangeConverter = new RangeConverter();

        try {
            rangeConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }
}
