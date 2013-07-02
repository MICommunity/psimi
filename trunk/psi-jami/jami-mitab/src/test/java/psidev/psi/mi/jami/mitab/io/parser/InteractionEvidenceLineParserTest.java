package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.tab.extension.MitabFeature;
import psidev.psi.mi.jami.tab.io.parser.InteractionEvidenceLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.*;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Unit tester for InteractionEvidenceLineParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class InteractionEvidenceLineParserTest {

    @Test
    public void test_read_valid_mitab25() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab25_line.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());

        Iterator<ParticipantEvidence> iterator = binary.getParticipants().iterator();
        ParticipantEvidence A = iterator.next();
        Assert.assertEquals("LY96", A.getInteractor().getShortName());
        Assert.assertEquals(2, A.getInteractor().getIdentifiers().size());
        Iterator<Xref> xrefIterator = A.getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-82738", CvTermUtils.createIdentityQualifier()), xrefIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000136869", CvTermUtils.createSecondaryXrefQualifier()), xrefIterator.next());
        Assert.assertEquals(5, A.getInteractor().getAliases().size());
        Iterator<Alias> aliasIterator = A.getInteractor().getAliases().iterator();
        Assert.assertEquals(new DefaultAlias(new DefaultCvTerm("gene name"), "bla"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("O00206"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("TLR4_HUMAN"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_138554"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_612564"), aliasIterator.next());
        Assert.assertEquals(9606, A.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", A.getInteractor().getOrganism().getCommonName());
        Assert.assertEquals("Homo Sapiens", A.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), A.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), A.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), A.getInteractor().getInteractorType());
        Assert.assertEquals(0, A.getInteractor().getXrefs().size());
        Assert.assertEquals(0, A.getAnnotations().size());
        Assert.assertEquals(1, A.getInteractor().getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxx4"), A.getInteractor().getChecksums().iterator().next());
        Assert.assertEquals(0, A.getFeatures().size());
        Assert.assertNull(A.getStoichiometry());
        Assert.assertEquals(0, A.getIdentificationMethods().size());

        ParticipantEvidence B = iterator.next();
        Assert.assertEquals("LY96", B.getInteractor().getShortName());
        xrefIterator = B.getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(2, B.getInteractor().getIdentifiers().size());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-25842", CvTermUtils.createIdentityQualifier()), xrefIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000154589", CvTermUtils.createSecondaryXrefQualifier()), xrefIterator.next());
        Assert.assertEquals(6, B.getInteractor().getAliases().size());
        aliasIterator = B.getInteractor().getAliases().iterator();
        Assert.assertEquals(new DefaultAlias("Q9Y6Y9"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("LY96_HUMAN"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_015364"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_056179"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_001195797"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_001182726"), aliasIterator.next());
        Assert.assertEquals(9606, B.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", B.getInteractor().getOrganism().getCommonName());
        Assert.assertNull(B.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), B.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), B.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), B.getInteractor().getInteractorType());
        Assert.assertEquals(0, B.getInteractor().getXrefs().size());
        Assert.assertEquals(0, B.getAnnotations().size());
        Assert.assertEquals(0, B.getInteractor().getChecksums().size());
        Assert.assertTrue(B.getFeatures().isEmpty());
        Assert.assertNull(B.getStoichiometry());
        Assert.assertEquals(0, B.getIdentificationMethods().size());

        Experiment experiment = binary.getExperiment();
        Assert.assertNotNull(experiment);
        Assert.assertEquals(CvTermUtils.createMICvTerm("anti tag coimmunoprecipitation", "MI:0007"), experiment.getInteractionDetectionMethod());
        Publication publication = experiment.getPublication();
        Assert.assertNotNull(publication);
        Assert.assertEquals(1, publication.getAuthors().size());
        Assert.assertEquals("Shimazu", publication.getAuthors().iterator().next());
        Assert.assertEquals(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("1999"), publication.getPublicationDate());
        Assert.assertEquals("10359581", publication.getPubmedId());
        Assert.assertEquals(new DefaultSource("innatedb", "MI:0974"), publication.getSource());
        Assert.assertNull(experiment.getHostOrganism());

        Assert.assertEquals(CvTermUtils.createMICvTerm("physical association","MI:0915"), binary.getInteractionType());
        Assert.assertEquals(1, binary.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref("innatedb", "IDB-113240"), binary.getIdentifiers().iterator().next());
        Assert.assertEquals("IM-1-1", binary.getImexId());
        Assert.assertEquals(3, binary.getConfidences().size());
        Iterator<Confidence> confIterator = binary.getConfidences().iterator();
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("lpr"), "2"), confIterator.next());
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("hpr"), "2"), confIterator.next());
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("np"), "1"), confIterator.next());
        Assert.assertEquals(1, binary.getXrefs().size());
        Assert.assertEquals(0, binary.getAnnotations().size());
        Assert.assertEquals(0, binary.getParameters().size());
        Assert.assertFalse(binary.isNegative());
        Assert.assertEquals(0, binary.getChecksums().size());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_valid_mitab26() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab26_line.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());

        Iterator<ParticipantEvidence> iterator = binary.getParticipants().iterator();
        ParticipantEvidence A = iterator.next();
        Assert.assertEquals("LY96", A.getInteractor().getShortName());
        Assert.assertEquals(2, A.getInteractor().getIdentifiers().size());
        Iterator<Xref> xrefIterator = A.getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-82738", CvTermUtils.createIdentityQualifier()), xrefIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000136869", CvTermUtils.createSecondaryXrefQualifier()), xrefIterator.next());
        Assert.assertEquals(5, A.getInteractor().getAliases().size());
        Iterator<Alias> aliasIterator = A.getInteractor().getAliases().iterator();
        Assert.assertEquals(new DefaultAlias(new DefaultCvTerm("gene name"), "bla"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("O00206"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("TLR4_HUMAN"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_138554"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_612564"), aliasIterator.next());
        Assert.assertEquals(9606, A.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", A.getInteractor().getOrganism().getCommonName());
        Assert.assertEquals("Homo Sapiens", A.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), A.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("bait", "MI:0496"), A.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), A.getInteractor().getInteractorType());
        Assert.assertEquals(1, A.getInteractor().getXrefs().size());
        Assert.assertEquals(XrefUtils.createXref("go", "GO:xxxx"), A.getInteractor().getXrefs().iterator().next());
        Assert.assertEquals(1, A.getAnnotations().size());
        Assert.assertEquals(AnnotationUtils.createAnnotation("caution","test caution"), A.getAnnotations().iterator().next());
        Assert.assertEquals(1, A.getInteractor().getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxx4"), A.getInteractor().getChecksums().iterator().next());
        Assert.assertEquals(0, A.getFeatures().size());
        Assert.assertNull(A.getStoichiometry());
        Assert.assertEquals(0, A.getIdentificationMethods().size());

        ParticipantEvidence B = iterator.next();
        Assert.assertEquals("LY96", B.getInteractor().getShortName());
        xrefIterator = B.getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(2, B.getInteractor().getIdentifiers().size());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-25842", CvTermUtils.createIdentityQualifier()), xrefIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000154589", CvTermUtils.createSecondaryXrefQualifier()), xrefIterator.next());
        Assert.assertEquals(6, B.getInteractor().getAliases().size());
        aliasIterator = B.getInteractor().getAliases().iterator();
        Assert.assertEquals(new DefaultAlias("Q9Y6Y9"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("LY96_HUMAN"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_015364"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_056179"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_001195797"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_001182726"), aliasIterator.next());
        Assert.assertEquals(9606, B.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", B.getInteractor().getOrganism().getCommonName());
        Assert.assertNull(B.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), B.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("prey", "MI:0498"), B.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), B.getInteractor().getInteractorType());
        Assert.assertEquals(1, B.getInteractor().getXrefs().size());
        Assert.assertEquals(XrefUtils.createXref("interpro", "interpro:xxx"), B.getInteractor().getXrefs().iterator().next());
        Assert.assertEquals(0, B.getAnnotations().size());
        Assert.assertEquals(1, B.getInteractor().getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxxx2"), B.getInteractor().getChecksums().iterator().next());
        Assert.assertTrue(B.getFeatures().isEmpty());
        Assert.assertNull(B.getStoichiometry());
        Assert.assertEquals(0, B.getIdentificationMethods().size());

        Experiment experiment = binary.getExperiment();
        Assert.assertNotNull(experiment);
        Assert.assertEquals(CvTermUtils.createMICvTerm("anti tag coimmunoprecipitation", "MI:0007"), experiment.getInteractionDetectionMethod());
        Publication publication = experiment.getPublication();
        Assert.assertNotNull(publication);
        Assert.assertEquals(1, publication.getAuthors().size());
        Assert.assertEquals("Shimazu", publication.getAuthors().iterator().next());
        Assert.assertEquals(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("1999"), publication.getPublicationDate());
        Assert.assertEquals("10359581", publication.getPubmedId());
        Assert.assertEquals(new DefaultSource("innatedb", "MI:0974"), publication.getSource());
        Assert.assertEquals(10090, experiment.getHostOrganism().getTaxId());
        Assert.assertEquals("mouse", experiment.getHostOrganism().getCommonName());

        Assert.assertEquals(CvTermUtils.createMICvTerm("physical association","MI:0915"), binary.getInteractionType());
        Assert.assertEquals(1, binary.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref("innatedb", "IDB-113240"), binary.getIdentifiers().iterator().next());
        Assert.assertEquals("IM-1-1", binary.getImexId());
        Assert.assertEquals(3, binary.getConfidences().size());
        Iterator<Confidence> confIterator = binary.getConfidences().iterator();
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("lpr"), "2"), confIterator.next());
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("hpr"), "2"), confIterator.next());
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("np"), "1"), confIterator.next());
        Assert.assertEquals(2, binary.getXrefs().size());
        xrefIterator = binary.getXrefs().iterator();
        xrefIterator.next();
        Assert.assertEquals(XrefUtils.createXrefWithQualifier("go", "GO:xxx1", "process"), xrefIterator.next());
        Assert.assertEquals(1, binary.getAnnotations().size());
        Assert.assertEquals(AnnotationUtils.createAnnotation("figure-legend", "Fig1."), binary.getAnnotations().iterator().next());
        Assert.assertEquals(1, binary.getParameters().size());
        Assert.assertEquals("2x10^(-1)", binary.getParameters().iterator().next().getValue().toString());
        Assert.assertEquals(MitabUtils.DATE_FORMAT.parse("2008/03/30"), binary.getCreatedDate());
        Assert.assertEquals(MitabUtils.DATE_FORMAT.parse("2008/03/30"), binary.getUpdatedDate());
        Assert.assertFalse(binary.isNegative());
        Assert.assertEquals(1, binary.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRigid("xxxx3"), binary.getChecksums().iterator().next());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_valid_mitab27() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_line.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());

        Iterator<ParticipantEvidence> iterator = binary.getParticipants().iterator();
        ParticipantEvidence A = iterator.next();
        Assert.assertEquals("LY96", A.getInteractor().getShortName());
        Assert.assertEquals(2, A.getInteractor().getIdentifiers().size());
        Iterator<Xref> xrefIterator = A.getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-82738", CvTermUtils.createIdentityQualifier()), xrefIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000136869", CvTermUtils.createSecondaryXrefQualifier()), xrefIterator.next());
        Assert.assertEquals(5, A.getInteractor().getAliases().size());
        Iterator<Alias> aliasIterator = A.getInteractor().getAliases().iterator();
        Assert.assertEquals(new DefaultAlias(new DefaultCvTerm("gene name"), "bla"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("O00206"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("TLR4_HUMAN"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_138554"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_612564"), aliasIterator.next());
        Assert.assertEquals(9606, A.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", A.getInteractor().getOrganism().getCommonName());
        Assert.assertEquals("Homo Sapiens", A.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), A.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("bait", "MI:0496"), A.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), A.getInteractor().getInteractorType());
        Assert.assertEquals(1, A.getInteractor().getXrefs().size());
        Assert.assertEquals(XrefUtils.createXref("go", "GO:xxxx"), A.getInteractor().getXrefs().iterator().next());
        Assert.assertEquals(1, A.getAnnotations().size());
        Assert.assertEquals(AnnotationUtils.createAnnotation("caution","test caution"), A.getAnnotations().iterator().next());
        Assert.assertEquals(1, A.getInteractor().getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxx4"), A.getInteractor().getChecksums().iterator().next());
        Assert.assertEquals(1, A.getFeatures().size());
        MitabFeature f = (MitabFeature)A.getFeatures().iterator().next();
        Assert.assertEquals(new DefaultCvTerm("binding site"), f.getType());
        Assert.assertEquals(2, f.getRanges().size());
        Iterator<Range> rangeIterator = f.getRanges().iterator();
        Range r1 = rangeIterator.next();
        Assert.assertEquals(PositionUtils.createCertainPosition(3), r1.getStart());
        Assert.assertEquals(PositionUtils.createCertainPosition(3), r1.getEnd());
        Range r2 = rangeIterator.next();
        Assert.assertEquals(PositionUtils.createFuzzyPosition(4, 5), r2.getStart());
        Assert.assertEquals(PositionUtils.createFuzzyPosition(6, 7), r2.getEnd());
        Assert.assertEquals("interpro:xxxx", f.getText());
        Assert.assertEquals(new DefaultStoichiometry(2), A.getStoichiometry());
        Assert.assertEquals(1, A.getIdentificationMethods().size());
        Assert.assertEquals(CvTermUtils.createMICvTerm("inferred by author", "MI:0363"), A.getIdentificationMethods().iterator().next());

        ParticipantEvidence B = iterator.next();
        Assert.assertEquals("LY96", B.getInteractor().getShortName());
        xrefIterator = B.getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(2, B.getInteractor().getIdentifiers().size());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-25842", CvTermUtils.createIdentityQualifier()), xrefIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000154589", CvTermUtils.createSecondaryXrefQualifier()), xrefIterator.next());
        Assert.assertEquals(6, B.getInteractor().getAliases().size());
        aliasIterator = B.getInteractor().getAliases().iterator();
        Assert.assertEquals(new DefaultAlias("Q9Y6Y9"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("LY96_HUMAN"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_015364"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_056179"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NM_001195797"), aliasIterator.next());
        Assert.assertEquals(new DefaultAlias("NP_001182726"), aliasIterator.next());
        Assert.assertEquals(9606, B.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", B.getInteractor().getOrganism().getCommonName());
        Assert.assertNull(B.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), B.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("prey","MI:0498"), B.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), B.getInteractor().getInteractorType());
        Assert.assertEquals(1, B.getInteractor().getXrefs().size());
        Assert.assertEquals(XrefUtils.createXref("interpro", "interpro:xxx"), B.getInteractor().getXrefs().iterator().next());
        Assert.assertEquals(0, B.getAnnotations().size());
        Assert.assertEquals(1, B.getInteractor().getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRogid("xxxx2"), B.getInteractor().getChecksums().iterator().next());
        Assert.assertTrue(B.getFeatures().isEmpty());
        Assert.assertEquals(new DefaultStoichiometry(5), B.getStoichiometry());
        Assert.assertEquals(1, B.getIdentificationMethods().size());
        Assert.assertEquals(CvTermUtils.createMICvTerm("inferred by author", "MI:0363"), B.getIdentificationMethods().iterator().next());

        Experiment experiment = binary.getExperiment();
        Assert.assertNotNull(experiment);
        Assert.assertEquals(CvTermUtils.createMICvTerm("anti tag coimmunoprecipitation", "MI:0007"), experiment.getInteractionDetectionMethod());
        Publication publication = experiment.getPublication();
        Assert.assertNotNull(publication);
        Assert.assertEquals(1, publication.getAuthors().size());
        Assert.assertEquals("Shimazu", publication.getAuthors().iterator().next());
        Assert.assertEquals(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("1999"), publication.getPublicationDate());
        Assert.assertEquals("10359581", publication.getPubmedId());
        Assert.assertEquals(new DefaultSource("innatedb", "MI:0974"), publication.getSource());
        Assert.assertEquals(10090, experiment.getHostOrganism().getTaxId());
        Assert.assertEquals("mouse", experiment.getHostOrganism().getCommonName());

        Assert.assertEquals(CvTermUtils.createMICvTerm("physical association","MI:0915"), binary.getInteractionType());
        Assert.assertEquals(1, binary.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref("innatedb", "IDB-113240"), binary.getIdentifiers().iterator().next());
        Assert.assertEquals("IM-1-1", binary.getImexId());
        Assert.assertEquals(3, binary.getConfidences().size());
        Iterator<Confidence> confIterator = binary.getConfidences().iterator();
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("lpr"), "2"), confIterator.next());
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("hpr"), "2"), confIterator.next());
        Assert.assertEquals(new DefaultConfidence(new DefaultCvTerm("np"), "1"), confIterator.next());
        Assert.assertEquals(2, binary.getXrefs().size());
        xrefIterator = binary.getXrefs().iterator();
        xrefIterator.next();
        Assert.assertEquals(XrefUtils.createXrefWithQualifier("go", "GO:xxx1", "process"), xrefIterator.next());
        Assert.assertEquals(1, binary.getAnnotations().size());
        Assert.assertEquals(AnnotationUtils.createAnnotation("figure-legend", "Fig1."), binary.getAnnotations().iterator().next());
        Assert.assertEquals(1, binary.getParameters().size());
        Assert.assertEquals("2x10^(-1)", binary.getParameters().iterator().next().getValue().toString());
        Assert.assertEquals(MitabUtils.DATE_FORMAT.parse("2008/03/30"), binary.getCreatedDate());
        Assert.assertEquals(MitabUtils.DATE_FORMAT.parse("2008/03/30"), binary.getUpdatedDate());
        Assert.assertFalse(binary.isNegative());
        Assert.assertEquals(1, binary.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRigid("xxxx3"), binary.getChecksums().iterator().next());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_empty_file() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/empty_file.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNull(binary);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_header_and_empty_lines() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_line_header.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read title
        InteractionEvidence title = parser.MitabLine();
        Assert.assertNull(title);
        Assert.assertFalse(parser.hasFinished());

        // read title
        InteractionEvidence empty_line1 = parser.MitabLine();
        Assert.assertNull(empty_line1);
        Assert.assertFalse(parser.hasFinished());

        // read title
        InteractionEvidence line1 = parser.MitabLine();
        Assert.assertNotNull(line1);
        Assert.assertFalse(parser.hasFinished());

        // read title
        InteractionEvidence empty_line2 = parser.MitabLine();
        Assert.assertNull(empty_line2);
        Assert.assertFalse(parser.hasFinished());

        // read title
        InteractionEvidence line2 = parser.MitabLine();
        Assert.assertNotNull(line2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_syntax_error_unique_identifier() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_unique_identifier_error.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read title
        InteractionEvidence line1 = parser.MitabLine();
        Assert.assertNotNull(line1);
        Assert.assertEquals(2, line1.getParticipants().iterator().next().getInteractor().getIdentifiers().size());
        Iterator<Xref> identifierIterator = line1.getParticipants().iterator().next().getInteractor().getIdentifiers().iterator();
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("innatedb"), "IDBG-827", CvTermUtils.createIdentityQualifier()), identifierIterator.next());
        Assert.assertEquals(new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000136869", CvTermUtils.createSecondaryXrefQualifier()), identifierIterator.next());
        Assert.assertFalse(parser.hasFinished());

        // read title
        InteractionEvidence line2 = parser.MitabLine();
        Assert.assertNotNull(line2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_clustered_mitab27() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_clustered_line.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());

        Iterator<ParticipantEvidence> iterator = binary.getParticipants().iterator();
        ParticipantEvidence A = iterator.next();
        Assert.assertEquals(9606, A.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", A.getInteractor().getOrganism().getCommonName());
        Assert.assertEquals("Homo Sapiens", A.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), A.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("bait", "MI:0496"), A.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), A.getInteractor().getInteractorType());
        Assert.assertEquals(new DefaultStoichiometry(2), A.getStoichiometry());
        Assert.assertEquals(2, A.getIdentificationMethods().size());
        Assert.assertEquals(CvTermUtils.createMICvTerm("inferred by author", "MI:0363"), A.getIdentificationMethods().iterator().next());

        ParticipantEvidence B = iterator.next();
        Assert.assertEquals(9606, B.getInteractor().getOrganism().getTaxId());
        Assert.assertEquals("Human", B.getInteractor().getOrganism().getCommonName());
        Assert.assertNull(B.getInteractor().getOrganism().getScientificName());
        Assert.assertEquals(CvTermUtils.createUnspecifiedRole(), B.getBiologicalRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("prey","MI:0498"), B.getExperimentalRole());
        Assert.assertEquals(CvTermUtils.createProteinInteractorType(), B.getInteractor().getInteractorType());
        Assert.assertEquals(new DefaultStoichiometry(5), B.getStoichiometry());
        Assert.assertEquals(2, B.getIdentificationMethods().size());
        Assert.assertEquals(CvTermUtils.createMICvTerm("inferred by author", "MI:0363"), B.getIdentificationMethods().iterator().next());

        Experiment experiment = binary.getExperiment();
        Assert.assertNotNull(experiment);
        Assert.assertEquals(CvTermUtils.createMICvTerm("anti tag coimmunoprecipitation", "MI:0007"), experiment.getInteractionDetectionMethod());
        Publication publication = experiment.getPublication();
        Assert.assertNotNull(publication);
        Assert.assertEquals(1, publication.getAuthors().size());
        Assert.assertEquals("Shimazu", publication.getAuthors().iterator().next());
        Assert.assertEquals(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("1999"), publication.getPublicationDate());
        Assert.assertEquals(new DefaultSource("innatedb", "MI:0974"), publication.getSource());
        Assert.assertEquals(10090, experiment.getHostOrganism().getTaxId());
        Assert.assertEquals("mouse", experiment.getHostOrganism().getCommonName());

        Assert.assertEquals(CvTermUtils.createMICvTerm("physical association","MI:0915"), binary.getInteractionType());
        Assert.assertEquals(MitabUtils.DATE_FORMAT.parse("2008/03/30"), binary.getCreatedDate());
        Assert.assertEquals(MitabUtils.DATE_FORMAT.parse("2008/03/30"), binary.getUpdatedDate());
        Assert.assertFalse(binary.isNegative());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_no_interactor_details() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_no_interactor_details.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());

        Iterator<ParticipantEvidence> iterator = binary.getParticipants().iterator();
        ParticipantEvidence A = iterator.next();
        Assert.assertEquals(MitabUtils.UNKNOWN_NAME, A.getInteractor().getShortName());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), A.getInteractor().getInteractorType());
        Assert.assertNull(A.getInteractor().getOrganism());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_no_participants() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_no_participants.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());

        Assert.assertTrue(binary.getParticipants().isEmpty());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_too_many_columns() throws ParseException, java.text.ParseException {
        InputStream stream = InteractionEvidenceLineParserTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt");
        InteractionEvidenceLineParser parser = new InteractionEvidenceLineParser(stream);

        // read first interaction
        InteractionEvidence binary = parser.MitabLine();
        Assert.assertNotNull(binary);
        Assert.assertFalse(parser.hasFinished());
        Assert.assertEquals(2, binary.getParticipants().size());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }
}
