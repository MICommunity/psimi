/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.converter.tab2xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.xml2tab.InteractionConverter;
import psidev.psi.mi.tab.converter.xml2tab.InteractorConverter;
import psidev.psi.mi.tab.converter.xml2tab.MitabInteractionConverter;
import psidev.psi.mi.tab.converter.xml2tab.NullCrossReference;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.xml.PsimiXmlVersion;
import psidev.psi.mi.xml.model.*;

import java.util.*;

/**
 * Utility class allowing to convert MITAB25 data into PSI-MI XML.
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id$
 */

public class Tab2Xml {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog(Tab2Xml.class);

    /**
     * Interaction Converter
     */
    private InteractionConverter<?> interactionConverter;

    /**
     * Collection of all Interactions.
     */
    private Collection<Interaction> interactions;

    /**
     * Collection of Interactors.
     */
    private Collection<Interactor> interactors = new ArrayList<Interactor>();

    /**
     * Strategy defining which interactor name used.
     */
    private InteractorNameBuilder interactorNameBuilder;

    protected static final String IREFINDEX = "irefindex";

    public Tab2Xml() {
        this(new MitabInteractionConverter());
    }

    public Tab2Xml(InteractionConverter<?> interactionConverter) {
        this.interactionConverter = interactionConverter;
    }

    /**
     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
     *
     * @param binaryInteractions
     * @return EntrySet
     * @throws IllegalAccessException
     * @throws XmlConversionException
     */
    public EntrySet convert(Collection<BinaryInteraction> binaryInteractions) throws IllegalAccessException, XmlConversionException {
        return convert(binaryInteractions, createSource(null, null));
    }

    /**
     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
     *
     * @param binaryInteractions
     * @return EntrySet
     * @throws IllegalAccessException
     * @throws XmlConversionException
     */
    public EntrySet convert(Collection<BinaryInteraction> binaryInteractions, String dbMi, String dbName) throws IllegalAccessException, XmlConversionException {
        return convert(binaryInteractions, createSource(dbName, dbMi));
    }


    /**
     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
     *
     * @param mitabInteractions
     * @return EntrySet
     * @throws IllegalAccessException
     * @throws XmlConversionException
     */
    public EntrySet convert(Collection<BinaryInteraction> mitabInteractions, Source source) throws IllegalAccessException, XmlConversionException {

        if (mitabInteractions.isEmpty()) {
            throw new IllegalAccessException("No binary interactions found in the collection");
        }

        EntrySet entrySet = new EntrySet(PsimiXmlVersion.VERSION_25_UNDEFINED);

        Entry entry = new Entry();
        entry.setSource(source);

        interactions = new ArrayList<Interaction>();

        Map<String, Collection<Participant>> interactionMap = createInteractionMap(mitabInteractions);

        for (BinaryInteraction<?> binaryInteraction : mitabInteractions) {

            interactions = interactionConverter.fromMitab(binaryInteraction, interactionMap);

            entry.getInteractions().addAll(interactions);
        }

        entrySet.getEntries().add(entry);

        return entrySet;
    }

    /**
     * Getter for property 'interactorNameBuilder'
     */
    public InteractorNameBuilder getInteractorNameBuilder() {
        return interactorNameBuilder;
    }

    /**
     * Setter for property 'interactorNameBuilder'
     */
    public void setInteractorNameBuilder(InteractorNameBuilder interactorNameBuilder) {
        this.interactorNameBuilder = interactorNameBuilder;
    }

    public Collection<Interaction> getInteractions() {
        return interactions;
    }

    public Collection<Interactor> getInteractors() {
        return interactors;
    }

