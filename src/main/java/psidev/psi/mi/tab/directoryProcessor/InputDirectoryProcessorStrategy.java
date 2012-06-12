/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.directoryProcessor;

import java.io.File;
import java.util.Collection;

/**
 * Defines a processor for extracting files from a directory.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Jan-2007</pre>
 */
public interface InputDirectoryProcessorStrategy {

    /**
     * Processing a directory and return a selected collection of file.
     *
     * @param directory directory to extract files from.
     *
     * @return a collection of file extracted from the given directory.
     */
    public Collection<File> process( File directory );
}