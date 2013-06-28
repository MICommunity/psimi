package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.tab.io.parser.InteractionEvidenceLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;
import psidev.psi.mi.jami.utils.CvTermUtils;

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
    public void test_read_valid_mitab27() throws ParseException {
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

        Experiment experiment = binary.getExperiment();
        Assert.assertNotNull(experiment);
        Assert.assertEquals(CvTermUtils.createMICvTerm("anti tag coimmunoprecipitation", "MI:0007"), experiment.getInteractionDetectionMethod());
        Publication publication = experiment.getPublication();
        Assert.assertNotNull(publication);
        Assert.assertEquals(1, publication.getAuthors().size());
        Assert.assertEquals("Shimazu", publication.getAuthors().iterator().next());

        InteractionEvidence binary2 = parser.MitabLine();
        Assert.assertNotNull(binary2);
        Assert.assertTrue(parser.hasFinished());
    }
}
