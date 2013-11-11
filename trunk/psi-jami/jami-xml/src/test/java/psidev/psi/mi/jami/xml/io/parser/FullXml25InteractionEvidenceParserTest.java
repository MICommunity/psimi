package psidev.psi.mi.jami.xml.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25InteractionEvidence;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25ParticipantEvidence;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * Unit tester for Xml25EvidenceParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class FullXml25InteractionEvidenceParserTest {

    @Test
    public void test_read_valid_xml25_compact() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = FullXml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915.xml");

        PsiXml25Parser<InteractionEvidence> parser = new FullXml25EvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertNull(interaction.getImexId());
        Assert.assertEquals(2, interaction.getIdentifiers().size());
        Assert.assertEquals("copyright", interaction.getAvailability());
        Assert.assertEquals(100000000, ((ExtendedPsi25InteractionEvidence) interaction).getXmlAvailability().getId());

        // identifiers
        Iterator<Xref> identifierIterator = interaction.getIdentifiers().iterator();
        Xref mint = identifierIterator.next();
        Assert.assertEquals("mint", mint.getDatabase().getShortName());
        Assert.assertEquals("MI:0471", mint.getDatabase().getMIIdentifier());
        Assert.assertEquals("MINT-18818", mint.getId());
        Assert.assertEquals("identity", mint.getQualifier().getShortName());
        Assert.assertEquals("MI:0356", mint.getQualifier().getMIIdentifier());
        Xref intact = identifierIterator.next();
        Assert.assertEquals("intact", intact.getDatabase().getShortName());
        Assert.assertEquals("MI:0469", intact.getDatabase().getMIIdentifier());
        Assert.assertEquals("EBI-6948673", intact.getId());
        Assert.assertEquals("identity", intact.getQualifier().getShortName());
        Assert.assertEquals("MI:0356", intact.getQualifier().getMIIdentifier());
        Assert.assertEquals(0, interaction.getXrefs().size());

        // experiment
        Assert.assertNotNull(interaction.getExperiment());
        ExtendedPsi25Experiment exp = (ExtendedPsi25Experiment)interaction.getExperiment();
        Assert.assertEquals("dohrmann-1999-1", exp.getShortLabel());
        Assert.assertEquals("RAD53 regulates DBF4 independently of checkpoint function in Saccharomyces cerevisiae.", exp.getFullName());
        Assert.assertEquals(3, exp.getXrefs().size());
        // all annotations are publication annotations
        Assert.assertEquals(0, exp.getAnnotations().size());
        // detection method
        Assert.assertNotNull(exp.getInteractionDetectionMethod());
        CvTerm method = exp.getInteractionDetectionMethod();
        Assert.assertEquals("2 hybrid", method.getShortName());
        Assert.assertEquals("two hybrid", method.getFullName());
        Assert.assertEquals(8, method.getSynonyms().size());
        Assert.assertEquals("MI:0018", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(3, method.getXrefs().size());
        // participant identification method
        Assert.assertNotNull(exp.getParticipantIdentificationMethod());
        method = exp.getParticipantIdentificationMethod();
        Assert.assertEquals("predetermined", method.getShortName());
        Assert.assertEquals("predetermined participant", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0396", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        Assert.assertNull(exp.getFeatureDetectionMethod());
        // host organism
        Assert.assertNotNull(exp.getHostOrganism());
        Organism host = exp.getHostOrganism();
        Assert.assertEquals(-4, host.getTaxId());
        Assert.assertEquals("in vivo", host.getCommonName());
        Assert.assertNull(host.getScientificName());
        Assert.assertEquals(0, host.getAliases().size());
        Assert.assertNull(host.getCellType());
        Assert.assertNull(host.getTissue());
        Assert.assertNull(host.getCompartment());
        // publication
        Assert.assertNotNull(exp.getPublication());
        Publication pub = exp.getPublication();
        Assert.assertEquals("10049915", pub.getPubmedId());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(0, pub.getXrefs().size());
        Assert.assertEquals("Genetics (0016-6731)", pub.getJournal());
        Assert.assertEquals("1999", PsiXmlUtils.YEAR_FORMAT.format(pub.getPublicationDate()));
        Assert.assertEquals(4, pub.getAuthors().size());
        Assert.assertNotNull(pub.getReleasedDate());
        // source
        Assert.assertNotNull(pub.getSource());
        Source source = pub.getSource();
        Assert.assertEquals("MINT", source.getShortName());
        Assert.assertEquals("MINT, Dpt of Biology, University of Rome Tor Vergata", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0471", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(0, source.getXrefs().size());
        Assert.assertEquals(2, source.getAnnotations().size());
        Assert.assertEquals("http://mint.bio.uniroma2.it/mint", source.getUrl());
        // confidences
        Assert.assertEquals(1, exp.getConfidences().size());
        Confidence conf = exp.getConfidences().iterator().next();
        Assert.assertEquals("author-score", conf.getType().getShortName());
        Assert.assertEquals("1.2", conf.getValue());

        // attributes
        Assert.assertEquals(1, interaction.getAnnotations().size());
        Annotation comment = interaction.getAnnotations().iterator().next();
        Assert.assertEquals("comment", comment.getTopic().getShortName());
        Assert.assertEquals("MI:0612", comment.getTopic().getMIIdentifier());
        Assert.assertEquals("mint", comment.getValue());

        // boolean
        Assert.assertTrue(((ExtendedPsi25InteractionEvidence) interaction).isModelled());
        Assert.assertFalse(interaction.isNegative());
        Assert.assertFalse(((ExtendedPsi25InteractionEvidence) interaction).isIntraMolecular());
        // interaction type
        Assert.assertNotNull(interaction.getInteractionType());
        method = interaction.getInteractionType();
        Assert.assertEquals("physical association", method.getShortName());
        Assert.assertEquals("physical association", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0915", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ParticipantEvidence> partIterator = interaction.getParticipants().iterator();
        ParticipantEvidence p1 = partIterator.next();
        Assert.assertEquals("rad53_yeast", ((ExtendedPsi25ParticipantEvidence) p1).getShortLabel());
        Assert.assertEquals(2, p1.getXrefs().size());
        Assert.assertNotNull(p1.getStoichiometry());
        Assert.assertEquals(1, p1.getStoichiometry().getMinValue());
        Assert.assertEquals(0, p1.getAnnotations().size());
        // bio role
        Assert.assertNotNull(p1.getBiologicalRole());
        method = p1.getBiologicalRole();
        Assert.assertEquals("unspecified role", method.getShortName());
        Assert.assertEquals("unspecified role", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0499", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // experimental role
        Assert.assertNotNull(p1.getExperimentalRole());
        method = p1.getExperimentalRole();
        Assert.assertEquals("bait", method.getShortName());
        Assert.assertEquals("bait", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0496", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // experimental preparation
        Assert.assertEquals(1, p1.getExperimentalPreparations().size());
        method =  p1.getExperimentalPreparations().iterator().next();
        Assert.assertEquals("over-expressed", method.getShortName());
        Assert.assertEquals("over expressed level", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0506", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // identification method
        Assert.assertEquals(1, p1.getIdentificationMethods().size());
        Assert.assertNotSame(p1.getIdentificationMethods().iterator().next(), exp.getParticipantIdentificationMethod());
        // experimental interactor
        Assert.assertEquals(1, ((ExtendedPsi25ParticipantEvidence) p1).getExperimentalInteractors().size());
        ExperimentalInteractor expInteractor = ((ExtendedPsi25ParticipantEvidence) p1).getExperimentalInteractors().iterator().next();
        Assert.assertNotNull(expInteractor.getInteractor());
        Assert.assertTrue(expInteractor.getExperiments().isEmpty());
        Assert.assertEquals("dbf4_yeast", expInteractor.getInteractor().getShortName());
        // expressed in
        Assert.assertNotNull(p1.getExpressedInOrganism());
        host = p1.getExpressedInOrganism();
        Assert.assertEquals(9606, host.getTaxId());
        Assert.assertEquals("human-293t", host.getCommonName());
        Assert.assertNull(host.getScientificName());
        Assert.assertEquals(0, host.getAliases().size());
        Assert.assertNotNull(host.getCellType());
        Assert.assertEquals("293t", host.getCellType().getShortName());
        Assert.assertNull(host.getTissue());
        Assert.assertNull(host.getCompartment());
        // features
        Assert.assertEquals(1, p1.getFeatures().size());
        FeatureEvidence f = p1.getFeatures().iterator().next();
        Assert.assertEquals("tagged molecule", f.getShortName());
        Assert.assertEquals(2, f.getIdentifiers().size());
        Assert.assertEquals(0, f.getXrefs().size());
        Assert.assertNotNull(f.getType());
        Assert.assertEquals(0, f.getDetectionMethods().size());
        Assert.assertEquals(0, f.getAnnotations().size());
        Assert.assertEquals(1, f.getRanges().size());
        Assert.assertEquals("?-?", RangeUtils.convertRangeToString(f.getRanges().iterator().next()));
        // interactor
        Assert.assertNotNull(p1.getInteractor());
        Assert.assertEquals("rad53_yeast", p1.getInteractor().getShortName());
        Assert.assertEquals("Serine/threonine-protein kinase RAD53", p1.getInteractor().getFullName());
        Assert.assertEquals(8, p1.getInteractor().getAliases().size());
        Assert.assertTrue(p1.getInteractor() instanceof Protein);
        Protein prot = (Protein)p1.getInteractor();
        Assert.assertEquals("RAD53", prot.getGeneName());
        Assert.assertEquals(44, prot.getXrefs().size());
        Assert.assertEquals(4, prot.getIdentifiers().size());
        Assert.assertEquals("P22216", prot.getUniprotkb());
        Assert.assertEquals("MENITQPTQQSTQATQRFLIEKFSQEQIGENIVCRVICTTGQIPIRDLSADISQVLKEKRSIKKVWTFGRNPACDYHLGNISRLSNKHFQILLGEDGNLLLNDISTNGTWLNGQKVEKNSNQLLSQGDEITVGVGVESDILSLVIFINDKFKQCLEQNKVDRIRSNLKNTSKIASPGLTSSTASSMVANKTGIFKDFSIIDEVVGQGAFATVKKAIERTTGKTFAVKIISKRKVIGNMDGVTRELEVLQKLNHPRIVRLKGFYEDTESYYMVMEFVSGGDLMDFVAAHGAVGEDAGREISRQILTAIKYIHSMGISHRDLKPDNILIEQDDPVLVKITDFGLAKVQGNGSFMKTFCGTLAYVAPEVIRGKDTSVSPDEYEERNEYSSLVDMWSMGCLVYVILTGHLPFSGSTQDQLYKQIGRGSYHEGPLKDFRISEEARDFIDSLLQVDPNNRSTAAKALNHPWIKMSPLGSQSYGDFSQISLSQSLSQQKLLENMDDAQYEFVKAQRKLQMEQQLQEQDQEDQDGKIQGFKIPAHAPIRYTQPKSIEAETREQKLLHSNNTENVKSSKKKGNGRFLTLKPLPDSIIQESLEIQQGVNPFFIGRSEDCNCKIEDNRLSRVHCFIFKKRHAVGKSMYESPAQGLDDIWYCHTGTNVSYLNNNRMIQGTKFLLQDGDEIKIIWDKNNKFVIGFKVEINDTTGLFNEGLGMLQEQRVVLKQTAEEKDLVKKLTQMMAAQRANQPSASSSSMSAKKPPVSDTNNNGNNSVLNDLVESPINANTGNILKRIHSVSLSQSQIDPSKKVKRAKLDQTSKGPENLQFS", prot.getSequence());
        Assert.assertNotNull(prot.getOrganism());
        Assert.assertEquals(559292, prot.getOrganism().getTaxId());

        Assert.assertEquals(1, interaction.getConfidences().size());
        conf = interaction.getConfidences().iterator().next();
        Assert.assertEquals("intact-miscore", conf.getType().getShortName());
        Assert.assertEquals("0.8", conf.getValue());
        Assert.assertEquals(1, interaction.getParameters().size());
        Parameter param = interaction.getParameters().iterator().next();
        Assert.assertEquals("kd", param.getType().getShortName());
        Assert.assertEquals("5", param.getValue().toString());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_expanded() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = FullXml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915-expanded.xml");

        PsiXml25Parser<InteractionEvidence> parser = new FullXml25EvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertNull(interaction.getImexId());
        Assert.assertEquals(2, interaction.getIdentifiers().size());
        Assert.assertEquals("copyright", interaction.getAvailability());
        Assert.assertEquals(100000000, ((ExtendedPsi25InteractionEvidence) interaction).getXmlAvailability().getId());

        // identifiers
        Iterator<Xref> identifierIterator = interaction.getIdentifiers().iterator();
        Xref mint = identifierIterator.next();
        Assert.assertEquals("mint", mint.getDatabase().getShortName());
        Assert.assertEquals("MI:0471", mint.getDatabase().getMIIdentifier());
        Assert.assertEquals("MINT-18818", mint.getId());
        Assert.assertEquals("identity", mint.getQualifier().getShortName());
        Assert.assertEquals("MI:0356", mint.getQualifier().getMIIdentifier());
        Xref intact = identifierIterator.next();
        Assert.assertEquals("intact", intact.getDatabase().getShortName());
        Assert.assertEquals("MI:0469", intact.getDatabase().getMIIdentifier());
        Assert.assertEquals("EBI-6948673", intact.getId());
        Assert.assertEquals("identity", intact.getQualifier().getShortName());
        Assert.assertEquals("MI:0356", intact.getQualifier().getMIIdentifier());
        Assert.assertEquals(0, interaction.getXrefs().size());

        // experiment
        Assert.assertNotNull(interaction.getExperiment());
        ExtendedPsi25Experiment exp = (ExtendedPsi25Experiment)interaction.getExperiment();
        Assert.assertEquals("dohrmann-1999-1", exp.getShortLabel());
        Assert.assertEquals("RAD53 regulates DBF4 independently of checkpoint function in Saccharomyces cerevisiae.", exp.getFullName());
        Assert.assertEquals(3, exp.getXrefs().size());
        // all annotations are publication annotations
        Assert.assertEquals(0, exp.getAnnotations().size());
        // detection method
        Assert.assertNotNull(exp.getInteractionDetectionMethod());
        CvTerm method = exp.getInteractionDetectionMethod();
        Assert.assertEquals("2 hybrid", method.getShortName());
        Assert.assertEquals("two hybrid", method.getFullName());
        Assert.assertEquals(8, method.getSynonyms().size());
        Assert.assertEquals("MI:0018", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(3, method.getXrefs().size());
        // participant identification method
        Assert.assertNotNull(exp.getParticipantIdentificationMethod());
        method = exp.getParticipantIdentificationMethod();
        Assert.assertEquals("predetermined", method.getShortName());
        Assert.assertEquals("predetermined participant", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0396", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        Assert.assertNull(exp.getFeatureDetectionMethod());
        // host organism
        Assert.assertNotNull(exp.getHostOrganism());
        Organism host = exp.getHostOrganism();
        Assert.assertEquals(-4, host.getTaxId());
        Assert.assertEquals("in vivo", host.getCommonName());
        Assert.assertNull(host.getScientificName());
        Assert.assertEquals(0, host.getAliases().size());
        Assert.assertNull(host.getCellType());
        Assert.assertNull(host.getTissue());
        Assert.assertNull(host.getCompartment());
        // publication
        Assert.assertNotNull(exp.getPublication());
        Publication pub = exp.getPublication();
        Assert.assertEquals("10049915", pub.getPubmedId());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(0, pub.getXrefs().size());
        Assert.assertEquals("Genetics (0016-6731)", pub.getJournal());
        Assert.assertEquals("1999", PsiXmlUtils.YEAR_FORMAT.format(pub.getPublicationDate()));
        Assert.assertEquals(4, pub.getAuthors().size());
        Assert.assertNotNull(pub.getReleasedDate());
        // source
        Assert.assertNotNull(pub.getSource());
        Source source = pub.getSource();
        Assert.assertEquals("MINT", source.getShortName());
        Assert.assertEquals("MINT, Dpt of Biology, University of Rome Tor Vergata", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0471", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(0, source.getXrefs().size());
        Assert.assertEquals(2, source.getAnnotations().size());
        Assert.assertEquals("http://mint.bio.uniroma2.it/mint", source.getUrl());
        // confidences
        Assert.assertEquals(1, exp.getConfidences().size());
        Confidence conf = exp.getConfidences().iterator().next();
        Assert.assertEquals("author-score", conf.getType().getShortName());
        Assert.assertEquals("1.2", conf.getValue());

        // attributes
        Assert.assertEquals(1, interaction.getAnnotations().size());
        Annotation comment = interaction.getAnnotations().iterator().next();
        Assert.assertEquals("comment", comment.getTopic().getShortName());
        Assert.assertEquals("MI:0612", comment.getTopic().getMIIdentifier());
        Assert.assertEquals("mint", comment.getValue());

        // boolean
        Assert.assertTrue(((ExtendedPsi25InteractionEvidence) interaction).isModelled());
        Assert.assertFalse(interaction.isNegative());
        Assert.assertFalse(((ExtendedPsi25InteractionEvidence) interaction).isIntraMolecular());
        // interaction type
        Assert.assertNotNull(interaction.getInteractionType());
        method = interaction.getInteractionType();
        Assert.assertEquals("physical association", method.getShortName());
        Assert.assertEquals("physical association", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0915", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ParticipantEvidence> partIterator = interaction.getParticipants().iterator();
        ParticipantEvidence p1 = partIterator.next();
        Assert.assertEquals("rad53_yeast", ((ExtendedPsi25ParticipantEvidence) p1).getShortLabel());
        Assert.assertEquals(2, p1.getXrefs().size());
        Assert.assertNotNull(p1.getStoichiometry());
        Assert.assertEquals(1, p1.getStoichiometry().getMinValue());
        Assert.assertEquals(0, p1.getAnnotations().size());
        // bio role
        Assert.assertNotNull(p1.getBiologicalRole());
        method = p1.getBiologicalRole();
        Assert.assertEquals("unspecified role", method.getShortName());
        Assert.assertEquals("unspecified role", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0499", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // experimental role
        Assert.assertNotNull(p1.getExperimentalRole());
        method = p1.getExperimentalRole();
        Assert.assertEquals("bait", method.getShortName());
        Assert.assertEquals("bait", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0496", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // experimental preparation
        Assert.assertEquals(1, p1.getExperimentalPreparations().size());
        method =  p1.getExperimentalPreparations().iterator().next();
        Assert.assertEquals("over-expressed", method.getShortName());
        Assert.assertEquals("over expressed level", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0506", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // identification method
        Assert.assertEquals(1, p1.getIdentificationMethods().size());
        Assert.assertNotSame(p1.getIdentificationMethods().iterator().next(), exp.getParticipantIdentificationMethod());
        // experimental interactor
        Assert.assertEquals(1, ((ExtendedPsi25ParticipantEvidence) p1).getExperimentalInteractors().size());
        ExperimentalInteractor expInteractor = ((ExtendedPsi25ParticipantEvidence) p1).getExperimentalInteractors().iterator().next();
        Assert.assertNotNull(expInteractor.getInteractor());
        Assert.assertTrue(expInteractor.getExperiments().isEmpty());
        Assert.assertEquals("dbf4_yeast", expInteractor.getInteractor().getShortName());
        // expressed in
        Assert.assertNotNull(p1.getExpressedInOrganism());
        host = p1.getExpressedInOrganism();
        Assert.assertEquals(9606, host.getTaxId());
        Assert.assertEquals("human-293t", host.getCommonName());
        Assert.assertNull(host.getScientificName());
        Assert.assertEquals(0, host.getAliases().size());
        Assert.assertNotNull(host.getCellType());
        Assert.assertEquals("293t", host.getCellType().getShortName());
        Assert.assertNull(host.getTissue());
        Assert.assertNull(host.getCompartment());
        // features
        Assert.assertEquals(1, p1.getFeatures().size());
        FeatureEvidence f = p1.getFeatures().iterator().next();
        Assert.assertEquals("tagged molecule", f.getShortName());
        Assert.assertEquals(2, f.getIdentifiers().size());
        Assert.assertEquals(0, f.getXrefs().size());
        Assert.assertNotNull(f.getType());
        Assert.assertEquals(0, f.getDetectionMethods().size());
        Assert.assertEquals(0, f.getAnnotations().size());
        Assert.assertEquals(1, f.getRanges().size());
        Assert.assertEquals("?-?", RangeUtils.convertRangeToString(f.getRanges().iterator().next()));
        // interactor
        Assert.assertNotNull(p1.getInteractor());
        Assert.assertEquals("rad53_yeast", p1.getInteractor().getShortName());
        Assert.assertEquals("Serine/threonine-protein kinase RAD53", p1.getInteractor().getFullName());
        Assert.assertEquals(8, p1.getInteractor().getAliases().size());
        Assert.assertTrue(p1.getInteractor() instanceof Protein);
        Protein prot = (Protein)p1.getInteractor();
        Assert.assertEquals("RAD53", prot.getGeneName());
        Assert.assertEquals(44, prot.getXrefs().size());
        Assert.assertEquals(4, prot.getIdentifiers().size());
        Assert.assertEquals("P22216", prot.getUniprotkb());
        Assert.assertEquals("MENITQPTQQSTQATQRFLIEKFSQEQIGENIVCRVICTTGQIPIRDLSADISQVLKEKRSIKKVWTFGRNPACDYHLGNISRLSNKHFQILLGEDGNLLLNDISTNGTWLNGQKVEKNSNQLLSQGDEITVGVGVESDILSLVIFINDKFKQCLEQNKVDRIRSNLKNTSKIASPGLTSSTASSMVANKTGIFKDFSIIDEVVGQGAFATVKKAIERTTGKTFAVKIISKRKVIGNMDGVTRELEVLQKLNHPRIVRLKGFYEDTESYYMVMEFVSGGDLMDFVAAHGAVGEDAGREISRQILTAIKYIHSMGISHRDLKPDNILIEQDDPVLVKITDFGLAKVQGNGSFMKTFCGTLAYVAPEVIRGKDTSVSPDEYEERNEYSSLVDMWSMGCLVYVILTGHLPFSGSTQDQLYKQIGRGSYHEGPLKDFRISEEARDFIDSLLQVDPNNRSTAAKALNHPWIKMSPLGSQSYGDFSQISLSQSLSQQKLLENMDDAQYEFVKAQRKLQMEQQLQEQDQEDQDGKIQGFKIPAHAPIRYTQPKSIEAETREQKLLHSNNTENVKSSKKKGNGRFLTLKPLPDSIIQESLEIQQGVNPFFIGRSEDCNCKIEDNRLSRVHCFIFKKRHAVGKSMYESPAQGLDDIWYCHTGTNVSYLNNNRMIQGTKFLLQDGDEIKIIWDKNNKFVIGFKVEINDTTGLFNEGLGMLQEQRVVLKQTAEEKDLVKKLTQMMAAQRANQPSASSSSMSAKKPPVSDTNNNGNNSVLNDLVESPINANTGNILKRIHSVSLSQSQIDPSKKVKRAKLDQTSKGPENLQFS", prot.getSequence());
        Assert.assertNotNull(prot.getOrganism());
        Assert.assertEquals(559292, prot.getOrganism().getTaxId());

        Assert.assertEquals(1, interaction.getConfidences().size());
        conf = interaction.getConfidences().iterator().next();
        Assert.assertEquals("intact-miscore", conf.getType().getShortName());
        Assert.assertEquals("0.8", conf.getValue());
        Assert.assertEquals(1, interaction.getParameters().size());
        Parameter param = interaction.getParameters().iterator().next();
        Assert.assertEquals("kd", param.getType().getShortName());
        Assert.assertEquals("5", param.getValue().toString());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_inferred() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = FullXml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/21703451.xml");

        PsiXml25Parser<InteractionEvidence> parser = new FullXml25EvidenceParser(stream);
        int index = 0;
        while(!parser.hasFinished()){
            InteractionEvidence interaction = parser.parseNextInteraction();
            Assert.assertNotNull(interaction);
            Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
            if (index == 1){
                Iterator<ParticipantEvidence> pIterator = interaction.getParticipants().iterator();
                ParticipantEvidence p1 = pIterator.next();
                FeatureEvidence f1 = p1.getFeatures().iterator().next();
                Assert.assertEquals(1, f1.getLinkedFeatures().size());
                ParticipantEvidence p2 = pIterator.next();
                FeatureEvidence f2 = p2.getFeatures().iterator().next();
                Assert.assertEquals(1, f2.getLinkedFeatures().size());
                Assert.assertEquals(f1.getLinkedFeatures().iterator().next(), f2);
                Assert.assertEquals(f2.getLinkedFeatures().iterator().next(), f1);
            }
            index++;
        }

        Assert.assertEquals(26, index);

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_2() throws PsiXmlParserException, JAXBException, XMLStreamException, IOException {
        InputStream stream = new URL("ftp://ftp.ebi.ac.uk/pub/databases/intact/current/psi25/pmid/2011/19536198_gong-2009-1_01.xml").openStream();

        System.out.println("Start"+System.currentTimeMillis());
        PsiXml25Parser<InteractionEvidence> parser = new FullXml25EvidenceParser(stream);
        int index = 0;
        while(!parser.hasFinished()){
            InteractionEvidence interaction = parser.parseNextInteraction();
            Assert.assertNotNull(interaction);
            Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
            index++;
        }
        System.out.println("End"+System.currentTimeMillis());

        System.out.println("Read "+index+" interactions");

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_several_entries() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915-several-entries.xml");

        PsiXml25Parser<InteractionEvidence> parser = new FullXml25EvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());

        // experiment
        Assert.assertNotNull(interaction.getExperiment());
        ExtendedPsi25Experiment exp = (ExtendedPsi25Experiment)interaction.getExperiment();
        Assert.assertEquals("dohrmann-1999-1", exp.getShortLabel());
        // publication
        Assert.assertNotNull(exp.getPublication());
        Publication pub = exp.getPublication();
        Assert.assertEquals("10049915", pub.getPubmedId());
        // source
        Assert.assertNotNull(pub.getSource());
        Source source = pub.getSource();
        Assert.assertEquals("MINT", source.getShortName());
        Assert.assertEquals("MINT, Dpt of Biology, University of Rome Tor Vergata", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0471", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(0, source.getXrefs().size());
        Assert.assertEquals(2, source.getAnnotations().size());
        Assert.assertEquals("http://mint.bio.uniroma2.it/mint", source.getUrl());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ParticipantEvidence> partIterator = interaction.getParticipants().iterator();
        ParticipantEvidence p1 = partIterator.next();
        Assert.assertEquals("rad53_yeast", ((ExtendedPsi25ParticipantEvidence) p1).getShortLabel());
        // features
        Assert.assertEquals(1, p1.getFeatures().size());
        FeatureEvidence f = p1.getFeatures().iterator().next();
        Assert.assertEquals("tagged molecule", f.getShortName());
        // interactor
        Assert.assertNotNull(p1.getInteractor());
        Assert.assertEquals("rad53_yeast", p1.getInteractor().getShortName());
        Assert.assertEquals("Serine/threonine-protein kinase RAD53", p1.getInteractor().getFullName());
        Assert.assertEquals(8, p1.getInteractor().getAliases().size());
        Assert.assertTrue(p1.getInteractor() instanceof Protein);
        Protein prot = (Protein)p1.getInteractor();
        Assert.assertEquals("RAD53", prot.getGeneName());
        Assert.assertEquals(44, prot.getXrefs().size());
        Assert.assertEquals(4, prot.getIdentifiers().size());
        Assert.assertEquals("P22216", prot.getUniprotkb());
        Assert.assertEquals("MENITQPTQQSTQATQRFLIEKFSQEQIGENIVCRVICTTGQIPIRDLSADISQVLKEKRSIKKVWTFGRNPACDYHLGNISRLSNKHFQILLGEDGNLLLNDISTNGTWLNGQKVEKNSNQLLSQGDEITVGVGVESDILSLVIFINDKFKQCLEQNKVDRIRSNLKNTSKIASPGLTSSTASSMVANKTGIFKDFSIIDEVVGQGAFATVKKAIERTTGKTFAVKIISKRKVIGNMDGVTRELEVLQKLNHPRIVRLKGFYEDTESYYMVMEFVSGGDLMDFVAAHGAVGEDAGREISRQILTAIKYIHSMGISHRDLKPDNILIEQDDPVLVKITDFGLAKVQGNGSFMKTFCGTLAYVAPEVIRGKDTSVSPDEYEERNEYSSLVDMWSMGCLVYVILTGHLPFSGSTQDQLYKQIGRGSYHEGPLKDFRISEEARDFIDSLLQVDPNNRSTAAKALNHPWIKMSPLGSQSYGDFSQISLSQSLSQQKLLENMDDAQYEFVKAQRKLQMEQQLQEQDQEDQDGKIQGFKIPAHAPIRYTQPKSIEAETREQKLLHSNNTENVKSSKKKGNGRFLTLKPLPDSIIQESLEIQQGVNPFFIGRSEDCNCKIEDNRLSRVHCFIFKKRHAVGKSMYESPAQGLDDIWYCHTGTNVSYLNNNRMIQGTKFLLQDGDEIKIIWDKNNKFVIGFKVEINDTTGLFNEGLGMLQEQRVVLKQTAEEKDLVKKLTQMMAAQRANQPSASSSSMSAKKPPVSDTNNNGNNSVLNDLVESPINANTGNILKRIHSVSLSQSQIDPSKKVKRAKLDQTSKGPENLQFS", prot.getSequence());
        Assert.assertNotNull(prot.getOrganism());
        Assert.assertEquals(559292, prot.getOrganism().getTaxId());

        Assert.assertFalse(parser.hasFinished());

        interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertEquals("trp-inad-2", interaction.getShortName());
        Assert.assertEquals("IM-16552-12", interaction.getImexId());

        // experiment
        Assert.assertNotNull(interaction.getExperiment());
        exp = (ExtendedPsi25Experiment)interaction.getExperiment();
        Assert.assertEquals("liu-2011d-10", exp.getShortLabel());
        // publication
        Assert.assertNotNull(exp.getPublication());
        pub = exp.getPublication();
        Assert.assertEquals("21703451", pub.getPubmedId());
        // source
        Assert.assertNotNull(pub.getSource());
        source = pub.getSource();
        Assert.assertEquals("IntAct", source.getShortName());
        Assert.assertEquals("European Bioinformatics Institute", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0469", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(1, source.getXrefs().size());
        Assert.assertEquals(3, source.getAnnotations().size());
        Assert.assertEquals("http://www.ebi.ac.uk/", source.getUrl());
        Assert.assertEquals("European Bioinformatics Institute; Wellcome Trust Genome Campus; Hinxton, Cambridge; CB10 1SD; United Kingdom", source.getPostalAddress());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        partIterator = interaction.getParticipants().iterator();
        p1 = partIterator.next();
        Assert.assertEquals("n/a", ((ExtendedPsi25ParticipantEvidence) p1).getShortLabel());
        // features
        Assert.assertEquals(2, p1.getFeatures().size());
        f = p1.getFeatures().iterator().next();
        Assert.assertEquals("gb1 tag region", f.getShortName());
        // interactor
        Assert.assertNotNull(p1.getInteractor());
        Assert.assertEquals("trp_drome", p1.getInteractor().getShortName());
        Assert.assertEquals("Transient receptor potential protein", p1.getInteractor().getFullName());
        Assert.assertEquals(2, p1.getInteractor().getAliases().size());
        Assert.assertTrue(p1.getInteractor() instanceof Protein);
        prot = (Protein)p1.getInteractor();
        Assert.assertEquals("trp", prot.getGeneName());
        Assert.assertEquals(23, prot.getXrefs().size());
        Assert.assertEquals(4, prot.getIdentifiers().size());
        Assert.assertEquals("P19334", prot.getUniprotkb());
        Assert.assertEquals("MGSNTESDAEKALGSRLDYDLMMAEEYILSDVEKNFILSCERGDLPGVKKILEEYQGTDKFNINCTDPMNRSALISAIENENFDLMVILLEHNIEVGDALLHAISEEYVEAVEELLQWEETNHKEGQPYSWEAVDRSKSTFTVDITPLILAAHRNNYEILKILLDRGATLPMPHDVKCGCDECVTSQMTDSLRHSQSRINAYRALSASSLIALSSRDPVLTAFQLSWELKRLQAMESEFRAEYTEMRQMVQDFGTSLLDHARTSMELEVMLNFNHEPSHDIWCLGQRQTLERLKLAIRYKQKTFVAHPNVQQLLAAIWYDGLPGFRRKQASQQLMDVVKLGCSFPIYSLKYILAPDSEGAKFMRKPFVKFITHSCSYMFFLMLLGAASLRVVQITFELLAFPWMLTMLEDWRKHERGSLPGPIELAIITYIMALIFEELKSLYSDGLFEYIMDLWNIVDYISNMFYVTWILCRATAWVIVHRDLWFRGIDPYFPREHWHPFDPMLLSEGAFAAGMVFSYLKLVHIFSINPHLGPLQVSLGRMIIDIIKFFFIYTLVLFAFGCGLNQLLWYYAELEKNKCYHLHPDVADFDDQEKACTIWRRFSNLFETSQSLFWASFGLVDLVSFDLAGIKSFTRFWALLMFGSYSVINIIVLLNMLIAMMSNSYQIISERADTEWKFARSQLWMSYFEDGGTIPPPFNLCPNMKMLRKTLGRKRPSRTKSFMRKSMERAQTLHDKVMKLLVRRYITAEQRRRDDYGITEDDIIEVRQDISSLRFELLEIFTNNNWDVPDIEKKSQGVARTTKGKVMERRILKDFQIGFVENLKQEMSESESGRDIFSSLAKVIGRKKTQKGDKDWNAIARKNTFASDPIGSKRSSMQRHSQRSLRRKIIEQANEGLQMNQTQLIEFNPNLGDVTRATRVAYVKFMRKKMAADEVSLADDEGAPNGEGEKKPLDASGSKKSITSGGTGGGASMLAAAALRASVKNVDEKSGADGKPGTMGKPTDDKKAGDDKDKQQPPKDSKPSAGGPKPGDQKPTPGAGAPKPQAAGTISKPGESQKKDAPAPPTKPGDTKPAAPKPGESAKPEAAAKKEESSKTEASKPAATNGAAKSAAPSAPSDAKPDSKLKPGAAGAPEATKATNGASKPDEKKSGPEEPKKAAGDSKPGDDAKDKDKKPGDDKDKKPGDDKDKKPADNNDKKPADDKDKKPGDDKDKKPGDDKDKKPSDDKDKKPADDKDKKPAAAPLKPAIKVGQSSAAAGGERGKSTVTGRMISGWL", prot.getSequence());
        Assert.assertNotNull(prot.getOrganism());
        Assert.assertEquals(7227, prot.getOrganism().getTaxId());

        parser.close();
    }

    @Test(expected = PsiXmlParserException.class)
    public void test_empty_file() throws JAXBException, XMLStreamException, PsiXmlParserException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/empty.xml");
        PsiXml25Parser<InteractionEvidence> parser = new FullXml25EvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        // read first interaction
        Assert.assertNull(interaction);
        Assert.assertTrue(parser.hasFinished());
    }

    /*@Test
    @Ignore
    public void test_read_valid_xml25_3() throws JAXBException, XMLStreamException, IOException {
        InputStream stream = new FileInputStream("/home/marine/Downloads/BIOGRID-ALL-3.2.97.psi25.xml");

        System.out.println("Start"+System.currentTimeMillis());
        PsiXml25Parser<InteractionEvidence> parser = new Xml25EvidenceParser(stream);
        int index = 0;
        while(!parser.hasFinished()){
            InteractionEvidence interaction = parser.parseNextInteraction();
            if (interaction != null){
                index++;
            }
        }
        System.out.println("End"+System.currentTimeMillis());

        System.out.println("Read "+index+" interactions");

        parser.close();
    }*/
}
