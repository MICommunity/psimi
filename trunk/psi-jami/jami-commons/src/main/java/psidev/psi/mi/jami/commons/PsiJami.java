package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.parser.*;
import psidev.psi.mi.jami.tab.io.writer.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

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

        // mitab interaction evidence writers
        initialiseInteractionEvidenceWriters();

        // mitab modelled interaction writers
        initialiseModelledInteractionWriters();

        // mitab mixed interaction writer
        initialiseMixedInteractionWriters();
    }

    public static void initialiseMixedInteractionWriters() {
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions28 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed, MitabVersion.v2_5, false, true);
        writerFactory.registerDataSourceWriter(Mitab25Writer.class, supportedOptions28);

        Map<String, Object> supportedOptions29 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed, MitabVersion.v2_5, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25Writer.class, supportedOptions29);

        Map<String, Object> supportedOptions30 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed_binary, MitabVersion.v2_5, false, false);
        writerFactory.registerDataSourceWriter(Mitab25BinaryWriter.class, supportedOptions30);

        Map<String, Object> supportedOptions31 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed_binary, MitabVersion.v2_5, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25BinaryWriter.class, supportedOptions31);

        Map<String, Object> supportedOptions32 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed, MitabVersion.v2_6, false, true);
        writerFactory.registerDataSourceWriter(Mitab26Writer.class, supportedOptions32);

        Map<String, Object> supportedOptions33 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed, MitabVersion.v2_6, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26Writer.class, supportedOptions33);

        Map<String, Object> supportedOptions34 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed_binary, MitabVersion.v2_6, false, false);
        writerFactory.registerDataSourceWriter(Mitab26BinaryWriter.class, supportedOptions34);

        Map<String, Object> supportedOptions35 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed_binary, MitabVersion.v2_6, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26BinaryWriter.class, supportedOptions35);

        Map<String, Object> supportedOptions36 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed, MitabVersion.v2_7, false, true);
        writerFactory.registerDataSourceWriter(Mitab27Writer.class, supportedOptions36);

        Map<String, Object> supportedOptions37 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed, MitabVersion.v2_7, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27Writer.class, supportedOptions37);

        Map<String, Object> supportedOptions38 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed_binary, MitabVersion.v2_7, false, false);
        writerFactory.registerDataSourceWriter(Mitab27BinaryWriter.class, supportedOptions38);

        Map<String, Object> supportedOptions39 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.mixed_binary, MitabVersion.v2_7, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27BinaryWriter.class, supportedOptions39);
    }

    public static void initialiseModelledInteractionWriters() {
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions16 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled, MitabVersion.v2_5, false, true);
        writerFactory.registerDataSourceWriter(Mitab25ModelledInteractionWriter.class, supportedOptions16);

        Map<String, Object> supportedOptions17 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled, MitabVersion.v2_5, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledInteractionWriter.class, supportedOptions17);

        Map<String, Object> supportedOptions18 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled_binary, MitabVersion.v2_5, false, false);
        writerFactory.registerDataSourceWriter(Mitab25ModelledBinaryWriter.class, supportedOptions18);

        Map<String, Object> supportedOptions19 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled_binary, MitabVersion.v2_5, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledBinaryWriter.class, supportedOptions19);

        Map<String, Object> supportedOptions20 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled, MitabVersion.v2_6, false, true);
        writerFactory.registerDataSourceWriter(Mitab26ModelledInteractionWriter.class, supportedOptions20);

        Map<String, Object> supportedOptions21 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled, MitabVersion.v2_6, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26ModelledInteractionWriter.class, supportedOptions21);

        Map<String, Object> supportedOptions22 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled_binary, MitabVersion.v2_6, false, false);
        writerFactory.registerDataSourceWriter(Mitab26ModelledBinaryWriter.class, supportedOptions22);

        Map<String, Object> supportedOptions23 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled_binary, MitabVersion.v2_6, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26ModelledBinaryWriter.class, supportedOptions23);

        Map<String, Object> supportedOptions24 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled, MitabVersion.v2_7, false, true);
        writerFactory.registerDataSourceWriter(Mitab27ModelledInteractionWriter.class, supportedOptions24);

        Map<String, Object> supportedOptions25 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled, MitabVersion.v2_7, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27ModelledInteractionWriter.class, supportedOptions25);

        Map<String, Object> supportedOptions26 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled_binary, MitabVersion.v2_7, false, false);
        writerFactory.registerDataSourceWriter(Mitab27ModelledBinaryWriter.class, supportedOptions26);

        Map<String, Object> supportedOptions27 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.modelled_binary, MitabVersion.v2_7, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27ModelledBinaryWriter.class, supportedOptions27);
    }

    public static void initialiseInteractionEvidenceWriters() {
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions4 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.evidence, MitabVersion.v2_5, false, true);
        writerFactory.registerDataSourceWriter(Mitab25InteractionEvidenceWriter.class, supportedOptions4);

        Map<String, Object> supportedOptions5 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.evidence, MitabVersion.v2_5, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25InteractionEvidenceWriter.class, supportedOptions5);

        Map<String, Object> supportedOptions6 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.binary_evidence, MitabVersion.v2_5, false, false);
        writerFactory.registerDataSourceWriter(Mitab25BinaryEvidenceWriter.class, supportedOptions6);

        Map<String, Object> supportedOptions7 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.binary_evidence, MitabVersion.v2_5, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25BinaryEvidenceWriter.class, supportedOptions7);

        Map<String, Object> supportedOptions8 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.evidence, MitabVersion.v2_6, false, true);
        writerFactory.registerDataSourceWriter(Mitab26InteractionEvidenceWriter.class, supportedOptions8);

        Map<String, Object> supportedOptions9 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.evidence, MitabVersion.v2_6, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26InteractionEvidenceWriter.class, supportedOptions9);

        Map<String, Object> supportedOptions10 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.binary_evidence, MitabVersion.v2_6, false, false);
        writerFactory.registerDataSourceWriter(Mitab26BinaryEvidenceWriter.class, supportedOptions10);

        Map<String, Object> supportedOptions11 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.binary_evidence, MitabVersion.v2_6, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26BinaryEvidenceWriter.class, supportedOptions11);

        Map<String, Object> supportedOptions12 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.evidence, MitabVersion.v2_7, false, true);
        writerFactory.registerDataSourceWriter(Mitab27InteractionEvidenceWriter.class, supportedOptions12);

        Map<String, Object> supportedOptions13 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.evidence, MitabVersion.v2_7, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27InteractionEvidenceWriter.class, supportedOptions13);

        Map<String, Object> supportedOptions14 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.binary_evidence, MitabVersion.v2_7, false, false);
        writerFactory.registerDataSourceWriter(Mitab27BinaryEvidenceWriter.class, supportedOptions14);

        Map<String, Object> supportedOptions15 = createInteractionWriterOptions(MIFileType.mitab.toString(), InteractionObjectCategory.binary_evidence, MitabVersion.v2_7, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27BinaryEvidenceWriter.class, supportedOptions15);
    }

    public static void initialiseAllMIDataSources() {

        // mitab interaction evidence datasource
        initialiseInteractionEvidenceSources();

        // modelled interaction mitab datasource
        initialiseModelledInteractionSources();

        // basic interaction mitab datasource
        initialiseBasicInteractionSources();
    }

    public static void initialiseBasicInteractionSources() {
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions3 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.basic);
        datasourceFactory.registerDataSource(LightMitabDataSource.class, supportedOptions3);

        Map<String, Object> supportedOptions33 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.basic_binary);
        datasourceFactory.registerDataSource(LightMitabBinaryDataSource.class, supportedOptions33);
    }

    public static void initialiseModelledInteractionSources() {
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions2 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.modelled);
        datasourceFactory.registerDataSource(MitabModelledDataSource.class, supportedOptions2);

        Map<String, Object> supportedOptions22 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.modelled_binary);
        datasourceFactory.registerDataSource(MitabModelledBinaryDataSource.class, supportedOptions22);
    }

    public static void initialiseInteractionEvidenceSources() {
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions1 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.evidence);
        datasourceFactory.registerDataSource(MitabEvidenceDataSource.class, supportedOptions1);

        Map<String, Object> supportedOptions11 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.binary_evidence);
        datasourceFactory.registerDataSource(MitabBinaryEvidenceDataSource.class, supportedOptions11);
    }

    private static Map<String, Object> createInteractionWriterOptions(String outputFormat, InteractionObjectCategory interactionCategory, MitabVersion version, boolean extended, boolean needCompleExpansion) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(InteractionWriterFactory.OUTPUT_FORMAT_OPTION_KEY, outputFormat);
        supportedOptions4.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, interactionCategory);
        if (needCompleExpansion){
            supportedOptions4.put(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY, null);
        }
        supportedOptions4.put(MitabUtils.MITAB_HEADER_OPTION, null);
        supportedOptions4.put(MitabUtils.MITAB_VERSION_OPTION, version);
        supportedOptions4.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, null);
        supportedOptions4.put(MitabUtils.MITAB_EXTENDED_OPTION, extended);
        return supportedOptions4;
    }

    private static Map<String, Object> createDataSourceOptions(String inputFormat, boolean streaming, InteractionObjectCategory objectCategory) {
        Map<String, Object> supportedOptions1 = new HashMap<String, Object>(7);
        supportedOptions1.put(MIDataSourceFactory.INPUT_FORMAT_OPTION_KEY, inputFormat);
        supportedOptions1.put(MIDataSourceFactory.STREAMING_OPTION_KEY, streaming);
        supportedOptions1.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory);
        supportedOptions1.put(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY, null);
        supportedOptions1.put(MIDataSourceFactory.INPUT_OPTION_KEY, null);
        return supportedOptions1;
    }
}
