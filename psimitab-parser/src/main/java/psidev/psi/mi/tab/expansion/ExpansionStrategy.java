/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.expansion;

import psidev.psi.mi.xml.model.Interaction;

import java.util.Collection;

/**
 * Interface for applying an expansion to an interaction.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13-Oct-2006</pre>
 */
public interface ExpansionStrategy {

    /**
     * Expand an interaction into a 
     * @param interaction
     * @return
     */
    public Collection<Interaction> expand( Interaction interaction );
    
    /**
     * Gets the method of the ExpansionStrategy
     * @return spoke, matrix or none
     */
    public String getName();
}