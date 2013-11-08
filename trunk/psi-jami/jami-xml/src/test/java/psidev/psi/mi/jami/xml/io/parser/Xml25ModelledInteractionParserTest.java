package psidev.psi.mi.jami.xml.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interaction;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Participant;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * Unit tester for Xml25ModelledInteractionParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/11/13</pre>
 */

public class Xml25ModelledInteractionParserTest {

    @Test
    public void test_read_valid_xml25_compact() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915.xml");

        PsiXml25Parser<ModelledInteraction> parser = new Xml25ModelledInteractionParser(stream);

        ModelledInteraction interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertEquals(2, interaction.getIdentifiers().size());

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

        // source
        Assert.assertNotNull(interaction.getSource());
        Source source = interaction.getSource();
        Assert.assertEquals("MINT", source.getShortName());
        Assert.assertEquals("MINT, Dpt of Biology, University of Rome Tor Vergata", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0471", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(0, source.getXrefs().size());
        Assert.assertEquals(2, source.getAnnotations().size());
        Assert.assertEquals("http://mint.bio.uniroma2.it/mint", source.getUrl());

        // attributes
        Assert.assertEquals(1, interaction.getAnnotations().size());
        Annotation comment = interaction.getAnnotations().iterator().next();
        Assert.assertEquals("comment", comment.getTopic().getShortName());
        Assert.assertEquals("MI:0612", comment.getTopic().getMIIdentifier());
        Assert.assertEquals("mint", comment.getValue());

        // boolean
        Assert.assertFalse(((ExtendedPsi25Interaction) interaction).isIntraMolecular());
        // interaction type
        Assert.assertNotNull(interaction.getInteractionType());
        CvTerm method = interaction.getInteractionType();
        Assert.assertEquals("physical association", method.getShortName());
        Assert.assertEquals("physical association", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0915", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ModelledParticipant> partIterator = interaction.getParticipants().iterator();
        ModelledParticipant p1 = partIterator.next();
        Assert.assertEquals("rad53_yeast", ((ExtendedPsi25Participant) p1).getShortLabel());
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
        // features
        Assert.assertEquals(1, p1.getFeatures().size());
        ModelledFeature f = p1.getFeatures().iterator().next();
        Assert.assertEquals("tagged molecule", f.getShortName());
        Assert.assertEquals(2, f.getIdentifiers().size());
        Assert.assertEquals(0, f.getXrefs().size());
        Assert.assertNotNull(f.getType());
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

        Assert.assertEquals(1, interaction.getModelledConfidences().size());
        ModelledConfidence conf = interaction.getModelledConfidences().iterator().next();
        Assert.assertEquals("intact-miscore", conf.getType().getShortName());
        Assert.assertEquals("0.8", conf.getValue());
        Assert.assertEquals(1, interaction.getModelledParameters().size());
        Parameter param = interaction.getModelledParameters().iterator().next();
        Assert.assertEquals("kd", param.getType().getShortName());
        Assert.assertEquals("5", param.getValue().toString());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_expanded() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915-expanded.xml");

        PsiXml25Parser<ModelledInteraction> parser = new Xml25ModelledInteractionParser(stream);

        ModelledInteraction interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertEquals(2, interaction.getIdentifiers().size());

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

        // source
        Assert.assertNotNull(interaction.getSource());
        Source source = interaction.getSource();
        Assert.assertEquals("MINT", source.getShortName());
        Assert.assertEquals("MINT, Dpt of Biology, University of Rome Tor Vergata", source.getFullName());
        Assert.assertEquals(0, source.getSynonyms().size());
        Assert.assertEquals("MI:0471", source.getMIIdentifier());
        Assert.assertEquals(2, source.getIdentifiers().size());
        Assert.assertEquals(0, source.getXrefs().size());
        Assert.assertEquals(2, source.getAnnotations().size());
        Assert.assertEquals("http://mint.bio.uniroma2.it/mint", source.getUrl());

        // attributes
        Assert.assertEquals(1, interaction.getAnnotations().size());
        Annotation comment = interaction.getAnnotations().iterator().next();
        Assert.assertEquals("comment", comment.getTopic().getShortName());
        Assert.assertEquals("MI:0612", comment.getTopic().getMIIdentifier());
        Assert.assertEquals("mint", comment.getValue());

        // boolean
        Assert.assertFalse(((ExtendedPsi25Interaction) interaction).isIntraMolecular());
        // interaction type
        Assert.assertNotNull(interaction.getInteractionType());
        CvTerm method = interaction.getInteractionType();
        Assert.assertEquals("physical association", method.getShortName());
        Assert.assertEquals("physical association", method.getFullName());
        Assert.assertEquals(0, method.getSynonyms().size());
        Assert.assertEquals("MI:0915", method.getMIIdentifier());
        Assert.assertEquals(2, method.getIdentifiers().size());
        Assert.assertEquals(1, method.getXrefs().size());

        // participants
        Assert.assertEquals(2, interaction.getParticipants().size());
        Iterator<ModelledParticipant> partIterator = interaction.getParticipants().iterator();
        ModelledParticipant p1 = partIterator.next();
        Assert.assertEquals("rad53_yeast", ((ExtendedPsi25Participant) p1).getShortLabel());
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
        // features
        Assert.assertEquals(1, p1.getFeatures().size());
        ModelledFeature f = p1.getFeatures().iterator().next();
        Assert.assertEquals("tagged molecule", f.getShortName());
        Assert.assertEquals(2, f.getIdentifiers().size());
        Assert.assertEquals(0, f.getXrefs().size());
        Assert.assertNotNull(f.getType());
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

        Assert.assertEquals(1, interaction.getModelledConfidences().size());
        ModelledConfidence conf = interaction.getModelledConfidences().iterator().next();
        Assert.assertEquals("intact-miscore", conf.getType().getShortName());
        Assert.assertEquals("0.8", conf.getValue());
        Assert.assertEquals(1, interaction.getModelledParameters().size());
        Parameter param = interaction.getModelledParameters().iterator().next();
        Assert.assertEquals("kd", param.getType().getShortName());
        Assert.assertEquals("5", param.getValue().toString());

        Assert.assertTrue(parser.hasFinished());

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_inferred() throws PsiXmlParserException, JAXBException, XMLStreamException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/21703451.xml");

        PsiXml25Parser<ModelledInteraction> parser = new Xml25ModelledInteractionParser(stream);

        int index = 0;
        while(!parser.hasFinished()){
            ModelledInteraction interaction = parser.parseNextInteraction();
            Assert.assertNotNull(interaction);
            Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
            if (index == 1){
                Iterator<ModelledParticipant> pIterator = interaction.getParticipants().iterator();
                ModelledParticipant p1 = pIterator.next();
                ModelledFeature f1 = p1.getFeatures().iterator().next();
                Assert.assertEquals(1, f1.getLinkedFeatures().size());
                ModelledParticipant p2 = pIterator.next();
                ModelledFeature f2 = p2.getFeatures().iterator().next();
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
        PsiXml25Parser<ModelledInteraction> parser = new Xml25ModelledInteractionParser(stream);
        int index = 0;
        while(!parser.hasFinished()){
            ModelledInteraction interaction = parser.parseNextInteraction();
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

        PsiXml25Parser<ModelledInteraction> parser = new Xml25ModelledInteractionParser(stream);

        ModelledInteraction interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertNotNull(((FileSourceContext)interaction).getSourceLocator());
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());

        // source
        Assert.assertNotNull(interaction.getSource());
        Source source = interaction.getSource();
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
        Iterator<ModelledParticipant> partIterator = interaction.getParticipants().iterator();
        ModelledParticipant p1 = partIterator.next();
        Assert.assertEquals("rad53_yeast", ((ExtendedPsi25Participant) p1).getShortLabel());
        // features
        Assert.assertEquals(1, p1.getFeatures().size());
        ModelledFeature f = p1.getFeatures().iterator().next();
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

        // source
        Assert.assertNotNull(interaction.getSource());
        source = interaction.getSource();
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
        Assert.assertEquals("n/a", ((ExtendedPsi25Participant) p1).getShortLabel());
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
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/empty.xml");
        PsiXml25Parser<ModelledInteraction> parser = new Xml25ModelledInteractionParser(stream);

        ModelledInteraction interaction = parser.parseNextInteraction();

        // read first interaction
        Assert.assertNull(interaction);
        Assert.assertTrue(parser.hasFinished());
    }
}
