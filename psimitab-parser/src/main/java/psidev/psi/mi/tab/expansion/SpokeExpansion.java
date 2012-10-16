/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.expansion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.xml2tab.XrefUtils;
import psidev.psi.mi.xml.model.ExperimentalRole;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Process an interaction and expand it using the spoke model.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13-Oct-2006</pre>
 */
public class SpokeExpansion extends BinaryExpansionStrategy {

    public static final String EXPANSION_NAME = "Spoke";

    public static final String BAIT_MI_REF = "MI:0496";

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( SpokeExpansion.class );

    ///////////////////////////////////////////
    // Implements ExpansionStrategy contract

    /**
     * Interaction having more than 2 participants get split following the spoke model expansion. That is, we build
     * pairs of participant following bait-prey and enzyme-target associations.
     *
     * @param interaction a non null interaction.
     *
     * @return a non null collection of interaction, in case the expansion is not possible, we may return an empty
     *         collection.
     */
    public Collection<Interaction> expand( Interaction interaction ) {

        Collection<Interaction> interactions = new ArrayList<Interaction>();

        if (interaction.getParticipants().isEmpty()) {
            return interactions;
        }

        if ( isBinary( interaction ) || interaction.getParticipants().size() == 1 ) {

            log.debug( "interaction " + interaction.getId() + "/" + interaction.getImexId() + " was binary or intra molecular, no further processing involved." );
            interactions.add( interaction );

        } else {

            // split interaction
            Collection<Participant> participants = interaction.getParticipants();
            log.debug( participants.size() + " participant(s) found." );

            Participant bait = searchBaitParticipant( participants );
            if ( bait != null ) {
                Collection<Participant> preys = new ArrayList<Participant>( participants.size() - 1 );
                preys.addAll( participants );
                preys.remove( bait );

                for ( Participant prey : preys ) {

                    if ( log.isDebugEnabled() ) {
                        String baitStr = displayParticipant( bait );
                        String preyStr = displayParticipant( prey );
                        log.debug( "Build new binary interaction [" + baitStr + "," + preyStr + "]" );
                    }

                    Interaction i = buildInteraction( interaction, bait, prey );
                    interactions.add( i );
                }
            } else {
                Collection<Interaction> noBaitExpandedInteractions = processNoBaitExpansion(interaction);
                interactions.addAll(noBaitExpandedInteractions);
            }

            log.debug( "After expansion: " + interactions.size() + " binary interaction(s) were generated." );
        }

        return interactions;
    }

    protected Collection<Interaction> processNoBaitExpansion(Interaction interaction) {
        log.debug( "Could not find a bait participant. No further processing involved." );
        return Collections.EMPTY_LIST;
    }

    ////////////////////////////
    // Private methods

    protected String displayParticipant( Participant p ) {

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

    protected boolean isBait( Participant participant ) {

        if ( participant == null ) {
            throw new IllegalArgumentException( "Participant must not be null." );
        }

        if ( participant.hasExperimentalRoles() ) {
            for ( ExperimentalRole role : participant.getExperimentalRoles() ) {
                // search for bait
                log.debug( "Checking if participant (id:" + participant.getId() + ") is a bait." );
                if ( XrefUtils.hasPsiId( role.getXref(), BAIT_MI_REF ) ) {
                    log.debug( "Yes it is." );
                    return true;
                }
                log.debug( "No it is not." );
            }
        }

        return false;
    }

    protected Participant searchBaitParticipant( Collection<Participant> participants ) {

        if ( participants == null ) {
            throw new IllegalArgumentException( "Participants must not be null." );
        }

        for ( Participant participant : participants ) {
            if ( isBait( participant ) ) {
                return participant;
            }
        }

        return null;
    }

	public String getName() {
		return EXPANSION_NAME;
	}
}