/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.txt2tab.behaviour;

import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;

/**
 * Simply ignore quietly unparseable lines.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06-Feb-2007</pre>
 */
public class IgnoreQuietlyUnparseableLine implements UnparseableLineBehaviour {

    public void respond( String line, MitabLineException t ) throws MitabLineException {
        // keep quiet
    }

    public boolean propagateException() {
        return false;
    }
}