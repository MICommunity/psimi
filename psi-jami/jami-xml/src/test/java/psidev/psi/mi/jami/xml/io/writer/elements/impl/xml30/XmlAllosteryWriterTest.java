package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlAllosteryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlAllosteryWriterTest extends AbstractXmlWriterTest {
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    private String allostery = "<allostery>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n" +
            "  <allostericMoleculeRef>2</allostericMoleculeRef>\n" +
            "  <allostericEffectorRef>3</allostericEffectorRef>\n" +
            "</allostery>";
    private String allostery_response = "<allostery>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "  <cooperativeEffectResponse>\n" +
            "    <names>\n" +
            "      <shortLabel>binding site hiding</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1173\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectResponse>\n"+
            "  <allostericMoleculeRef>2</allostericMoleculeRef>\n" +
            "  <allostericEffectorRef>3</allostericEffectorRef>\n" +
            "</allostery>";
    private String allostery_attributes = "<allostery>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "  <allostericMoleculeRef>2</allostericMoleculeRef>\n" +
            "  <allostericEffectorRef>3</allostericEffectorRef>\n" +
            "</allostery>";
    private String allostery_already_registered = "<allostery>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>2</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "  <allostericMoleculeRef>3</allostericMoleculeRef>\n" +
            "  <allostericEffectorRef>4</allostericEffectorRef>\n" +
            "</allostery>";
    private String allostery_feature_effector = "<allostery>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n" +
            "  <allostericMoleculeRef>2</allostericMoleculeRef>\n" +
            "  <allostericModificationRef>3</allostericModificationRef>\n" +
            "</allostery>";
    private String allostery_type_mechanism = "<allostery>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n" +
            "  <allostericMoleculeRef>2</allostericMoleculeRef>\n" +
            "  <allostericEffectorRef>3</allostericEffectorRef>\n" +
            "  <allostericMechanism>\n" +
            "    <names>\n" +
            "      <shortLabel>allosteric change in dynamics</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1166\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </allostericMechanism>\n" +
            "  <allosteryType>\n" +
            "    <names>\n" +
            "      <shortLabel>heterotropic allostery</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1168\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </allosteryType>\n" +
            "</allostery>";

    @Test
    public void test_write_allostery() throws XMLStreamException, IOException, IllegalRangeException {
        Allostery effect = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor())));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());

        XmlAllosteryWriter writer = new XmlAllosteryWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(this.allostery, output.toString());
    }

    @Test
    public void test_write_allostery_response() throws XMLStreamException, IOException, IllegalRangeException {
        Allostery effect = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor())));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());
        effect.setResponse(CvTermUtils.createMICvTerm("binding site hiding","MI:1173"));

        XmlAllosteryWriter writer = new XmlAllosteryWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(allostery_response, output.toString());
    }

    @Test
    public void test_write_allostery_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        Allostery effect = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor())));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());
        effect.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        XmlAllosteryWriter writer = new XmlAllosteryWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(allostery_attributes, output.toString());
    }

    @Test
    public void test_write_modelled_interaction_already_registered() throws XMLStreamException {
        ModelledInteraction interaction = new DefaultModelledInteraction();
        this.elementCache.clear();
        this.elementCache.extractIdForInteraction(new DefaultModelledInteraction());
        this.elementCache.extractIdForInteraction(interaction);

        Allostery effect = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor())));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(interaction);

        XmlAllosteryWriter writer = new XmlAllosteryWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(this.allostery_already_registered, output.toString());
    }

    @Test
    public void test_write_allostery_feature_effector() throws XMLStreamException, IOException, IllegalRangeException {
        Allostery effect = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultFeatureModificationEffector(new DefaultModelledFeature()));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());

        XmlAllosteryWriter writer = new XmlAllosteryWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(this.allostery_feature_effector, output.toString());
    }

    @Test
    public void test_write_allostery_type_mechanism() throws XMLStreamException, IOException, IllegalRangeException {
        Allostery effect = new DefaultAllostery(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"),
                new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()),
                new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor())));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());
        effect.setAllosteryType(CvTermUtils.createMICvTerm("heterotropic allostery","MI:1168"));
        effect.setAllostericMechanism(CvTermUtils.createMICvTerm("allosteric change in dynamics","MI:1166"));

        XmlAllosteryWriter writer = new XmlAllosteryWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(this.allostery_type_mechanism, output.toString());
    }
}
