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
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.Feature;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.*;

import java.util.Collection;
import java.util.List;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabInteractorConverter extends InteractorConverter<Interactor> {

	public static final String PSIMI = "psi-mi";
	public static final String PSIMI_MI = "MI:0488";
	public static final String IDENTITY = "identity";
	public static final String IDENTITY_REF = "MI:0356";
	private static final String FEATURE_TYPE = "MI:0116";
	private static final String UNSPECIFIED_ROLE = "MI:0499";


	/**
	 * Converts a CrossReference to CV from Mitab
	 */
	private CvConverter cvConverter = new CvConverter();


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
				dbRef.setDb(PSIMI);
				dbRef.setDbAc(PSIMI_MI);
				dbRef.setId(UNSPECIFIED_ROLE);
				dbRef.setRefType(IDENTITY);
				dbRef.setRefTypeAc(IDENTITY_REF);

				biologicalRole.setNames(names);
				biologicalRole.setXref(new Xref(dbRef));

			} else {
				biologicalRole = cvConverter.fromMitab(biologicalRoles, BiologicalRole.class);
			}

			participant.setBiologicalRole(biologicalRole);


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
				dbRef.setDb(PSIMI);
				dbRef.setDbAc(PSIMI_MI);
				dbRef.setId(UNSPECIFIED_ROLE);
				dbRef.setRefType(IDENTITY);
				dbRef.setRefTypeAc(IDENTITY_REF);

				experimentalRole.setNames(names);
				experimentalRole.setXref(new Xref(dbRef));

			} else {
				experimentalRole = cvConverter.fromMitab(experimentalRoles, ExperimentalRole.class);

			}

			participant.getExperimentalRoles().add(experimentalRole);

			//Fields 21 22 Interactor Type (They are in InteractorConverter)

			//Fields 23 24 Xrefs (They are in InteractorConverter)

			//Field 26 27 Annotations (They are in InteractorConverter)

			//Field 33 34 Checksums (They are in InteractorConverter)

			//Field 37 38 Features
			Collection<Feature> features = mitabInteractor.getFeatures();
			if (!features.isEmpty()) {
				for (Feature mitabFeature : features) {
					psidev.psi.mi.xml.model.Feature xmlfeature = new psidev.psi.mi.xml.model.Feature();

					//Feature Id
					xmlfeature.setId(IdentifierGenerator.getInstance().nextId());

					//Feature Type
					FeatureType featureType = new FeatureType();

					// In this moment we don't have all the information about the  feature type,
					// only the name (we would need to search the CVTerm)
					// TODO search the MITAB term by Name

					if (mitabFeature.getFeatureType() != null) {
						// If we have the feature type, the name and xref are mandatory in the xml schema, so
						// we need to create a temporal solution for the xref, because now we can't provide the
						// real ID for the feature type

						Names featureTypeNames = new Names();
						featureTypeNames.setShortLabel(mitabFeature.getFeatureType());
						featureTypeNames.setFullName(mitabFeature.getFeatureType());

						featureType.setNames(featureTypeNames);
						//TODO search the MITAB term by Name
//                        featureType.setXref(searchTermByName(names));
						DbReference dbRef = new DbReference();
						dbRef.setDb(PSIMI);
						dbRef.setDbAc(PSIMI_MI);
						dbRef.setId(FEATURE_TYPE);
						dbRef.setRefType(IDENTITY);
						dbRef.setRefTypeAc(IDENTITY_REF);

						Xref xref = new Xref(dbRef);
						featureType.setXref(xref);
						xmlfeature.setFeatureType(featureType);
						Attribute attribute = new Attribute("MI:0018", "caution", "Due to a limitation in the conversion form MITAB to XML," +
								" the real xref for the feature type can not be provided. However, you could use the name of the feature type.");
						participant.getAttributes().add(attribute);
					}


					//Feature RangeList
					//Review
					if (mitabFeature.getRanges() != null && !mitabFeature.getRanges().isEmpty()) {
						try {
							xmlfeature.getRanges().addAll(RangeUtils.fromMitab(mitabFeature.getRanges()));
						} catch (ConverterException e) {
							throw new XmlConversionException("The ranges could not be converted");
						}

					}

					if (mitabFeature.getText() != null && !mitabFeature.getText().isEmpty()) {
						Attribute attribute = new Attribute("MI:0612", "comment", "This feature in mitab had the next text: " + mitabFeature.getText());
						xmlfeature.getAttributes().add(attribute);
					}
					participant.getFeatures().add(xmlfeature);
				}

			}

			//Field 39 40 Stoichiometry
			Collection<Integer> stoichiometry = mitabInteractor.getStoichiometry();
			if (!stoichiometry.isEmpty()) {
				for (Integer integer : stoichiometry) {
					//TODO Review the format
					Attribute attribute = new Attribute("MI:0612", "comment", "Stoichiometry: " + integer.toString());
					if (!participant.getAttributes().contains(attribute)) {
						participant.getAttributes().add(attribute);
					}
				}
			}

			//Fields 41 42 Participant Identification Methods
			List<CrossReference> participantIdentificationMethods = mitabInteractor.getParticipantIdentificationMethods();
			if (!participantIdentificationMethods.isEmpty()) {

				//In this moment we assumed that we have only one identification method per Interactor
				//TODO Check the psi-mi accessions to know if we have more than one ID method and group by this ID
				//TODO Move this functionality to another class (conversion utils)
				boolean found = false;
				Names names = new Names();
				Xref xref = new Xref();

				for (CrossReference participantIdentificationMethod : participantIdentificationMethods) {
					//The first psi-mi term is ours primary ref and name
					if (!found && participantIdentificationMethod.getDatabase().equalsIgnoreCase(PSIMI)) {

						names.setShortLabel(participantIdentificationMethod.getText());
						//TODO find a better full name
						names.setFullName(participantIdentificationMethod.getText());

						DbReference dbRef = new DbReference();
						dbRef.setDb(PSIMI);
						dbRef.setDbAc(PSIMI_MI);
						dbRef.setId(participantIdentificationMethod.getIdentifier());
						dbRef.setRefType(IDENTITY);
						dbRef.setRefTypeAc(IDENTITY_REF);

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
