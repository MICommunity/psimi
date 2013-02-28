/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.mi.xml253.jaxb.DbReferenceType;
import psidev.psi.mi.xml253.jaxb.XrefType;

import static junit.framework.Assert.*;

/**
 * XrefConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/16/2006</pre>
 */
public class XrefConverterTest {

    @Test
    public void fromJaxb() {

        XrefConverter xrefConverter = new XrefConverter();

        try {
            xrefConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        XrefType jXref = new XrefType();

        DbReferenceType primaryRef = new DbReferenceType();
        primaryRef.setDb( "uniprotkb" );
        primaryRef.setDbAc( "MI:1111" );
        primaryRef.setId( "P12345" );
        primaryRef.setRefType( "identity" );
        primaryRef.setRefTypeAc( "MI:2222" );
        primaryRef.setSecondary( "Q98765" );
        primaryRef.setVersion( "67" );

        jXref.setPrimaryRef( primaryRef );

        DbReferenceType secondaryRef = new DbReferenceType();
        secondaryRef.setDb( "uniprotkb" );
        secondaryRef.setDbAc( "MI:1111" );
        secondaryRef.setId( "P23456" );
        secondaryRef.setRefTypeAc( "MI:2222" );

        jXref.getSecondaryReves().add( secondaryRef );

        Xref mXref = xrefConverter.fromJaxb( jXref );

        DbReference mPrimaryRef = mXref.getPrimaryRef();
        Assert.assertNotNull( mPrimaryRef );
        assertEquals( "uniprotkb", mPrimaryRef.getDb() );
        assertEquals( "MI:1111", mPrimaryRef.getDbAc() );
        assertEquals( "P12345", mPrimaryRef.getId() );
        assertEquals( "identity", mPrimaryRef.getRefType() );
        assertEquals( "MI:2222", mPrimaryRef.getRefTypeAc() );
        assertEquals( "Q98765", mPrimaryRef.getSecondary() );
        assertEquals( "67", mPrimaryRef.getVersion() );

        assertTrue( mXref.hasSecondaryRef() );
        assertEquals( 1, mXref.getSecondaryRef().size() );
        DbReference mSecondaryRef = mXref.getSecondaryRef().iterator().next();
        Assert.assertNotNull( mSecondaryRef );
        assertEquals( "uniprotkb", mSecondaryRef.getDb() );
        assertEquals( "MI:1111", mSecondaryRef.getDbAc() );
        assertEquals( "P23456", mSecondaryRef.getId() );
        assertEquals( "MI:2222", mSecondaryRef.getRefTypeAc() );
        assertFalse( mSecondaryRef.hasVersion() );
        assertFalse( mSecondaryRef.hasRefType() );
        assertFalse( mSecondaryRef.hasSecondary() );
    }

    @Test
    public void toJaxb() {

        XrefConverter xrefConverter = new XrefConverter();

        try {
            xrefConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        Xref mXref = new Xref();

        DbReference primaryRef = new DbReference();
        primaryRef.setDb( "uniprotkb" );
        primaryRef.setDbAc( "MI:1111" );
        primaryRef.setId( "P12345" );
        primaryRef.setRefType( "identity" );
        primaryRef.setRefTypeAc( "MI:2222" );
        primaryRef.setSecondary( "Q98765" );
        primaryRef.setVersion( "67" );

        mXref.setPrimaryRef( primaryRef );

        DbReference secondaryRef = new DbReference();
        secondaryRef.setDb( "uniprotkb" );
        secondaryRef.setDbAc( "MI:1111" );
        secondaryRef.setId( "P23456" );
        secondaryRef.setRefTypeAc( "MI:2222" );

        mXref.getSecondaryRef().add( secondaryRef );

        XrefType jXref = xrefConverter.toJaxb( mXref );

        DbReferenceType jPrimaryRef = jXref.getPrimaryRef();
        Assert.assertNotNull( jPrimaryRef );
        assertEquals( "uniprotkb", jPrimaryRef.getDb() );
        assertEquals( "MI:1111", jPrimaryRef.getDbAc() );
        assertEquals( "P12345", jPrimaryRef.getId() );
        assertEquals( "identity", jPrimaryRef.getRefType() );
        assertEquals( "MI:2222", jPrimaryRef.getRefTypeAc() );
        assertEquals( "Q98765", jPrimaryRef.getSecondary() );
        assertEquals( "67", jPrimaryRef.getVersion() );

        assertEquals( 1, jXref.getSecondaryReves().size() );
        DbReferenceType jSecondaryRef = jXref.getSecondaryReves().iterator().next();
        Assert.assertNotNull( jSecondaryRef );
        assertEquals( "uniprotkb", jSecondaryRef.getDb() );
        assertEquals( "MI:1111", jSecondaryRef.getDbAc() );
        assertEquals( "P23456", jSecondaryRef.getId() );
        assertEquals( "MI:2222", jSecondaryRef.getRefTypeAc() );
        assertNull( jSecondaryRef.getVersion() );
        assertEquals( "unknown", jSecondaryRef.getRefType() );
        assertNull( jSecondaryRef.getSecondary() );
    }
}
