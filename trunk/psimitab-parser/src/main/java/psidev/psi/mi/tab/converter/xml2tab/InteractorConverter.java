/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.IdentifierGenerator;
import psidev.psi.mi.tab.converter.tab2xml.InteractorNameBuilder;
import psidev.psi.mi.tab.converter.tab2xml.InteractorUniprotIdBuilder;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.xml.model.Alias;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.model.Feature;
import psidev.psi.mi.xml.model.Interactor;

import java.util.*;

/**
 * Converts an psi25 interactor into a simple interactor for MITAB25;
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public abstract class InteractorConverter<T extends psidev.psi.mi.tab.model.Interactor> {

	public static final String IDENTITY = "identity";
	public static final String IDENTITY_REF = "MI:0356";
	public static final String SOURCE_REFERENCE = "source reference";
	public static final String SOURCE_REFERENCE_REF = "MI:0685";

	public static final String UNIPROT = "uniprotkb";
	public static final String UNIPROT_MI = "MI:0486";

	public static final String INTACT = "intact";
	public static final String INTACT_MI = "MI:0469";

	public static final String CHEBI = "chebi";
	public static final String CHEBI_MI = "MI:0474";

	public static final String PSIMI = "psi-mi";
	public static final String PSIMI_MI = "MI:0488";

	public static final String UNKNOWN = "unknown";

	private static final List<String> uniprotKeys = new ArrayList<String>(Arrays.asList(new String[]
			{"gene name", "gene name synonym", "isoform synonym", "locus name", "ordered locus name", "open reading frame name"}));

	private static final List<String> checksumNames = new ArrayList<String>(Arrays.asList(new String[]
			{"checksum", "smiles string", "standard inchi", "inchi key", "standard inchi key", "rogid", "rigid", "crogid", "crc"}));

	public static final String SHORT_LABEL = "shortLabel";
	private static final String FULL_NAME = "fullName";

	/**
	 * Override the alias source database (column 3/4 & 5/6).
	 */
	@Deprecated
	private CrossReference overrideAliasSourceDatabase;

	/**
	 * Sets up a logger for that class.
	 */
	public static final Log log = LogFactory.getLog(InteractorConverter.class);

	/**
	 * Converts a CV to a CrossReference in Mitab
	 */
	private CvConverter cvConverter = new CvConverter();


	///////////////////////////
	// Setter

	/**
	 * Setter for property 'overrideAliasSourceDatabase'.
	 *
	 * @param overrideAliasSourceDatabase Value to set for property 'overrideAliasSourceDatabase'.
	 */
	@Deprecated
	public void setOverrideAliasSourceDatabase(CrossReference overrideAliasSourceDatabase) {
		this.overrideAliasSourceDatabase = overrideAliasSourceDatabase;

	}

	private CrossReference buildCrossReference(DbReference ref) {
		return new CrossReferenceImpl(ref.getDb(), ref.getId());
	}

	///////////////////////
	// Conversion method

	public psidev.psi.mi.tab.model.Interactor toMitab(Participant xmlParticipant) throws TabConversionException {

		if (xmlParticipant == null) {
			throw new IllegalArgumentException("Participant must not be null");
		}

		Interactor xmlInteractor = xmlParticipant.getInteractor();

		if (xmlInteractor == null) {
			throw new IllegalArgumentException("Interactor must not be null");
		}


		List<CrossReference> identifiers = new ArrayList<CrossReference>();
		List<CrossReference> altIdentifiers = new ArrayList<CrossReference>();
		List<CrossReference> xrefs = new ArrayList<CrossReference>();

		Collection<DbReference> identityRefs;

		// primary accession id
		if (xmlInteractor.getXref() != null) {

			identityRefs = XrefUtils.searchByType(xmlInteractor.getXref(), IDENTITY, IDENTITY_REF);
			// identityRefs.addAll(XrefUtils.searchByType( interactor.getXref(), SOURCE_REFERENCE, SOURCE_REFERENCE_REF ));    \
			if (!identityRefs.isEmpty()) {
				//We have the unique identifier in this list

				CrossReference primaryIdentifier = selectBestIdentfier(identityRefs);
				if (primaryIdentifier != null) {
					identifiers.add(primaryIdentifier);
				}
//            for (DbReference ref : identityRefs) {
//                CrossReference cr = buildCrossReference(ref);
//                if (!identifiers.contains(cr)) {
//                    identifiers.add(cr);
//                }
//            }
				// alternative Ids

				// -> use the rest of identify xrefs that are not the unique identifier
				for (DbReference ref : identityRefs) {
					CrossReference cr = buildCrossReference(ref);
					if (!altIdentifiers.contains(cr)) {
						altIdentifiers.add(cr);
					}
				}
				//We remove the unique identifier from alternatives identifiers
				altIdentifiers.remove(primaryIdentifier);

				// xrefs
				List<DbReference> allRefs = XrefUtils.getAllDbReferences(xmlInteractor.getXref());

				// -> use the rest of non-identify xrefs that are not the unique identifier
				if (allRefs != null) {
					for (DbReference ref : allRefs) {
						if (!ref.hasRefType() ||(ref.hasRefType() && !ref.getRefType().equalsIgnoreCase("identity"))) {
							CrossReference cr = buildCrossReference(ref);
							if (!xrefs.contains(cr)) {
								xrefs.add(cr);
							}
						}
					}
				}
			} else {

				//Here we don't have identity objects, then the unique id must be a xrefs for the interactor
				List<DbReference> allRefs = XrefUtils.getAllDbReferences(xmlInteractor.getXref());

				if (identifiers.isEmpty() && !allRefs.isEmpty()) {

					// sort identifiers
					//TODO Why do we need sort if all are equal? Why don't we choose the first one?
					allRefs = XrefUtils.sortByIdentifier(allRefs);

					CrossReference primaryIdentifier = buildCrossReference(allRefs.iterator().next());

					// pick the first one in the list
					identifiers.add(primaryIdentifier);

					/* Xrefs Columns 23, 24 */

					// -> use the rest of non-identify xrefs that are not the unique identifier
					for (DbReference ref : allRefs) {
						CrossReference cr = buildCrossReference(ref);
						if (!xrefs.contains(cr)) {
							xrefs.add(cr);
						}
					}
					//We remove the unique identifier from alternatives identifiers
					xrefs.remove(primaryIdentifier);
				}
			}
			if (identifiers.isEmpty()) {
				throw new TabConversionException("Could not find any identifiers for interactor " + xmlInteractor.getId());
			}

		} // xrefs

		//TODO Check if we need add information of the participant in altIds or Xrefs

		/* Primary identifier Columns 1, 2 */
		T tabInteractor = newInteractor(identifiers);


		/* Alternative Identifiers Columns 3, 4 */
		if (!altIdentifiers.isEmpty()) {
			tabInteractor.setAlternativeIdentifiers(altIdentifiers);
		}

		/* Xrefs Columns 23, 24 */
		if (!xrefs.isEmpty()) {
			tabInteractor.setXrefs(xrefs);
		}
//        //   -> use the gene name
//        Collection<Alias> aliases = AliasUtils.getAllAliases(interactor.getNames());
//        Alias gn = AliasUtils.getGeneName(aliases);
//        if (gn != null) {
//            aliases.remove(gn); // (!) the rest will be stored in the alias builder
//            String geneNameValue = gn.getValue();
//
//            if (geneNameValue != null && geneNameValue.trim().length() > 0) {
//                CrossReference cr = new CrossReferenceImpl(UNIPROT, geneNameValue);
//                cr.setText(gn.getType());
//                altIdentifiers.add(cr);
//                tabInteractor.setAlternativeIdentifiers(altIdentifiers);
//            } else {
//                log.warn("Found alias (gene name) without value. Ignoring");
//            }
//        }


		// aliases
		if (xmlInteractor.getNames() != null) {

			List<psidev.psi.mi.tab.model.Alias> tabAliases = new ArrayList<psidev.psi.mi.tab.model.Alias>();
			Collection<Alias> aliases = xmlInteractor.getNames().getAliases();

			if (aliases != null) {
				for (Alias alias : aliases) {

					String aliasValue = alias.getValue();

					if (aliasValue != null && aliasValue.trim().length() > 0) {
						String db;
						if (uniprotKeys.contains(alias.getType())) {
							db = UNIPROT;
						} else {
							db = UNKNOWN;
						}
						psidev.psi.mi.tab.model.Alias a = new psidev.psi.mi.tab.model.AliasImpl(db, aliasValue);
						a.setAliasType(alias.getType());
						tabAliases.add(a);
					} else {
						log.warn("Found alias without value. Ignoring alias");
					}
				}
			}

			// shortlabel
			//We put in alias because we don't have another place now
			String shortLabel = xmlInteractor.getNames().getShortLabel();
			if (shortLabel != null) {
				psidev.psi.mi.tab.model.Alias a = new psidev.psi.mi.tab.model.AliasImpl(UNKNOWN, shortLabel);
				a.setAliasType(SHORT_LABEL);
				tabAliases.add(a);

			}

			// fullName
			String fullName = xmlInteractor.getNames().getFullName();
			if (fullName != null) {
				psidev.psi.mi.tab.model.Alias a = new psidev.psi.mi.tab.model.AliasImpl(UNKNOWN, fullName);
				a.setAliasType(FULL_NAME);
				tabAliases.add(a);
			}

			/* Aliases Columns 5,6 */
			if (!tabAliases.isEmpty()) {
				tabInteractor.setAliases(tabAliases);
			}
		}


		// taxonomy
		if (xmlInteractor.hasOrganism()) {
			psidev.psi.mi.xml.model.Organism o = xmlInteractor.getOrganism();
			List<CrossReference> organismXrefs = new ArrayList<CrossReference>();
			String taxId = String.valueOf(o.getNcbiTaxId());

			if (o.hasNames()) {
				// shortlabel
				String shortLabel = o.getNames().getShortLabel();
				if (shortLabel != null) {
					organismXrefs.add(new CrossReferenceImpl("taxid", taxId, shortLabel));
				}

				// fullName
				String fullName = o.getNames().getFullName();
				if (fullName != null && !shortLabel.equalsIgnoreCase(fullName)) {
					organismXrefs.add(new CrossReferenceImpl("taxid", taxId, fullName));
				}

				if (shortLabel == null && fullName == null) {
					organismXrefs.add(new CrossReferenceImpl("taxid", taxId));
				}
			} else {
				organismXrefs.add(new CrossReferenceImpl("taxid", taxId));
			}

			/* Organism Columns 10, 11 */
			tabInteractor.setOrganism(new OrganismImpl(organismXrefs));
		}

		//biological roles
		if (xmlParticipant.hasBiologicalRole()) {

			List<CrossReference> tabBioRole = new ArrayList<CrossReference>();

			BiologicalRole xmlBioRole = xmlParticipant.getBiologicalRole();
			CrossReference cr = cvConverter.toMitab(xmlBioRole);

			if (cr != null) {
				tabBioRole.add(cr);
			}

			if (!tabBioRole.isEmpty()) {
				/* Biological Roles Columns 17, 18 */
				tabInteractor.setBiologicalRoles(tabBioRole);
			}
		}

		//experimental roles
		if (xmlParticipant.hasExperimentalRoles()) {

			List<CrossReference> tabExpRoles = new ArrayList<CrossReference>();
			Collection<ExperimentalRole> xmlExpRoles = xmlParticipant.getExperimentalRoles();

			for (ExperimentalRole xmlExpRole : xmlExpRoles) {
				CrossReference cr = cvConverter.toMitab(xmlExpRole);

				if (cr != null && !tabExpRoles.contains(cr)) {
					tabExpRoles.add(cr);
				}
			}

			if (!tabExpRoles.isEmpty()) {

				/* Biological Roles Columns 19, 20 */
				tabInteractor.setExperimentalRoles(tabExpRoles);
			}

		}

		//interactor type
		if (xmlInteractor.getInteractorType() != null) {

			List<CrossReference> tabInteractorType = new ArrayList<CrossReference>();

			InteractorType xmlInteractorType = xmlInteractor.getInteractorType();
			CrossReference cr = cvConverter.toMitab(xmlInteractorType);

			if (cr != null) {
				tabInteractorType.add(cr);
			}

			if (!tabInteractorType.isEmpty()) {

				/* Interactor Type Columns 21, 22 */
				tabInteractor.setInteractorTypes(tabInteractorType);
			}
		}

		//xrefs. See identifiers
		/* Xrefs Columns 23, 24 */

		//annotations && checksum
		List<Annotation> annotations = new ArrayList<Annotation>();
		List<Checksum> checksums = new ArrayList<Checksum>();

		if (xmlInteractor.getAttributes() != null) {
			Collection<Attribute> xmlInteractorAttributes = xmlInteractor.getAttributes();
			for (Attribute xmlInteractorAttribute : xmlInteractorAttributes) {
				String name = xmlInteractorAttribute.getName();
				String text = xmlInteractorAttribute.getValue();
				if (checksumNames.contains(name.toLowerCase())) {
					//Is a checksum
					checksums.add(new ChecksumImpl(name, text));
				} else {
					//Is a normal attribute
					annotations.add(new AnnotationImpl(name, text));
				}
			}
		}
		if (xmlParticipant.getAttributes() != null) {
			Collection<Attribute> xmlParticipantAttributes = xmlParticipant.getAttributes();
			for (Attribute xmlParticipantAttribute : xmlParticipantAttributes) {
				String name = xmlParticipantAttribute.getName();
				String text = xmlParticipantAttribute.getValue();
				if (checksumNames.contains(name.toLowerCase())) {
					//Is a checksum
					checksums.add(new ChecksumImpl(name, text));
				} else {
					//Is a normal attribute
					annotations.add(new AnnotationImpl(name, text));
				}
			}
		}

		if (!annotations.isEmpty()) {

			/* Annotations Columns 26, 27 */
			tabInteractor.setAnnotations(annotations);
		}

		if (!checksums.isEmpty()) {

			/* Annotations Columns 33, 34 */
			tabInteractor.setChecksums(checksums);
		}

		if (xmlParticipant.getFeatures() != null) {

			Collection<Feature> xmlFeatures = xmlParticipant.getFeatures();
			List<psidev.psi.mi.tab.model.Feature> tabFeatures
					= new ArrayList<psidev.psi.mi.tab.model.Feature>();

			for (Feature xmlFeature : xmlFeatures) {

				List<String> ranges = null;
				String tabFeatureType;
				String text = null;

				if (xmlFeature.getRanges() != null && !xmlFeature.getRanges().isEmpty()) {
					ranges = RangeUtils.toMitab(xmlFeature.getRanges());
				}

				if (xmlFeature.hasFeatureType()) {
					FeatureType featureType = xmlFeature.getFeatureType();
					if (featureType.hasNames()) {
						String shortLabel = featureType.getNames().getShortLabel();
						String fullName = featureType.getNames().getFullName();

						if (shortLabel != null) {
							tabFeatureType = shortLabel;
						} else if (fullName != null) {
							tabFeatureType = fullName;
						} else {
							throw new TabConversionException("The feature need a feature type");
						}

					} else {
						throw new TabConversionException("The feature need a feature type");
					}

				} else {
					throw new TabConversionException("The feature need a feature type");

				}

				if (xmlFeature.hasNames()) {

					String shortLabel = xmlFeature.getNames().getShortLabel();
					String fullName = xmlFeature.getNames().getFullName();

					if (shortLabel != null) {
						text = shortLabel;
					} else if (fullName != null) {
						text = fullName;
					}

				}
				tabFeatures.add(new FeatureImpl(tabFeatureType, ranges, text));
			}


			/* Features Columns 37, 38 */
			if (!tabFeatures.isEmpty()) {
				tabInteractor.setFeatures(tabFeatures);
			}

		}

		/* Stoichiometry Columns 39, 40 */
		if (xmlParticipant.getAttributes() != null) {
			Collection<Attribute> attributes = xmlParticipant.getAttributes();

			List<Integer> tabStoichiometry
					= new ArrayList<Integer>();

			for (Attribute attribute : attributes) {
				if (attribute.getName().equals("comment") || attribute.getName().equals("MI:0612")) {
					if (attribute.getValue() != null && attribute.getValue().equalsIgnoreCase("stoichiometry")) {
						Scanner s = new Scanner(attribute.getValue());
						try {
							if (s.hasNextLong()) {
								tabStoichiometry.add(s.nextInt());
							}
						} finally {
							s.close();
						}
					}
				}
			}

			/* Stoichiometry Columns 39, 40 */
			if (!tabStoichiometry.isEmpty()) {
				tabInteractor.setStoichiometry(tabStoichiometry);
			}
		}

		if (xmlParticipant.getParticipantIdentificationMethods() != null) {

			List<psidev.psi.mi.tab.model.CrossReference> tabPartIdentMethods
					= new ArrayList<psidev.psi.mi.tab.model.CrossReference>();
			Collection<ParticipantIdentificationMethod> xmlPartIdentMethodsRoles = xmlParticipant.getParticipantIdentificationMethods();

			for (ParticipantIdentificationMethod xmlPartIdentMethodsRole : xmlPartIdentMethodsRoles) {
				psidev.psi.mi.tab.model.CrossReference cr
						= cvConverter.toMitab(xmlPartIdentMethodsRole);

				if (cr != null && !tabPartIdentMethods.contains(cr)) {
					tabPartIdentMethods.add(cr);
				}
			}

			if (!tabPartIdentMethods.isEmpty()) {

				/* Participant Identification Methods Columns 41, 42 */
				tabInteractor.setParticipantIdentificationMethods(tabPartIdentMethods);
			}


		}

		return tabInteractor;
	}

	private CrossReference selectBestIdentfier(Collection<DbReference> identityRefs) {
		Iterator<DbReference> iterator = identityRefs.iterator();
		boolean found = false;
		CrossReference reference = null;

		while (iterator.hasNext() && !found) {
			DbReference ref = iterator.next();
			if (ref.getDb().equals(UNIPROT)) {
				found = true;
				reference = buildCrossReference(ref);
			} else if (ref.getDb().equals(CHEBI)) {
				found = true;
				reference = buildCrossReference(ref);
			} else if (ref.getDb().equals(INTACT)) {
				found = true;
				reference = buildCrossReference(ref);
			}
		}

		//If we don't have a "good" candidate with select the first one.
		if (!found && !identityRefs.isEmpty()) {
			reference = buildCrossReference(identityRefs.iterator().next());
		}
		return reference;  //To change body of created methods use File | Settings | File Templates.
	}

	protected T newInteractor(List<CrossReference> identifiers) {
		return (T) new psidev.psi.mi.tab.model.Interactor(identifiers);
	}

	/**
	 * Strategy defining which interactor name used.
	 */
	private InteractorNameBuilder interactorNameBuilder;


	/////////////////////////////////////
	// Getters & Setters

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

	public psidev.psi.mi.xml.model.Interactor fromMitab(psidev.psi.mi.tab.model.Interactor tabInteractor) throws XmlConversionException {

		if (tabInteractor == null) {
			throw new IllegalArgumentException("Interactor must not be null");
		}

		psidev.psi.mi.xml.model.Interactor xmlInteractor = new psidev.psi.mi.xml.model.Interactor();
		xmlInteractor.setId(IdentifierGenerator.getInstance().nextId());

		// set interactor name
		Names interactorName;
		if (interactorNameBuilder != null) {
			interactorName = interactorNameBuilder.select(tabInteractor);
		} else {
			log.debug("No InteractorNameBuilder choosen. default = " + InteractorUniprotIdBuilder.class);
			InteractorNameBuilder builder = new InteractorUniprotIdBuilder();
			interactorName = builder.select(tabInteractor);
		}

		if (interactorName != null) {
			xmlInteractor.setNames(interactorName);
		}

		// taxonomy
		if (tabInteractor.hasOrganism()) {
			psidev.psi.mi.tab.model.Organism o = tabInteractor.getOrganism();


			// get shortLabel
			Iterator<CrossReference> idIterator = o.getIdentifiers().iterator();
			String shortlabel = null;
			if (idIterator.hasNext()) {
				CrossReference first = idIterator.next();
				if (first.hasText()) {
					shortlabel = first.getText();
				}
			}

			// get taxid
			int ncbiTaxId;
			if (o.getTaxid() != null) {
				try {
					ncbiTaxId = Integer.parseInt(o.getTaxid());
				} catch (NumberFormatException e) {
					final String msg = "Could not parse taxid " + o.getTaxid() + ", it doesn't seem to be a valid integer value.";
					throw new XmlConversionException(msg);
				}

				psidev.psi.mi.xml.model.Organism organism = new psidev.psi.mi.xml.model.Organism();

				Names names = new Names();
				organism.setNames(names);
				if (shortlabel != null) {
					names.setShortLabel(shortlabel);
				} else {
					switch (ncbiTaxId) {
						case -1:
							names.setShortLabel("in vitro");
							break;
						case -2:
							names.setShortLabel("chemical synthesis");
							break;
						case -3:
							names.setShortLabel("unknown");
							break;
						case -4:
							names.setShortLabel("in vivo");
							break;
						case -5:
							names.setShortLabel("in silico");
							break;
						default:
							names.setShortLabel(String.valueOf(ncbiTaxId));

					}
				}

				organism.setNcbiTaxId(ncbiTaxId);
				xmlInteractor.setOrganism(organism);
			}
		}

		// set primary & secondary references
		DbReference primaryReferece = null;
		Collection<DbReference> secondaryRefs = new ArrayList<DbReference>();

		Collection<CrossReference> tabIdentifiers = tabInteractor.getIdentifiers();
		String primaryDatabase;
		String primaryId;

		if (!tabIdentifiers.isEmpty()) {

			Iterator<CrossReference> identifierIterator = tabIdentifiers.iterator();
			CrossReference primaryIdentifier = identifierIterator.next();
			overrideAliasSourceDatabase = primaryIdentifier;

			primaryDatabase = primaryIdentifier.getDatabase();
			primaryId = primaryIdentifier.getIdentifier();

			primaryReferece = new DbReference(primaryId, primaryDatabase);

			if (primaryDatabase.equals(UNIPROT)) {
				primaryReferece.setDbAc(UNIPROT_MI);
				primaryReferece.setRefType(IDENTITY);
				primaryReferece.setRefTypeAc(IDENTITY_REF);
			} else if (primaryDatabase.equals(CHEBI)) {
				primaryReferece.setDbAc(CHEBI_MI);
				primaryReferece.setRefType(IDENTITY);
				primaryReferece.setRefTypeAc(IDENTITY_REF);
			} else if (primaryDatabase.equals(INTACT)) {
				primaryReferece.setDbAc(INTACT_MI);
				primaryReferece.setRefType(IDENTITY);
				primaryReferece.setRefTypeAc(IDENTITY_REF);
			}

			while (identifierIterator.hasNext()) {
				CrossReference secondaryIdentifier = identifierIterator.next();

				DbReference secondaryRef = createSecondaryRef(secondaryIdentifier);
				secondaryRefs.add(secondaryRef);
			}
		}


		//Fields 23 24  Xrefs
		if (!tabInteractor.getXrefs().isEmpty()) {

			for (CrossReference secondaryIdentifier : tabInteractor.getXrefs()) {
				DbReference secondaryRef = createSecondaryRef(secondaryIdentifier);
				secondaryRefs.add(secondaryRef);
			}

		}
		if (primaryReferece != null) {
			if (!secondaryRefs.isEmpty()) {
				Xref interactorXref = new Xref(primaryReferece, secondaryRefs);
				xmlInteractor.setXref(interactorXref);
			} else {
				Xref interactorXref = new Xref(primaryReferece);
				xmlInteractor.setXref(interactorXref);
			}
		} else {
			throw new XmlConversionException("No Xref found");
		}

		// Fields 21 22 Interactor Type
		InteractorType interactorType = null;

		if (tabInteractor.getInteractorTypes() != null && !tabInteractor.getInteractorTypes().isEmpty()) {

			Collection<CrossReference> interactorTypes = tabInteractor.getInteractorTypes();
			interactorType = cvConverter.fromMitab(interactorTypes, InteractorType.class);

			if (!interactorType.hasNames() && interactorType.getXref() != null && interactorType.getXref().getPrimaryRef() != null) {
				String id = interactorType.getXref().getPrimaryRef().getId();
				Names interactorTypeName = new Names();

				//Main types of interactors
				if (id.equals("MI:0326")) {
					interactorTypeName.setShortLabel("protein");
					interactorTypeName.setFullName("protein");
				} else if (id.equals("MI:0328")) {
					interactorTypeName.setShortLabel("small molecule");
					interactorTypeName.setFullName("small molecule");
				} else if (id.equals("MI:0317")) {
					interactorTypeName.setShortLabel("interaction");
					interactorTypeName.setFullName("interaction");
				} else if (id.equals("MI:0329")) {
					interactorTypeName.setShortLabel("unknown participant");
					interactorTypeName.setFullName("unknown participant");
				} else {
					interactorTypeName.setShortLabel(id);
					interactorTypeName.setFullName(id);
				}
				interactorType.setNames(interactorTypeName);
			}

			xmlInteractor.setInteractorType(interactorType);

		} else {
			Xref interactorTypeXref;
			Names interactorTypeName = new Names();

			//Default value
			if (xmlInteractor.hasXref()) {
				interactorType = new InteractorType();

				String xrefPrimaryDB = xmlInteractor.getXref().getPrimaryRef().getDb();
				if (xrefPrimaryDB.equals(UNIPROT)) {
					// in this case the interactorType is a protein
					interactorTypeName.setShortLabel("protein");
					interactorTypeName.setFullName("protein");

					DbReference dbRef = new DbReference("MI:0326", PSIMI);
					dbRef.setDbAc(PSIMI_MI);
					dbRef.setRefType(IDENTITY);
					dbRef.setRefTypeAc(IDENTITY_REF);
					interactorTypeXref = new Xref(dbRef);
				} else if (xrefPrimaryDB.equals(CHEBI)) {
					// in this case the interactorType is a small molecule
					interactorTypeName.setShortLabel("small molecule");
					interactorTypeName.setFullName("small molecule");

					DbReference dbRef = new DbReference("MI:0328", PSIMI);
					dbRef.setDbAc(PSIMI_MI);
					dbRef.setRefType(IDENTITY);
					dbRef.setRefTypeAc(IDENTITY_REF);
					interactorTypeXref = new Xref(dbRef);
				} else {
					// in other cases its not possible to know which type -> so we create a unknown
					interactorTypeName.setShortLabel("unknown participant");
					interactorTypeName.setFullName("unknown participant");

					DbReference dbRef = new DbReference("MI:0329", PSIMI);
					dbRef.setDbAc(PSIMI_MI);
					dbRef.setRefType(IDENTITY);
					dbRef.setRefTypeAc(IDENTITY_REF);
					interactorTypeXref = new Xref(dbRef);
					log.debug("Interactor type is unknown");
				}
			} else {
				// in other cases its not possible to know which type -> so we create a unknown
				interactorTypeName.setShortLabel("unknown participant");
				interactorTypeName.setFullName("unknown participant");

				DbReference dbRef = new DbReference("MI:0329", PSIMI);
				dbRef.setDbAc(PSIMI_MI);
				dbRef.setRefType(IDENTITY);
				dbRef.setRefTypeAc(IDENTITY_REF);
				interactorTypeXref = new Xref(dbRef);
				log.debug("Interactor type is unknown");
			}
			interactorType.setNames(interactorTypeName);
			interactorType.setXref(interactorTypeXref);
			xmlInteractor.setInteractorType(interactorType);

		}


		//Fields 26 27  Annotations for interactors
		if (tabInteractor.getAnnotations() != null) {
			if (!tabInteractor.getAnnotations().isEmpty()) {
				for (Annotation annotation : tabInteractor.getAnnotations()) {
					Attribute attribute = new Attribute(annotation.getTopic(), annotation.getText());
					xmlInteractor.getAttributes().add(attribute);
				}
			}
		}
		// note: interactionType are not stored in MITAB25, but XMLWriter need one -> default (=empty) InteractorType were set.

		//Fields 34 35 Checksum
		Collection<Checksum> checksums = tabInteractor.getChecksums();
		if (!checksums.isEmpty()) {
			for (Checksum checksum : checksums) {
				Attribute attribute = new Attribute(checksum.getMethodName(), checksum.getChecksum());
				if (!xmlInteractor.getAttributes().contains(attribute)) {
					xmlInteractor.getAttributes().add(attribute);
				}
			}
		}

		return xmlInteractor;
	}

	private DbReference createSecondaryRef(CrossReference secondaryIdentifier) {
		String database = secondaryIdentifier.getDatabase();
		String id = secondaryIdentifier.getIdentifier();

		return new DbReference(id, database);
	}

	public abstract Participant buildParticipantA(Interactor xmlInteractor, BinaryInteraction binaryInteraction, int index)
			throws XmlConversionException;

	public abstract Participant buildParticipantB(Interactor xmlInteractor, BinaryInteraction binaryInteraction, int index)
			throws XmlConversionException;
}

