/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.txt2tab.behaviour;

import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;

/**
 * Write unparseable lines to a file.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06-Feb-2007</pre>
 */
public class FailFastUnparseableLine implements UnparseableLineBehaviour {

    //////////////////////////////
    // UnparseableLineBehaviour

    public void respond( String line, MitabLineException t ) throws MitabLineException {
        throw t;
    }

    public boolean propagateException() {
        return true;
    }
}