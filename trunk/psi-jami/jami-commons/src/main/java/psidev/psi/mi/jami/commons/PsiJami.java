package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.datasource.InteractionWriterOptions;
import psidev.psi.mi.jami.datasource.MIDataSourceOptions;
import psidev.psi.mi.jami.datasource.MIFileDataSourceOptions;
import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.parser.*;
import psidev.psi.mi.jami.tab.io.writer.*;
import psidev.psi.mi.jami.tab.utils.MitabWriterOptions;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.io.parser.*;
import psidev.psi.mi.jami.xml.io.writer.compact.*;
import psidev.psi.mi.jami.xml.io.writer.expanded.*;
import psidev.psi.mi.jami.xml.utils.PsiXmlWriterOptions;

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

        // MITAB writers
        Map<String, Object> supportedOptions28 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed, MitabVersion.v2_5, false, true);
        writerFactory.registerDataSourceWriter(Mitab25Writer.class, supportedOptions28);
        Map<String, Object> supportedOptions29 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed, MitabVersion.v2_5, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25Writer.class, supportedOptions29);
        Map<String, Object> supportedOptions30 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed_binary, MitabVersion.v2_5, false, false);
        writerFactory.registerDataSourceWriter(Mitab25BinaryWriter.class, supportedOptions30);
        Map<String, Object> supportedOptions31 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed_binary, MitabVersion.v2_5, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25BinaryWriter.class, supportedOptions31);
        Map<String, Object> supportedOptions32 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed, MitabVersion.v2_6, false, true);
        writerFactory.registerDataSourceWriter(Mitab26Writer.class, supportedOptions32);
        Map<String, Object> supportedOptions33 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed, MitabVersion.v2_6, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26Writer.class, supportedOptions33);
        Map<String, Object> supportedOptions34 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed_binary, MitabVersion.v2_6, false, false);
        writerFactory.registerDataSourceWriter(Mitab26BinaryWriter.class, supportedOptions34);
        Map<String, Object> supportedOptions35 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed_binary, MitabVersion.v2_6, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26BinaryWriter.class, supportedOptions35);
        Map<String, Object> supportedOptions36 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed, MitabVersion.v2_7, false, true);
        writerFactory.registerDataSourceWriter(Mitab27Writer.class, supportedOptions36);
        Map<String, Object> supportedOptions37 = createMITABInteractionWriterOptions( InteractionObjectCategory.mixed, MitabVersion.v2_7, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27Writer.class, supportedOptions37);
        Map<String, Object> supportedOptions38 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed_binary, MitabVersion.v2_7, false, false);
        writerFactory.registerDataSourceWriter(Mitab27BinaryWriter.class, supportedOptions38);
        Map<String, Object> supportedOptions39 = createMITABInteractionWriterOptions(InteractionObjectCategory.mixed_binary, MitabVersion.v2_7, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27BinaryWriter.class, supportedOptions39);

        // XML writers
        Map<String, Object> supportedOptions4 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed, PsiXmlType.compact, false, false);
        writerFactory.registerDataSourceWriter(CompactXml25Writer.class, supportedOptions4);
        Map<String, Object> supportedOptions5 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed, PsiXmlType.expanded, false, false);
        writerFactory.registerDataSourceWriter(ExpandedXml25Writer.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed_binary, PsiXmlType.compact, false, false);
        writerFactory.registerDataSourceWriter(CompactXml25BinaryWriter.class, supportedOptions6);
        Map<String, Object> supportedOptions7 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed_binary, PsiXmlType.expanded, false, false);
        writerFactory.registerDataSourceWriter(ExpandedXml25BinaryWriter.class, supportedOptions7);

        Map<String, Object> supportedOptions44 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed, PsiXmlType.compact, false, true);
        writerFactory.registerDataSourceWriter(CompactXml25NamedWriter.class, supportedOptions44);
        Map<String, Object> supportedOptions55 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed, PsiXmlType.expanded, false, true);
        writerFactory.registerDataSourceWriter(ExpandedXml25NamedWriter.class, supportedOptions55);
        Map<String, Object> supportedOptions66 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed_binary, PsiXmlType.compact, false, true);
        writerFactory.registerDataSourceWriter(CompactXml25NamedBinaryWriter.class, supportedOptions66);
        Map<String, Object> supportedOptions77 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed_binary, PsiXmlType.expanded, false, true);
        writerFactory.registerDataSourceWriter(ExpandedXml25NamedBinaryWriter.class, supportedOptions77);

        Map<String, Object> supportedOptions8 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed, PsiXmlType.compact, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXml25Writer.class, supportedOptions8);
        Map<String, Object> supportedOptions9 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed, PsiXmlType.expanded, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXml25Writer.class, supportedOptions9);
        Map<String, Object> supportedOptions10 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed_binary, PsiXmlType.compact, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXml25BinaryWriter.class, supportedOptions10);
        Map<String, Object> supportedOptions11 = createXML25InteractionWriterOptions(InteractionObjectCategory.mixed_binary, PsiXmlType.expanded, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXml25BinaryWriter.class, supportedOptions11);
    }

    public static void initialiseModelledInteractionWriters() {
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        // MITAB options
        Map<String, Object> supportedOptions16 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled, MitabVersion.v2_5, false, true);
        writerFactory.registerDataSourceWriter(Mitab25ModelledWriter.class, supportedOptions16);
        Map<String, Object> supportedOptions17 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled, MitabVersion.v2_5, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledWriter.class, supportedOptions17);
        Map<String, Object> supportedOptions18 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled_binary, MitabVersion.v2_5, false, false);
        writerFactory.registerDataSourceWriter(Mitab25ModelledBinaryWriter.class, supportedOptions18);
        Map<String, Object> supportedOptions19 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled_binary, MitabVersion.v2_5, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledBinaryWriter.class, supportedOptions19);
        Map<String, Object> supportedOptions20 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled, MitabVersion.v2_6, false, true);
        writerFactory.registerDataSourceWriter(Mitab26ModelledWriter.class, supportedOptions20);
        Map<String, Object> supportedOptions21 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled, MitabVersion.v2_6, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26ModelledWriter.class, supportedOptions21);
        Map<String, Object> supportedOptions22 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled_binary, MitabVersion.v2_6, false, false);
        writerFactory.registerDataSourceWriter(Mitab26ModelledBinaryWriter.class, supportedOptions22);
        Map<String, Object> supportedOptions23 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled_binary, MitabVersion.v2_6, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26ModelledBinaryWriter.class, supportedOptions23);
        Map<String, Object> supportedOptions24 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled, MitabVersion.v2_7, false, true);
        writerFactory.registerDataSourceWriter(Mitab27ModelledWriter.class, supportedOptions24);
        Map<String, Object> supportedOptions25 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled, MitabVersion.v2_7, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27ModelledWriter.class, supportedOptions25);
        Map<String, Object> supportedOptions26 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled_binary, MitabVersion.v2_7, false, false);
        writerFactory.registerDataSourceWriter(Mitab27ModelledBinaryWriter.class, supportedOptions26);
        Map<String, Object> supportedOptions27 = createMITABInteractionWriterOptions(InteractionObjectCategory.modelled_binary, MitabVersion.v2_7, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27ModelledBinaryWriter.class, supportedOptions27);

        // XML 2.5 options
        Map<String, Object> supportedOptions4 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled, PsiXmlType.compact, false, false);
        writerFactory.registerDataSourceWriter(CompactXml25ModelledWriter.class, supportedOptions4);
        Map<String, Object> supportedOptions5 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled, PsiXmlType.expanded, false, false);
        writerFactory.registerDataSourceWriter(ExpandedXml25ModelledWriter.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled_binary, PsiXmlType.compact, false, false);
        writerFactory.registerDataSourceWriter(CompactXml25ModelledBinaryWriter.class, supportedOptions6);
        Map<String, Object> supportedOptions7 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled_binary, PsiXmlType.expanded, false, false);
        writerFactory.registerDataSourceWriter(ExpandedXml25ModelledBinaryWriter.class, supportedOptions7);

        Map<String, Object> supportedOptions44 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled, PsiXmlType.compact, false, true);
        writerFactory.registerDataSourceWriter(CompactXml25NamedModelledWriter.class, supportedOptions44);
        Map<String, Object> supportedOptions55 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled, PsiXmlType.expanded, false, true);
        writerFactory.registerDataSourceWriter(ExpandedXml25NamedModelledWriter.class, supportedOptions55);
        Map<String, Object> supportedOptions66 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled_binary, PsiXmlType.compact, false, true);
        writerFactory.registerDataSourceWriter(CompactXml25NamedModelledBinaryWriter.class, supportedOptions66);
        Map<String, Object> supportedOptions77 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled_binary, PsiXmlType.expanded, false, true);
        writerFactory.registerDataSourceWriter(ExpandedXml25NamedModelledBinaryWriter.class, supportedOptions77);

        Map<String, Object> supportedOptions8 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled, PsiXmlType.compact, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXml25ModelledWriter.class, supportedOptions8);
        Map<String, Object> supportedOptions9 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled, PsiXmlType.expanded, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXml25ModelledWriter.class, supportedOptions9);
        Map<String, Object> supportedOptions10 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled_binary, PsiXmlType.compact, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXml25ModelledBinaryWriter.class, supportedOptions10);
        Map<String, Object> supportedOptions11 = createXML25InteractionWriterOptions(InteractionObjectCategory.modelled_binary, PsiXmlType.expanded, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXml25ModelledBinaryWriter.class, supportedOptions11);
    }

    public static void initialiseInteractionEvidenceWriters() {
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();
        // writers for MITAB
        Map<String, Object> supportedOptions4 = createMITABInteractionWriterOptions(InteractionObjectCategory.evidence, MitabVersion.v2_5, false, true);
        writerFactory.registerDataSourceWriter(Mitab25EvidenceWriter.class, supportedOptions4);
        Map<String, Object> supportedOptions5 = createMITABInteractionWriterOptions(InteractionObjectCategory.evidence, MitabVersion.v2_5, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25EvidenceWriter.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createMITABInteractionWriterOptions(InteractionObjectCategory.binary_evidence, MitabVersion.v2_5, false, false);
        writerFactory.registerDataSourceWriter(Mitab25BinaryEvidenceWriter.class, supportedOptions6);
        Map<String, Object> supportedOptions7 = createMITABInteractionWriterOptions(InteractionObjectCategory.binary_evidence, MitabVersion.v2_5, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab25BinaryEvidenceWriter.class, supportedOptions7);
        Map<String, Object> supportedOptions8 = createMITABInteractionWriterOptions(InteractionObjectCategory.evidence, MitabVersion.v2_6, false, true);
        writerFactory.registerDataSourceWriter(Mitab26EvidenceWriter.class, supportedOptions8);
        Map<String, Object> supportedOptions9 = createMITABInteractionWriterOptions(InteractionObjectCategory.evidence, MitabVersion.v2_6, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26EvidenceWriter.class, supportedOptions9);
        Map<String, Object> supportedOptions10 = createMITABInteractionWriterOptions(InteractionObjectCategory.binary_evidence, MitabVersion.v2_6, false, false);
        writerFactory.registerDataSourceWriter(Mitab26BinaryEvidenceWriter.class, supportedOptions10);
        Map<String, Object> supportedOptions11 = createMITABInteractionWriterOptions(InteractionObjectCategory.binary_evidence, MitabVersion.v2_6, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab26BinaryEvidenceWriter.class, supportedOptions11);
        Map<String, Object> supportedOptions12 = createMITABInteractionWriterOptions(InteractionObjectCategory.evidence, MitabVersion.v2_7, false, true);
        writerFactory.registerDataSourceWriter(Mitab27EvidenceWriter.class, supportedOptions12);
        Map<String, Object> supportedOptions13 = createMITABInteractionWriterOptions(InteractionObjectCategory.evidence, MitabVersion.v2_7, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27EvidenceWriter.class, supportedOptions13);
        Map<String, Object> supportedOptions14 = createMITABInteractionWriterOptions(InteractionObjectCategory.binary_evidence, MitabVersion.v2_7, false, false);
        writerFactory.registerDataSourceWriter(Mitab27BinaryEvidenceWriter.class, supportedOptions14);
        Map<String, Object> supportedOptions15 = createMITABInteractionWriterOptions(InteractionObjectCategory.binary_evidence, MitabVersion.v2_7, true, false);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.tab.io.writer.extended.Mitab27BinaryEvidenceWriter.class, supportedOptions15);

        // xml 25 writers
        Map<String, Object> supportedOptions1 = createXML25InteractionWriterOptions(InteractionObjectCategory.evidence, PsiXmlType.compact, false, false);
        writerFactory.registerDataSourceWriter(CompactXml25EvidenceWriter.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createXML25InteractionWriterOptions(InteractionObjectCategory.evidence, PsiXmlType.expanded, false, false);
        writerFactory.registerDataSourceWriter(ExpandedXml25EvidenceWriter.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createXML25InteractionWriterOptions(InteractionObjectCategory.binary_evidence, PsiXmlType.compact, false, false);
        writerFactory.registerDataSourceWriter(CompactXml25BinaryEvidenceWriter.class, supportedOptions3);
        Map<String, Object> supportedOptions16 = createXML25InteractionWriterOptions(InteractionObjectCategory.binary_evidence, PsiXmlType.expanded, false, false);
        writerFactory.registerDataSourceWriter(ExpandedXml25BinaryEvidenceWriter.class, supportedOptions16);

        Map<String, Object> supportedOptions44 = createXML25InteractionWriterOptions(InteractionObjectCategory.evidence, PsiXmlType.compact, false, true);
        writerFactory.registerDataSourceWriter(CompactXml25NamedEvidenceWriter.class, supportedOptions44);
        Map<String, Object> supportedOptions55 = createXML25InteractionWriterOptions(InteractionObjectCategory.evidence, PsiXmlType.expanded, false, true);
        writerFactory.registerDataSourceWriter(ExpandedXml25NamedEvidenceWriter.class, supportedOptions55);
        Map<String, Object> supportedOptions66 = createXML25InteractionWriterOptions(InteractionObjectCategory.binary_evidence, PsiXmlType.compact, false, true);
        writerFactory.registerDataSourceWriter(CompactXml25NamedBinaryEvidenceWriter.class, supportedOptions66);
        Map<String, Object> supportedOptions77 = createXML25InteractionWriterOptions(InteractionObjectCategory.binary_evidence, PsiXmlType.expanded, false, true);
        writerFactory.registerDataSourceWriter(ExpandedXml25NamedBinaryEvidenceWriter.class, supportedOptions77);

        Map<String, Object> supportedOptions17 = createXML25InteractionWriterOptions(InteractionObjectCategory.evidence, PsiXmlType.compact, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXml25EvidenceWriter.class, supportedOptions17);
        Map<String, Object> supportedOptions18 = createXML25InteractionWriterOptions(InteractionObjectCategory.evidence, PsiXmlType.expanded, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXml25EvidenceWriter.class, supportedOptions18);
        Map<String, Object> supportedOptions19 = createXML25InteractionWriterOptions(InteractionObjectCategory.binary_evidence, PsiXmlType.compact, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXml25BinaryEvidenceWriter.class, supportedOptions19);
        Map<String, Object> supportedOptions20 = createXML25InteractionWriterOptions(InteractionObjectCategory.binary_evidence, PsiXmlType.expanded, true, true);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXml25BinaryEvidenceWriter.class, supportedOptions20);

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
        datasourceFactory.registerDataSource(LightMitabStreamSource.class, supportedOptions3);

        Map<String, Object> supportedOptions33 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.basic_binary);
        datasourceFactory.registerDataSource(LightMitabBinaryStreamSource.class, supportedOptions33);

        Map<String, Object> supportedOptions333 = createDataSourceOptions(MIFileType.mitab.toString(), false, InteractionObjectCategory.basic);
        datasourceFactory.registerDataSource(LightMitabSource.class, supportedOptions333);

        Map<String, Object> supportedOptions3333 = createDataSourceOptions(MIFileType.mitab.toString(), false, InteractionObjectCategory.basic_binary);
        datasourceFactory.registerDataSource(LightMitabBinarySource.class, supportedOptions3333);

        Map<String, Object> supportedOptions4 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), true, InteractionObjectCategory.basic);
        datasourceFactory.registerDataSource(LightXml25StreamSource.class, supportedOptions4);

        Map<String, Object> supportedOptions44 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), true, InteractionObjectCategory.basic_binary);
        datasourceFactory.registerDataSource(LightXml25BinaryStreamSource.class, supportedOptions44);

        Map<String, Object> supportedOptions444 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), false, InteractionObjectCategory.basic);
        datasourceFactory.registerDataSource(LightXml25Source.class, supportedOptions444);

        Map<String, Object> supportedOptions4444 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), false, InteractionObjectCategory.basic_binary);
        datasourceFactory.registerDataSource(LightXml25BinarySource.class, supportedOptions444);
    }

    public static void initialiseModelledInteractionSources() {
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions2 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.modelled);
        datasourceFactory.registerDataSource(MitabModelledStreamSource.class, supportedOptions2);

        Map<String, Object> supportedOptions22 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.modelled_binary);
        datasourceFactory.registerDataSource(MitabModelledBinaryStreamSource.class, supportedOptions22);

        Map<String, Object> supportedOptions222 = createDataSourceOptions(MIFileType.mitab.toString(), false, InteractionObjectCategory.modelled);
        datasourceFactory.registerDataSource(MitabModelledSource.class, supportedOptions222);

        Map<String, Object> supportedOptions2222 = createDataSourceOptions(MIFileType.mitab.toString(), false, InteractionObjectCategory.modelled_binary);
        datasourceFactory.registerDataSource(MitabModelledBinarySource.class, supportedOptions2222);

        Map<String, Object> supportedOptions3 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), true, InteractionObjectCategory.modelled);
        datasourceFactory.registerDataSource(Xml25ModelledStreamSource.class, supportedOptions3);

        Map<String, Object> supportedOptions33 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), true, InteractionObjectCategory.modelled_binary);
        datasourceFactory.registerDataSource(Xml25ModelledBinaryStreamSource.class, supportedOptions33);

        Map<String, Object> supportedOptions333 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), false, InteractionObjectCategory.modelled);
        datasourceFactory.registerDataSource(Xml25ModelledSource.class, supportedOptions333);

        Map<String, Object> supportedOptions3333 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), false, InteractionObjectCategory.modelled_binary);
        datasourceFactory.registerDataSource(Xml25ModelledBinarySource.class, supportedOptions3333);
    }

    public static void initialiseInteractionEvidenceSources() {
        MIDataSourceFactory datasourceFactory = MIDataSourceFactory.getInstance();

        Map<String, Object> supportedOptions1 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.evidence);
        datasourceFactory.registerDataSource(MitabEvidenceStreamSource.class, supportedOptions1);

        Map<String, Object> supportedOptions11 = createDataSourceOptions(MIFileType.mitab.toString(), true, InteractionObjectCategory.binary_evidence);
        datasourceFactory.registerDataSource(MitabBinaryEvidenceStreamSource.class, supportedOptions11);

        Map<String, Object> supportedOptions111 = createDataSourceOptions(MIFileType.mitab.toString(), false, InteractionObjectCategory.evidence);
        datasourceFactory.registerDataSource(MitabEvidenceSource.class, supportedOptions111);

        Map<String, Object> supportedOptions1111 = createDataSourceOptions(MIFileType.mitab.toString(), false, InteractionObjectCategory.binary_evidence);
        datasourceFactory.registerDataSource(MitabBinaryEvidenceSource.class, supportedOptions1111);

        Map<String, Object> supportedOptions2 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), true, InteractionObjectCategory.evidence);
        datasourceFactory.registerDataSource(Xml25EvidenceStreamSource.class, supportedOptions2);

        Map<String, Object> supportedOptions22 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), true, InteractionObjectCategory.binary_evidence);
        datasourceFactory.registerDataSource(Xml25BinaryEvidenceStreamSource.class, supportedOptions22);

        Map<String, Object> supportedOptions222 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), false, InteractionObjectCategory.evidence);
        datasourceFactory.registerDataSource(Xml25EvidenceSource.class, supportedOptions222);

        Map<String, Object> supportedOptions2222 = createXmlDataSourceOptions(MIFileType.psi25_xml.toString(), false, InteractionObjectCategory.binary_evidence);
        datasourceFactory.registerDataSource(Xml25BinaryEvidenceSource.class, supportedOptions2222);
    }

    private static Map<String, Object> createMITABInteractionWriterOptions(InteractionObjectCategory interactionCategory, MitabVersion version, boolean extended, boolean needCompleExpansion) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        supportedOptions4.put(InteractionWriterOptions.INTERACTION_OBJECT_OPTION_KEY, interactionCategory);
        if (needCompleExpansion){
            supportedOptions4.put(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        }
        supportedOptions4.put(MitabWriterOptions.MITAB_HEADER_OPTION, null);
        supportedOptions4.put(MitabWriterOptions.MITAB_VERSION_OPTION, version);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        supportedOptions4.put(MitabWriterOptions.MITAB_EXTENDED_OPTION, extended);
        return supportedOptions4;
    }

    private static Map<String, Object> createXML25InteractionWriterOptions(InteractionObjectCategory interactionCategory, PsiXmlType type,
                                                                           boolean extended, boolean writeNames) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(14);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psi25_xml.toString());
        supportedOptions4.put(InteractionWriterOptions.INTERACTION_OBJECT_OPTION_KEY, interactionCategory);
        supportedOptions4.put(PsiXmlWriterOptions.XML_TYPE_OPTION, type);
        supportedOptions4.put(PsiXmlWriterOptions.XML25_EXTENDED_OPTION, extended);
        supportedOptions4.put(PsiXmlWriterOptions.XML25_NAMES_OPTION, writeNames);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        switch (type){
            case compact:
                supportedOptions4.put(PsiXmlWriterOptions.COMPACT_XML_EXPERIMENT_SET_OPTION, null);
                supportedOptions4.put(PsiXmlWriterOptions.COMPACT_XML_INTERACTOR_SET_OPTION, null);
                supportedOptions4.put(PsiXmlWriterOptions.COMPACT_XML_AVAILABILITY_SET_OPTION, null);
                break;
            default:
                break;
        }
        supportedOptions4.put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.XML_INTERACTION_SET_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.DEFAULT_RELEASE_DATE_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.DEFAULT_SOURCE_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.XML_ENTRY_ATTRIBUTES_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.WRITE_COMPLEX_AS_INTERACTOR_OPTION, null);
        supportedOptions4.put(PsiXmlWriterOptions.XML_VERSION_OPTION, null);
        return supportedOptions4;
    }

    private static Map<String, Object> createDataSourceOptions(String inputFormat, boolean streaming, InteractionObjectCategory objectCategory) {
        Map<String, Object> supportedOptions1 = new HashMap<String, Object>(7);
        supportedOptions1.put(MIFileDataSourceOptions.INPUT_FORMAT_OPTION_KEY, inputFormat);
        supportedOptions1.put(MIFileDataSourceOptions.STREAMING_OPTION_KEY, streaming);
        supportedOptions1.put(MIFileDataSourceOptions.INTERACTION_OBJECT_OPTION_KEY, objectCategory);
        supportedOptions1.put(MIFileDataSourceOptions.PARSER_LISTENER_OPTION_KEY, null);
        supportedOptions1.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, null);
        return supportedOptions1;
    }

    private static Map<String, Object> createXmlDataSourceOptions(String inputFormat, boolean streaming, InteractionObjectCategory objectCategory) {
        Map<String, Object> supportedOptions1 = createDataSourceOptions(inputFormat, streaming, objectCategory);
        supportedOptions1.put(MIDataSourceOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions1.put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, null);
        return supportedOptions1;
    }
}
