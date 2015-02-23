package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultNucleicAcid;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultNucleicAcid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class DefaultNucleicAcidTest {

    @Test
    public void create_nucleic_acid_with_shortname(){

        NucleicAcid nucleicAcid = new DefaultNucleicAcid("test");

        Assert.assertEquals(CvTermUtils.createNucleicAcidInteractorType(), nucleicAcid.getInteractorType());
        Assert.assertEquals("test", nucleicAcid.getShortName());
        Assert.assertNotNull(nucleicAcid.getAliases());
        Assert.assertNotNull(nucleicAcid.getIdentifiers());
        Assert.assertNotNull(nucleicAcid.getXrefs());
        Assert.assertNotNull(nucleicAcid.getAnnotations());
        Assert.assertNotNull(nucleicAcid.getChecksums());
        Assert.assertNull(nucleicAcid.getOrganism());

        nucleicAcid = new DefaultNucleicAcid("test", (String) null);
        Assert.assertEquals(CvTermUtils.createNucleicAcidInteractorType(), nucleicAcid.getInteractorType());
    }

    @Test
    public void create_nulceic_acid_set_interactor_type_null(){

        NucleicAcid interactor = new DefaultNucleicAcid("test", CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createNucleicAcidInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());
    }

    @Test
    public void test_set_unset_ddbj_embl_genbank(){

        NucleicAcid interactor = new DefaultNucleicAcid("test");
        Assert.assertNull(interactor.getDdbjEmblGenbank());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setDdbjEmblGenbank("xxx");
        Assert.assertEquals("xxx", interactor.getDdbjEmblGenbank());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, "xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setDdbjEmblGenbank("xx2");
        Assert.assertEquals("xx2", interactor.getDdbjEmblGenbank());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, "xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getDdbjEmblGenbank());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, "xx2"));
        Assert.assertEquals("xx2", interactor.getDdbjEmblGenbank());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_MI, "xx3"));
        Assert.assertEquals("xx3", interactor.getDdbjEmblGenbank());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setDdbjEmblGenbank(null);
        Assert.assertNull(interactor.getDdbjEmblGenbank());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_refseq(){

        NucleicAcid interactor = new DefaultNucleicAcid("test");
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setRefseq("xxx");
        Assert.assertEquals("xxx", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setRefseq("xx2");
        Assert.assertEquals("xx2", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xx2"));
        Assert.assertEquals("xx2", interactor.getRefseq());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.REFSEQ, Xref.REFSEQ_MI, "xx3"));
        Assert.assertEquals("xx3", interactor.getRefseq());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setRefseq(null);
        Assert.assertNull(interactor.getRefseq());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }
}
