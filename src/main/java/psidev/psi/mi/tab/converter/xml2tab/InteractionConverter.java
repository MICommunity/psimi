/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.IdentifierGenerator;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.Parameter;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.model.Confidence;
import psidev.psi.mi.xml.model.Organism;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts an PSIMI XML Binary Interaction into PSIMITAB interaction.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public abstract class InteractionConverter<T extends BinaryInteraction<?>> {

    public static final String IDENTITY = "identity";
    public static final String IDENTITY_REF = "MI:0356";

    private static final Log log = LogFactory.getLog(InteractionConverter.class);

    /**
     * Class converting an XML source into a Publication.
     */
    private PublicationConverter pubConverter = new PublicationConverter();

    /**
     * Override the alias source database (column 3/4 & 5/6).
     */
    private CrossReference overrideAliasSourceDatabase;

    /**
     * Source database for the Interaction Id (column 14).
     */
    private Collection<CrossReference> sourceDatabases;

    /**
     * Converts a CV to a CrossReference in Mitab
     */
    private CvConverter cvConverter = new CvConverter();


    // identifies patterns like "AuthorName B (2002)", capturing the authorName and the year
    private Pattern FIRST_AUTHOR_REGEX = Pattern.compile("(\\w+(?:\\P{Ps}+)?)(?:\\((\\d{4})\\))?");

    private static final List<String> checksumNames = new ArrayList<String>(Arrays.asList(new String[]
            {"checksum", "smiles string", "standard inchi", "inchi key",
                    "standard inchi key", "rogid", "rigid", "crogid", "crc", "crc64"}));


    protected static final String IREFINDEX = "irefindex";

    ////////////////////////
    // Getters and Setters

    /**
     * Getter for property 'sourceDatabase'.
     *
     * @return Value for property 'sourceDatabase'.
     */
    public Collection<CrossReference> getSourceDatabase() {
        if (sourceDatabases == null) {
            sourceDatabases = new ArrayList<CrossReference>();
        }
        return sourceDatabases;
    }

    public void addSourceDatabase(CrossReference sourceDatabase) {
        if (sourceDatabase == null) {
            throw new IllegalArgumentException("You must give a non null source database.");
        }
        getSourceDatabase().add(sourceDatabase);
    }

    /**
     * Setter for property 'overrideAliasSourceDatabase'.
     *
     * @param overrideAliasSourceDatabase Value to set for property 'overrideAliasSourceDatabase'.
     */
    public void setOverrideAliasSourceDatabase(CrossReference overrideAliasSourceDatabase) {
        this.overrideAliasSourceDatabase = overrideAliasSourceDatabase;
    }

    //////////////////////
    // Convertion

    public BinaryInteraction toMitab(Interaction interaction) throws TabConversionException {
//        In this moment, we always convert to mitab 2.7, so it will be the writer which needs to check the version and
//        if the interaction is negative.
//        if (interaction.isNegative()) {
//            log.warn("interaction (id:" + interaction.getId() + ") could not be converted to MITAB25 as it is negative.");
//            return null;
//        }

        if (interaction.getParticipants().size() != 2) {
            log.warn("interaction (id:" + interaction.getId() + ") could not be converted to MITAB25 as it does not have exactly 2 participants.");
            return null;
        }

        Iterator<Participant> pi = interaction.getParticipants().iterator();
        Participant pA = pi.next();
        Participant pB = pi.next();

        final Interactor interactorA = getInteractorConverter().toMitab(pA);
        final Interactor interactorB = getInteractorConverter().toMitab(pB);

        BinaryInteraction<?> bi = newBinaryInteraction(interactorA, interactorB);

        // first thing off, set the source. It can come from two places: the source-reference cross ref, and if not present, from the entry source
        if (interaction.hasXref()) {
            Collection<DbReference> sourceRefs = XrefUtils.searchByType(interaction.getXref(), "source reference", "MI:0685");

            String text = null;

            for (Attribute attr : interaction.getAttributes()) {
                if ("source reference:label".equals(attr.getName())) {
                    text = attr.getValue();
                    break;
                }
            }

            for (DbReference sourceRef : sourceRefs) {
                String db = "unknown";

                if (sourceRef.getId() != null && sourceRef.getId().startsWith("MI:")) {
                    db = "psi-mi";
                }

                CrossReference xref = new CrossReferenceImpl(db, sourceRef.getId(), text);
                bi.getSourceDatabases().add(xref);
            }
        }

        if (bi.getSourceDatabases().isEmpty() && sourceDatabases != null) {
            bi.getSourceDatabases().addAll(sourceDatabases);
        }

        //Interaction AC and Xrefs

        //We have several options:
        // 1 - We can use the primary xref for the InteractionAc and the secondary refs as mitab xrefs
        // 2 - We can search the identities xref for the Interaction Acs and the rest of xrefs for the mitab xrefs.

        // This code implements the second option

        if (interaction.hasXref()) {

            // First, try to search by source-reference type.
            // If nothing is found: navigate through the source databases and,
            // if the refType or refTypeAc not available using searchByDatabase
            // but in some cases you will get more than one interactionAcs

            // Interaction AC  field 14
            Collection<DbReference> interactionAcs = XrefUtils.searchByType(interaction.getXref(), IDENTITY, IDENTITY_REF);

            // If the interaction ACs has the source, we remove it.

            Iterator<DbReference> interactionAcsIter = interactionAcs.iterator();

            while (interactionAcsIter.hasNext()) {
                DbReference identityRef = interactionAcsIter.next();

                for (CrossReference sourceDatabase : bi.getSourceDatabases()) {
                    if (!sourceDatabase.getIdentifier().equals(identityRef.getDb()) &&
                            !sourceDatabase.getIdentifier().equals(identityRef.getDbAc())) {
                        interactionAcsIter.remove();
                        break;
                    }
                }
            }

            // We don't have any identity ref, but we can add the first xref that we have
            if (interactionAcs.isEmpty() && interaction.hasXref()) {
                interactionAcs.add(interaction.getXref().getPrimaryRef());
                log.warn("The interaction identifiers provided is not an identity in the original XML");
            }

            List<CrossReference> refs = new ArrayList<CrossReference>();

            for (DbReference interactionAc : interactionAcs) {
                refs.add(new CrossReferenceImpl(interactionAc.getDb(), interactionAc.getId()));
            }

            /* Interaction AC  field 14	 */
            bi.getInteractionAcs().addAll(refs);
        }

        /* Interaction type field 12 */
        if (interaction.hasInteractionTypes()) {
            List<CrossReference> types = new ArrayList<CrossReference>(interaction.getInteractionTypes().size());

            for (psidev.psi.mi.xml.model.InteractionType interactionType : interaction.getInteractionTypes()) {

                CrossReference type = cvConverter.toMitab(interactionType);

                if (type != null) {
                    types.add(type);
                } else {
                    log.warn("Failed to convert interaction type: " + interactionType);
                }
            }

            if (!types.isEmpty()) {
                bi.setInteractionTypes(types);
            }
        }

        // publication field 8 and 9 and interaction detection field 7
        int expCount = interaction.getExperiments().size();
        if (expCount > 0) {

            // init the interaction
            List<CrossReference> publications = new ArrayList<CrossReference>(expCount);
            /* Publication field 7 */
            bi.setPublications(publications);

            List<CrossReference> detections =
                    new ArrayList<CrossReference>(expCount);
            bi.setDetectionMethods(detections);

            for (ExperimentDescription experiment : interaction.getExperiments()) {

                // detection method
                CrossReference detection = cvConverter.toMitab(experiment.getInteractionDetectionMethod());

                if (detection != null) {
                    bi.getDetectionMethods().add(detection);
                }

                // publication
                if (experiment.getBibref() != null) {
                    CrossReference pub = pubConverter.toMitab(experiment.getBibref());
                    if (pub != null) {
                        bi.getPublications().add(pub);
                    }
                }

                // Note: authors are not stored in PSI-MI. skip this.
                if (experiment.getAttributes() != null) {
                    String authorName = "-";
                    String pubYear = "";

                    for (Attribute attribute : experiment.getAttributes()) {
                        if ("author-list".equals(attribute.getName())) {
                            authorName = attribute.getValue();

                            if (authorName.contains(",")) {
                                authorName = authorName.split(" ")[0].concat(" et al");
                            }
                        } else if ("publication year".equals(attribute.getName())) {
                            pubYear = "(" + attribute.getValue() + ")";
                        }
                    }

                    String authorNameYear = authorName + " " + pubYear;

                    Author author = new AuthorImpl(authorNameYear.trim());
                    bi.getAuthors().add(author);
                }
            }
        }

        // confidence scores field 15
        for (Confidence confidence : interaction.getConfidences()) {
            final Unit unit = confidence.getUnit();

            String type = null;
            String value = confidence.getValue();
            String text = null;

            if (unit != null) {
                if (unit.getNames() != null) {
                    type = unit.getNames().getShortLabel();
                    text = unit.getNames().getFullName();
                }
            } else {
                type = "unknown";
            }

            //confidence.
            psidev.psi.mi.tab.model.Confidence tabConfidence = new ConfidenceImpl(type, value, text);
            bi.getConfidenceValues().add(tabConfidence);
        }


        // complex expansion field 16 is in Xml2Tab file


        //Annotations && Checksum for the interaction fields 28 and 33
        List<Annotation> iAnnotations = new ArrayList<Annotation>();
        List<Checksum> iChecksums = new ArrayList<Checksum>();

        for (Attribute attribute : interaction.getAttributes()) {
            String name = attribute.getName();
            String text = attribute.getValue();
            if (checksumNames.contains(name.toLowerCase())) {
                //Is a checksum
                iChecksums.add(new ChecksumImpl(name, text));
            } else {
                //Is a normal attribute
                iAnnotations.add(new AnnotationImpl(name, text));
            }
        }


        if (!iAnnotations.isEmpty()) {
            bi.setAnnotations(iAnnotations);
        }
        if (!iChecksums.isEmpty()) {
            bi.setChecksums(iChecksums);
        }

        //Host organism field 29
        //It is in the Experiment

        //Parameters for the interaction field 30
        List<Parameter> iParameters = new ArrayList<Parameter>();
        for (psidev.psi.mi.xml.model.Parameter parameter : interaction.getParameters()) {
            iParameters.add(new ParameterImpl(parameter.getTerm(),
                    parameter.getFactor(),
                    parameter.getBase(),
                    parameter.getExponent(),
                    parameter.getUncertainty(),
                    parameter.getUnit()));

        }
        if (!iParameters.isEmpty()) {
            bi.setParameters(iParameters);
        }


        //Creation date and update date field 31 and 32 is in Xml2Tab file

        //Negative interaction field  36
        bi.setNegativeInteraction(interaction.isNegative());

        return bi;
    }

    /**
     * Collection of all experiments for all binaryInteractions.
     */
    private Collection<ExperimentDescription> experimentList = new ArrayList<ExperimentDescription>();


    /**
     * Collection of all interaction of one binaryInteraction.
     */
    private Collection<Interaction> interactions = null;

    /////////////////////////////////////
    // Getters & Setters

    /**
     * @return a ExperimentDescription collection of all BinaryInteractions.
     */
    public Collection<ExperimentDescription> getExperimentList() {
        return experimentList;
    }

    /**
     * TODO comment this
     *
     * @param interactionAc
     * @param sourceReference
     * @return primaryReference of these interaction.
     */
    private DbReference getPrimaryRef(CrossReference interactionAc, CrossReference sourceReference) {
        DbReference primaryRef = null;

        // set references
        if (interactionAc != null) {

            String id = interactionAc.getIdentifier();
            String db = interactionAc.getDatabase();

            primaryRef = new DbReference(id, db);

            if (sourceReference != null) {
                primaryRef.setDbAc(sourceReference.getIdentifier());
            }

            primaryRef.setRefType("identity");
            primaryRef.setRefTypeAc("MI:0356");
        }
        return primaryRef;
    }

    /**
     * @param interaction
     * @return names of these interaction.
     */
    private Names getInteractionName(Interaction interaction) {
        Names interactionName = null;
        Collection<Participant> participants = interaction.getParticipants();
        for (Participant participant : participants) {
            if (interactionName != null) {
                String shortLabel = interactionName.getShortLabel().concat("-");
                shortLabel = shortLabel.concat(participant.getInteractor().getNames().getShortLabel().split("_")[0]);
                interactionName.setShortLabel(shortLabel);
            }
            if (interactionName == null) {
                interactionName = new Names();
                interactionName.setShortLabel(participant.getInteractor().getNames().getShortLabel().split("_")[0]);
            }
        }
        if (interactionName == null) {
            log.warn("Interaction don't have a name");
        }
        return interactionName;
    }

    /**
     * @param binaryInteraction
     * @param index
     * @return experimentDescription of these Interaction.
     */
    private Collection<psidev.psi.mi.xml.model.ExperimentDescription> getExperimentDescriptions(T binaryInteraction, int index) throws XmlConversionException {

        Collection<ExperimentDescription> experimentDescriptions = new ArrayList<ExperimentDescription>();

        ExperimentDescription experimentDescription = null;


        //Field 9
        psidev.psi.mi.xml.model.Bibref bibref = null;
        if (binaryInteraction.getPublications().size() <= index) {
            log.warn("Size of InteractionAcs is " + binaryInteraction.getInteractionAcs().size() + " but we have only " +
                    binaryInteraction.getPublications().size() + " publication(s)! -> We could not know which publication dependents on which InteractionAcs");
        } else {
            CrossReference binaryPublication = binaryInteraction.getPublications().get(index);
            bibref = pubConverter.fromMitab(binaryPublication);
        }

        //Field 7
        psidev.psi.mi.xml.model.InteractionDetectionMethod detectionMethod = null;
        if (binaryInteraction.getDetectionMethods().size() <= index) {
            log.warn("Size of InteractionAcs is " + binaryInteraction.getInteractionAcs().size() + " but we have only " +
                    binaryInteraction.getDetectionMethods().size() + " detectionMethod(s)! -> We could not know which detectionMethode dependents on which InteractionAcs");
        } else {
            CrossReference binaryDetectionMethod = binaryInteraction.getDetectionMethods().get(index);
            detectionMethod = cvConverter.fromMitab(binaryDetectionMethod, psidev.psi.mi.xml.model.InteractionDetectionMethod.class);
        }

        if (bibref != null && detectionMethod != null) {
            experimentDescription = new ExperimentDescription(bibref, detectionMethod);
            experimentDescription.setId(IdentifierGenerator.getInstance().nextId());
        }

        //Field 8
        if (index < binaryInteraction.getAuthors().size()) {
            String firstAuthor = binaryInteraction.getAuthors().get(index).getName();
            if (!firstAuthor.equals("-")) {

                Names names = new Names();
                String shortLabel;

                Matcher matcher = FIRST_AUTHOR_REGEX.matcher(firstAuthor);

                if (matcher.matches()) {
                    firstAuthor = matcher.group(1).trim();

                    shortLabel = firstAuthor.split(" ")[0];

                    if (matcher.groupCount() > 1) {
                        String year = matcher.group(2);
                        shortLabel = shortLabel + "-" + year;

                        Attribute pubYear = new Attribute("publication year", year);
                        experimentDescription.getAttributes().add(pubYear);

                    }
                } else {
                    shortLabel = firstAuthor.split(" ")[0];
                }

                shortLabel = shortLabel.toLowerCase();

                names.setShortLabel(shortLabel);
                experimentDescription.setNames(names);
                Attribute authorList = new Attribute("author-list", firstAuthor);

                if (!experimentDescription.getAttributes().contains(authorList)) {
                    experimentDescription.getAttributes().add(authorList);
                }
            }
        }

        //Field 29

        // taxonomy
        if (binaryInteraction.hasHostOrganism()) {
            psidev.psi.mi.tab.model.Organism o = binaryInteraction.getHostOrganism();

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
            //TODO Test this
            int ncbiTaxId = 0;
            Organism xmlOrganism = null;
            if (o.getTaxid() != null) {

                try {
                    ncbiTaxId = Integer.parseInt(o.getTaxid());
                } catch (NumberFormatException e) {
                    final String msg = "Could not parse taxid " + o.getTaxid() + ", it doesn't seem to be a valid integer value.";
                    throw new XmlConversionException(msg);
                }


                Names names = new Names();

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

                xmlOrganism = new Organism();
                xmlOrganism.setNcbiTaxId(ncbiTaxId);
                xmlOrganism.setNames(names);
            }
            if (xmlOrganism != null) {
                experimentDescription.getHostOrganisms().add(xmlOrganism);
            }
        } else {
            log.warn("The id of the host organism is not a NCBI id ");
        }


        if (!experimentDescriptions.contains(experimentDescription) && experimentDescription != null) {
            experimentDescriptions.add(experimentDescription);
        }

        return experimentDescriptions;
    }

    protected abstract BinaryInteraction newBinaryInteraction(Interactor interactorA, Interactor interactorB);

    public abstract InteractorConverter<?> getInteractorConverter();

    /**
     * TODO comment this
     *
     * @param binaryInteraction
     * @return a collection of InteractionTypes
     */
    private Collection<psidev.psi.mi.xml.model.InteractionType> getInteractionTypes(BinaryInteraction binaryInteraction) {
        List<psidev.psi.mi.xml.model.InteractionType> types = null;

        if (binaryInteraction.getInteractionTypes() != null) {

            types = new ArrayList<psidev.psi.mi.xml.model.InteractionType>(binaryInteraction.getInteractionTypes().size());
            List<CrossReference> tabInteractionTypes = binaryInteraction.getInteractionTypes();

            for (CrossReference interactionType : tabInteractionTypes) {
                psidev.psi.mi.xml.model.InteractionType type = null;
                try {
                    type = cvConverter.fromMitab(interactionType, psidev.psi.mi.xml.model.InteractionType.class);
                } catch (XmlConversionException e) {
                    e.printStackTrace();
                }

                if (type != null) {
                    if (!types.contains(type)) {
                        types.add(type);
                    }
                } else {
                    log.warn("Failed to convert interaction type: " + type);
                }
            }
        }
        return types;
    }

    /**
     * @param binaryInteraction
     * @param interactionMap
     * @return
     * @throws IllegalAccessException
     * @throws XmlConversionException
     */
    public Collection<Interaction> fromMitab(BinaryInteraction<?> binaryInteraction, Map<String, Collection<Participant>> interactionMap) throws IllegalAccessException, XmlConversionException {

        interactions = new ArrayList<Interaction>();

        Set<String> interactionAcs = new HashSet<String>();

        int numberOfInteraction = 1;
//        int index = 0;

        final List<CrossReference> acs = binaryInteraction.getInteractionAcs();

        if (acs.isEmpty()) {
            acs.add(new NullCrossReference(binaryInteraction));
        }

//        for ( int i = 0; i < acs.size(); i++ ) {

        //interaction ids field 14
        CrossReference interactionAc = acs.get(0);
        String interactionId = interactionAc.getIdentifier();
        interactionId = interactionId + "_" + binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier()
                + "_" + binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier();

        if (!interactionAcs.contains(interactionId)) {
            interactionAcs.add(interactionId);

            CrossReference source = binaryInteraction.getSourceDatabases().get(0);

            DbReference primaryReference = getPrimaryRef(interactionAc, source);
            Interaction interaction = new psidev.psi.mi.xml.model.Interaction();
            // this generates the id for the interaction
            interaction.setId(IdentifierGenerator.getInstance().nextId());
            interaction.setXref(new Xref(primaryReference));

            // database source field 13
            for (CrossReference sourceXref : binaryInteraction.getSourceDatabases()) {
                String id = sourceXref.getIdentifier();

                String refDbMi = null;
                String refDb = null;

                if (id.startsWith("MI:")) {
                    refDbMi = "MI:0488";
                    refDb = "psi-mi";
                }

                DbReference sourceReference = new DbReference(refDb, refDbMi, id, "source reference", "MI:0685");
                interaction.getXref().getSecondaryRef().add(sourceReference);

                if (sourceXref.hasText()) {
                    interaction.getAttributes().add(new Attribute("source reference:label", sourceXref.getText()));
                }

            }

            // set participants
            if (interactionMap.get(interactionId).size() > 1) {
                for (Participant participant : interactionMap.get(interactionId)) {
                    interaction.getParticipants().add(participant);
                }
            }

            // set interaction name
            if (getInteractionName(interaction) != null) {
                Names interactionName = getInteractionName(interaction);
                String shortLabel = interactionName.getShortLabel().concat("-".concat(String.valueOf(numberOfInteraction)));
                numberOfInteraction++;
                interactionName.setShortLabel(shortLabel);
                interaction.setNames(interactionName);
            }

            // set interaction Type field 12
            if (!getInteractionTypes(binaryInteraction).isEmpty()) {
                interaction.getInteractionTypes().addAll(getInteractionTypes(binaryInteraction));
            }

            // set experiment List
            Collection<ExperimentDescription> experiments = getExperimentDescriptions((T) binaryInteraction, 0);
            for (ExperimentDescription experimentDescription : experiments) {
                interaction.getExperiments().add(experimentDescription);
            }

            // set confidences field 15
            for (psidev.psi.mi.tab.model.Confidence tabConfidence : binaryInteraction.getConfidenceValues()) {
                Unit unit = new Unit();
                unit.setNames(new Names());
                unit.getNames().setShortLabel(tabConfidence.getType());
                if (tabConfidence.getText() != null) unit.getNames().setFullName(tabConfidence.getText());

                Confidence confidence = new Confidence(unit, tabConfidence.getValue());
                interaction.getConfidences().add(confidence);
            }

            // set xrefs field 25
            Collection<CrossReference> xrefs = binaryInteraction.getInteractionXrefs();
            if (xrefs != null) {
                if (!xrefs.isEmpty()) {
                    Collection<DbReference> secondaryRefs = new ArrayList<DbReference>();
                    for (CrossReference xref : xrefs) {
                        String database = xref.getDatabase();
                        String id = xref.getIdentifier();

                        //TODO What are the refType and refTypeAcs? How can we obtain the dbAcs?
                        DbReference secondaryRef = new DbReference(database, null, id, "identity", "MI:0356");
                        secondaryRefs.add(secondaryRef);

                    }
                    if (!secondaryRefs.isEmpty()) {
                        interaction.getXref().getSecondaryRef().addAll(secondaryRefs);
                    }
                }
            }

            //set Annotations field 28
            Collection<Annotation> annotations = binaryInteraction.getInteractionAnnotations();
            if (annotations != null) {
                if (!annotations.isEmpty()) {
                    for (Annotation annotation : annotations) {
                        Attribute attribute = new Attribute(annotation.getTopic(), annotation.getText());
                        interaction.getAttributes().add(attribute);
                    }
                }
            }

            //set parameters field 30
            Collection<Parameter> parameters = binaryInteraction.getInteractionParameters();
            if (parameters != null) {
                if (!parameters.isEmpty()) {
                    for (Parameter parameter : parameters) {
                        psidev.psi.mi.xml.model.Parameter xmlParameter = new psidev.psi.mi.xml.model.Parameter();

                        String parameterType = parameter.getType();
                        if (parameterType != null) {
                            // We complete the termAc for the most frequent terms

                            xmlParameter.setTerm(parameter.getType());
                            if (parameterType.equalsIgnoreCase("kd")) {
                                xmlParameter.setTermAc("MI:0646");
                            } else if (parameterType.equalsIgnoreCase("ic50")) {
                                xmlParameter.setTermAc("MI:0641");
                            } else if (parameterType.equalsIgnoreCase("kcat")) {
                                xmlParameter.setTermAc("MI:0645");
                            }

                        }

                        if (parameter.getBase() != null) {
                            xmlParameter.setBase(parameter.getBase());
                        }
                        if (parameter.getExponent() != null) {
                            xmlParameter.setExponent(parameter.getExponent());
                        }

                        if (parameter.getFactor() != null) {
                            xmlParameter.setFactor(parameter.getFactor());
                        }


                        String unit = parameter.getUnit();
                        if (unit != null) {
                            xmlParameter.setUnit(parameter.getUnit());
                            // We complete the unitAc for the most frequent units
                            if (unit.equalsIgnoreCase("molar")) {
                                xmlParameter.setUnitAc("MI:0648");
                            } else if (unit.equalsIgnoreCase("second -1")) {
                                xmlParameter.setUnitAc("IA:1721");
                            }
                        }

                        xmlParameter.setUncertainty(parameter.getUncertainty());

                    }
                }
            }

            //set checksum field 35
            Collection<Checksum> checksums = binaryInteraction.getInteractionChecksums();
            if (checksums != null) {
                if (!checksums.isEmpty()) {
                    for (Checksum checksum : checksums) {
                        Attribute attribute = new Attribute(checksum.getMethodName(), checksum.getChecksum());
                        interaction.getAttributes().add(attribute);
                    }
                }

            }

            //set negative field 36
            Boolean negativeInteraction = binaryInteraction.isNegativeInteraction();
            if (negativeInteraction != null) {
                interaction.setNegative(negativeInteraction);

            }
            populateInteractionFromMitab(interaction, binaryInteraction, 0);

            interactions.add(interaction);
        }

//            index++;
//        }

        return interactions;
    }

    protected abstract void populateInteractionFromMitab(Interaction interaction, BinaryInteraction<?> binaryInteraction, int index);
}