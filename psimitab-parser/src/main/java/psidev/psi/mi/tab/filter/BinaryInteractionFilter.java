/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.filter;

import psidev.psi.mi.tab.model.BinaryInteraction;

/**
 * Defines a filtering for BinaryInteractionImpl. BinaryInteractionFilter instances can be used to implement queries or
 * to do filtering.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08-Feb-2007</pre>
 */
@Deprecated
public interface BinaryInteractionFilter {

    /**
     * Returns true if the input object matches this predicate.
     *
     * @param interaction the interaction to be filtered.
     *
     * @return Returns true if the input object matches this predicate.
     */
    public boolean evaluate( BinaryInteraction<?> interaction );
}