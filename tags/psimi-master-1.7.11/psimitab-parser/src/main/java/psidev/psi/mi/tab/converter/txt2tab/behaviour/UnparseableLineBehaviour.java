/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.txt2tab.behaviour;

import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;

/**
 * Defines a behaviour meant to respond in case the parser encounters an non parseable line.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06-Feb-2007</pre>
 */
public interface UnparseableLineBehaviour {

    public void respond( String line, MitabLineException t ) throws MitabLineException;

    public boolean propagateException();
}