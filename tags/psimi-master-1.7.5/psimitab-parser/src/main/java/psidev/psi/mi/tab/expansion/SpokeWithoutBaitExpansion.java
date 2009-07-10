/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.expansion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Participant;

import java.util.*;

/**
 * Process an interaction and expand it using the spoke model. Whenever no bait can be found we select an arbitrary
 * bait (1st one by alphabetical order based on the interactor shortlabel) and build the spoke interactions based on
 * that fake bait.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13-Oct-2006</pre>
 */
public class SpokeWithoutBaitExpansion extends SpokeExpansion {

    public static final String BAIT_MI_REF = "MI:0496";

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( SpokeWithoutBaitExpansion.class );

    ///////////////////////////////////////////
    // Implements ExpansionStrategy contract

    @Override
    protected Collection<Interaction> processNoBaitExpansion(Interaction interaction) {
        List<Interaction> interactions = new ArrayList<Interaction>();

        log.debug("Could not find a bait participant. Pick a participant arbitrarily: 1st by alphabetical order.");

        // Collect and sort participants by name
        List<Participant> sortedParticipants = sortParticipants(interaction.getParticipants());

        // Pick the first one
        Participant fakeBait = sortedParticipants.get(0);

        // Build interactions
        for (int i = 1; i < sortedParticipants.size(); i++) {
            Participant fakePrey = sortedParticipants.get(i);

            if (log.isDebugEnabled()) {
                String baitStr = displayParticipant(fakeBait);
                String preyStr = displayParticipant(fakePrey);
                log.debug("Build new binary interaction [" + baitStr + "," + preyStr + "]");
            }

            Interaction spokeInteraction = buildInteraction(interaction, fakeBait, fakePrey);
            interactions.add(spokeInteraction);
        }

        return interactions;
    }

    ////////////////////////////
    // Private methods

    /**
     * Sort a Collection of Participant based on their shorltabel.
     * @param participants collection to sort.
     * @return a non null List of Participant.
     */
    protected List<Participant> sortParticipants(Collection<Participant> participants) {

        List<Participant> sortedParticipants = new ArrayList<Participant>( participants );

        Collections.sort( sortedParticipants, new Comparator<Participant>() {
            public int compare(Participant p1, Participant p2) {

                Interactor i1 = p1.getInteractor();
                if( i1 == null ) {
                    throw new IllegalArgumentException( "Both participant should hold a valid interactor." );
                }
                Interactor i2 = p2.getInteractor();
                if( i2 == null ) {
                    throw new IllegalArgumentException( "Both participant should hold a valid interactor." );
                }

                String name1 = getName( i1 );
                String name2 = getName( i2 );

                int result;
                if ( name1 == null ) {
                    result = -1;
                } else if ( name2 == null ) {
                    result = 1;
                } else {
                    result = name1.compareTo( name2 );
                }

                return result;
            }
        } );

        return sortedParticipants;
    }

    /**
     * Get the interactor's shortlabel.
     * @param interactor
     * @return the name of null of no shortlabel available.
     */
    private String getName(Interactor interactor) {
        if( interactor.getNames() != null ) {
            if ( interactor.getNames().hasShortLabel() ) {
                return interactor.getNames().getShortLabel();
            }
        }

        return null;
    }
}