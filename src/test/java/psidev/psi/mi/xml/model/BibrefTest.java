/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Assert;

/**
 * Bibref Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/15/2006</pre>
 */
public class BibrefTest {

    private Bibref buildBibref() {

        Bibref bibref = new Bibref();

        assertNotNull( bibref );
        assertEquals( true, true );

        return bibref;
    }

    @Test
    public void getAttributes() {
        //TODO: Test of getAttributes should go here...
    }

    @Test
    public void setGetXref() throws Exception {
        Bibref bibref = new Bibref();

        Assert.assertNull(bibref.getXref());

        Xref xref = new Xref();
        bibref.setXref(xref);
        Assert.assertNotNull(bibref.getXref());

        bibref.setXref(null);
        Assert.assertNull(bibref.getXref());
    }
}