    /**
     * This method create a Map which is used to find the ExperimentalRole (bait,prey or unspecified)
     * key = InteractionID and value = tab.model.InteractorPair(s)
     *
     * @param miTabInteractions
     * @return
     * @throws IllegalAccessException
     */
    protected Map<String, Collection<Participant>> createInteractionMap(Collection<BinaryInteraction> miTabInteractions) throws IllegalAccessException, XmlConversionException {
        InteractorConverter<?> interactorConverter = interactionConverter.getInteractorConverter();

        Map<String, Collection<Participant>> interactionMap = new HashMap<String, Collection<Participant>>();
        interactorConverter.setInteractorNameBuilder(getInteractorNameBuilder());

        for (BinaryInteraction<?> binaryInteraction : miTabInteractions) {
            final List<CrossReference> crossReference = binaryInteraction.getInteractionAcs();

            if (crossReference.isEmpty()) {
                crossReference.add(new NullCrossReference(binaryInteraction));
            }

            // Arbitrarily pick the first one
            String interactionId = crossReference.get(0).getIdentifier();
            interactionId = interactionId + "_" + binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier()
                    + "_" + binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier();

            Interactor iA = interactorConverter.fromMitab(binaryInteraction.getInteractorA());
            Interactor iB = interactorConverter.fromMitab(binaryInteraction.getInteractorB());

            // reusing the interactor in the participants
            iA = checkInteractor(iA);
            iB = checkInteractor(iB);

            // Note: the index is not used by these methods, just left here not to break the API.
            Participant pA = interactorConverter.buildParticipantA(iA, binaryInteraction, 0);
            Participant pB = interactorConverter.buildParticipantB(iB, binaryInteraction, 0);

            Collection<Participant> participants = null;
            if (!interactionMap.containsKey(interactionId)) {
                participants = new ArrayList<Participant>();
            } else {
                participants = interactionMap.get(interactionId);
            }

            if (!participants.contains(pA)) {
                participants.add(pA);
            }
            if (!participants.contains(pB)) {
                participants.add(pB);
            }

            interactionMap.put(interactionId, participants);
        }
        return interactionMap;
    }

    private psidev.psi.mi.xml.model.Interactor checkInteractor(psidev.psi.mi.xml.model.Interactor interactor1) {
        psidev.psi.mi.xml.model.Interactor interactor = interactor1;

        for (psidev.psi.mi.xml.model.Interactor interactor2 : interactors) {

            if (interactor1.getNames().equals(interactor2.getNames()) &&
                    interactor1.getXref().equals(interactor2.getXref())) {
                interactor = interactor2;
                break;
            }

        }
        if (interactor.equals(interactor1)) {
            if (!interactors.contains(interactor1)) {
                interactors.add(interactor);
            }
        }

        return interactor;
    }

