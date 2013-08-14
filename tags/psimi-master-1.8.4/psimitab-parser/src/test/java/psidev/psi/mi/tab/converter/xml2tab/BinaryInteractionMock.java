/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import psidev.psi.mi.tab.model.BinaryInteractionImpl;
import psidev.psi.mi.tab.model.Interactor;

/**
 * Mock extension of the BinaryInteractionImpl.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10-Jan-2007</pre>
 */
public class BinaryInteractionMock extends BinaryInteractionImpl {
    public BinaryInteractionMock( Interactor interactorA, Interactor interactorB ) {
        super( interactorA, interactorB );
    }
}