package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultCausalRelationship;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlCausalRelationshipWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlCausalRelationshipWriterTest extends AbstractXmlWriterTest {
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    private String causal_relationship = "<causalRelationship>\n" +
            "  <sourceParticipantRef>1</sourceParticipantRef>\n" +
            "  <causalityStatement>\n" +
            "    <names>\n" +
            "      <shortLabel>increases RNA expression of </shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:xxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </causalityStatement>\n"+
            "  <targetParticipantRef>2</targetParticipantRef>\n" +
            "</causalRelationship>";
    private String causal_relationship_registered = "<causalRelationship>\n" +
            "  <sourceParticipantRef>2</sourceParticipantRef>\n" +
            "  <causalityStatement>\n" +
            "    <names>\n" +
            "      <shortLabel>increases RNA expression of </shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:xxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </causalityStatement>\n"+
            "  <targetParticipantRef>3</targetParticipantRef>\n" +
            "</causalRelationship>";

    @Test
    public void test_write_variable_parameter() throws XMLStreamException, IOException, IllegalRangeException {
        CausalRelationship rel = new DefaultCausalRelationship(CvTermUtils.createMICvTerm("increases RNA expression of ","MI:xxxx"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));

        XmlCausalRelationshipWriter writer = new XmlCausalRelationshipWriter(createStreamWriter(), elementCache);
        writer.write(rel, new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        streamWriter.flush();

        Assert.assertEquals(this.causal_relationship, output.toString());
    }

    @Test
    public void test_write_variable_parameter_unit() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant part = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        this.elementCache.clear();
        this.elementCache.extractIdForParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        this.elementCache.extractIdForParticipant(part);

        CausalRelationship rel = new DefaultCausalRelationship(CvTermUtils.createMICvTerm("increases RNA expression of ","MI:xxxx"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));

        XmlCausalRelationshipWriter writer = new XmlCausalRelationshipWriter(createStreamWriter(), elementCache);
        writer.write(rel, part);
        streamWriter.flush();

        Assert.assertEquals(causal_relationship_registered, output.toString());
    }
}