    /**
     * This method create a new Source of the binaryInteraction data.
     *
     * @return source
     */
    private Source createSource(String db, String dbMi) {

        Source source = new Source();

        // set default release Date
        Date date = new Date();
        source.setReleaseDate(date);

        // set Source name
        Names names = new Names();

        if (db != null) {
            names.setShortLabel(db);
        } else {
            names.setShortLabel("unspecified");
        }

        source.setNames(names);

        if (dbMi != null && dbMi.startsWith("MI:")) {
            DbReference dbReference = new DbReference("psi-mi", "MI:0488", dbMi, "primary-reference", "MI:0358");
            Xref xref = new Xref(dbReference);
            source.setXref(xref);
        }

        return source;
    }
}
//
///*
// * Copyright 2001-2007 The European Bioinformatics Institute.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package psidev.psi.mi.tab.converter.tab2xml;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import psidev.psi.mi.tab.converter.xml2tab.InteractionConverter;
//import psidev.psi.mi.tab.converter.xml2tab.InteractorConverter;
//import psidev.psi.mi.tab.converter.xml2tab.MitabInteractionConverter;
//import psidev.psi.mi.tab.converter.xml2tab.NullCrossReference;
//import psidev.psi.mi.tab.model.BinaryInteraction;
//import psidev.psi.mi.tab.model.CrossReference;
//import psidev.psi.mi.xml.PsimiXmlVersion;
//import psidev.psi.mi.xml.model.*;
//
//import java.util.*;
//
///**
// * Utility class allowing to convert MITAB25 data into PSI-MI XML.
// *
// * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
// * @version $Id$
// */
//
//public class Tab2Xml {
//
//    /**
//     * Sets up a logger for that class.
//     */
//    public static final Log log = LogFactory.getLog(Tab2Xml.class);
//
//    /**
//     * Interaction Converter
//     */
//    private InteractionConverter<?> interactionConverter;
//
//    /**
//     * Collection of all Interactions.
//     */
//    private Collection<Interaction> interactions;
//
//    /**
//     * Collection of Interactors.
//     */
//    private Collection<Interactor> interactors = new ArrayList<Interactor>();
//
//    /**
//     * Strategy defining which interactor name used.
//     */
//    private InteractorNameBuilder interactorNameBuilder;
//
//    protected static final String IREFINDEX = "irefindex";
//
//    public Tab2Xml() {
//        this(new MitabInteractionConverter());
//    }
//
//    public Tab2Xml(InteractionConverter<?> interactionConverter) {
//        this.interactionConverter = interactionConverter;
//    }
//
//    /**
//     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
//     *
//     * @param binaryInteractions
//     * @return EntrySet
//     * @throws IllegalAccessException
//     * @throws XmlConversionException
//     */
//    public EntrySet convert(Collection<BinaryInteraction> binaryInteractions) throws IllegalAccessException, XmlConversionException {
//        return convert(binaryInteractions, createSource(null, null,null));
//    }
//
//    /**
//     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
//     *
//     * @param binaryInteractions
//     * @return EntrySet
//     * @throws IllegalAccessException
//     * @throws XmlConversionException
//     */
//    public EntrySet convert(Collection<BinaryInteraction> binaryInteractions, String dbMi, String dbName) throws IllegalAccessException, XmlConversionException {
//        return convert(binaryInteractions, createSource(dbName, dbMi, null));
//    }
//
//    /**
//     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
//     *
//     * @param binaryInteractions
//     * @return EntrySet
//     * @throws IllegalAccessException
//     * @throws XmlConversionException
//     */
//    public EntrySet convert(Collection<BinaryInteraction> binaryInteractions, String dbMi, String dbName, Date date) throws IllegalAccessException, XmlConversionException {
//        return convert(binaryInteractions, createSource(dbName, dbMi, date));
//    }
//
//
//    /**
//     * This method convert a Collection of BinaryInteractions to a EntrySet, grouping the interactions by entries depending on its source db.
//     *
//     * @param mitabInteractions
//     * @return EntrySet
//     * @throws IllegalAccessException
//     * @throws XmlConversionException
//     */
//    public EntrySet convert(Collection<BinaryInteraction> mitabInteractions, Source source) throws IllegalAccessException, XmlConversionException {
//
//        if (mitabInteractions.isEmpty()) {
//            throw new IllegalAccessException("No binary interactions found in the collection");
//        }
//
//        EntrySet entrySet = new EntrySet(PsimiXmlVersion.VERSION_25_UNDEFINED);
//
//        Entry entry = new Entry();
//        entry.setSource(source);
//
//        interactions = new ArrayList<Interaction>();
//
//        Map<String, Collection<Participant>> interactionMap = createInteractionMap(mitabInteractions);
//
//        for (BinaryInteraction<?> binaryInteraction : mitabInteractions) {
//
//            interactions = interactionConverter.fromMitab(binaryInteraction, interactionMap);
//
//            entry.getInteractions().addAll(interactions);
//        }
//
//        entrySet.getEntries().add(entry);
//
//        return entrySet;
//    }
//
//    /**
//     * Getter for property 'interactorNameBuilder'
//     */
//    public InteractorNameBuilder getInteractorNameBuilder() {
//        return interactorNameBuilder;
//    }
//
//    /**
//     * Setter for property 'interactorNameBuilder'
//     */
//    public void setInteractorNameBuilder(InteractorNameBuilder interactorNameBuilder) {
//        this.interactorNameBuilder = interactorNameBuilder;
//    }
//
//    public Collection<Interaction> getInteractions() {
//        return interactions;
//    }
//
//    public Collection<Interactor> getInteractors() {
//        return interactors;
//    }
//
//    /**
//     * This method create a Map which is used to find the ExperimentalRole (bait,prey or unspecified)
//     * key = InteractionID and value = tab.model.InteractorPair(s)
//     *
//     * @param miTabInteractions
//     * @return
//     * @throws IllegalAccessException
//     */
//    protected Map<String, Collection<Participant>> createInteractionMap(Collection<BinaryInteraction> miTabInteractions) throws IllegalAccessException, XmlConversionException {
//        InteractorConverter<?> interactorConverter = interactionConverter.getInteractorConverter();
//
//        Map<String, Collection<Participant>> interactionMap = new HashMap<String, Collection<Participant>>();
//        interactorConverter.setInteractorNameBuilder(getInteractorNameBuilder());
//
//        for (BinaryInteraction<?> binaryInteraction : miTabInteractions) {
//            final List<CrossReference> crossReference = binaryInteraction.getInteractionAcs();
//
//            if (crossReference.isEmpty()) {
//                crossReference.add(new NullCrossReference(binaryInteraction));
//            }
//
//            // Arbitrarily pick the first one
//            String interactionId = crossReference.get(0).getIdentifier();
//            interactionId = interactionId + "_" + binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier()
//                    + "_" + binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier();
//
//            Interactor iA = interactorConverter.fromMitab(binaryInteraction.getInteractorA());
//            Interactor iB = interactorConverter.fromMitab(binaryInteraction.getInteractorB());
//
//            // reusing the interactor in the participants
//            iA = checkInteractor(iA);
//            iB = checkInteractor(iB);
//
//            // Note: the index is not used by these methods, just left here not to break the API.
//            Participant pA = interactorConverter.buildParticipantA(iA, binaryInteraction, 0);
//            Participant pB = interactorConverter.buildParticipantB(iB, binaryInteraction, 0);
//
//            Collection<Participant> participants = null;
//            if (!interactionMap.containsKey(interactionId)) {
//                participants = new ArrayList<Participant>();
//            } else {
//                participants = interactionMap.get(interactionId);
//            }
//
//            if (!participants.contains(pA)) {
//                participants.add(pA);
//            }
//            if (!participants.contains(pB)) {
//                participants.add(pB);
//            }
//
//            interactionMap.put(interactionId, participants);
//        }
//        return interactionMap;
//    }
//
//    private psidev.psi.mi.xml.model.Interactor checkInteractor(psidev.psi.mi.xml.model.Interactor interactor1) {
//        psidev.psi.mi.xml.model.Interactor interactor = interactor1;
//
//        for (psidev.psi.mi.xml.model.Interactor interactor2 : interactors) {
//
//            if (interactor1.getNames().equals(interactor2.getNames()) &&
//                    interactor1.getXref().equals(interactor2.getXref())) {
//                interactor = interactor2;
//                break;
//            }
//
//        }
//        if (interactor.equals(interactor1)) {
//            if (!interactors.contains(interactor1)) {
//                interactors.add(interactor);
//            }
//        }
//
//        return interactor;
//    }
//
//    /**
//     * This method create a new Source of the binaryInteraction data.
//     *
//     * @return source
//     */
//    private Source createSource(String db, String dbMi, Date date) {
//
//        Source source = new Source();
//
//        // set default release Date
//        if (date == null) {
//            source.setReleaseDate(new Date());
//        }
//        source.setReleaseDate(date);
//
//        // set Source name
//        Names names = new Names();
//
//        if (db != null) {
//            names.setShortLabel(db);
//        } else {
//            names.setShortLabel("unspecified");
//        }
//
//        source.setNames(names);
//
//        if (dbMi != null && dbMi.startsWith("MI:")) {
//            DbReference dbReference = new DbReference("psi-mi", "MI:0488", dbMi, "primary-reference", "MI:0358");
//            Xref xref = new Xref(dbReference);
//            source.setXref(xref);
//        }
//
//        return source;
//    }
//}