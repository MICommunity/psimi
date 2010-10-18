/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;

/**
 * Attribute Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class AttributeTest {

    private Attribute buildAttribute() {
        Attribute attribute = new Attribute( "comment", "a nice comment" );
        assertNotNull( attribute );
        attribute.setNameAc( "MI:xxxx" );
        return attribute;
    }

    @Test
    public void getValue() {
        Attribute attribute = buildAttribute();
        assertEquals( "a nice comment", attribute.getValue() );

    }

    @Test
    public void getName() {
        Attribute attribute = buildAttribute();
        assertEquals( "comment", attribute.getName() );
    }

    @Test
    public void getNameAc() {
        Attribute attribute = buildAttribute();
        assertEquals( "MI:xxxx", attribute.getNameAc() );
    }
}