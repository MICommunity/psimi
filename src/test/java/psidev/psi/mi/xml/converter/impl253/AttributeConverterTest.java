/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static org.junit.Assert.*;
import org.junit.Test;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml253.jaxb.AttributeListType;

/**
 * AttributeConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/16/2006</pre>
 */
public class AttributeConverterTest {

    @Test
    public void fromJaxb() {

        AttributeConverter attributeConverter = new AttributeConverter();

        try {
            attributeConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        AttributeListType.Attribute att = new AttributeListType.Attribute();

        att.setName( "comment" );
        att.setNameAc( "MI:1234" );
        att.setValue( "a comment" );

        Attribute mAttribute = attributeConverter.fromJaxb( att );

        assertNotNull( mAttribute );
        assertEquals( "comment", mAttribute.getName() );
        assertEquals( "MI:1234", mAttribute.getNameAc() );
        assertEquals( "a comment", mAttribute.getValue() );
    }

    @Test
    public void toJaxb() {

        AttributeConverter attributeConverter = new AttributeConverter();

        try {
            attributeConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }
        Attribute att = new Attribute();

        att.setName( "comment" );
        att.setNameAc( "MI:1234" );
        att.setValue( "a comment" );

        AttributeListType.Attribute jAttribute = attributeConverter.toJaxb( att );

        assertNotNull( jAttribute );
        assertEquals( "comment", jAttribute.getName() );
        assertEquals( "MI:1234", jAttribute.getNameAc() );
        assertEquals( "a comment", jAttribute.getValue() );
    }
}
