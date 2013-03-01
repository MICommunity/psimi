/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.clone.ParticipantCloner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class BinaryInteractionImpl extends AbstractBinaryInteraction<Interactor>{

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
    protected void initialiseParticipantEvidences(){
        super.initialiseParticipantEvidencesWith(Collections.EMPTY_LIST);
    }

    @Override
    protected void initialiseParticipantEvidencesWith(Collection<ParticipantEvidence> participants) {
        if (participants == null){
            super.initialiseParticipantEvidencesWith(Collections.EMPTY_LIST);
        }
        else{
            for (ParticipantEvidence p : participants){
                addParticipantEvidence(p);
            }
        }
    }

    @Override
    public Collection<? extends ParticipantEvidence> getParticipantEvidences() {
        return Arrays.asList(interactorA, interactorB);
    }

    @Override
    public boolean addParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (interactorA != null && interactorB != null){
            throw new IllegalArgumentException("A BinaryInteraction cannot have more that two participants");
        }
        else if (interactorA == null){
            if (! (part instanceof Interactor)){

                Interactor mitab = new Interactor();
                ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(part, mitab);
                interactorA = mitab;
                interactorA.setInteractionEvidence(getInstance());
            }
            else {
                interactorA = (Interactor) part;
                interactorA.setInteractionEvidence(getInstance());
            }
        }
        else {
            if (! (part instanceof Interactor)){
                Interactor mitab = new Interactor();
                ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(part, mitab);
                interactorB = mitab;
                interactorB.setInteractionEvidence(getInstance());
            }
            else {
                interactorB = (Interactor) part;
                interactorB.setInteractionEvidence(getInstance());
            }
        }
        return true;
    }

    @Override
    public boolean removeParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (part.equals(interactorA)){
            interactorA = null;
            return true;
        }
        else if (part.equals(interactorB)){
            interactorB = null;
            return true;
        }

        return false;
    }
}