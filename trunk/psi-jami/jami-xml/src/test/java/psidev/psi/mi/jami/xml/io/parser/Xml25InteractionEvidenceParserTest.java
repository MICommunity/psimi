package psidev.psi.mi.jami.xml.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Unit tester for Xml25InteractionEvidenceParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25InteractionEvidenceParserTest {

    @Test
    public void test_read_valid_xml25() throws JAXBException, XMLStreamException, IOException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/10049915.xml");

        PsiXml25Parser<InteractionEvidence> parser = new Xml25InteractionEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);
        Assert.assertEquals("rad53-dbf4", interaction.getShortName());
        Assert.assertNull(interaction.getImexId());
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

        parser.close();
    }

    @Test
    public void test_read_valid_xml25_inferred() throws JAXBException, XMLStreamException, IOException {
        InputStream stream = Xml25InteractionEvidenceParserTest.class.getResourceAsStream("/samples/21703451.xml");

        PsiXml25Parser<InteractionEvidence> parser = new Xml25InteractionEvidenceParser(stream);

        InteractionEvidence interaction = parser.parseNextInteraction();

        Assert.assertNotNull(interaction);

        parser.close();
    }
}
