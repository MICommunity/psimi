 /* Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.tab2xml;

 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import psidev.psi.mi.tab.model.CrossReference;
 import psidev.psi.mi.tab.model.Interactor;
 import psidev.psi.mi.xml.model.Alias;
 import psidev.psi.mi.xml.model.Names;

 import java.util.ArrayList;
 import java.util.Collection;

/**
 * Build a name for an interaction using the interactor identifier.
 * 
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 *
 */
public class InteractorIdBuilder implements InteractorNameBuilder {

	/**
	 * Sets up a logger for that class.
	 */
	public final static Log log = LogFactory.getLog(InteractorIdBuilder.class);
	
	/**
	 * Build a name for a interaction.
	 * 
	 * @param interactor 
	 * @return a name for these interactor
	 */
	public Names select(Interactor interactor) {
		
		Names interactorName = null;
		
		if (interactor == null){
			throw new IllegalArgumentException( "You must give a non null Interactor." );
		}
		
		// set interactor name shortLabel 
		if (!interactor.getIdentifiers().isEmpty()){
			String shortLabel = null;
			for (CrossReference identifier : interactor.getIdentifiers()){
				if (identifier.getDatabase().equals("uniprotkb")){
					shortLabel = identifier.getIdentifier();
					break;
				}
			}
			if (shortLabel != null){
				interactorName = new Names();
				interactorName.setShortLabel(shortLabel);
			} else {
				log.debug("No valid uniprot interactorName found. default : 1.interactor identifier of db = "+interactor.getIdentifiers().iterator().next().getDatabase());
				
				interactorName = new Names();
				interactorName.setShortLabel(interactor.getIdentifiers().iterator().next().getIdentifier());
			}
		}
		
		// set interactor name alias
        if (!interactor.getAliases().isEmpty() && interactorName != null){
        	
        	Collection<Alias> aliases = new ArrayList<Alias>();
        	
        	for (psidev.psi.mi.tab.model.Alias tabAlias : interactor.getAliases()){
        		String type = tabAlias.getAliasType();
        		String value = tabAlias.getName();
        		
        		Alias alias = new Alias();
        		alias.setValue(value);        		
        		alias.setType(type);
        		aliases.add(alias);
        	}
        	if (!aliases.isEmpty()){
        		interactorName.getAliases().addAll(aliases);
        	}
        }
		
		return interactorName;
	}
}
