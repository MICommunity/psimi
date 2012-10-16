/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.expansion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.model.ExperimentalRole;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Process an interaction and expand it using the matrix model.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13-Oct-2006</pre>
 */
public class MatrixExpansion extends BinaryExpansionStrategy {

    public static final String EXPANSION_NAME = "Matrix";

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( MatrixExpansion.class );

    /**
     * Apply the matrix expansion to the given interaction. Essentially, an interaction is created between any two
     * partitipant.
     *
     * @param interaction the interaction to expand.
     *
     * @return a non null collection of binary interaction
     */
    public Collection<Interaction> expand( Interaction interaction ) {
        Collection<Interaction> interactions = new ArrayList<Interaction>();

        if ( isBinary( interaction ) || interaction.getParticipants().size() == 1) {

            log.debug( "interaction " + interaction.getId() + "/" + interaction.getImexId() + " was binary or intra molecular, no further processing involved." );
            interactions.add( interaction );

        } else {

            // split interaction
            Participant[] participants = interaction.getParticipants().toArray( new Participant[]{ } );
            log.debug( participants.length + " participant(s) found." );

            for ( int i = 0; i < participants.length; i++ ) {
                Participant p1 = participants[ i ];
                for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                    Participant p2 = participants[ j ];

                    // build a new interaction
                    if ( log.isDebugEnabled() ) {
                        String p1Str = displayParticipant( p1 );
                        String p2Str = displayParticipant( p2 );
                        log.debug( "Build new binary interaction [" + p1Str + "," + p2Str + "]" );
                    }

                    Interaction newInteraction = buildInteraction( interaction, p1, p2 );
                    interactions.add( newInteraction );
                }
            }

            log.debug( "After expansion: " + interactions.size() + " binary interaction(s) were generated." );
        }

        return interactions;
    }

    private String displayParticipant( Participant p ) {

        // fetch role
        String role = "";

        for ( ExperimentalRole aRole : p.getExperimentalRoles() ) {
            if ( role.length() > 0 ) {
                role += "&";
            }

            if ( aRole.hasNames() ) {
                role += aRole.getNames().getShortLabel();
            } else {
                role += "?";
            }
        }

        // fetch interactor
        String interactor = p.getInteractor().getNames().getShortLabel();

        return interactor + ":" + role;
    }

	public String getName() {
		return EXPANSION_NAME;
	}
}