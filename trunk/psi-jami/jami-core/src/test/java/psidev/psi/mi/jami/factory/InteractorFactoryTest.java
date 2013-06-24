package psidev.psi.mi.jami.factory;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.util.Arrays;

/**
 * Unit tester for InteractorFactory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class InteractorFactoryTest {

    private InteractorFactory interactorFactory = new InteractorFactory();

    @Test
    public void test_interactor_type_null(){
        Assert.assertNull(interactorFactory.createInteractorFromInteractorType(null, "test interactor"));
    }

    @Test
    public void test_interactor_type_not_recognized(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createUnknownInteractorType(), "test interactor")
                instanceof DefaultInteractor);
    }

    @Test
    public void test_recognize_small_molecule(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createMICvTerm(BioactiveEntity.SMALL_MOLECULE, BioactiveEntity.SMALL_MOLECULE_MI), "test interactor")
                instanceof DefaultBioactiveEntity);
    }

    @Test
    public void test_recognize_polymer(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createPolymerInteractorType(), "test interactor")
                instanceof DefaultPolymer);
    }

    @Test
    public void test_recognize_nucleic_acid(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(new DefaultCvTerm("crna"), "test interactor")
                instanceof DefaultNucleicAcid);
    }

    @Test
    public void test_recognize_protein(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createProteinInteractorType(), "test interactor")
                instanceof DefaultProtein);
    }

    @Test
    public void test_recognize_complex_identifier_only(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createMICvTerm("crna", "MI:0317"), "test interactor")
                instanceof DefaultComplex);
    }

    @Test
    public void test_recognize_gene(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createGeneInteractorType(), "test interactor")
                instanceof DefaultGene);
    }

    @Test
    public void test_recognize_interactor_set(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(new DefaultCvTerm("molecule set"), "test interactor")
                instanceof DefaultInteractorSet);
    }

    @Test
    public void test_recognize_small_molecule_from_database(){
        Assert.assertTrue(interactorFactory.createInteractorFromDatabase(CvTermUtils.createMICvTerm("beilstein", "MI:1075"), "test interactor")
                instanceof DefaultBioactiveEntity);
    }

    @Test
    public void test_recognize_gene_from_database(){
        Assert.assertTrue(interactorFactory.createInteractorFromDatabase(CvTermUtils.createMICvTerm("test", "MI:1095"), "test interactor")
                instanceof DefaultGene);
    }

    @Test
    public void test_recognize_polymer_from_database(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(CvTermUtils.createMICvTerm("huge","MI:0249"), "test interactor")
                instanceof DefaultPolymer);
    }

    @Test
    public void test_recognize_nucleic_acid_from_database(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(new DefaultCvTerm("genbank_nucl_gi"), "test interactor")
                instanceof DefaultNucleicAcid);
    }

    @Test
    public void test_recognize_protein_from_database(){
        Assert.assertTrue(interactorFactory.createInteractorFromInteractorType(new DefaultCvTerm("genbank_protein_gi"), "test interactor")
                instanceof DefaultProtein);
    }

    @Test
    public void test_recognize_small_molecule_from_xref(){
        Assert.assertTrue(interactorFactory.createInteractorFromIdentityXrefs(Arrays.asList(XrefUtils.createChebiIdentity("CHEBI:xxxx"), XrefUtils.createEnsemblIdentity("ENSEMBL-xxxx")), "test interactor")
                instanceof DefaultBioactiveEntity);
    }

    @Test
    public void test_recognize_gene_from_xref(){
        Assert.assertTrue(interactorFactory.createInteractorFromIdentityXrefs(Arrays.asList(XrefUtils.createEnsemblIdentity("ENSEMBL-xxxx")), "test interactor")
                instanceof DefaultGene);
    }
}
