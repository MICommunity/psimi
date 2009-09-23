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
 * Build a name for an interaction using the interactor alternative identifier.
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 */
public class InteractorUniprotIdBuilder implements InteractorNameBuilder {

    /**
     * Sets up a logger for that class.
     */
    public final static Log log = LogFactory.getLog( InteractorUniprotIdBuilder.class );

    public Names select( Interactor interactor ) {

        Names interactorName = null;

        if ( interactor == null ) {
            throw new IllegalArgumentException( "You must give a non null Interactor." );
        }

        // set interactor name shortLabel
        String interactorId = null;

        if ( interactor.getAlternativeIdentifiers() != null ) {

            for ( CrossReference altIdentifier : interactor.getAlternativeIdentifiers() ) {
                if ( "gene name".equals( altIdentifier.getText() ) ) {
                    if ( "uniprotkb".equals( altIdentifier.getDatabase() ) ) {
                        interactorId = altIdentifier.getIdentifier().toLowerCase();
                        break;
                    }
                }
            }
        }

        String organismName = null;
        if ( interactor.hasOrganism() ) {
            for ( CrossReference organism : interactor.getOrganism().getIdentifiers() ) {
                String name = organism.getText();
                if ( name != null ) {
                    organismName = name.toLowerCase();
                    break;
                }
            }
        }

        if ( interactorId != null && organismName != null ) {
            interactorName = new Names();
            interactorName.setShortLabel( interactorId + "_" + organismName );

            // set interactor name alias
            if ( !interactor.getAliases().isEmpty() && interactorName != null ) {

                Collection<Alias> aliases = new ArrayList<Alias>();

                for ( psidev.psi.mi.tab.model.Alias tabAlias : interactor.getAliases() ) {
                    String type = tabAlias.getAliasType();
                    String value = tabAlias.getName();

                    Alias alias = new Alias();
                    alias.setValue( value );
                    alias.setType( type );
                    aliases.add( alias );
                }
                if ( !aliases.isEmpty() ) {
                    interactorName.getAliases().addAll( aliases );
                }

            }

            return interactorName;

        } else {
            log.debug( "No uniprotId found -> using InteractorIdBuilder to get a vaild name." );
            InteractorIdBuilder idBuilder = new InteractorIdBuilder();
            return idBuilder.select( interactor );
        }
    }

}
