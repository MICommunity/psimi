package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;

import java.util.*;

/**
 * Unit tester for XrefUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class XrefUtilsTest {

    @Test
    public void test_if_xref_is_identifier(){

        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.UNIPROTKB, "P12346");
        Xref noQualifier = XrefUtils.createXref(Xref.UNIPROTKB, "P12347");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);

        Assert.assertTrue(XrefUtils.isXrefAnIdentifier(identity));
        Assert.assertTrue(XrefUtils.isXrefAnIdentifier(secondary));
        Assert.assertFalse(XrefUtils.isXrefAnIdentifier(noQualifier));
        Assert.assertFalse(XrefUtils.isXrefAnIdentifier(differentQualifier));
    }

    @Test
    public void test_if_xref_does_have_qualifier(){

        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.UNIPROTKB, "P12346");
        Xref noQualifier = XrefUtils.createXref(Xref.UNIPROTKB, "P12347");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);

        Assert.assertFalse(XrefUtils.doesXrefHaveQualifier(identity, Xref.SEE_ALSO_MI, Xref.SEE_ALSO));
        Assert.assertFalse(XrefUtils.doesXrefHaveQualifier(secondary, Xref.SEE_ALSO_MI, Xref.SEE_ALSO));
        Assert.assertFalse(XrefUtils.doesXrefHaveQualifier(noQualifier, Xref.SEE_ALSO_MI, Xref.SEE_ALSO));
        Assert.assertTrue(XrefUtils.doesXrefHaveQualifier(differentQualifier, Xref.SEE_ALSO_MI, Xref.SEE_ALSO));
    }

    @Test
    public void test_if_xref_does_have_database(){

        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);

        Assert.assertTrue(XrefUtils.isXrefFromDatabase(identity, Xref.UNIPROTKB_MI, Xref.UNIPROTKB));
        Assert.assertFalse(XrefUtils.isXrefFromDatabase(secondary, Xref.UNIPROTKB_MI, Xref.UNIPROTKB));
        Assert.assertFalse(XrefUtils.isXrefFromDatabase(noQualifier, Xref.UNIPROTKB_MI, Xref.UNIPROTKB));
        Assert.assertTrue(XrefUtils.isXrefFromDatabase(differentQualifier, Xref.UNIPROTKB_MI, Xref.UNIPROTKB));
    }

    @Test
    public void test_collect_xrefs_empty_input(){

        Assert.assertTrue(XrefUtils.collectAllIdentifiersFrom(Collections.EMPTY_LIST).isEmpty());
        Assert.assertTrue(XrefUtils.collectAllXrefsHavingQualifier(Collections.EMPTY_LIST, Xref.SEE_ALSO_MI, Xref.SEE_ALSO).isEmpty());
        Assert.assertTrue(XrefUtils.collectAllXrefsHavingDatabase(Collections.EMPTY_LIST, Xref.UNIPROTKB_MI, Xref.UNIPROTKB).isEmpty());
        Assert.assertTrue(XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(Collections.EMPTY_LIST, Xref.UNIPROTKB_MI, Xref.UNIPROTKB, Xref.SEE_ALSO_MI, Xref.SEE_ALSO).isEmpty());
    }

    @Test
    public void test_collect_identifiers(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.UNIPROTKB, "P12346");
        Xref noQualifier = XrefUtils.createXref(Xref.UNIPROTKB, "P12347");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(identity, secondary, noQualifier, differentQualifier);

        Collection<Xref> selection = XrefUtils.collectAllIdentifiersFrom(xrefs);
        Assert.assertEquals(2, selection.size());
        Assert.assertFalse(selection.contains(noQualifier));
        Assert.assertFalse(selection.contains(differentQualifier));
    }

    @Test
    public void test_collect_all_xref_having_qualifier(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.UNIPROTKB, "P12346");
        Xref noQualifier = XrefUtils.createXref(Xref.UNIPROTKB, "P12347");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(identity, secondary, noQualifier, differentQualifier);

        Collection<Xref> selection = XrefUtils.collectAllXrefsHavingQualifier(xrefs, Xref.SEE_ALSO_MI, Xref.SEE_ALSO);
        Assert.assertEquals(1, selection.size());
        Assert.assertTrue(selection.contains(differentQualifier));
    }

    @Test
    public void test_collect_all_xref_having_database(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(identity, secondary, noQualifier, differentQualifier);

        Collection<Xref> selection = XrefUtils.collectAllXrefsHavingDatabase(xrefs, Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        Assert.assertEquals(2, selection.size());
        Assert.assertFalse(selection.contains(secondary));
        Assert.assertFalse(selection.contains(noQualifier));
    }

    @Test
    public void test_collect_all_xref_having_database_and_qualifier(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(identity, secondary, noQualifier, differentQualifier);

        Collection<Xref> selection = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(xrefs, Xref.UNIPROTKB_MI, Xref.UNIPROTKB,Xref.SEE_ALSO_MI, Xref.SEE_ALSO);
        Assert.assertEquals(1, selection.size());
        Assert.assertTrue(selection.contains(differentQualifier));
    }

    @Test
    public void test_collect_all_xref_having_databases_and_qualifiers(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(identity, secondary, noQualifier, differentQualifier);

        Collection<Xref> selection = XrefUtils.searchAllXrefsHavingDatabaseAndQualifier(xrefs, Arrays.asList(Xref.UNIPROTKB_MI, Xref.ENSEMBL_MI), Arrays.asList(Xref.UNIPROTKB, Xref.ENSEMBL) ,Arrays.asList(Xref.SEE_ALSO_MI, Xref.SECONDARY_MI), Arrays.asList(Xref.SEE_ALSO, Xref.SECONDARY));
        Assert.assertEquals(2, selection.size());
        Assert.assertFalse(selection.contains(noQualifier));
        Assert.assertFalse(selection.contains(identity));
    }

    @Test
    public void test_collect_all_xref_having_databases(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(identity, secondary, noQualifier, differentQualifier);

        Collection<Xref> selection = XrefUtils.searchAllXrefsHavingDatabases(xrefs, Arrays.asList(Xref.UNIPROTKB_MI, Xref.ENSEMBL_MI), Arrays.asList(Xref.UNIPROTKB, Xref.ENSEMBL));
        Assert.assertEquals(3, selection.size());
        Assert.assertFalse(selection.contains(noQualifier));
    }

    @Test
    public void test_collect_first_identifier(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = Arrays.asList(secondary, identity, noQualifier, differentQualifier);

        Xref selection = XrefUtils.collectFirstIdentifierWithDatabase(xrefs, Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        Assert.assertEquals(secondary, selection);
    }

    @Test
    public void test_remove_all_xref_having_databases(){
        Xref identity = XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345");
        Xref secondary = XrefUtils.createSecondaryXref(Xref.ENSEMBL, "ENSEMBL-xxx");
        Xref noQualifier = XrefUtils.createXref(Xref.REFSEQ, "xxx");
        Xref differentQualifier = XrefUtils.createXrefWithQualifier(Xref.UNIPROTKB, "P12348", Xref.SEE_ALSO);
        List<Xref> xrefs = new ArrayList<Xref>(Arrays.asList(identity, secondary, noQualifier, differentQualifier));

        XrefUtils.removeAllXrefsWithDatabase(xrefs, Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        Assert.assertEquals(2, xrefs.size());
        Assert.assertFalse(xrefs.contains(identity));
        Assert.assertFalse(xrefs.contains(differentQualifier));
    }
}
