/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.directoryProcessor.InputDirectoryProcessorStrategy;
import psidev.psi.mi.tab.directoryProcessor.PatternBasedFilenameSelection;
import psidev.psi.mi.tab.expansion.ExpansionStrategy;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.tab.processor.PostProcessorStrategy;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.*;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Utility class allowing to convert PSI-MI XML data into MITAB25.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class Xml2Tab {

	/**
	 * Sets up a logger for that class.
	 */
	public static final Log log = LogFactory.getLog(Xml2Tab.class);

	public static final String PSI_MI = "psi-mi";
	public static final String PSI_MI_REF = "MI:0488";
	public static final String IDENTITY = "identity";
	public static final String IDENTITY_REF = "MI:0356";
	public static final String UNKNOWN = "unknown";

	private static final Pattern XML_EXTENSION = Pattern.compile(".*\\.xml");

	/**
	 * Strategy allowing to customize how the converter behaves when the input if a directory instead of a file.
	 * The default behaviour is to search recursively for any file matching the pattern '*.xml' .
	 */
	public static final InputDirectoryProcessorStrategy DEFAULT_DIRECTORY_PROCESSOR = new PatternBasedFilenameSelection(true, XML_EXTENSION);

	/**
	 * Collection<BinaryInteractionImpl> post processor.
	 */
	private PostProcessorStrategy postProcessor;

	/**
	 * When set, expand interaction read from XML file.
	 */
	private ExpansionStrategy expansionStrategy;

	/**
	 * Strategy defining how a directory should be handled
	 */
	private InputDirectoryProcessorStrategy directoryProcessorStrategy = DEFAULT_DIRECTORY_PROCESSOR;

	/**
	 * The interaction converter
	 */
	private InteractionConverter<?> interactionConverter;


	///////////////////////////
	// Constructor

	/**
	 * Constructs a new Xml2Tab.
	 */
	public Xml2Tab() {
		this(new MitabInteractionConverter(), null);
	}

	public Xml2Tab(InteractionConverter<?> interactionConverter) {
		this(interactionConverter, null);
	}

	public Xml2Tab(InteractionConverter<?> interactionConverter, PostProcessorStrategy postProcessorStrategy) {
		this.interactionConverter = interactionConverter;
		this.postProcessor = postProcessorStrategy;
	}

	//////////////////////////
	// Getters & Setters

	/**
	 * Getter for property 'postProcessor'.
	 *
	 * @return Value for property 'postProcessor'.
	 */
	public PostProcessorStrategy getPostProcessor() {
		return postProcessor;
	}

	/**
	 * Setter for property 'postProcessor'.
	 *
	 * @param postProcessor Value to set for property 'postProcessor'.
	 */
	public void setPostProcessor(PostProcessorStrategy postProcessor) {
		this.postProcessor = postProcessor;
	}

	/**
	 * Getter for property 'expansionStrategy'.
	 *
	 * @return Value for property 'expansionStrategy'.
	 */
	public ExpansionStrategy getExpansionStrategy() {
		return expansionStrategy;
	}

	/**
	 * Setter for property 'expansionStrategy'.
	 *
	 * @param expansionStrategy Value to set for property 'expansionStrategy'.
	 */
	public void setExpansionStrategy(ExpansionStrategy expansionStrategy) {
		this.expansionStrategy = expansionStrategy;
	}

	/**
	 * Getter for property 'directoryProcessorStrategy'.
	 *
	 * @return Value for property 'directoryProcessorStrategy'.
	 */
	public InputDirectoryProcessorStrategy getDirectoryProcessorStrategy() {
		return directoryProcessorStrategy;
	}

	/**
	 * Setter for property 'directoryProcessorStrategy'.
	 *
	 * @param directoryProcessorStrategy Value to set for property 'directoryProcessorStrategy'.
	 */
	public void setDirectoryProcessorStrategy(InputDirectoryProcessorStrategy directoryProcessorStrategy) {
		this.directoryProcessorStrategy = directoryProcessorStrategy;
	}


	////////////////////////
	// Public methods

	/**
	 * Convert a file into a collection of PSIMITAB BinaryInteractionImpl. Post processing will be applied if requested.
	 *
	 * @param file the file to convert.
	 * @return a non null collection of PSIMITAB BinaryInteractionImpl.
	 * @throws TabConversionException if an error occur during the processing.
	 */
	public Collection<BinaryInteraction> convert(File file) throws TabConversionException {
		return convert(file, false);
	}

	/**
	 * Convert a file into a collection of PSIMITAB BinaryInteractionImpl.
	 *
	 * @param file               the file to convert.
	 * @param skipPostProcessing
	 * @return a non null collection of PSIMITAB BinaryInteractionImpl.
	 * @throws TabConversionException if an error occur during the processing.
	 */
	public Collection<BinaryInteraction> convert(File file, boolean skipPostProcessing) throws TabConversionException {

		if (file == null) {
			throw new IllegalArgumentException("File cannot be null.");
		}

		Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();

		File f = null;
		try {
			if (file.isDirectory()) {

				if (log.isDebugEnabled()) log.debug("Converting directory: " + file.getAbsolutePath());

				// search for all XML files recursively
				Collection<File> files = null;
				if (directoryProcessorStrategy == null) {
					files = DEFAULT_DIRECTORY_PROCESSOR.process(file);
				} else {
					files = directoryProcessorStrategy.process(file);
				}

				// convert each file found
				for (Iterator<File> iterator = files.iterator(); iterator.hasNext(); ) {
					f = iterator.next();

					if (log.isDebugEnabled()) log.debug("Converting file: " + f.getAbsolutePath());

					PsimiXmlReader reader = new PsimiXmlReader();
					EntrySet entrySet = reader.read(f);
					interactions.addAll(convert(entrySet, true)); // skip post processing, we'll do it later.
				}

			} else {
				if (log.isDebugEnabled()) log.debug("Converting file: " + file);

				PsimiXmlReader reader = new PsimiXmlReader();
				EntrySet entrySet = reader.read(file);
				interactions.addAll(convert(entrySet, true)); // skip post processing, we'll do it later.
			}
		} catch (Exception e) {
			throw new TabConversionException("An error occured during PSIMITAB conversion of " +
					(f == null ? file.getAbsolutePath() : f.getAbsolutePath()), e);
		}

//        if ( false == skipPostProcessing ) {
//            interactions = doPostProcessing( interactions );
//        }

		return interactions;
	}

	/**
	 * Convert a collection of files into a collection of PSIMITAB BinaryInteractionImpl.
	 *
	 * @param inputFiles Collection of files to convert.
	 * @return a non null collection of PSIMITAB BinaryInteractionImpl.
	 * @throws TabConversionException if an error occur during the processing.
	 */
	public Collection<BinaryInteraction> convert(Collection<File> inputFiles) throws TabConversionException {
		return convert(inputFiles, false);
	}

	/**
	 * Convert a collection of files into a collection of PSIMITAB BinaryInteractionImpl. The postprocessing can be
	 * enabled/disabled by using the skipPostProcessing variable. The postprocessing can be enabled/disabled by using
	 * the skipPostProcessing variable.
	 *
	 * @param inputFiles Collection of files to convert.
	 * @return a non null collection of PSIMITAB BinaryInteractionImpl.
	 * @throws TabConversionException if an error occur during the processing.
	 */
	public Collection<BinaryInteraction> convert(Collection<File> inputFiles, boolean skipPostProcessing) throws TabConversionException {
		Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>(128);

		for (File file : inputFiles) {
			interactions.addAll(convert(file));
		}

//        if ( false == skipPostProcessing ) {
//            interactions = doPostProcessing( interactions );
//        }

		return interactions;
	}

	/**
	 * Converts a given entrySet into a Collection of BinaryInteractionImpl. <br/> If a ExpansionStrategy was set, every
	 * interaction in the given entrySet will be expanded prior to conversion to MITAB25. Should the expansion result in
	 * multiple interaction, they are converted iteratively.
	 *
	 * @param entrySet the entrySet to convert in MITAB25.
	 * @return a non null collection of BinaryInteractionImpl.
	 */
	public Collection<BinaryInteraction> convert(EntrySet entrySet) throws TabConversionException {
		return convert(entrySet, false);
	}

	/**
	 * Converts a given entrySet into a Collection of BinaryInteractionImpl. <br/> If a ExpansionStrategy was set, every
	 * interaction in the given entrySet will be expanded prior to conversion to MITAB25. Should the expansion result in
	 * multiple interaction, they are converted iteratively. The postprocessing can be enabled/disabled by using the
	 * skipPostProcessing variable.
	 *
	 * @param entrySet           the entrySet to convert in MITAB25.
	 * @param skipPostProcessing if true, post processing is not applied.
	 * @return a non null collection of BinaryInteractionImpl.
	 */
	public Collection<BinaryInteraction> convert(EntrySet entrySet, boolean skipPostProcessing) throws TabConversionException {

		if (entrySet == null) {
			throw new IllegalArgumentException("You must give a non null entrySet.");
		}

		Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();

		// propagate configuration

		for (Entry entry : entrySet.getEntries()) {

			String sourceName = null;
			String sourceId = null;
			String sourceDb = null;

			// extract source database
			if (entry.hasSource()) {

				Source source = entry.getSource();
				if (source.hasNames()) {
					Names names = source.getNames();
					if (names.getShortLabel() != null) {
						sourceName = names.getShortLabel();
					}
					else if (names.getFullName() != null) {
						sourceName = names.getFullName();
					} else {
						 sourceName = UNKNOWN;
					}
				}

				if (source.hasXref()) {
					Xref xref = source.getXref();
					Collection<DbReference> refs = XrefUtils.searchByType(xref, IDENTITY, IDENTITY_REF);

					for (Iterator<DbReference> dbReferenceIterator = refs.iterator(); dbReferenceIterator.hasNext(); ) {
						DbReference dbReference = dbReferenceIterator.next();

						// if the dbAc is not the PSI-MI (MI:0488), remove it from the list
						if (dbReference.getDbAc() != null && !(dbReference.getDbAc().equals(PSI_MI_REF))) {
							dbReferenceIterator.remove();
						}
                        else if (dbReference.getDbAc() == null && !(dbReference.getDb().equals(PSI_MI))){
                            dbReferenceIterator.remove();
                        }
					}

					if (refs.size() >= 1) { //We choose the first one
						DbReference ref = refs.iterator().next();
						if (ref != null) {
							sourceId = ref.getId();
							sourceDb = ref.getDb();
						}

					}
				}
			}

			if (sourceId != null && sourceDb != null) {
				// set the source on the converter
				interactionConverter.addSourceDatabase(new CrossReferenceImpl(sourceDb, sourceId, sourceName));
			} else {
				interactionConverter.addSourceDatabase(new CrossReferenceImpl(UNKNOWN, sourceName, sourceName));
			}


			if (log.isDebugEnabled()) {
				log.debug("Interaction count: " + entry.getInteractions().size());
			}

			for (Interaction interaction : entry.getInteractions()) {

				// apply interaction expansion if required.
				if (expansionStrategy != null && interaction.getParticipants().size() != 2) {

					// run expansion on interaction prior to MITAB25 conversion
					Collection<Interaction> expandedInteractions = expansionStrategy.expand(interaction);

					for (Interaction exi : expandedInteractions) {
						// convert the interaction into a MITAB25 line
						BinaryInteraction binaryInteraction = interactionConverter.toMitab(exi);
//                        processAfterConversion(binaryInteraction, true);
						if (binaryInteraction != null) {

							String expansionName = expansionStrategy.getName();
							List<CrossReference> complexExpansion = new ArrayList<CrossReference>();
							if (expansionName.equalsIgnoreCase("spoke")) {
								complexExpansion.add(new CrossReferenceImpl(PSI_MI, "MI:1060", "spoke expansion"));
							} else if (expansionName.equalsIgnoreCase("matrix")) {
								complexExpansion.add(new CrossReferenceImpl(PSI_MI, "MI:1061", "matrix expansion"));
							} else if (expansionName.equalsIgnoreCase("bipartite")) {
								complexExpansion.add(new CrossReferenceImpl(PSI_MI, "MI:1062", "bipartite expansion"));
							}
							binaryInteraction.setComplexExpansion(complexExpansion);

							//Creation date and update date field 31 and 32
							//Now a days is the same
							List<Date> creationDate = new ArrayList<Date>();
							creationDate.add(entry.getSource().getReleaseDate());
							if (!creationDate.isEmpty()) {
								binaryInteraction.setCreationDate(creationDate);
								binaryInteraction.setUpdateDate(creationDate);

							}

							interactions.add(binaryInteraction);
						}
					}

				} else {

					// convert the interaction into a MITAB25 line
					BinaryInteraction binaryInteraction = interactionConverter.toMitab(interaction);
//                    processAfterConversion(binaryInteraction, false);

					if (binaryInteraction != null) {
						//Creation date and update date field 31 and 32
						//Now a days is the same
						//TODO add a overwrite mechanism?

						List<Date> creationDate = new ArrayList<Date>();
						creationDate.add(entry.getSource().getReleaseDate());
						if (!creationDate.isEmpty()) {
							binaryInteraction.setCreationDate(creationDate);
							binaryInteraction.setUpdateDate(creationDate);
						}

						interactions.add(binaryInteraction);
					}
				}
			}
		}

//        if (!skipPostProcessing) {
//            interactions = doPostProcessing( interactions );
//        }

		return interactions;
	}

//    @Deprecated
//    protected void processAfterConversion(BinaryInteraction binaryInteraction, boolean expanded) {
//        // nothing here. This methods is only here to keep the intact-psimitab dependecy happy. Not good design.
//    }

//    /**
//     * Apply post processing to the given collecition of interactions. if no processing was requested, the given
//     * collection is returned.
//     *
//     * @param interactions the collection of interaction on which to apply post processing.
//     * @return a non null collection of interactions.
//     */
//    @Deprecated
//    private Collection<BinaryInteraction> doPostProcessing( final Collection<BinaryInteraction> interactions ) {
//
//        if ( interactions == null ) {
//            throw new IllegalArgumentException( "Interaction cannot be null." );
//        }
//
//        Collection<BinaryInteraction> processedInteraction = null;
//
//        // Run post processing (if requested)
//        if ( postProcessor != null ) {
//            if ( log.isDebugEnabled() ) {
//                log.debug( "Running " + postProcessor.getClass().getSimpleName() + "..." );
//            }
//            //postProcessor.setColumnHandler( columnHandler );
//            processedInteraction = postProcessor.process( interactions );
//            log.debug( "Post processing completed." );
//        } else {
//            log.debug( "No post processing requested." );
//            processedInteraction = interactions;
//        }
//
//        return processedInteraction;
//    }

	public EntrySet convert(Collection<BinaryInteraction> interactions) {
		// TODO impplement that !
		throw new UnsupportedOperationException();
	}
}
