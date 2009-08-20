/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.xml.model;

import psidev.psi.mi.xml.PsimiXmlVersion;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Factory to generate PSI MI XML model objects.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public final class PsiFactory {

    private static int id = 1;

    private PsiFactory() {
    }

    public static EntrySet createEntrySet(PsimiXmlVersion version, Entry ... entries) {
        return createEntrySet(version, Arrays.asList(entries));
    }

    public static EntrySet createEntrySet(PsimiXmlVersion version, Collection<Entry> entries) {
        EntrySet entrySet = new EntrySet(version);
        entrySet.getEntries().addAll(entries);
        return entrySet;
    }

    public static Entry createEntry(Source source, Collection<Interaction> interactions) {
        Entry entry = new Entry();

        entry.setSource(source);

        for (Interaction interaction : interactions) {
            entry.getInteractions().add(interaction);

            for (ExperimentDescription expDesc : interaction.getExperiments()) {
                entry.getExperiments().add(expDesc);
            }

            for (Participant part : interaction.getParticipants()) {
                entry.getInteractors().add(part.getInteractor());
            }
        }

        return entry;
    }

    public static void assignNextId(Object object) {
        if (object instanceof HasId) {
            ((HasId)object).setId(id++);
        }
    }

    public static Source createSource(String name) {
        return createSource(name, null, null, null);
    }

    public static Source createSource(String name, String description, String sourcePublicationId, String miId) {
        Source source = new Source();

        Names names = createNames(name, description);

        source.setNames(names);

        if (miId != null) {
            Xref miRef = createXrefPsiMi(miId);
            source.setXref(miRef);
        }

        if (sourcePublicationId != null) {
            source.setBibref(createBibrefPubmed(sourcePublicationId));
        }

        return source;
    }

    public static Names createNames(String name, String description) {
        Names names = new Names();
        names.setShortLabel(name);
        names.setFullName(description);
        return names;
    }

    public static Bibref createBibrefPubmed(String publicationId) {
        return createBibref(publicationId, "MI:0446", "pubmed");
    }

    public static Bibref createBibref(String publicationId, String dbAc, String dbName) {
        DbReference dbReference = createDbReference(publicationId, dbAc, dbName, "MI:0358", "primary-reference");
        Xref xref = new Xref(dbReference);

        return new Bibref(xref);
    }

    public static Interaction createInteraction(String name, ExperimentDescription experiment, InteractionType interactionType, Collection<Participant> participants) {
        Interaction interaction = new Interaction();
        assignNextId(interaction);

        interaction.setNames(createNames(name, null));

        for (Participant participant : participants) {
            interaction.getParticipants().add(participant);
            participant.setInteraction(interaction);
        }

        interaction.getInteractionTypes().add(interactionType);
        interaction.getExperiments().add(experiment);

        return interaction;
    }

    public static InteractionType createInteractionType(String mi, String name) {
        return createCvType(InteractionType.class, mi, name);
    }

    public static Participant createParticipant(Interactor interactor, BiologicalRole biologicalRole, ExperimentalRole experimentalRole) {
        Participant participant = new Participant();
        assignNextId(participant);
        participant.setInteractor(interactor);
        participant.setBiologicalRole(biologicalRole);
        participant.getExperimentalRoles().add(experimentalRole);

        return participant;
    }

    public static Participant createParticipantBait(Interactor interactor) {
        BiologicalRole biologicalRole = createBiologicalRoleUnspecified();
        ExperimentalRole experimentalRole = createExperimentalRoleBait();
        return createParticipant(interactor, biologicalRole, experimentalRole);
    }

    public static Participant createParticipantPrey(Interactor interactor) {
        BiologicalRole biologicalRole = createBiologicalRoleUnspecified();
        ExperimentalRole experimentalRole = createExperimentalRolePrey();
        return createParticipant(interactor, biologicalRole, experimentalRole);
    }

    public static ParticipantIdentificationMethod createParticipantIdentificationMethod(String mi, String name) {
        return createCvType(ParticipantIdentificationMethod.class, mi, name);
    }

    public static ParticipantIdentificationMethod createParticipantIdentificationMethodPredetermined() {
        return createParticipantIdentificationMethod("MI:0396", "predetermined");
    }

    public static BiologicalRole createBiologicalRole(String mi, String name) {
        return createCvType(BiologicalRole.class, mi, name);
    }

    public static BiologicalRole createBiologicalRoleUnspecified() {
        return createBiologicalRole("MI:0499", "unspecified role");
    }

    public static ExperimentalRole createExperimentalRole(String mi, String name) {
        return createCvType(ExperimentalRole.class, mi, name);
    }

    public static ExperimentalRole createExperimentalRoleUnspecified() {
        return createExperimentalRole("MI:0499", "unspecified role");
    }

    public static ExperimentalRole createExperimentalRoleBait() {
        return createExperimentalRole("MI:0496", "bait");
    }

    public static ExperimentalRole createExperimentalRolePrey() {
        return createExperimentalRole("MI:0498", "prey");
    }

    public static Interactor createInteractor(String primaryId, String dbMi, InteractorType interactorType, Organism organism) {
        Interactor interactor = new Interactor();
        assignNextId(interactor);
        interactor.setNames(createNames(primaryId, null));
        interactor.setInteractorType(interactorType);
        interactor.setOrganism(organism);

        interactor.setXref(createXrefIdentity(primaryId, dbMi, dbMi));

        return interactor;
    }

    public static Interactor createInteractorUniprotProtein(String primaryId, int taxId, String organismName) {
        return createInteractorUniprotProtein(primaryId, createOrganism(taxId, organismName));
    }

    public static Interactor createInteractorUniprotProtein(String primaryId, Organism organism) {
        InteractorType interactorType = createInteractorType("MI:0326", "protein");
        return createInteractor(primaryId, "MI:0486", interactorType, organism);
    }

    public static Interactor createInteractorChebiSmallMolecule(String primaryId, int taxId, String organismName) {
        Organism organism = PsiFactory.createOrganism(taxId, organismName);
        InteractorType interactorType = createInteractorType("MI:0328", "small molecule");
        return createInteractor(primaryId, "MI:0474", interactorType, organism);
    }

    public static ExperimentDescription createExperiment(String name, String publicationId, InteractionDetectionMethod interactionDetectionMethod,
                                                         ParticipantIdentificationMethod participantIdentificationMethod, Organism hostOrganism) {
        ExperimentDescription experiment = new ExperimentDescription();
        assignNextId(experiment);
        experiment.setNames(createNames(name, null));
        experiment.setBibref(createBibrefPubmed(publicationId));
        experiment.setInteractionDetectionMethod(interactionDetectionMethod);
        experiment.setParticipantIdentificationMethod(participantIdentificationMethod);
        experiment.getHostOrganisms().add(hostOrganism);

        return experiment;
    }

    public static InteractionDetectionMethod createInteractionDetectionMethod(String mi, String label) {
        return createCvType(InteractionDetectionMethod.class, mi, label);
    }

    public static Confidence createConfidence(String value, String unitMi, String unitName){
       Unit unit = createCvType( Unit.class, unitMi, unitName);
       return new Confidence(unit, value);
    }

    public static Organism createOrganism(int taxid, String name) {
        Organism organism = new Organism();
        organism.setNames(createNames(name, null));
        organism.setNcbiTaxId(taxid);

        return organism;
    }

    public static Organism createOrganismHuman() {
        return createOrganism(9606, "human");
    }

    public static Organism createOrganismInVitro() {
        return createOrganism(-1, "in vitro");
    }

    public static Organism createOrganismChemicalSynthesis() {
        return createOrganism(-2, "chemical synthesis");
    }

    public static Organism createOrganismUnknown() {
        return createOrganism(-3, "unknown");
    }

    public static Organism createOrganismInVivo() {
        return createOrganism(-4, "in vivo");
    }

    public static Organism createOrganismInSilico() {
        return createOrganism(-5, "in silico");
    }

    public static Xref createXrefPsiMi(String primaryId) {
        return createXrefIdentity(primaryId, "MI:0488", "psi-mi");
    }

    public static Xref createXref(String primaryId, String dbMi, String dbName, String qualifierMi, String qualifierName) {
        Xref xref = new Xref();
        xref.setPrimaryRef(createDbReference(primaryId, dbMi, dbName, qualifierMi, qualifierName));
        return xref;
    }

    public static Xref createXrefIdentity(String primaryId, String dbMi, String dbName) {
        Xref xref = new Xref();
        xref.setPrimaryRef(createDbReference(primaryId, dbMi, dbName, "MI:0356", "identity"));
        return xref;
    }

    public static Alias createAlias(String value, String aliasType, String aliasTypeAc) {
        Alias alias = new Alias();
        alias.setValue(value);
        alias.setType(aliasType);
        alias.setTypeAc(aliasTypeAc);

        return alias;
    }

    private static <C extends CvType> C createCvType(Class<C> cvTypeClass, String name) {
        C cv;

        try {
            cv = cvTypeClass.newInstance();
            cv.setNames(createNames(name, null));
        } catch (Exception e) {
            throw new IllegalArgumentException("Is this a cvType?: "+cvTypeClass, e);
        }

        return cv;
    }

    public static <C extends CvType> C createCvType(Class<C> cvTypeClass, String miRef, String label) {
        C cv = createCvType(cvTypeClass, label);
        cv.setXref(createXrefPsiMi(miRef));

        return cv;
    }

    public static InteractorType createInteractorType(String mi, String name) {
        InteractorType intType = createCvType(InteractorType.class, name);
        intType.setXref(createXrefPsiMi(mi));
        return intType;
    }

    public static Attribute createAttribute(String name, String value) {
        return new Attribute(name, value);
    }

    public static DbReference createDbReference(String primaryId, String dbAc, String dbName, String refTypeAc, String refTypeName) {
        DbReference dbRef = new DbReference();
        dbRef.setId(primaryId);
        dbRef.setDbAc(dbAc);
        dbRef.setDb(dbName);
        dbRef.setRefType(refTypeName);
        dbRef.setRefTypeAc(refTypeAc);
        return dbRef;
    }

    public static DbReference createDbReferencePsiMi(String psiMiRef) {
        DbReference ref = createDbReferenceIdentity(psiMiRef, "MI:0488", "psi-mi");
        ref.setId(psiMiRef);

        return ref;
    }

    public static DbReference createDbReferenceIdentity(String primaryId, String dbAc, String dbName) {
        return createDbReference(primaryId, dbAc, dbName, "MI:0356", "identity");
    }

    public static Feature createFeature(String name, FeatureType featureType, Range range) {
        return createFeature(name, featureType, Collections.singleton(range));
    }

    public static Feature createFeature(String name, FeatureType featureType, Collection<Range> ranges) {
        Feature feature = new Feature();
        assignNextId(feature);
        feature.setNames(createNames(name, name));
        feature.getRanges().addAll(ranges);
        feature.setFeatureType(featureType);
        return feature;
    }

    public static FeatureType createFeatureType(String mi, String label) {
        return createCvType(FeatureType.class, mi, label);
    }

    public static FeatureType createFeatureTypeSufficientBinding() {
        return createCvType(FeatureType.class, "MI:0442", "sufficient for binding");
    }

    public static FeatureType createFeatureTypeMutation() {
        return createCvType(FeatureType.class, "MI:0118", "mutation");
    }

    public static Range createRange(RangeStatus startStatus, RangeStatus endStatus, long startPosition, long endPosition) {
        Position start = new Position(startPosition);
        Position end = new Position(endPosition);

        return new Range(startStatus, start, endStatus, end);
    }

    public static Range createRangeCertain(long startPosition, long endPosition) {
        Position start = new Position(startPosition);
        Position end = new Position(endPosition);

        return new Range(createRangeStatusCertain(), start, createRangeStatusCertain(), end);
    }

    public static RangeStatus createRangeStatus(String mi, String label) {
        return createCvType(RangeStatus.class, mi, label);
    }

    public static RangeStatus createRangeStatusCertain() {
        return createRangeStatus("MI:0335", "certain");
    }

    public static RangeStatus createRangeStatusNTerminal() {
        return createRangeStatus("MI:0340", "n-terminal");
    }

    public static RangeStatus createRangeStatusCTerminal() {
        return createRangeStatus("MI:0334", "c-terminal");
    }
}