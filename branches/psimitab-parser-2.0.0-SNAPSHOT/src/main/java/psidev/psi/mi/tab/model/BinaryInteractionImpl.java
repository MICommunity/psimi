/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.clone.ParticipantCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class BinaryInteractionImpl extends AbstractBinaryInteraction<Interactor> {

    public BinaryInteractionImpl() {
        super();
    }

    public BinaryInteractionImpl(Interactor interactor) {
        super(interactor);
    }

    public BinaryInteractionImpl(Interactor interactorA, Interactor interactorB) {
        super(interactorA, interactorB);
    }

    protected BinaryInteraction<Interactor> getInstance(){
        return this;
    }

    @Override
    protected void initializeParticipants(){
        this.participants = new ParticipantList();
    }

    protected class ParticipantList extends AbstractListHavingPoperties<ParticipantEvidence> {
        public ParticipantList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ParticipantEvidence added) {

            if (size() > 2){
                throw new IllegalArgumentException("A BinaryInteraction cannot have more that two participants");
            }
            else if (interactorA == null){
                if (! (added instanceof Interactor)){
                    removeOnly(added);

                    Interactor mitab = new Interactor();
                    ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(added, mitab);
                    interactorA = mitab;
                    addOnly(interactorA);

                    interactorA.setInteraction(getInstance());
                }
                else {
                    interactorA = (Interactor) added;
                    interactorA.setInteraction(getInstance());
                }
            }
            else {
                if (! (added instanceof Interactor)){
                    removeOnly(added);

                    Interactor mitab = new Interactor();
                    ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(added, mitab);
                    interactorB = mitab;
                    addOnly(interactorB);
                    interactorB.setInteraction(getInstance());
                }
                else {
                    interactorB = (Interactor) added;
                    interactorB.setInteraction(getInstance());
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(ParticipantEvidence removed) {
            removed.setInteraction(null);
            if (removed == interactorA){
                interactorA = null;
            }
            else if (removed == interactorB){
                interactorB = null;
            }
        }

        @Override
        protected void clearProperties() {
            interactorA = null;
            interactorB = null;
        }
    }
}