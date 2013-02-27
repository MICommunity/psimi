/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.utils;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.OrganismImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract binary interaction handler.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractBinaryInteractionHandler<T extends BinaryInteraction> {

    public abstract T newBinaryInteraction(Interactor i1, Interactor i2);

    public abstract Interactor newInteractor(List<CrossReference> identifiers);

    public T merge(T bi1, T bi2) {
        T merged = cloneBinaryInteraction(bi1);

        merged.setInteractorA(mergeInteractorA(bi1.getInteractorA(), bi2.getInteractorA()));
        merged.setInteractorB(mergeInteractorB(bi1.getInteractorB(), bi2.getInteractorB()));

        populateBinaryInteraction(bi2, merged);

        return merged;
    }

    public T cloneBinaryInteraction(T interaction) {
        T clone = newBinaryInteraction(cloneInteractor(interaction.getInteractorA()),
                                       cloneInteractor(interaction.getInteractorB()));

        populateBinaryInteraction(interaction, clone);

        return clone;
    }

    protected void populateBinaryInteraction(T source, T target) {
        target.setPublications(source.getPublications());
        target.setAuthors(source.getAuthors());
        target.setInteractionTypes(source.getInteractionTypes());
        target.setDetectionMethods(source.getDetectionMethods());
        target.setConfidenceValues(source.getConfidenceValues());
        target.setSourceDatabases(source.getSourceDatabases());
        target.setInteractionAcs(source.getInteractionAcs());
        target.setAnnotations(source.getInteractionAnnotations());
        target.setChecksums(source.getInteractionChecksums());
        target.setComplexExpansion(source.getComplexExpansion());
        target.setCreationDate(source.getCreationDate());
        target.setParameters(source.getInteractionParameters());

        if (target.getHostOrganism() == null){
            target.setHostOrganism(source.getHostOrganism());
        }
        else if (source.hasHostOrganism()){
            target.getHostOrganism().setIdentifiers(source.getHostOrganism().getIdentifiers());
        }
    }

    protected Interactor mergeInteractorA(Interactor i1, Interactor i2) {
        return mergeInteractors(i1, i2);
    }

    protected Interactor mergeInteractorB(Interactor i1, Interactor i2) {
        return mergeInteractors(i1, i2);
    }

    protected Interactor mergeInteractors(Interactor i1, Interactor i2) {
        if (i1 != null && i2 != null){
            Interactor merged = cloneInteractor(i1);
            merged.getIdentifiers().addAll(i2.getIdentifiers());

            populateInteractor(i2, merged);

            return merged;
        }
        else if (i1 != null){
            return i1;
        }
        else {
            return i2;
        }
    }

    public Interactor cloneInteractor(Interactor interactor) {
        if (interactor == null){
           return null;
        }
        Interactor clone = newInteractor( new ArrayList( interactor.getIdentifiers() ) );

        populateInteractor(interactor, clone);

        return clone;
    }

    protected void populateInteractor(Interactor source, Interactor target) {
        if (target != null && source != null){
            target.getAlternativeIdentifiers().addAll(source.getAlternativeIdentifiers());
            target.getAliases().addAll(source.getAliases());

            if ( !target.hasOrganism() ) {
                target.setOrganism(new OrganismImpl());
            }

            if( source.hasOrganism() ) {
                target.getOrganism().getIdentifiers().addAll(source.getOrganism().getIdentifiers());
            }

            target.setAnnotations(source.getInteractorAnnotations());
            target.setBiologicalRoles(source.getBiologicalRoles());
            target.setChecksums(source.getChecksums());
            target.setFeatures(source.getFeatures());
            target.setInteractorTypes(source.getInteractorTypes());
            target.setParticipantIdentificationMethods(source.getParticipantIdentificationMethods());
            target.setStoichiometry(source.getInteractorStoichiometry());
            target.setXrefs(source.getInteractorXrefs());
        }
    }
}