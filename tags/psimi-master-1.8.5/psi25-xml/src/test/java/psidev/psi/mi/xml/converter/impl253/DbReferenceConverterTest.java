/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import junit.framework.Assert;
import static junit.framework.Assert.fail;
import org.junit.Test;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml253.jaxb.DbReferenceType;

/**
 * DbReferenceConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/19/2006</pre>
 */
public class DbReferenceConverterTest {

    @Test
    public void fromJaxb() {

        DbReferenceConverter dbReferenceConverter = new DbReferenceConverter();

        try {
            dbReferenceConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        DbReferenceType ref = new DbReferenceType();
        ref.setId( "1" );
        ref.setDb( "2" );
        ref.setDbAc( "3" );
        ref.setRefType( "4" );
        ref.setRefTypeAc( "5" );
        ref.setSecondary( "6" );
        ref.setVersion( "7" );

        DbReference mRef = dbReferenceConverter.fromJaxb( ref );

        Assert.assertNotNull( mRef );
        Assert.assertEquals( "1", mRef.getId() );
        Assert.assertEquals( "2", mRef.getDb() );
        Assert.assertEquals( "3", mRef.getDbAc() );
        Assert.assertEquals( "4", mRef.getRefType() );
        Assert.assertEquals( "5", mRef.getRefTypeAc() );
        Assert.assertEquals( "6", mRef.getSecondary() );
        Assert.assertEquals( "7", mRef.getVersion() );
    }

    @Test
    public void toJaxb() {

        DbReferenceConverter dbReferenceConverter = new DbReferenceConverter();

        try {
            dbReferenceConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        DbReference ref = new DbReference();
        ref.setId( "1" );
        ref.setDb( "2" );
        ref.setDbAc( "3" );
        ref.setRefType( "4" );
        ref.setRefTypeAc( "5" );
        ref.setSecondary( "6" );
        ref.setVersion( "7" );

        DbReferenceType jRef = dbReferenceConverter.toJaxb( ref );

        Assert.assertNotNull( jRef );
        Assert.assertEquals( "1", jRef.getId() );
        Assert.assertEquals( "2", jRef.getDb() );
        Assert.assertEquals( "3", jRef.getDbAc() );
        Assert.assertEquals( "4", jRef.getRefType() );
        Assert.assertEquals( "5", jRef.getRefTypeAc() );
        Assert.assertEquals( "6", jRef.getSecondary() );
        Assert.assertEquals( "7", jRef.getVersion() );
    }
}
