/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.filter;

import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility allowing to filter a Collection of interaction given a filter.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0-beta4
 */
@Deprecated
public class BinaryInteractionCollectionFilter {

    /**
     * Filter a collection of interaction based on the given filter.
     *
     * @param interactions non null interaction collection.
     * @param filter       non null filter.
     * @return a new Collection of interaction. never null, yet may be empty.
     */
    public static Collection<BinaryInteraction> filter( Collection<BinaryInteraction> interactions,
                                                        BinaryInteractionFilter filter
    ) {
        if ( interactions == null ) {
            throw new NullPointerException( "interactions must not be null." );
        }

        if ( filter == null ) {
            throw new NullPointerException( "filter must not be null." );
        }


        Collection<BinaryInteraction> filteredCollection = new ArrayList<BinaryInteraction>( interactions.size() );
        for ( BinaryInteraction interaction : interactions ) {
            if ( filter.evaluate( interaction ) ) {
                filteredCollection.add( interaction );
            }
        }

        return filteredCollection;
    }
}