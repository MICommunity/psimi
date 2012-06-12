/*
 * Copyright (c) 2008 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.tab.model.builder;

import org.junit.Test;
import org.junit.Assert;

/**
 * PlainTextFieldBuilder Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */
public class PlainTextFieldBuilderTest {

    @Test
    public void createField() throws Exception {

        FieldBuilder builder = new PlainTextFieldBuilder();

        Assert.assertEquals( new Field( "b" ), builder.createField( "b" ) );
        Assert.assertEquals( new Field( "b" ), builder.createField( "\"b\"" ) );

        // line return removal
        Assert.assertEquals( new Field( "l a l a" ), builder.createField( "l\na\nl\na" ) );
        Assert.assertEquals( new Field( "l a l a" ), builder.createField( "\"l\na\nl\na\"" ) );

        Assert.assertNull( builder.createField( String.valueOf( Column.EMPTY_COLUMN ) ) );
    }
}
