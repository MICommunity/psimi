/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.filter;

import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.Set;

/**
 * Set of Filter that evaluates them using a logical OR.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0-beta4
 */
@Deprecated
public class DisjonctiveFilterSet extends AbstractBinaryInteractionFilterSet {

    public DisjonctiveFilterSet( Set<BinaryInteractionFilter> filters ) {
        super( filters );
    }

    public boolean evaluate( BinaryInteraction<?> interaction ) {
        for ( BinaryInteractionFilter filter : filters ) {
            if ( true == filter.evaluate( interaction ) ) {
                return true;
            }
        }
        return false;
    }
}