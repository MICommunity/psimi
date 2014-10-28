package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultBioactiveEntity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class DefaultBioactiveEntityTest {

    @Test
    public void create_bioactive_entity_with_shortname(){

        BioactiveEntity entity = new DefaultBioactiveEntity("test");

        Assert.assertEquals(CvTermUtils.createBioactiveEntityType(), entity.getInteractorType());
        Assert.assertEquals("test", entity.getShortName());
        Assert.assertNotNull(entity.getAliases());
        Assert.assertNotNull(entity.getIdentifiers());
        Assert.assertNotNull(entity.getXrefs());
        Assert.assertNotNull(entity.getAnnotations());
        Assert.assertNotNull(entity.getChecksums());
        Assert.assertNull(entity.getOrganism());

        entity = new DefaultBioactiveEntity("test", (String) null);
        Assert.assertEquals(CvTermUtils.createBioactiveEntityType(), entity.getInteractorType());
    }

    @Test
    public void create_small_molecule_set_interactor_type_null(){

        BioactiveEntity interactor = new DefaultBioactiveEntity("test", CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createBioactiveEntityType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());
    }

    @Test
    public void test_set_unset_chebi(){

        BioactiveEntity interactor = new DefaultBioactiveEntity("test");
        Assert.assertNull(interactor.getChebi());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setChebi("CHEBI:xxx");
        Assert.assertEquals("CHEBI:xxx", interactor.getChebi());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xxx"), interactor.getIdentifiers().iterator().next());
        Assert.assertEquals(0, interactor.getXrefs().size());

        interactor.setChebi("CHEBI:xx2");
        Assert.assertEquals("CHEBI:xx2", interactor.getChebi());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xx2"), interactor.getIdentifiers().iterator().next());

        interactor.getIdentifiers().clear();
        Assert.assertNull(interactor.getChebi());
        Assert.assertEquals(0, interactor.getIdentifiers().size());

        interactor.getIdentifiers().add(XrefUtils.createXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xx2"));
        Assert.assertEquals("CHEBI:xx2", interactor.getChebi());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        interactor.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.CHEBI, Xref.CHEBI_MI, "CHEBI:xx3"));
        Assert.assertEquals("CHEBI:xx3", interactor.getChebi());
        Assert.assertEquals(2, interactor.getIdentifiers().size());

        interactor.setChebi(null);
        Assert.assertNull(interactor.getChebi());
        Assert.assertEquals(0, interactor.getIdentifiers().size());
    }

    @Test
    public void test_set_unset_smile(){

        BioactiveEntity interactor = new DefaultBioactiveEntity("test");
        Assert.assertNull(interactor.getSmile());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.setSmile("xxxx1");
        Assert.assertEquals("xxxx1", interactor.getSmile());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createSmile("xxxx1"), interactor.getChecksums().iterator().next());

        interactor.setSmile("xxxx2");
        Assert.assertEquals("xxxx2", interactor.getSmile());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createSmile("xxxx2"), interactor.getChecksums().iterator().next());

        interactor.getChecksums().clear();
        Assert.assertNull(interactor.getSmile());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createSmile("xxxx2"));
        Assert.assertEquals("xxxx2", interactor.getSmile());
        Assert.assertEquals(1, interactor.getChecksums().size());
        interactor.getChecksums().add(ChecksumUtils.createSmile("xxxx3"));
        Assert.assertEquals("xxxx2", interactor.getSmile());
        Assert.assertEquals(2, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createStandardInchi("xxxx3"));
        interactor.setSmile(null);
        Assert.assertNull(interactor.getSmile());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createStandardInchi("xxxx3"), interactor.getChecksums().iterator().next());
    }

    @Test
    public void test_set_unset_standard_inchi(){

        BioactiveEntity interactor = new DefaultBioactiveEntity("test");
        Assert.assertNull(interactor.getStandardInchi());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.setStandardInchi("xxxx1");
        Assert.assertEquals("xxxx1", interactor.getStandardInchi());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createStandardInchi("xxxx1"), interactor.getChecksums().iterator().next());

        interactor.setStandardInchi("xxxx2");
        Assert.assertEquals("xxxx2", interactor.getStandardInchi());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createStandardInchi("xxxx2"), interactor.getChecksums().iterator().next());

        interactor.getChecksums().clear();
        Assert.assertNull(interactor.getStandardInchi());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createStandardInchi("xxxx2"));
        Assert.assertEquals("xxxx2", interactor.getStandardInchi());
        Assert.assertEquals(1, interactor.getChecksums().size());
        interactor.getChecksums().add(ChecksumUtils.createStandardInchi("xxxx3"));
        Assert.assertEquals("xxxx2", interactor.getStandardInchi());
        Assert.assertEquals(2, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createSmile("xxxx3"));
        interactor.setStandardInchi(null);
        Assert.assertNull(interactor.getStandardInchi());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createSmile("xxxx3"), interactor.getChecksums().iterator().next());
    }

    @Test
    public void test_set_unset_standard_inchi_key(){

        BioactiveEntity interactor = new DefaultBioactiveEntity("test");
        Assert.assertNull(interactor.getStandardInchiKey());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.setStandardInchiKey("xxxx1");
        Assert.assertEquals("xxxx1", interactor.getStandardInchiKey());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createStandardInchiKey("xxxx1"), interactor.getChecksums().iterator().next());

        interactor.setStandardInchiKey("xxxx2");
        Assert.assertEquals("xxxx2", interactor.getStandardInchiKey());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createStandardInchiKey("xxxx2"), interactor.getChecksums().iterator().next());

        interactor.getChecksums().clear();
        Assert.assertNull(interactor.getStandardInchiKey());
        Assert.assertEquals(0, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createStandardInchiKey("xxxx2"));
        Assert.assertEquals("xxxx2", interactor.getStandardInchiKey());
        Assert.assertEquals(1, interactor.getChecksums().size());
        interactor.getChecksums().add(ChecksumUtils.createStandardInchiKey("xxxx3"));
        Assert.assertEquals("xxxx2", interactor.getStandardInchiKey());
        Assert.assertEquals(2, interactor.getChecksums().size());

        interactor.getChecksums().add(ChecksumUtils.createSmile("xxxx3"));
        interactor.setStandardInchiKey(null);
        Assert.assertNull(interactor.getStandardInchiKey());
        Assert.assertEquals(1, interactor.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createSmile("xxxx3"), interactor.getChecksums().iterator().next());
    }
}
