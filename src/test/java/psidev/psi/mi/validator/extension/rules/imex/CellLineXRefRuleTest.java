package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.CellType;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Xref;
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
        CellType cellLine = new CellType();
        Xref cabri = new Xref();
        cabri.setPrimaryRef(new DbReference(RuleUtils.CABRI, RuleUtils.CABRI_MI_REF, "test", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF));

        cellLine.setXref(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( cellLine );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_cellLine_has_CELL_ONTOLOGY() throws ValidatorException {
        CellType cellLine = new CellType();
        Xref cabri = new Xref();
        cabri.setPrimaryRef(new DbReference(RuleUtils.CELL_ONTOLOGY, RuleUtils.CELL_ONTOLOGY_MI_REF, "test", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF));

        cellLine.setXref(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( cellLine );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_cellLine_has_PUBMED_PRIMARY() throws ValidatorException {
        CellType cellLine = new CellType();
        Xref cabri = new Xref();
        cabri.setPrimaryRef(new DbReference("pubmed", "MI:0446", "12345", "primary-reference", "MI:0358"));

        cellLine.setXref(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( cellLine );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_cellLine_no_xRef() throws ValidatorException {
        CellType cellLine = new CellType();

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( cellLine );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_cellLine_has_other_xref() throws ValidatorException {
        CellType cellLine = new CellType();
        Xref cabri = new Xref();
        cabri.setPrimaryRef(new DbReference("pubmed", "MI:0446", "12345", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF));

        cellLine.setXref(cabri);

        CellLineXrefRule rule = new CellLineXrefRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( cellLine );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
