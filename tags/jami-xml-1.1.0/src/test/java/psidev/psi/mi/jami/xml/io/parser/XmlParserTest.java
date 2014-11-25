package psidev.psi.mi.jami.xml.io.parser;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.model.extension.*;
import psidev.psi.mi.jami.xml.model.extension.xml300.BindingFeatures;
import psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlCausalRelationship;
import psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlModelledInteraction;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Unit tester for XmlEvidenceParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlParserTest {

    @Test
    public void test_read_valid_xml25_compact() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlParserTest.class.getResourceAsStream("/samples/10049915.xml");

        PsiXmlParser<InteractionEvidence> parser = new XmlEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertNull(interaction.getImexId());
        Assert.assertEquals(2, interaction.getIdentifiers().size());
        Assert.assertEquals("copyright", interaction.getAvailability());
        Assert.assertEquals(100000000, ((ExtendedPsiXmlInteractionEvidence) interaction).getXmlAvailability().getId());

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
        ExtendedPsiXmlExperiment exp = (ExtendedPsiXmlExperiment)interaction.getExperiment();
        Assert.assertEquals("dohrmann-1999-1", exp.getShortName());
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
        Assert.assertTrue(((ExtendedPsiXmlInteractionEvidence) interaction).isModelled());
        Assert.assertFalse(interaction.isNegative());
        Assert.assertFalse(((ExtendedPsiXmlInteractionEvidence) interaction).isIntraMolecular());
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
        Assert.assertEquals("rad53_yeast", ((ExtendedPsiXmlParticipantEvidence) p1).getShortName());
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
        Assert.assertEquals(1, ((ExtendedPsiXmlParticipantEvidence) p1).getExperimentalInteractors().size());
        ExperimentalInteractor expInteractor = ((ExtendedPsiXmlParticipantEvidence) p1).getExperimentalInteractors().iterator().next();
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
        InputStream stream = XmlParserTest.class.getResourceAsStream("/samples/10049915-expanded.xml");

        PsiXmlParser<InteractionEvidence> parser = new XmlEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertNull(interaction.getImexId());
        Assert.assertEquals(2, interaction.getIdentifiers().size());
        Assert.assertEquals("copyright", interaction.getAvailability());
        Assert.assertEquals(100000000, ((ExtendedPsiXmlInteractionEvidence) interaction).getXmlAvailability().getId());

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
        ExtendedPsiXmlExperiment exp = (ExtendedPsiXmlExperiment)interaction.getExperiment();
        Assert.assertEquals("dohrmann-1999-1", exp.getShortName());
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
        Assert.assertTrue(((ExtendedPsiXmlInteractionEvidence) interaction).isModelled());
        Assert.assertFalse(interaction.isNegative());
        Assert.assertFalse(((ExtendedPsiXmlInteractionEvidence) interaction).isIntraMolecular());
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
        Assert.assertEquals("rad53_yeast", ((ExtendedPsiXmlParticipantEvidence) p1).getShortName());
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
        Assert.assertEquals(1, ((ExtendedPsiXmlParticipantEvidence) p1).getExperimentalInteractors().size());
        ExperimentalInteractor expInteractor = ((ExtendedPsiXmlParticipantEvidence) p1).getExperimentalInteractors().iterator().next();
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
    public void test_read_valid_xml25_several_entries() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlParserTest.class.getResourceAsStream("/samples/10049915-several-entries.xml");

        PsiXmlParser<InteractionEvidence> parser = new XmlEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());

        // experiment
        Assert.assertNotNull(interaction.getExperiment());
        ExtendedPsiXmlExperiment exp = (ExtendedPsiXmlExperiment)interaction.getExperiment();
        Assert.assertEquals("dohrmann-1999-1", exp.getShortName());
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
        Assert.assertEquals("rad53_yeast", ((ExtendedPsiXmlParticipantEvidence) p1).getShortName());
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
        exp = (ExtendedPsiXmlExperiment)interaction.getExperiment();
        Assert.assertEquals("liu-2011d-10", exp.getShortName());
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
        Assert.assertEquals("n/a", ((ExtendedPsiXmlParticipantEvidence) p1).getShortName());
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

    @Test
    public void test_empty_file() throws JAXBException, XMLStreamException, PsiXmlParserException {
        InputStream stream = XmlParserTest.class.getResourceAsStream("/samples/empty.xml");
        PsiXmlParser<InteractionEvidence> parser = new XmlEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        // read first interaction
        Assert.assertNull(interaction);
        Assert.assertTrue(parser.hasFinished());
    }

    @Test
    public void test_read_valid_xml25_inferred() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlParserTest.class.getResourceAsStream("/samples/21703451.xml");

        PsiXmlParser<InteractionEvidence> parser = new XmlEvidenceParser(stream);
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
    @Ignore
    public void test_read_valid_xml25_2() throws PsiXmlParserException, JAXBException, XMLStreamException, IOException {
        InputStream stream = new URL("ftp://ftp.ebi.ac.uk/pub/databases/intact/current/psi25/pmid/2011/19536198_gong-2009-1_01.xml").openStream();

        System.out.println("Start"+System.currentTimeMillis());
        PsiXmlParser<InteractionEvidence> parser = new XmlEvidenceParser(stream);
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
    public void test_read_valid_xml30_bibref() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/19411066_bibref.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        InteractionEvidence interaction = (InteractionEvidence)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);
        Assert.assertEquals("mdm2-p53", interaction.getShortName());
        Assert.assertEquals("IM-13592-1",interaction.getImexId());
        Assert.assertEquals(1, interaction.getIdentifiers().size());
        Assert.assertNull(interaction.getAvailability());

        // identifiers
        Iterator<Xref> identifierIterator = interaction.getIdentifiers().iterator();
        Xref intact = identifierIterator.next();
        Assert.assertEquals("intact", intact.getDatabase().getShortName());
        Assert.assertEquals("MI:0469", intact.getDatabase().getMIIdentifier());
        Assert.assertEquals("EBI-2573567", intact.getId());
        Assert.assertEquals("identity", intact.getQualifier().getShortName());
        Assert.assertEquals("MI:0356", intact.getQualifier().getMIIdentifier());

        // experiment
        Assert.assertNotNull(interaction.getExperiment());
        ExtendedPsiXmlExperiment exp = (ExtendedPsiXmlExperiment)interaction.getExperiment();
        Assert.assertEquals("gu-2009-1", exp.getShortName());
        Assert.assertNull(exp.getFullName());
        Assert.assertEquals(1, exp.getXrefs().size());
        // all annotations are publication annotations
        Assert.assertEquals(0, exp.getAnnotations().size());
        // detection method
        Assert.assertNotNull(exp.getInteractionDetectionMethod());
        CvTerm method = exp.getInteractionDetectionMethod();
        Assert.assertEquals("anti bait coip", method.getShortName());
        Assert.assertEquals("anti bait coimmunoprecipitation", method.getFullName());
        Assert.assertTrue(method.getSynonyms().isEmpty());
        Assert.assertEquals("MI:0006", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // participant identification method
        Assert.assertNotNull(exp.getParticipantIdentificationMethod());
        // host organism
        Assert.assertNotNull(exp.getHostOrganism());
        Organism host = exp.getHostOrganism();
        Assert.assertEquals(9606, host.getTaxId());
        Assert.assertEquals("human-eu-1", host.getCommonName());
        Assert.assertEquals("Homo sapiens EU-1 leukemia cell line",host.getScientificName());
        Assert.assertEquals(0, host.getAliases().size());
        Assert.assertNotNull(host.getCellType());
        Assert.assertNull(host.getTissue());
        Assert.assertNull(host.getCompartment());
        // publication
        Assert.assertNotNull(exp.getPublication());
        Publication pub = exp.getPublication();
        Assert.assertEquals("19411066", pub.getPubmedId());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(1, pub.getXrefs().size());
        Assert.assertEquals("Cancer Cell (1535-6108)", pub.getJournal());
        Assert.assertEquals("2009", PsiXmlUtils.YEAR_FORMAT.format(pub.getPublicationDate()));
        Assert.assertEquals(6, pub.getAuthors().size());
        Assert.assertNotNull(pub.getReleasedDate());
        // source
        Assert.assertNotNull(pub.getSource());
        Source source = pub.getSource();
        Assert.assertEquals("IntAct", source.getShortName());
        Assert.assertEquals("European Bioinformatics Institute", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0469", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(0, source.getXrefs().size());
        Assert.assertEquals(3, source.getAnnotations().size());
        Assert.assertEquals("http://www.ebi.ac.uk/", source.getUrl());
        // confidences
        Assert.assertEquals(0, exp.getConfidences().size());

        // attributes
        Assert.assertEquals(2, interaction.getAnnotations().size());
        Annotation comment = interaction.getAnnotations().iterator().next();
        Assert.assertEquals("antagonist", comment.getTopic().getShortName());
        Assert.assertEquals("MI:0626", comment.getTopic().getMIIdentifier());
        Assert.assertEquals("Cells were treated with 10 Gy Infra-red radiation", comment.getValue());

        // boolean
        Assert.assertFalse(((ExtendedPsiXmlInteractionEvidence) interaction).isModelled());
        Assert.assertFalse(interaction.isNegative());
        Assert.assertFalse(((ExtendedPsiXmlInteractionEvidence) interaction).isIntraMolecular());
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
        ParticipantEvidence p1 = interaction.getParticipants().iterator().next();
        Assert.assertEquals("n/a", ((ExtendedPsiXmlParticipantEvidence) p1).getShortName());
        Assert.assertEquals(1, p1.getXrefs().size());
        Assert.assertNull(p1.getStoichiometry());
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
        Assert.assertEquals("prey", method.getShortName());
        Assert.assertEquals("prey", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0498", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());
        // experimental preparation
        Assert.assertEquals(0, p1.getExperimentalPreparations().size());
        // identification method
        Assert.assertEquals(1, p1.getIdentificationMethods().size());
        Assert.assertEquals(p1.getIdentificationMethods().iterator().next(), exp.getParticipantIdentificationMethod());
        // experimental interactor
        Assert.assertEquals(0, ((ExtendedPsiXmlParticipantEvidence) p1).getExperimentalInteractors().size());
        // expressed in
        Assert.assertNull(p1.getExpressedInOrganism());
        // features
        Assert.assertEquals(0, p1.getFeatures().size());
        // interactor
        Assert.assertNotNull(p1.getInteractor());
        Assert.assertEquals("p53_human", p1.getInteractor().getShortName());
        Assert.assertEquals("Cellular tumor antigen p53", p1.getInteractor().getFullName());
        Assert.assertEquals(5, p1.getInteractor().getAliases().size());
        Assert.assertTrue(p1.getInteractor() instanceof Protein);
        Protein prot = (Protein)p1.getInteractor();
        Assert.assertEquals("TP53", prot.getGeneName());
        Assert.assertEquals(28, prot.getIdentifiers().size());
        Assert.assertEquals("P04637", prot.getUniprotkb());
        Assert.assertEquals("MEEPQSDPSVEPPLSQETFSDLWKLLPENNVLSPLPSQAMDDLMLSPDDIEQWFTEDPGPDEAPRMPEAAPPVAPAPAAPTPAAPAPAPSWPLSSSVPSQKTYQGSYGFRLGFL" +
                "HSGTAKSVTCTYSPALNKMFCQLAKTCPVQLWVDSTPPPGTRVRAMAIYKQSQHMTEVVRRCPHHERCSDSDGLAPPQHLIRVEGNLRVEYLDDRNTFRHSVVVPYEPPEVGSDCTTIHYNYMCNS" +
                "SCMGGMNRRPILTIITLEDSSGNLLGRNSFEVRVCACPGRDRRTEEENLRKKGEPHHELPPGSTKRALPNNTSSSPQPKKKPLDGEYFTLQIRGRERFEMFRELNEALELKDAQAGKEPGGSRAHS" +
                "SHLKSKKGQSTSRHKKLMFKTEGPDSD", prot.getSequence());
        Assert.assertNotNull(prot.getOrganism());
        Assert.assertEquals(9606, prot.getOrganism().getTaxId());

        Assert.assertEquals(0, interaction.getConfidences().size());

        Assert.assertFalse(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_resulting_sequence() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/7566652_variant.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        InteractionEvidence interaction = (InteractionEvidence)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        // experiment
        Assert.assertNotNull(interaction.getExperiment());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ParticipantEvidence> pIterator = interaction.getParticipants().iterator();
        ParticipantEvidence p1 = pIterator.next();
        ParticipantEvidence p2 = pIterator.next();
        Assert.assertEquals(1, p2.getFeatures().size());

        FeatureEvidence f1 = p2.getFeatures().iterator().next();
        Assert.assertEquals("cys130arg", f1.getShortName());
        Assert.assertEquals(CvTermUtils.createMICvTerm("variant", "MI:1241"), f1.getType());
        Assert.assertTrue(f1.getDetectionMethods().isEmpty());
        Assert.assertEquals(1, f1.getRanges().size());

        Range r1 = f1.getRanges().iterator().next();
        Assert.assertEquals("112-112", RangeUtils.convertRangeToString(r1));
        Assert.assertNotNull(r1.getResultingSequence());
        Assert.assertEquals("C", r1.getResultingSequence().getOriginalSequence());
        Assert.assertEquals("R", r1.getResultingSequence().getNewSequence());
        Assert.assertEquals(1, r1.getResultingSequence().getXrefs().size());

        Assert.assertFalse(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_stoichiometry() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/11779463_stoichiometry.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        InteractionEvidence interaction = (InteractionEvidence)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        // experiment
        Assert.assertNotNull(interaction.getExperiment());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        ParticipantEvidence p1 = interaction.getParticipants().iterator().next();
        Assert.assertNotNull(p1.getStoichiometry());
        Assert.assertEquals(14, p1.getStoichiometry().getMinValue());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_stoichiometry_range() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/gaba_receptor_abstract.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        ModelledInteraction interaction = (ModelledInteraction)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        // participants
        Assert.assertEquals(3, interaction.getParticipants().size());
        ModelledParticipant p1 = interaction.getParticipants().iterator().next();
        Assert.assertNotNull(p1.getStoichiometry());
        Assert.assertEquals(1, p1.getStoichiometry().getMinValue());
        Assert.assertEquals(3, p1.getStoichiometry().getMaxValue());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_feature_parameters() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/23334297_feature_parameter.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        InteractionEvidence interaction = (InteractionEvidence)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        // participants
        Assert.assertEquals(3, interaction.getParticipants().size());
        Iterator<ParticipantEvidence> pIterator = interaction.getParticipants().iterator();
        pIterator.next();
        pIterator.next();
        ParticipantEvidence p3 = pIterator.next();
        Assert.assertEquals(2, p3.getFeatures().size());
        Iterator<FeatureEvidence> fIterator = p3.getFeatures().iterator();
        fIterator.next();
        FeatureEvidence f2 = fIterator.next();

        Assert.assertEquals(1, f2.getParameters().size());
        Parameter param = f2.getParameters().iterator().next();
        Assert.assertEquals("kd", param.getType().getShortName());
        Assert.assertEquals(new ParameterValue(new BigDecimal("150.0"),(short)10,(short)-9), param.getValue());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_abstract() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/CI-example_1_allostery_abstract.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        ExtendedPsiXmlModelledInteraction interaction = (ExtendedPsiXmlModelledInteraction)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        Assert.assertNotNull(interaction.getInteractionType());

        Assert.assertNotNull(interaction.getOrganism());
        Assert.assertEquals(9606, interaction.getOrganism().getTaxId());

        Assert.assertNotNull(interaction.getInteractorType());
        Assert.assertEquals("protein complex", interaction.getInteractorType().getShortName());

        Assert.assertNotNull(interaction.getEvidenceType());
        Assert.assertEquals("experimental evidence", interaction.getEvidenceType().getShortName());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Assert.assertEquals(1, interaction.getBindingFeatures().size());
        List<BindingFeatures> bindingF = interaction.getBindingFeatures();

        Assert.assertEquals(2, bindingF.iterator().next().getLinkedFeatures().size());
        Assert.assertTrue(bindingF.iterator().next().getLinkedFeatures().iterator().next() instanceof XmlModelledFeature);

        Assert.assertEquals(1, interaction.getCooperativeEffects().size());
        Allostery allostery = (Allostery)interaction.getCooperativeEffects().iterator().next();
        Assert.assertEquals(1, allostery.getCooperativityEvidences().size());
        CooperativityEvidence ev = allostery.getCooperativityEvidences().iterator().next();
        Assert.assertEquals("18498752", ev.getPublication().getPubmedId());
        Assert.assertEquals(2, ev.getEvidenceMethods().size());
        Assert.assertEquals("x-ray diffraction", ev.getEvidenceMethods().iterator().next().getShortName());

        Assert.assertEquals(1, allostery.getAffectedInteractions().size());
        Assert.assertTrue(allostery.getAffectedInteractions().iterator().next() instanceof ExtendedPsiXmlModelledInteraction);

        Assert.assertNotNull(allostery.getOutCome());
        Assert.assertEquals("positive cooperative effect", allostery.getOutCome().getShortName());
        Assert.assertNotNull(allostery.getResponse());
        Assert.assertEquals("allosteric k-type response", allostery.getResponse().getShortName());
        Assert.assertNotNull(allostery.getAllostericMechanism());
        Assert.assertEquals("allosteric change in structure", allostery.getAllostericMechanism().getShortName());
        Assert.assertNotNull(allostery.getAllosteryType());
        Assert.assertEquals("heterotropic allostery", allostery.getAllosteryType().getShortName());

        Assert.assertTrue(allostery.getAllostericMolecule() instanceof XmlModelledParticipant);
        Assert.assertTrue(((MoleculeEffector)allostery.getAllostericEffector()).getMolecule() instanceof XmlModelledParticipant);

        // check feature role
        interaction = (ExtendedPsiXmlModelledInteraction)parser.parseNextInteraction();
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ModelledParticipant> pIterator = interaction.getParticipants().iterator();
        pIterator.next();

        ModelledParticipant p2 = pIterator.next();
        Assert.assertEquals(2, p2.getFeatures().size());
        Iterator<ModelledFeature> fIterator = p2.getFeatures().iterator();
        fIterator.next();
        ModelledFeature f2 = fIterator.next();

        Assert.assertNotNull(f2.getRole());
        Assert.assertEquals(CvTermUtils.createMICvTerm("prerequisite-ptm","MI:0638"), f2.getRole());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }


    @Test
    public void test_read_valid_xml30_preassembly() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/CI-example_2_preassembly_abstract.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        ExtendedPsiXmlModelledInteraction interaction = (ExtendedPsiXmlModelledInteraction)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        Assert.assertNotNull(interaction.getInteractionType());

        Assert.assertNotNull(interaction.getOrganism());
        Assert.assertEquals(9606, interaction.getOrganism().getTaxId());

        Assert.assertNotNull(interaction.getInteractorType());
        Assert.assertEquals("complex", interaction.getInteractorType().getShortName());

        Assert.assertNotNull(interaction.getEvidenceType());
        Assert.assertEquals("experimental evidence", interaction.getEvidenceType().getShortName());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Assert.assertEquals(0, interaction.getBindingFeatures().size());

        Assert.assertEquals(2, interaction.getCooperativeEffects().size());
        Preassembly preassembly = (Preassembly)interaction.getCooperativeEffects().iterator().next();
        Assert.assertEquals(1, preassembly.getCooperativityEvidences().size());
        CooperativityEvidence ev = preassembly.getCooperativityEvidences().iterator().next();
        Assert.assertEquals("20037628", ev.getPublication().getPubmedId());
        Assert.assertEquals(3, ev.getEvidenceMethods().size());
        Assert.assertEquals("itc", ev.getEvidenceMethods().iterator().next().getShortName());

        Assert.assertEquals(1, preassembly.getAffectedInteractions().size());
        Assert.assertTrue(preassembly.getAffectedInteractions().iterator().next() instanceof ExtendedPsiXmlModelledInteraction);

        Assert.assertNotNull(preassembly.getOutCome());
        Assert.assertEquals("negative cooperative effect", preassembly.getOutCome().getShortName());
        Assert.assertNotNull(preassembly.getResponse());
        Assert.assertEquals("altered physicochemical compatibility", preassembly.getResponse().getShortName());

        // check parameters
        interaction = (ExtendedPsiXmlModelledInteraction)parser.parseNextInteraction();

        Assert.assertEquals(1, interaction.getModelledParameters().size());
        ModelledParameter parame = interaction.getModelledParameters().iterator().next();
        Assert.assertNotNull(parame.getPublication());
        Assert.assertEquals("20037628", parame.getPublication().getPubmedId());
        Assert.assertEquals("Kd", parame.getType().getShortName());
        Assert.assertEquals("M", parame.getUnit().getShortName());

        Assert.assertFalse(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_complex_binding_site() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/CI-example_3_p27-Cks1-Skp2_abstract.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);
        parser.parseNextInteraction();

        ExtendedPsiXmlModelledInteraction interaction = (ExtendedPsiXmlModelledInteraction)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        ModelledParticipant p1 = interaction.getParticipants().iterator().next();

        Assert.assertEquals(1, p1.getFeatures().size());
        Assert.assertEquals(2, p1.getFeatures().iterator().next().getRanges().size());
        Range range = p1.getFeatures().iterator().next().getRanges().iterator().next();

        Assert.assertTrue(range.getParticipant() instanceof XmlModelledParticipant);

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml30_variable_parameters() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = XmlEvidenceParserTest.class.getResourceAsStream("/samples/xml30/several_variable_parameters.xml");

        PsiXmlParser<Interaction> parser = new XmlParser(stream);

        psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlInteractionEvidence interaction =
                (psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlInteractionEvidence)parser.parseNextInteraction();
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());

        Assert.assertNotNull(interaction);

        // variable parameters experiment
        Assert.assertNotNull(interaction.getExperiment());
        Assert.assertEquals(2, interaction.getExperiment().getVariableParameters().size());
        VariableParameter p1 = interaction.getExperiment().getVariableParameters().iterator().next();
        Assert.assertEquals("Curdlan (ug/ml)", p1.getDescription());
        Assert.assertEquals(p1.getExperiment(), interaction.getExperiment());
        Assert.assertEquals(2, p1.getVariableValues().size());
        VariableParameterValue v1 = p1.getVariableValues().iterator().next();
        Assert.assertEquals("10", v1.getValue());
        Assert.assertNull(v1.getOrder());
        Assert.assertNotNull(p1.getUnit());
        Assert.assertEquals("ug/ml", p1.getUnit().getShortName());

        // participants
        Assert.assertEquals(5, interaction.getParticipants().size());

        // variable parameter values
        Assert.assertEquals(1, interaction.getVariableParameterValues().size());
        VariableParameterValueSet set = interaction.getVariableParameterValues().iterator().next();
        Assert.assertEquals(2, set.size());
        Iterator<VariableParameterValue> vIterator = set.iterator();
        VariableParameterValue v11 = vIterator.next();
        VariableParameterValue v12 = vIterator.next();
        Assert.assertTrue(v1 == v11 || v1 == v12);

        // check causal relationships
        Assert.assertEquals(1, interaction.getCausalRelationships().size());
        ExtendedPsiXmlCausalRelationship c = interaction.getCausalRelationships().iterator().next();
        Assert.assertTrue(c.getSource() == interaction.getParticipants().iterator().next());
        Assert.assertNotNull(c.getRelationType());
        Assert.assertEquals("increases RNA expression of ", c.getRelationType().getShortName());
        Assert.assertTrue(c.getTarget() instanceof XmlParticipantEvidence);

        Assert.assertEquals(1, c.getSource().getCausalRelationships().size());
        Assert.assertTrue(c == c.getSource().getCausalRelationships().iterator().next());

        Assert.assertFalse(parser.hasFinished());

        parser.close();
    }
}
