/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.BinaryInteractionImpl;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Organism;

/**
 * Default MITAB implementation.
 */
public class MitabInteractionConverter extends InteractionConverter<BinaryInteraction<Interactor>> {

    private MitabInteractorConverter interactorConverter;

    public MitabInteractionConverter() {
        this.interactorConverter = new MitabInteractorConverter();
    }

    /**
     * This method updates the default hostOrganism for the xml.
     *
     * @param binaryInteraction Binary interaction where I get the additional Information.
     * @param hostOrganism      hostOrganism to be processed.
     * @param index             index of the hostOrganism in the BinaryInteraction.
     */
    protected void updateHostOrganism(BinaryInteraction<Interactor> binaryInteraction, Organism hostOrganism, int index) {
        // nothing
    }

    protected BinaryInteraction newBinaryInteraction(Interactor interactorA, Interactor interactorB) {
        return new BinaryInteractionImpl(interactorA, interactorB);
    }

    public InteractorConverter<?> getInteractorConverter() {
        return interactorConverter;
    }

    protected void populateInteractionFromMitab(Interaction interaction, BinaryInteraction<?> binaryInteraction, int index) {
       // nothing
    }

    public psidev.psi.mi.xml.model.Interactor fromMitabInteractor(Interactor interactor)
        throws XmlConversionException{
         return interactorConverter.fromMitab(interactor);
    }

}
