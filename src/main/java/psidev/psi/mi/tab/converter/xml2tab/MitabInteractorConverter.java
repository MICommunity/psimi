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
package psidev.psi.mi.tab.converter.xml2tab;

import psidev.psi.mi.tab.converter.IdentifierGenerator;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.model.Feature;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.ParticipantIdentificationMethod;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.Alias;
import psidev.psi.mi.xml.model.*;

import java.util.Collection;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabInteractorConverter extends InteractorConverter<Interactor> {

    public Participant buildParticipantA(psidev.psi.mi.xml.model.Interactor xmlInteractor,
                                         BinaryInteraction binaryInteraction,
                                         int index) throws XmlConversionException {


        //New functionality to mitab 2.7
        psidev.psi.mi.tab.model.Interactor mitabInteractor = binaryInteraction.getInteractorA();
        return buildParticipant(xmlInteractor, mitabInteractor);
    }

    public Participant buildParticipantB(psidev.psi.mi.xml.model.Interactor xmlInteractor,
                                         BinaryInteraction binaryInteraction,
                                         int index) throws XmlConversionException {


        psidev.psi.mi.tab.model.Interactor mitabInteractor = binaryInteraction.getInteractorB();
        return buildParticipant(xmlInteractor, mitabInteractor);
    }

    protected Participant buildParticipant(psidev.psi.mi.xml.model.Interactor interactor, psidev.psi.mi.tab.model.Interactor mitabInteractor) throws XmlConversionException {


        Participant participant = null;

        if (interactor != null && mitabInteractor != null) {

            participant = new Participant();
            participant.setId(IdentifierGenerator.getInstance().nextId());
            participant.setInteractor(interactor);


            //Fields 17 18 BiologicalRole
            //TODO check the logic
            Collection<CrossReference> biologicalRoles = mitabInteractor.getBiologicalRoles();
            BiologicalRole biologicalRole;

            /* In this case fromMitab return a empty list, so we add the default value */
            if (biologicalRoles.isEmpty()) {
                //Default value
                biologicalRole = new BiologicalRole();
                Names names = new Names();

                //For mitab without ExperimentalRole
                names.setShortLabel("unspecified role");
                names.setFullName("unspecified role");

                DbReference dbRef = new DbReference();
                dbRef.setDb("psi-mi");
                dbRef.setDbAc("MI:0488");
                dbRef.setId("MI:0499");
                dbRef.setRefType("identity");
                dbRef.setRefTypeAc("MI:0356");

                biologicalRole.setNames(names);
                biologicalRole.setXref(new Xref(dbRef));

            }
            else{
                  biologicalRole = CrossReferenceConverter.fromMitab(biologicalRoles, BiologicalRole.class);
            }

            participant.setBiologicalRole(biologicalRole);


//            toMitab()
//
//            Interactor A = new Interactor();
//            BiologicalRole biologicalRole1 = participant.getBiologicalRole();
//
//
//            A.setBiologicalRoles();

            //Fields 19 20 ExperimentalRole

            /*If we have more than one role in the interaction, these are secondary refs in the xml*/
            Collection<CrossReference> experimentalRoles = mitabInteractor.getExperimentalRoles();
            ExperimentalRole experimentalRole;

            /* In this case fromMitab return a empty list, so we add the default value */
            if (experimentalRoles.isEmpty()) {
                //Default value
                experimentalRole = new ExperimentalRole();
                Names names = new Names();

                //For mitab without ExperimentalRole
                names.setShortLabel("unspecified role");
                names.setFullName("unspecified role");

                DbReference dbRef = new DbReference();
                dbRef.setDb("psi-mi");
                dbRef.setDbAc("MI:0488");
                dbRef.setId("MI:0499");
                dbRef.setRefType("identity");
                dbRef.setRefTypeAc("MI:0356");

                experimentalRole.setNames(names);
                experimentalRole.setXref(new Xref(dbRef));

            } else {
                experimentalRole = CrossReferenceConverter.fromMitab(experimentalRoles, ExperimentalRole.class);

            }

            participant.getExperimentalRoles().add(experimentalRole);

            //Fields 21 22 Interactor Type (They are in InteractorConverter)

//            //Fields 23 24
//            //TODO Noe Is it correct in this place? Delete from the other InteractorConverter
//            if(!mitabInteractor.getXrefs().isEmpty()){
//
//                Iterator<CrossReference> xrefsIterator = mitabInteractor.getXrefs().iterator();
//                CrossReference primaryIdentifier = xrefsIterator.next();
//
//                DbReference primaryReference = new DbReference(primaryIdentifier.getDatabase(), primaryIdentifier.getIdentifier());
//                Collection<DbReference> secondaryRefs = new ArrayList<DbReference>();
//
//                while (xrefsIterator.hasNext()){
//                    CrossReference secondaryIdentifier = xrefsIterator.next();
//
//                    String database = secondaryIdentifier.getDatabase();
//                    String id = secondaryIdentifier.getIdentifier();
//
//                    DbReference secondaryRef = new DbReference(id,database);
//                    secondaryRefs.add(secondaryRef);
//
//                }
//
//                Xref xref = new Xref(primaryReference, secondaryRefs);
//                participant.setXref(xref);
//            }

            //Field 26 27 (They are in InteractorConverter)
//            Collection<Annotation> annotations = mitabInteractor.getAnnotations();


            //Field 33 34
            // TODO I don't find the field in the xml
            Collection<Checksum> checksums = mitabInteractor.getChecksums();
            if (!checksums.isEmpty()) {
                for (Checksum checksum : checksums) {
                    Attribute attribute = new Attribute(checksum.getMethodName(), checksum.getChecksum());
                    if (!participant.getAttributes().contains(attribute)) {
                        participant.getAttributes().add(attribute);
                    }
                }
            }


            //Field 37 38 Features
            Collection<Feature> features = mitabInteractor.getFeatures();
            if (!features.isEmpty()) {
                for (Feature mitabFeature : features) {
                    psidev.psi.mi.xml.model.Feature xmlfeature = new psidev.psi.mi.xml.model.Feature();

                    //Feature Id
                    xmlfeature.setId(IdentifierGenerator.getInstance().nextId());

                    //Feature Name
                    Names featureNames = new Names();

                    //In this moment we don't have information about the name of the feature
                    // we can guess that is in the text of the feature if it exists.
                    //TODO improve the method to search the name
                    if (mitabFeature.getText() != null) {

                        featureNames.setShortLabel(mitabFeature.getText());
                        featureNames.setFullName(mitabFeature.getText());

                        xmlfeature.setNames(featureNames);
                    }

                    //Feature Type
                    FeatureType featureType = new FeatureType();

                    //In this moment we don't have information about the type, only the name (we would need to search the CVTerm)
                    //TODO search the MITAB term by Name
                    //featureType.setXref(searchTermByName(names));
                    if (mitabFeature.getFeatureType() != null) {


                        Names featureTypeNames = new Names();
                        featureTypeNames.setShortLabel(mitabFeature.getFeatureType());
                        featureTypeNames.setFullName(mitabFeature.getFeatureType());

                        featureType.setNames(featureTypeNames);

                        xmlfeature.setFeatureType(featureType);

                    }

                    //Feature RangeList
                    //Review
                    if (mitabFeature.getRanges() != null && !mitabFeature.getRanges().isEmpty()) {
                        try {
                            xmlfeature.getRanges().addAll(RangeUtils.fromMitab(mitabFeature.getRanges()));
                        } catch (ConverterException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }

            //Field 39 40 Stoichiometry
            //TODO I don't find it in the xml
            Collection<Integer> stoichiometry = mitabInteractor.getStoichiometry();
            if (!stoichiometry.isEmpty()) {
                for (Integer integer : stoichiometry) {
                    //TODO Review the format
                    Attribute attribute = new Attribute("MI:0612", "comment","Stoichiometry: " + integer.toString());
                    if (!participant.getAttributes().contains(attribute)) {
                        participant.getAttributes().add(attribute);
                    }
                }
            }

            //Fields 41 42 Participant Identification Methods
            Collection<ParticipantIdentificationMethod> participantIdentificationMethods = mitabInteractor.getParticipantIdentificationMethods();
            if (!participantIdentificationMethods.isEmpty()) {

                //In this moment we assumed that we have only one identification method per Interactor
                //TODO Check the psi-mi accessions to know if we have more than one ID method and group by this ID
                //TODO Move this functionality to another class (conversion utils)
                boolean found = false;
                Names names = new Names();
                Xref xref = new Xref();

                for (ParticipantIdentificationMethod participantIdentificationMethod : participantIdentificationMethods) {
                    //The first psi-mi term is ours primary ref and name
                    if (!found && participantIdentificationMethod.getDatabase().equalsIgnoreCase("psi-mi")) {

                        names.setShortLabel(participantIdentificationMethod.getText());
                        //TODO find a better full name
                        names.setFullName(participantIdentificationMethod.getText());

                        DbReference dbRef = new DbReference();
                        dbRef.setDb("psi-mi");
                        dbRef.setDbAc("MI:0488");
                        dbRef.setId(participantIdentificationMethod.getIdentifier());
                        dbRef.setRefType("identity");
                        dbRef.setRefTypeAc("MI:0356");

                        xref.setPrimaryRef(dbRef);
                        found = true;

                    } else {

                        String database = participantIdentificationMethod.getDatabase();

                        DbReference dbRef = new DbReference();
                        dbRef.setId(participantIdentificationMethod.getIdentifier());
                        dbRef.setDb(participantIdentificationMethod.getDatabase());

                        //TODO check more databases

                        if (database.equals(INTACT)) {
                            dbRef.setDbAc(INTACT_MI);
                            dbRef.setRefType(IDENTITY);
                            dbRef.setRefTypeAc(IDENTITY_REF);
                        }

                        xref.getSecondaryRef().add(dbRef);

                        if (participantIdentificationMethod.hasText()) {
                            Alias alias = new Alias(participantIdentificationMethod.getText());
                            if (names.getAliases().isEmpty()) {
                                names.getAliases().add(alias);
                            } else {
                                if (!names.getAliases().contains(alias))
                                    names.getAliases().add(alias);
                            }

                        }

                    }
                }

                if (found) {

                    psidev.psi.mi.xml.model.ParticipantIdentificationMethod xmlParticipantIdMethod
                            = new psidev.psi.mi.xml.model.ParticipantIdentificationMethod();

                    xmlParticipantIdMethod.setNames(names);
                    xmlParticipantIdMethod.setXref(xref);
                    participant.getParticipantIdentificationMethods().add(xmlParticipantIdMethod);


                } else {
                    log.warn("No psi-mi participant identification method given.");
                }

            }

        } else {
            log.warn("No interactor given.");
        }

        return participant;

    }
}