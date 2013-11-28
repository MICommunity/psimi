package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.InMemoryLightIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25Type;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

/**
 * The factory to get options for the InteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIWriterOptionFactory {

    private static final MIWriterOptionFactory instance = new MIWriterOptionFactory();

    private MIWriterOptionFactory(){
    }

    public static MIWriterOptionFactory getInstance() {
        return instance;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param outputFile : the file where to write the interactions
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(File outputFile){
        Map<String, Object> options = getMitabOptions(outputFile, InteractionObjectCategory.mixed, null, true, null, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param outputFile : the file where to write the interactions
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, File outputFile){
        Map<String, Object> options = getMitabOptions(outputFile, objectCategory, null, true, null, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param outputFile : the file where to write the interactions
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, File outputFile){
        Map<String, Object> options = getMitabOptions(outputFile, null, null, writeHeader, version, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param output : the output
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(OutputStream output){
        Map<String, Object> options = getMitabOptions(output, InteractionObjectCategory.mixed, null, true, null, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param output : the output
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, OutputStream output){
        Map<String, Object> options = getMitabOptions(output, objectCategory, null, true, null, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param output : the outputstream
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, OutputStream output){
        Map<String, Object> options = getMitabOptions(output, null, null, writeHeader, version, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writer : the writer
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(Writer writer){
        Map<String, Object> options = getMitabOptions(writer, InteractionObjectCategory.mixed, null, true, null, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param objectCategory : the interaction object type to write
     * @param writer : the writer
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, Writer writer){
        Map<String, Object> options = getMitabOptions(writer, objectCategory, null, true, null, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param writer : the writer
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, Writer writer){
        Map<String, Object> options = getMitabOptions(writer, null, null, writeHeader, version, false);
        return options;
    }

    /**
     * Create the options for the MITAB InteractionWriter.
     * @param objectCategory : the interaction object type to write
     * @param expansion : the complex expansion method to use if we have n-ary interactions
     * @param writeHeader : true if we want to write the header
     * @param version : the MITAB version
     * @param extended : true if all the aliases, features and confidences are pure mitab objects
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(Object output, InteractionObjectCategory objectCategory, ComplexExpansionMethod expansion, boolean writeHeader, MitabVersion version, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);
        options.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, output);
        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        if (expansion != null){
            options.put(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY, expansion);
        }
        options.put(MitabUtils.MITAB_HEADER_OPTION, writeHeader);
        options.put(MitabUtils.MITAB_VERSION_OPTION, version != null ? version : MitabVersion.v2_7);
        options.put(MitabUtils.MITAB_EXTENDED_OPTION, extended);
        return options;
    }

    /**
     *
     * @param objectCategory: type of objects to write. It is mixed by default
     * @param type: compact or expanded. It is expanded by default
     * @return
     */
    public Map<String, Object> getDefaultXml25Options(InteractionObjectCategory objectCategory, PsiXml25Type type){
        if (type == null){
            return getDefaultExpandedXml25Options(objectCategory);
        }
        switch (type){
            case compact:
                return getDefaultCompactXml25Options(objectCategory);
            case expanded:
                return getDefaultExpandedXml25Options(objectCategory);
            default:
                return getDefaultExpandedXml25Options(objectCategory);
        }
    }

    /**
     * Create the options for the default expanded PSI-XML 2.5 InteractionWriter.
     * The writer will not write extended XML objects and it will be based on an identity cache for generating ids
     * All sub complexes will be written as interactions.
     * @param objectCategory : the interaction object type to write
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getDefaultExpandedXml25Options(InteractionObjectCategory objectCategory){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.expanded);
        options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, new InMemoryLightIdentityObjectCache());

        options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Interaction, Boolean>()));
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, false);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, false);
        return options;
    }

    /**
     * Create the default options for the compact PSI-XML 2.5 InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry
     * All complexes will be written as interactions.
     * It will not write PsiXml25 extended object features
     * It will use an identity cache to generate ids for the different interactions/experiments/interactors/participants/features
     * @param objectCategory : the interaction object type to write
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getDefaultCompactXml25Options(InteractionObjectCategory objectCategory){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.compact);
        options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, new InMemoryIdentityObjectCache());
        options.put(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Experiment, Boolean>()));
        options.put(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Interactor, Boolean>()));
        options.put(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET_OPTION, new HashSet<String>());
        options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Experiment, Boolean>()));
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, false);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, false);
        return options;
    }

    /**
     * Create the options for the expanded PSI-XML 2.5 InteractionWriter.
     * The writer will not write extended XML objects and it will be based on an identity cache for generating ids
     * All sub complexes will be written as interactions.
     * @param objectCategory : the interaction object type to write
     * @param defaultSource : default source if no source provided
     * @param defaultReleaseDate : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getExpandedXml25Options(InteractionObjectCategory objectCategory, Source defaultSource,
                                                       XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.expanded);
        options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, new InMemoryLightIdentityObjectCache());

        options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Interaction, Boolean>()));

        if (defaultSource != null){
            options.put(PsiXml25Utils.DEFAULT_SOURCE_OPTION, defaultSource);
        }
        if (defaultReleaseDate != null){
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, defaultReleaseDate);
        }
        else{
            try {
                DatatypeFactory datatypeFactory = null;
                datatypeFactory = DatatypeFactory.newInstance();
                options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

            } catch (DatatypeConfigurationException e) {
                System.out.println(e);
            }
        }
        if (defaultEntryAnnotations != null){
            options.put(PsiXml25Utils.XML_ENTRY_ATTRIBUTES_OPTION, defaultEntryAnnotations);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, false);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, false);
        return options;
    }

    /**
     * Create the options for the compact PSI-XML 2.5 InteractionWriter.
     * The writer will not write extended XML objects and it will be based on an identity cache for generating ids
     * @param objectCategory : the interaction object type to write
     * @param defaultSource : default source if no source provided
     * @param defaultReleaseDate : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getCompactXml25Options(InteractionObjectCategory objectCategory, Source defaultSource,
                                                      XMLGregorianCalendar defaultReleaseDate,
                                                      Collection<Annotation> defaultEntryAnnotations){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.compact);
        options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, new InMemoryIdentityObjectCache());

        options.put(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Experiment, Boolean>()));
        options.put(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Interactor, Boolean>()));
        options.put(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET_OPTION, new HashSet<String>());
        options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, Collections.newSetFromMap(new IdentityHashMap<Interaction, Boolean>()));
        if (defaultSource != null){
            options.put(PsiXml25Utils.DEFAULT_SOURCE_OPTION, defaultSource);
        }
        if (defaultReleaseDate != null){
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, defaultReleaseDate);
        }
        else{
            try {
                DatatypeFactory datatypeFactory = null;
                datatypeFactory = DatatypeFactory.newInstance();
                options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

            } catch (DatatypeConfigurationException e) {
                System.out.println(e);
            }
        }
        if (defaultEntryAnnotations != null){
            options.put(PsiXml25Utils.XML_ENTRY_ATTRIBUTES_OPTION, defaultEntryAnnotations);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, false);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, false);
        return options;
    }


    /**
     * Create the options for the expanded PSI-XML 2.5 InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry.
     * @param objectCategory : the interaction object type to write
     * @param elementCache: the cache of objects necessary to assign ids to the different MI elements
     * @param interactionSet : the instance of set for the interactions that we want to use in compact and expanded mode
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getExpandedXml25Options(InteractionObjectCategory objectCategory, PsiXml25ObjectCache elementCache,
                                                       Set<Interaction> interactionSet, boolean writeComplexAsInteractors, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.expanded);
        if (elementCache != null){
            options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, elementCache);
        }
        if (interactionSet != null){
            options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, interactionSet);
        }
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, writeComplexAsInteractors);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, extended);
        return options;
    }

    /**
     * Create the options for the compact PSI-XML 2.5 InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry.
     * @param objectCategory : the interaction object type to write
     * @param elementCache: the cache of objects necessary to assign ids to the different MI elements
     * @param experimentSet : the instance of set for the experiments that we want to use in compact mode
     * @param interactorSet : the instance of set for the interactors that we want to use in compact mode
     * @param availabilitySet : the instance of set for the availability that we want to use in compact mode
     * @param interactionSet : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml25 objects
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getCompactXml25Options(InteractionObjectCategory objectCategory, PsiXml25ObjectCache elementCache,
                                                      Set<Experiment> experimentSet,Set<Interactor> interactorSet,Set<String> availabilitySet,
                                               Set<Interaction> interactionSet, boolean writeComplexAsInteractors, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.compact);
        if (elementCache != null){
            options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, elementCache);
        }
        if (experimentSet != null){
            options.put(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET_OPTION, experimentSet);
        }
        if (interactorSet != null){
            options.put(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET_OPTION, interactorSet);
        }
        if (availabilitySet != null){
            options.put(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET_OPTION, availabilitySet);
        }
        if (interactionSet != null){
            options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, interactionSet);
        }
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, writeComplexAsInteractors);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, extended);
        return options;
    }


    /**
     * Create the options for the compact PSI-XML 2.5 InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry.
     * All sub complexes will be written as interactions.
     * @param objectCategory : the interaction object type to write
     * @param elementCache: the cache of objects necessary to assign ids to the different MI elements
     * @param interactionSet : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param defaultSource : default source if no source provided
     * @param defaultReleaseDate : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml25 objects
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getExpandedXml25Options(InteractionObjectCategory objectCategory, PsiXml25ObjectCache elementCache,
                                                       Set<Interaction> interactionSet, Source defaultSource,
                                                       XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                                       boolean writeComplexAsInteractors, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.expanded);
        if (elementCache != null){
            options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, elementCache);
        }
        if (interactionSet != null){
            options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, interactionSet);
        }
        if (defaultSource != null){
            options.put(PsiXml25Utils.DEFAULT_SOURCE_OPTION, defaultSource);
        }
        if (defaultReleaseDate != null){
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, defaultReleaseDate);
        }
        else{
            try {
                DatatypeFactory datatypeFactory = null;
                datatypeFactory = DatatypeFactory.newInstance();
                options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

            } catch (DatatypeConfigurationException e) {
                System.out.println(e);
            }
        }
        if (defaultEntryAnnotations != null){
            options.put(PsiXml25Utils.XML_ENTRY_ATTRIBUTES_OPTION, defaultEntryAnnotations);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, writeComplexAsInteractors);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, extended);
        return options;
    }

    /**
     * Create the options for the compact PSI-XML 2.5 InteractionWriter.
     * @param objectCategory : the interaction object type to write
     * @param elementCache: the cache of objects necessary to assign ids to the different MI elements
     * @param experimentSet : the instance of set for the experiments that we want to use in compact mode
     * @param interactorSet : the instance of set for the interactors that we want to use in compact mode
     * @param availabilitySet : the instance of set for the availability that we want to use in compact mode
     * @param interactionSet : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param defaultSource : default source if no source provided
     * @param defaultReleaseDate : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml25 objects
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getCompactXml25Options(InteractionObjectCategory objectCategory, PsiXml25ObjectCache elementCache,
                                                      Set<Experiment> experimentSet,Set<Interactor> interactorSet,Set<String> availabilitySet,
                                                      Set<Interaction> interactionSet, Source defaultSource,
                                                      XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                                      boolean writeComplexAsInteractors, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, PsiXml25Type.compact);
        if (elementCache != null){
            options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, elementCache);
        }
        if (experimentSet != null){
            options.put(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET_OPTION, experimentSet);
        }
        if (interactorSet != null){
            options.put(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET_OPTION, interactorSet);
        }
        if (availabilitySet != null){
            options.put(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET_OPTION, availabilitySet);
        }
        if (interactionSet != null){
            options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, interactionSet);
        }
        if (defaultSource != null){
            options.put(PsiXml25Utils.DEFAULT_SOURCE_OPTION, defaultSource);
        }
        if (defaultReleaseDate != null){
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, defaultReleaseDate);
        }
        else{
            try {
                DatatypeFactory datatypeFactory = null;
                datatypeFactory = DatatypeFactory.newInstance();
                options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

            } catch (DatatypeConfigurationException e) {
                System.out.println(e);
            }
        }
        if (defaultEntryAnnotations != null){
            options.put(PsiXml25Utils.XML_ENTRY_ATTRIBUTES_OPTION, defaultEntryAnnotations);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, writeComplexAsInteractors);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, extended);
        return options;
    }

    /**
     * Create the options for the PSI-XML 2.5 InteractionWriter.
     * @param objectCategory : the interaction object type to write
     * @param xmlType: the type of xml (compact or expanded). The default type is expanded
     * @param elementCache: the cache of objects necessary to assign ids to the different MI elements
     * @param experimentSet : the instance of set for the experiments that we want to use in compact mode
     * @param interactorSet : the instance of set for the interactors that we want to use in compact mode
     * @param availabilitySet : the instance of set for the availability that we want to use in compact mode
     * @param interactionSet : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param defaultSource : default source if no source provided
     * @param defaultReleaseDate : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml25 objects
     * @return the options for the PSI-XML 2.5  InteractionWriter
     */
    public Map<String, Object> getXml25Options(InteractionObjectCategory objectCategory, PsiXml25Type xmlType,
                                               PsiXml25ObjectCache elementCache, Set<Experiment> experimentSet,
                                               Set<Interactor> interactorSet, Set<String> availabilitySet,
                                               Set<Interaction> interactionSet, Source defaultSource,
                                               XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                               boolean writeComplexAsInteractors, boolean extended){
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.mixed);
        options.put(PsiXml25Utils.XML_TYPE_OPTION, xmlType != null ? xmlType : PsiXml25Type.expanded);
        if (elementCache != null){
            options.put(PsiXml25Utils.ELEMENT_WITH_ID_CACHE_OPTION, elementCache);
        }
        if (experimentSet != null){
            options.put(PsiXml25Utils.COMPACT_XML_EXPERIMENT_SET_OPTION, experimentSet);
        }
        if (interactorSet != null){
            options.put(PsiXml25Utils.COMPACT_XML_INTERACTOR_SET_OPTION, interactorSet);
        }
        if (availabilitySet != null){
            options.put(PsiXml25Utils.COMPACT_XML_AVAILABILITY_SET_OPTION, availabilitySet);
        }
        if (interactionSet != null){
            options.put(PsiXml25Utils.XML_INTERACTION_SET_OPTION, interactionSet);
        }
        if (defaultSource != null){
            options.put(PsiXml25Utils.DEFAULT_SOURCE_OPTION, defaultSource);
        }
        if (defaultReleaseDate != null){
            options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, defaultReleaseDate);
        }
        else{
            try {
                DatatypeFactory datatypeFactory = null;
                datatypeFactory = DatatypeFactory.newInstance();
                options.put(PsiXml25Utils.DEFAULT_RELEASE_DATE_OPTION, datatypeFactory.newXMLGregorianCalendar());

            } catch (DatatypeConfigurationException e) {
                System.out.println(e);
            }
        }
        if (defaultEntryAnnotations != null){
            options.put(PsiXml25Utils.XML_ENTRY_ATTRIBUTES_OPTION, defaultEntryAnnotations);
        }
        options.put(PsiXml25Utils.WRITE_COMPLEX_AS_INTERACTOR_OPTION, writeComplexAsInteractors);
        options.put(PsiXml25Utils.XML25_EXTENDED_OPTION, extended);
        return options;
    }
}
