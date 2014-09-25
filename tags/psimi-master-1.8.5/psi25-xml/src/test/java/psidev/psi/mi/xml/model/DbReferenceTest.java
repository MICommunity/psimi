/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import static junit.framework.Assert.*;
import org.junit.Test;

/**
 * DbReference Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class DbReferenceTest {

    public DbReference buildDbReference() {
        DbReference ref = new DbReference();

        ref.setDb( "uniprotkb" );
        ref.setDbAc( "MI:1111" );
        ref.setId( "P12345" );
        ref.setRefType( "identity" );
        ref.setRefTypeAc( "MI:2222" );
        ref.setSecondary( "Q98765" );
        ref.setVersion( "54" );

        Attribute att1 = new Attribute( "comment", "a short comment" );
        att1.setNameAc( "MI:9999" );
        ref.getAttributes().add( att1 );

        Attribute att2 = new Attribute( "remark", "a long remark" );
        att2.setNameAc( "MI:8888" );
        ref.getAttributes().add( att2 );

        return ref;
    }

    @Test
    public void getAttributes() {
        DbReference ref = buildDbReference();

        assertFalse( ref.getAttributes().isEmpty() );

        assertEquals( 2, ref.getAttributes().size() );

        for ( Attribute att : ref.getAttributes() ) {
            if ( att.getName().equals( "comment" ) ) {
                assertEquals( "MI:9999", att.getNameAc() );
                assertEquals( "a short comment", att.getValue() );
            } else if ( att.getName().equals( "remark" ) ) {
                assertEquals( "MI:8888", att.getNameAc() );
                assertEquals( "a long remark", att.getValue() );
            } else {
                fail( "Unexpected attribute found: " + att );
            }
        }
    }

    @Test
    public void getDb() {
        DbReference ref = buildDbReference();
        assertEquals( "uniprotkb", ref.getDb() );
    }

    @Test
    public void getDbAc() {
        DbReference ref = buildDbReference();
        assertEquals( "MI:1111", ref.getDbAc() );
    }

    @Test
    public void getId() {
        DbReference ref = buildDbReference();
        assertEquals( "P12345", ref.getId() );
    }

    @Test
    public void getRefType() {
        DbReference ref = buildDbReference();
        assertEquals( "identity", ref.getRefType() );
    }

    @Test
    public void getRefTypeAc() {
        DbReference ref = buildDbReference();
        assertEquals( "MI:2222", ref.getRefTypeAc() );
    }

    @Test
    public void getSecondary() {
        DbReference ref = buildDbReference();
        assertEquals( "Q98765", ref.getSecondary() );
    }

    @Test
    public void getVersion() {
        DbReference ref = buildDbReference();
        assertEquals( "54", ref.getVersion() );
    }
}
