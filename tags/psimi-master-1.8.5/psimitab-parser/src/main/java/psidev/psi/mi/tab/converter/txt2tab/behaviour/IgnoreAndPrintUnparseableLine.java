/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.txt2tab.behaviour;

import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;

import java.io.PrintStream;

/**
 * Simply ignore unparseable lines.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06-Feb-2007</pre>
 */
public class IgnoreAndPrintUnparseableLine implements UnparseableLineBehaviour {

    private PrintStream out;

    public IgnoreAndPrintUnparseableLine( PrintStream out) {
        if( out == null ) {
            out = System.out;
        }

        this.out = out;
    }

    public void respond( String line, MitabLineException t ) throws MitabLineException {
        out.println( "Parse error on: " + line );
    }

    public boolean propagateException() {
        return false;
    }
}