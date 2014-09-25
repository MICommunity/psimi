package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of CellLineXRefRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class CellLineXRefRuleTest extends AbstractRuleTest {

    @Test
    public void test_cellLine_has_CABRI() throws ValidatorException {
        CvTerm cellLine = new DefaultCvTerm("test cell line");
        Xref cabri = XrefUtils.createXrefWithQualifier(RuleUtils.CABRI, RuleUtils.CABRI_MI_REF, "test", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF);

        cellLine.getIdentifiers().add(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        Organism or = new DefaultOrganism(-1);
        or.setCellType(cellLine);
        final Collection<ValidatorMessage> messages = rule.check( or );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_cellLine_has_CELL_ONTOLOGY() throws ValidatorException {
        CvTerm cellLine = new DefaultCvTerm("test cell line");
        Xref cabri = XrefUtils.createXrefWithQualifier(RuleUtils.CELL_ONTOLOGY, RuleUtils.CELL_ONTOLOGY_MI_REF, "test", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF);

        cellLine.getIdentifiers().add(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        Organism or = new DefaultOrganism(-1);
        or.setCellType(cellLine);
        final Collection<ValidatorMessage> messages = rule.check( or );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_cellLine_has_PUBMED_PRIMARY() throws ValidatorException {
        CvTerm cellLine = new DefaultCvTerm("test cell line");
        Xref cabri = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "12345", "primary-reference", "MI:0358");

        cellLine.getXrefs().add(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        Organism or = new DefaultOrganism(-1);
        or.setCellType(cellLine);
        final Collection<ValidatorMessage> messages = rule.check( or );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_cellLine_no_xRef() throws ValidatorException {
        CvTerm cellLine = new DefaultCvTerm("test cell line");

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        Organism or = new DefaultOrganism(-1);
        or.setCellType(cellLine);
        final Collection<ValidatorMessage> messages = rule.check( or );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_cellLine_has_other_xref() throws ValidatorException {
        CvTerm cellLine = new DefaultCvTerm("test cell line");
        Xref cabri = XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "12345", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF);

        cellLine.getXrefs().add(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);
        Organism or = new DefaultOrganism(-1);
        or.setCellType(cellLine);
        final Collection<ValidatorMessage> messages = rule.check( or );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
