/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.tab2xml;

import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.xml.model.Names;

/**
 * Interface for build a name to an interaction.
 * 
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 *
 */
public interface InteractorNameBuilder {
	
	/**
	 * Select a name into a
	 * @param interaction
	 * @return
	 */
	public Names select(Interactor interactor);
}
