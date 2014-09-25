/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.processor;

import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.Collection;

/**
 * Interface describing a list of interaction's processor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12-Oct-2006</pre>
 */
public 
interface PostProcessorStrategy<T extends BinaryInteraction> {
    /**
     * Process a collection of Binary interaction.
     * @param interactions input list of interactions (must not be null).
     * @return a new collection of interactions.
     */
    public Collection<T> process( Collection<T> interactions );
}