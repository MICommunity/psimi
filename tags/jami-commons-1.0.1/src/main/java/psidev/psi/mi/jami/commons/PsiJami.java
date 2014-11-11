package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.factory.options.MIDataSourceOptions;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.datasource.DefaultMitabSource;
import psidev.psi.mi.jami.tab.extension.datasource.DefaultMitabStreamSource;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabWriterOptions;
import psidev.psi.mi.jami.tab.io.writer.DefaultMitabWriter;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.io.writer.DefaultXmlWriter;
import psidev.psi.mi.jami.xml.model.extension.datasource.DefaultPsiXmlSource;
import psidev.psi.mi.jami.xml.model.extension.datasource.DefaultPsiXmlStreamSource;
import psidev.psi.mi.jami.xml.model.extension.factory.options.PsiXmlWriterOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a central access to basic methods in psi-jami.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class PsiJami {

    public static void initialiseAllFactories(){

        initialiseAllMIDataSources();

        initialiseAllInteractionWriters();
    }

    public static void initialiseAllInteractionWriters() {
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions28 = createMITABInteractionWriterOptions(null, null, null, null);
        writerFactory.registerDataSourceWriter(DefaultMitabWriter.class, supportedOptions28);

        Map<String, Object> supportedOptions4 = createXMLInteractionWriterOptions(null, null, null, null, null);
        writerFactory.registerDataSourceWriter(DefaultXmlWriter.class, supportedOptions4);
    }

    public static void initialiseAllMIDataSources() {
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions3333 = createDataSourceOptions(MIFileType.mitab.toString(), true, null, null);
        datasourceFactory.registerDataSource(DefaultMitabStreamSource.class, supportedOptions3333);

        Map<String, Object> supportedOptions3334 = createDataSourceOptions(MIFileType.mitab.toString(), false, null, null);
        datasourceFactory.registerDataSource(DefaultMitabSource.class, supportedOptions3334);

        Map<String, Object> supportedOptions4 = createXmlDataSourceOptions(MIFileType.psimi_xml.toString(), true, null, null);
        datasourceFactory.registerDataSource(DefaultPsiXmlStreamSource.class, supportedOptions4);

        Map<String, Object> supportedOptions5 = createXmlDataSourceOptions(MIFileType.psimi_xml.toString(), false, null, null);
        datasourceFactory.registerDataSource(DefaultPsiXmlSource.class, supportedOptions5);
    }

    private static Map<String, Object> createMITABInteractionWriterOptions(InteractionCategory interactionCategory, ComplexType complexType,
                                                                           MitabVersion version, Boolean extended) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        supportedOptions4.put(InteractionWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(MitabWriterOptions.MITAB_HEADER_OPTION, null);
        supportedOptions4.put(MitabWriterOptions.MITAB_VERSION_OPTION, version);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        supportedOptions4.put(MitabWriterOptions.MITAB_EXTENDED_OPTION, extended);
        return supportedOptions4;
    }

    private static Map<String, Object> createXMLInteractionWriterOptions(InteractionCategory interactionCategory, ComplexType complexType,
                                                                         PsiXmlType type,
                                                                         Boolean extended, Boolean writeNames) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(14);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psimi_xml.toString());
        supportedOptions4.put(InteractionWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(PsiXmlWriterOptions.XML_TYPE_OPTION, type);
        supportedOptions4.put(PsiXmlWriterOptions.XML_EXTENDED_OPTION, extended);
        supportedOptions4.put(PsiXmlWriterOptions.XML_NAMES_OPTION, writeNames);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        supportedOptions4.put(PsiXmlWriterOptions.COMPACT_XML_EXPERIMENT_SET_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.COMPACT_XML_INTERACTOR_SET_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.COMPACT_XML_AVAILABILITY_SET_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.XML_INTERACTION_SET_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.DEFAULT_RELEASE_DATE_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.DEFAULT_SOURCE_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.XML_ENTRY_ATTRIBUTES_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.WRITE_COMPLEX_AS_INTERACTOR_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.XML_VERSION_OPTION, null);
        return supportedOptions4;
    }

    private static Map<String, Object> createDataSourceOptions(String inputFormat, boolean streaming, InteractionCategory objectCategory,
                                                               ComplexType complexType) {
        Map<String, Object> supportedOptions1 = new HashMap<String, Object>(7);
        supportedOptions1.put(MIFileDataSourceOptions.INPUT_TYPE_OPTION_KEY, inputFormat);
        supportedOptions1.put(MIFileDataSourceOptions.STREAMING_OPTION_KEY, streaming);
        supportedOptions1.put(MIFileDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY, objectCategory);
        supportedOptions1.put(MIFileDataSourceOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions1.put(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY, null);
        supportedOptions1.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, null);
        supportedOptions1.put(MIFileDataSourceOptions.INTERACTOR_FACTORY_OPTION_KEY, null);
        return supportedOptions1;
    }

    private static Map<String, Object> createXmlDataSourceOptions(String inputFormat, boolean streaming, InteractionCategory objectCategory,
                                                                  ComplexType complexType) {
        Map<String, Object> supportedOptions1 = createDataSourceOptions(inputFormat, streaming, objectCategory, complexType);
        supportedOptions1.put(MIDataSourceOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions1.put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, null);
        return supportedOptions1;
    }
}
