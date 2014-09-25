/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.fail;
import org.junit.Test;
import psidev.psi.mi.xml.model.Organism;

/**
 * OrganismConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/19/2006</pre>
 */
public class OrganismConverterTest {

    @Test
    public void fromJaxb() {

        OrganismConverter organismConverter = new OrganismConverter();

        try {
            organismConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test
    public void toJaxb() {

        OrganismConverter organismConverter = new OrganismConverter();

        try {
            organismConverter.toJaxb( null, null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        try {
            organismConverter.toJaxb( new Organism(), null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }
}
