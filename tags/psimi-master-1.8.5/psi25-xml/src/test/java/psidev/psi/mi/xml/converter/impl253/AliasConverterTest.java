/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.converter.impl253;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import psidev.psi.mi.xml.model.Alias;

/**
 * AliasConverter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/16/2006</pre>
 */
public class AliasConverterTest {

    @Test
    public void fromJaxb() {

        AliasConverter aliasConverter = new AliasConverter();

        try {
            aliasConverter.fromJaxb( null );
            Assert.fail();
        } catch ( Exception e ) {
            // ok
        }

        psidev.psi.mi.xml253.jaxb.NamesType.Alias jAlias = new psidev.psi.mi.xml253.jaxb.NamesType.Alias();

        jAlias.setType( "comment" );
        jAlias.setTypeAc( "MI:xxxx" );
        jAlias.setValue( "a lovely comment" );

        Alias mAlias = aliasConverter.fromJaxb( jAlias );

        Assert.assertNotNull( mAlias );

        Assert.assertTrue( mAlias.hasType() );
        Assert.assertEquals( "comment", mAlias.getType() );

        Assert.assertTrue( mAlias.hasTypeAc() );
        Assert.assertEquals( "MI:xxxx", mAlias.getTypeAc() );

        Assert.assertTrue( mAlias.hasValue() );
        Assert.assertEquals( "a lovely comment", mAlias.getValue() );
    }

    @Test
    public void toJaxb() {

        AliasConverter aliasConverter = new AliasConverter();

        try {
            aliasConverter.toJaxb( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        Alias mAlias = new Alias();

        mAlias.setType( "comment" );
        mAlias.setTypeAc( "MI:xxxx" );
        mAlias.setValue( "a lovely comment" );

        psidev.psi.mi.xml253.jaxb.NamesType.Alias jAlias = aliasConverter.toJaxb( mAlias );

        Assert.assertNotNull( jAlias );

        Assert.assertEquals( "comment", jAlias.getType() );
        Assert.assertEquals( "MI:xxxx", jAlias.getTypeAc() );
        Assert.assertEquals( "a lovely comment", jAlias.getValue() );
    }
}
