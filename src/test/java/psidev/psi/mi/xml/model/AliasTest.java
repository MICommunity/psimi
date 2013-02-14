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
 * Alias Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class AliasTest {

    private Alias buildAlias() {

        Alias alias = new Alias();

        assertNotNull( alias );

        alias.setType( "gene-name" );
        alias.setTypeAc( "MI:xxxx" );
        alias.setValue( "foo" );

        return alias;
    }

    @Test
    public void getValue() {
        Alias alias = buildAlias();
        assertEquals( "foo", alias.getValue() );
    }

    @Test
    public void getType() {
        Alias alias = buildAlias();
        assertEquals( "gene-name", alias.getType() );
    }

    @Test
    public void getTypeAc() {
        Alias alias = buildAlias();
        assertEquals( "MI:xxxx", alias.getTypeAc() );
    }
}
