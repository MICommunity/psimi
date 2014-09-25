/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.filter;

import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A set of filters behaving as a super filter. This class implements the composite design pattern.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08-Feb-2007</pre>
 */
@Deprecated
public abstract class AbstractBinaryInteractionFilterSet implements BinaryInteractionFilter {

    /**
     * A set of filters
     */
    Set<BinaryInteractionFilter> filters = new HashSet<BinaryInteractionFilter>();

    ////////////////////////
    // Constructor

    public AbstractBinaryInteractionFilterSet( Set<BinaryInteractionFilter> filters ) {
        if ( filters == null ) {
            throw new NullPointerException( "filters must not be null." );
        }
        if ( filters.isEmpty() ) {
            throw new NullPointerException( "filters must not be empty." );
        }
        addFilters( filters );
    }

    /////////////////////////
    // Set manipulation

    public Set<BinaryInteractionFilter> getFilters() {
        return Collections.unmodifiableSet( filters );
    }

    public void addFilters( Set<BinaryInteractionFilter> filters ) {
        if( filters == null ) {
            throw new IllegalArgumentException( );
        }
        this.filters.addAll( filters );
    }

    ////////////////////////////
    // BinaryInteractionFilter

    public abstract boolean evaluate( BinaryInteraction<?> interaction );

//    public boolean evaluate( BinaryInteractionImpl interaction ) {
//
//        // TODO define clearly what is being checked here !! are we doing an AND or a OR between predicate.
//        // TODO AND/OR will affect the way to shortcut the loop !!
//
//        // OR implementation
//        for ( BinaryInteractionFilter filter : filters ) {
//            if( true == filter.evaluate( interaction ) ) {
//                return true;
//            }
//        }
//        return false;

        // AND implementation
//        for ( BinaryInteractionFilter filter : filters ) {
//            if( false == filter.evaluate( interaction ) ) {
//                return false;
//            }
//        }
//        return true;
//    }
}