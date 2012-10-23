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

//    /**
//     * Collection of all Interactions.
//     */
//    private Collection<Interaction> interactions;
//
//    /**
//     * Collection of Interactors.
//     */
//    private Collection<Interactor> interactors = new ArrayList<Interactor>();

	/**
	 * Strategy defining which interactor name used.
	 */
	private InteractorNameBuilder interactorNameBuilder;

	protected static final String IREFINDEX = "irefindex";
	public static final String PSI_MI = "psi-mi";
	public static final String PSI_MI_REF = "MI:0488";
	public static final String IDENTITY = "identity";
	public static final String IDENTITY_REF = "MI:0356";
	public static final String UNKNOWN = "unknown";

	public static final String UNIPROT = "uniprotkb";
	private static final String UNIPROT_MI = "MI:0486";

	public static final String INTACT = "intact";
	private static final String INTACT_MI = "MI:0469";

	public static final String CHEBI = "chebi";
	private static final String CHEBI_MI = "MI:0474";


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

		if (binaryInteractions.isEmpty()) {
			throw new IllegalAccessException("No binary interactions found in the collection");
		}

		EntrySet entrySet = new EntrySet(PsimiXmlVersion.VERSION_25_UNDEFINED);

		HashMap<List<CrossReference>, Entry> listXrefEntryHashMap = new HashMap<List<CrossReference>, Entry>();


		Map<Entry, List<BinaryInteraction>> interactionsPerSource = new HashMap<Entry, List<BinaryInteraction>>();

		for (BinaryInteraction<?> binaryInteraction : binaryInteractions) {

			Entry xmlEntry;

			List<CrossReference> sources = binaryInteraction.getSourceDatabases();

			if (!listXrefEntryHashMap.containsKey(sources)) {
				//If we have more than one source we only use the first one
				Source xmlSource = createSource(sources);
				xmlEntry = new Entry();
				xmlEntry.setSource(xmlSource);
				listXrefEntryHashMap.put(sources, xmlEntry);

				List<BinaryInteraction> lbi = new ArrayList<BinaryInteraction>();
				lbi.add(binaryInteraction);

				interactionsPerSource.put(xmlEntry, lbi);

			} else {
				xmlEntry = listXrefEntryHashMap.get(sources);
				interactionsPerSource.get(xmlEntry).add(binaryInteraction);
			}
		}

		for(Entry entry : interactionsPerSource.keySet()){
			Collection<BinaryInteraction> binaryInteractionsPerSource = interactionsPerSource.get(entry);
			Map<String, Collection<Participant>> interactionMap = createInteractionMap(binaryInteractionsPerSource);

			for (BinaryInteraction interaction : binaryInteractionsPerSource) {
				Collection<Interaction> interactions = interactionConverter.fromMitab(interaction, interactionMap);
				entry.getInteractions().addAll(interactions);
			}
			entrySet.getEntries().add(entry);
		}

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


	/**
	 * This method create a Map which is used to find the ExperimentalRole (bait,prey or unspecified)
	 * key = InteractionID and value = tab.model.InteractorPair(s)
	 *
	 * @param miTabInteractions
	 * @return
	 * @throws IllegalAccessException
	 */
	protected Map<String, Collection<Participant>> createInteractionMap(Collection<BinaryInteraction> miTabInteractions) throws IllegalAccessException, XmlConversionException {


		List<Interactor> interactors = new ArrayList<Interactor>();

		InteractorConverter<?> interactorConverter = interactionConverter.getInteractorConverter();

		Map<String, Collection<Participant>> interactionMap = new HashMap<String, Collection<Participant>>();
		interactorConverter.setInteractorNameBuilder(getInteractorNameBuilder());

		for (BinaryInteraction<?> binaryInteraction : miTabInteractions) {
			final List<CrossReference> crossReference = binaryInteraction.getInteractionAcs();

			if (crossReference.isEmpty()) {
				crossReference.add(new NullCrossReference(binaryInteraction));
			}

			// Arbitrarily pick the first one
			StringBuilder interactionIdBuilder = new StringBuilder();

			interactionIdBuilder.append(crossReference.get(0).getIdentifier());
			interactionIdBuilder.append("_");

			psidev.psi.mi.tab.model.Interactor A = binaryInteraction.getInteractorA();
			psidev.psi.mi.tab.model.Interactor B = binaryInteraction.getInteractorB();

			Participant pA = null;
			Participant pB = null;

			if (A != null && B != null) {
				interactionIdBuilder.append(binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier());
				interactionIdBuilder.append("_");
				interactionIdBuilder.append(binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier());

				Interactor iA = interactorConverter.fromMitab(binaryInteraction.getInteractorA());
				Interactor iB = interactorConverter.fromMitab(binaryInteraction.getInteractorB());

				// reusing the interactor in the participants
				iA = checkInteractor(iA, interactors);
				iB = checkInteractor(iB, interactors);

				// Note: the index is not used by these methods, just left here not to break the API.
				pA = interactorConverter.buildParticipantA(iA, binaryInteraction, 0);
				pB = interactorConverter.buildParticipantB(iB, binaryInteraction, 0);

			} else if (A != null && !A.isEmpty()) {
				interactionIdBuilder.append(binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier());
				Interactor iA = interactorConverter.fromMitab(binaryInteraction.getInteractorA());

				// reusing the interactor in the participants
				iA = checkInteractor(iA, interactors);

				// Note: the index is not used by these methods, just left here not to break the API.
				pA = interactorConverter.buildParticipantA(iA, binaryInteraction, 0);

				//In this case is a self interaction

			} else if (B != null && !B.isEmpty()) {
				interactionIdBuilder.append(binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier());
				Interactor iB = interactorConverter.fromMitab(binaryInteraction.getInteractorB());

				// reusing the interactor in the participants
				iB = checkInteractor(iB, interactors);

				// Note: the index is not used by these methods, just left here not to break the API.
				pB = interactorConverter.buildParticipantB(iB, binaryInteraction, 0);

				//In this case is a self interaction

			} else {
				new XmlConversionException("Both interactors are null or empty , we can not convert the interaction");
			}


			String interactionId = interactionIdBuilder.toString();

			Collection<Participant> participants = null;

			if (!interactionMap.containsKey(interactionId)) {
				participants = new ArrayList<Participant>();
			} else {
				participants = interactionMap.get(interactionId);
			}

			if (pA != null && !participants.contains(pA)) {
				participants.add(pA);
			}
			if (pB != null && !participants.contains(pB)) {
				participants.add(pB);
			}

			interactionMap.put(interactionId, participants);
		}
		return interactionMap;
	}

	private psidev.psi.mi.xml.model.Interactor checkInteractor(psidev.psi.mi.xml.model.Interactor interactor1, List<Interactor> interactors) {
		psidev.psi.mi.xml.model.Interactor auxInteractor = interactor1;

		for (psidev.psi.mi.xml.model.Interactor interactor2 : interactors) {

			if (interactor1.equals(interactor2)) {
				auxInteractor = interactor2;
				break;
			}

		}
		if (auxInteractor.equals(interactor1)) {
			if (!interactors.contains(interactor1)) {
				interactors.add(auxInteractor);
			}
		}

		return auxInteractor;
	}


	/**
	 * This method create a new Source of the binaryInteraction data.
	 *
	 * @return source
	 */
	private Source createSource(Collection<CrossReference> sources) {

		Source source = new Source();

		// set default release Date
		Date date = new Date();
		source.setReleaseDate(date);
		Names names = new Names();
		names.setShortLabel(UNKNOWN);

		// set default values
		source.setNames(names);

		if (sources != null) {

			Xref xref = new Xref();

			boolean found = false;

			for (CrossReference crossReference : sources) {
				//The first psi-mi term is ours primary ref and name
				if (!found && crossReference.getDatabase().equalsIgnoreCase(PSI_MI)) {

					String label = crossReference.getText();
					if (label != null) {
						names.setShortLabel(label);
					}

					DbReference dbRef = new DbReference();
					dbRef.setDb(PSI_MI);
					dbRef.setDbAc(PSI_MI_REF);
					dbRef.setId(crossReference.getIdentifier());
					dbRef.setRefType(IDENTITY);
					dbRef.setRefTypeAc(IDENTITY_REF);

					xref.setPrimaryRef(dbRef);
					found = true;

				} else {

					String database = crossReference.getDatabase();

					DbReference dbRef = new DbReference();
					dbRef.setId(crossReference.getIdentifier());
					dbRef.setDb(crossReference.getDatabase());

					//TODO check more databases

					if (database.equals(UNIPROT)) {
						dbRef.setDbAc(UNIPROT_MI);
						dbRef.setRefType(IDENTITY);
						dbRef.setRefTypeAc(IDENTITY_REF);
					} else if (database.equals(CHEBI)) {
						dbRef.setDbAc(CHEBI_MI);
						dbRef.setRefType(IDENTITY);
						dbRef.setRefTypeAc(IDENTITY_REF);
					} else if (database.equals(INTACT)) {
						dbRef.setDbAc(INTACT_MI);
						dbRef.setRefType(IDENTITY);
						dbRef.setRefTypeAc(IDENTITY_REF);
					}

					xref.getSecondaryRef().add(dbRef);

					if (crossReference.hasText()) {
						Alias alias = new Alias(crossReference.getText());
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
				source.setNames(names);
				source.setXref(xref);
			}
		}
		return source;
	}
}
