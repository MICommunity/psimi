package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import java.util.*;

/**
 * Unit tester for AliasUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class AliasUtilsTest {

    @Test
    public void test_alias_null_does_not_have_alias_type(){

        Assert.assertFalse(AliasUtils.doesAliasHaveType(null, "MI:xxxx", "synonym"));
        Assert.assertFalse(AliasUtils.doesAliasHaveType(new DefaultAlias("bla"), null, null));
    }

    @Test
    public void test_alias_type_null_does_not_have_alias_type(){

        Assert.assertFalse(AliasUtils.doesAliasHaveType(new DefaultAlias("test name"), "MI:xxxx", "synonym"));
    }

    @Test
    public void test_retrieve_alias_type_with_identifier(){

        CvTerm type = CvTermUtils.createGeneNameAliasType();
        Alias alias = new DefaultAlias(type, "test_name");

        Assert.assertTrue(AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, null));
        Assert.assertFalse(AliasUtils.doesAliasHaveType(alias, Alias.SYNONYM_MI, null));
    }

    @Test
    public void test_retrieve_alias_type_with_name(){

        CvTerm type = CvTermUtils.createGeneNameAliasType();
        Alias alias = new DefaultAlias(type, "test_name");

        Assert.assertTrue(AliasUtils.doesAliasHaveType(alias, null, Alias.GENE_NAME));
        Assert.assertFalse(AliasUtils.doesAliasHaveType(alias, null, Alias.SYNONYM));
    }

    @Test
    public void test_retrieve_alias_type_with_identifier_ignore_name(){

        CvTerm type = CvTermUtils.createMICvTerm("synonym", Alias.GENE_NAME_MI);
        Alias alias = new DefaultAlias(type, "test_name");

        Assert.assertTrue(AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME));
        Assert.assertFalse(AliasUtils.doesAliasHaveType(alias, Alias.SYNONYM_MI, Alias.SYNONYM));
    }

    @Test
    public void test_retrieve_alias_type_with_name_no_identifier(){

        CvTerm type = new DefaultCvTerm(Alias.GENE_NAME);
        Alias alias = new DefaultAlias(type, "test_name");

        Assert.assertTrue(AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME_MI, Alias.GENE_NAME));
        Assert.assertFalse(AliasUtils.doesAliasHaveType(alias, Alias.GENE_NAME, Alias.SYNONYM));
    }

    @Test
    public void test_collect_aliases_empty_input(){

        Assert.assertTrue(AliasUtils.collectAllAliasesHavingType(Collections.EMPTY_LIST, Alias.GENE_NAME_MI, Alias.GENE_NAME).isEmpty());
    }

    @Test
    public void test_collect_aliases(){
        CvTerm type_name_and_id = CvTermUtils.createGeneNameAliasType();
        CvTerm type_id_wrong_name = CvTermUtils.createMICvTerm("synonym", Alias.GENE_NAME_MI);
        CvTerm type_name_no_id = new DefaultCvTerm(Alias.GENE_NAME);
        CvTerm type2 = CvTermUtils.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI);
        Alias alias1 = new DefaultAlias(type_name_and_id, "test_name 1");
        Alias alias2 = new DefaultAlias(type_id_wrong_name, "test_name 2");
        Alias alias3 = new DefaultAlias(type_name_no_id, "test_name 3");
        Alias alias4 = new DefaultAlias(type2, "test_name 4");

        List<Alias> aliases = Arrays.asList(alias1, alias2, alias3, alias4);

        Collection<Alias> selection = AliasUtils.collectAllAliasesHavingType(aliases, Alias.GENE_NAME_MI, Alias.GENE_NAME);
        Assert.assertEquals(3, selection.size());
        Assert.assertFalse(selection.contains(alias4));

        Collection<Alias> selection2 = AliasUtils.collectAllAliasesHavingType(aliases, Alias.SYNONYM_MI, Alias.SYNONYM);
        Assert.assertEquals(1, selection2.size());
        Assert.assertTrue(selection2.contains(alias4));
    }

    @Test
    public void test_retrieve_first_alias_with_type(){
        CvTerm type_name_and_id = CvTermUtils.createGeneNameAliasType();
        CvTerm type_id_wrong_name = CvTermUtils.createMICvTerm("synonym", Alias.GENE_NAME_MI);
        CvTerm type_name_no_id = new DefaultCvTerm(Alias.GENE_NAME);
        CvTerm type2 = CvTermUtils.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI);
        Alias alias1 = new DefaultAlias(type_name_and_id, "test_name 1");
        Alias alias2 = new DefaultAlias(type_id_wrong_name, "test_name 2");
        Alias alias3 = new DefaultAlias(type_name_no_id, "test_name 3");
        Alias alias4 = new DefaultAlias(type2, "test_name 4");

        List<Alias> aliases = Arrays.asList(alias1, alias2, alias3, alias4);

        Alias selection = AliasUtils.collectFirstAliasWithType(aliases, Alias.GENE_NAME_MI, Alias.GENE_NAME);
        Assert.assertNotNull(selection);
        Assert.assertEquals(alias1, selection);

        Alias selection2 = AliasUtils.collectFirstAliasWithType(aliases, Alias.SYNONYM_MI, Alias.SYNONYM);
        Assert.assertNotNull(selection2);
        Assert.assertEquals(alias4, selection2);
    }

    @Test
    public void test_remove_all_aliases_with_type(){
        CvTerm type_name_and_id = CvTermUtils.createGeneNameAliasType();
        CvTerm type_id_wrong_name = CvTermUtils.createMICvTerm("synonym", Alias.GENE_NAME_MI);
        CvTerm type_name_no_id = new DefaultCvTerm(Alias.GENE_NAME);
        CvTerm type2 = CvTermUtils.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI);
        Alias alias1 = new DefaultAlias(type_name_and_id, "test_name 1");
        Alias alias2 = new DefaultAlias(type_id_wrong_name, "test_name 2");
        Alias alias3 = new DefaultAlias(type_name_no_id, "test_name 3");
        Alias alias4 = new DefaultAlias(type2, "test_name 4");

        List<Alias> aliases = new ArrayList<Alias>(Arrays.asList(alias1, alias2, alias3, alias4));

        AliasUtils.removeAllAliasesWithType(aliases, Alias.GENE_NAME_MI, Alias.GENE_NAME);
        Assert.assertEquals(1, aliases.size());
        Assert.assertTrue(aliases.contains(alias4));
    }
}
