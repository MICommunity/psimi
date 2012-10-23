/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.directoryProcessor;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

/**
 * This strategy simply ignores the given directory.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Jan-2007</pre>
 */
public class IgnoreDirectory implements InputDirectoryProcessorStrategy {

    public IgnoreDirectory() {
    }

    /**
     * Simply returns an empty collection without even looking at the directory.
     * @param directory
     * @return
     */
    public Collection<File> process( File directory ) {
        return Collections.EMPTY_LIST;
    }
}