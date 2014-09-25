/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.Alias;
import psidev.psi.mi.xml.model.Names;
import psidev.psi.mi.xml253.jaxb.NamesType;

/**
 * NamesConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/16/2006</pre>
 */
public class NamesConverterTest {

    @Test
    public void fromJaxb() {

        NamesConverter namesConverter = new NamesConverter();

        try {
            namesConverter.fromJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        psidev.psi.mi.xml253.jaxb.NamesType jNames = new psidev.psi.mi.xml253.jaxb.NamesType();

        jNames.setShortLabel( "short" );
        jNames.setFullName( "full" );

        NamesType.Alias alias1 = new NamesType.Alias();
        alias1.setType( "type1" );
        alias1.setTypeAc( "MI:xxxx" );
        alias1.setValue( "1234" );
        jNames.getAlias().add( alias1 );

        Names mNames = namesConverter.fromJaxb( jNames );

        Assert.assertNotNull( mNames );
        assertEquals( "short", mNames.getShortLabel() );
        assertEquals( "full", mNames.getFullName() );

        // check on alias
        assertEquals( 1, mNames.getAliases().size() );
        Alias alias = mNames.getAliases().iterator().next();
        assertEquals( "type1", alias.getType() );
        assertEquals( "MI:xxxx", alias.getTypeAc() );
        assertEquals( "1234", alias.getValue() );
    }

    @Test
    public void toJaxb() {

        NamesConverter namesConverter = new NamesConverter();

        try {
            namesConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        psidev.psi.mi.xml.model.Names mNames = new Names();

        mNames.setShortLabel( "short" );
        mNames.setFullName( "full" );

        Alias alias1 = new Alias();
        alias1.setType( "type1" );
        alias1.setTypeAc( "MI:xxxx" );
        alias1.setValue( "1234" );
        mNames.getAliases().add( alias1 );

        psidev.psi.mi.xml253.jaxb.NamesType jNames = namesConverter.toJaxb( mNames );

        Assert.assertNotNull( jNames );
        assertEquals( "short", jNames.getShortLabel() );
        assertEquals( "full", jNames.getFullName() );

        // check on alias
        assertEquals( 1, jNames.getAlias().size() );
        NamesType.Alias alias = jNames.getAlias().iterator().next();
        assertEquals( "type1", alias.getType() );
        assertEquals( "MI:xxxx", alias.getTypeAc() );
        assertEquals( "1234", alias.getValue() );
    }
}
