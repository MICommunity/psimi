/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class BinaryInteractionImpl extends AbstractBinaryInteraction<Interactor> {

    public BinaryInteractionImpl(Interactor interactorA, Interactor interactorB) {
        super(interactorA, interactorB);
    }
}