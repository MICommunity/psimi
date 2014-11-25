package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import java.util.*;

/**
 * Unit tester for ChecksumUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class ChecksumUtilsTest {

    @Test
    public void test_checksum_null_does_not_have_method(){

        Assert.assertFalse(ChecksumUtils.doesChecksumHaveMethod(null, "MI:xxxx", "rogid"));
        Assert.assertFalse(ChecksumUtils.doesChecksumHaveMethod(new DefaultChecksum(new DefaultCvTerm("rogid"), "xxxx"), null, null));
    }

    @Test
    public void test_retrieve_checksum_method_with_identifier(){

        CvTerm type = CvTermUtils.createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
        Checksum checksum = new DefaultChecksum(type, "xxxx");

        Assert.assertTrue(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.SMILE_MI, null));
        Assert.assertFalse(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.INCHI_MI, null));
    }

    @Test
    public void test_retrieve_checksum_method_with_name(){

        CvTerm type = CvTermUtils.createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
        Checksum checksum = new DefaultChecksum(type, "xxxx");

        Assert.assertTrue(ChecksumUtils.doesChecksumHaveMethod(checksum, null, Checksum.SMILE));
        Assert.assertFalse(ChecksumUtils.doesChecksumHaveMethod(checksum, null, Checksum.INCHI_MI));
    }

    @Test
    public void test_retrieve_checksum_method_with_identifier_ignore_name(){

        CvTerm type = CvTermUtils.createMICvTerm("standard inchi", Checksum.SMILE_MI);
        Checksum checksum = new DefaultChecksum(type, "xxxx");

        Assert.assertTrue(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.SMILE_MI, Checksum.SMILE));
        Assert.assertFalse(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.INCHI_MI, Checksum.INCHI));
    }

    @Test
    public void test_retrieve_checksum_method_with_name_no_identifier(){

        CvTerm type = new DefaultCvTerm("smiles string");
        Checksum checksum = new DefaultChecksum(type, "xxxx");

        Assert.assertTrue(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.SMILE_MI, Checksum.SMILE));
        Assert.assertFalse(ChecksumUtils.doesChecksumHaveMethod(checksum, Checksum.INCHI_MI, Checksum.INCHI));
    }

    @Test
    public void test_collect_checksum_empty_input(){

        Assert.assertTrue(ChecksumUtils.collectAllChecksumsHavingMethod(Collections.EMPTY_LIST, Checksum.SMILE_MI, Checksum.SMILE).isEmpty());
    }

    @Test
    public void test_collect_checksums(){
        CvTerm method_name_and_id = CvTermUtils.createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
        CvTerm method_id_wrong_name = CvTermUtils.createMICvTerm("standard inchi", Checksum.SMILE_MI);
        CvTerm method_name_no_id = new DefaultCvTerm(Checksum.SMILE);
        CvTerm method2 = CvTermUtils.createMICvTerm(Checksum.INCHI, Checksum.INCHI_MI);
        Checksum checksum1 = new DefaultChecksum(method_name_and_id, "xxxx1");
        Checksum checksum2 = new DefaultChecksum(method_id_wrong_name, "xxxx2");
        Checksum checksum3 = new DefaultChecksum(method_name_no_id, "xxxx3");
        Checksum checksum4 = new DefaultChecksum(method2, "xxxx4");

        List<Checksum> checksums = Arrays.asList(checksum1, checksum2, checksum3, checksum4);

        Collection<Checksum> selection = ChecksumUtils.collectAllChecksumsHavingMethod(checksums, Checksum.SMILE_MI, Checksum.SMILE);
        Assert.assertEquals(3, selection.size());
        Assert.assertFalse(selection.contains(checksum4));

        Collection<Checksum> selection2 = ChecksumUtils.collectAllChecksumsHavingMethod(checksums, Checksum.INCHI_MI, Checksum.INCHI);
        Assert.assertEquals(1, selection2.size());
        Assert.assertTrue(selection2.contains(checksum4));
    }

    @Test
    public void test_retrieve_first_checksum_with_method(){
        CvTerm method_name_and_id = CvTermUtils.createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
        CvTerm method_id_wrong_name = CvTermUtils.createMICvTerm("standard inchi", Checksum.SMILE_MI);
        CvTerm method_name_no_id = new DefaultCvTerm(Checksum.SMILE);
        CvTerm method2 = CvTermUtils.createMICvTerm(Checksum.INCHI, Checksum.INCHI_MI);
        Checksum checksum1 = new DefaultChecksum(method_name_and_id, "xxxx1");
        Checksum checksum2 = new DefaultChecksum(method_id_wrong_name, "xxxx2");
        Checksum checksum3 = new DefaultChecksum(method_name_no_id, "xxxx3");
        Checksum checksum4 = new DefaultChecksum(method2, "xxxx4");

        List<Checksum> checksums = Arrays.asList(checksum1, checksum2, checksum3, checksum4);

        Checksum selection = ChecksumUtils.collectFirstChecksumWithMethod(checksums, Checksum.SMILE_MI, Checksum.SMILE);
        Assert.assertNotNull(selection);
        Assert.assertEquals(checksum1, selection);

        Checksum selection2 = ChecksumUtils.collectFirstChecksumWithMethod(checksums, Checksum.INCHI_MI, Checksum.INCHI);
        Assert.assertNotNull(selection2);
        Assert.assertEquals(checksum4, selection2);
    }

    @Test
    public void test_remove_all_checksums_with_method(){
        CvTerm method_name_and_id = CvTermUtils.createMICvTerm(Checksum.SMILE, Checksum.SMILE_MI);
        CvTerm method_id_wrong_name = CvTermUtils.createMICvTerm("standard inchi", Checksum.SMILE_MI);
        CvTerm method_name_no_id = new DefaultCvTerm(Checksum.SMILE);
        CvTerm method2 = CvTermUtils.createMICvTerm(Checksum.INCHI, Checksum.INCHI_MI);
        Checksum checksum1 = new DefaultChecksum(method_name_and_id, "xxxx1");
        Checksum checksum2 = new DefaultChecksum(method_id_wrong_name, "xxxx2");
        Checksum checksum3 = new DefaultChecksum(method_name_no_id, "xxxx3");
        Checksum checksum4 = new DefaultChecksum(method2, "xxxx4");

        List<Checksum> checksums = new ArrayList<Checksum>(Arrays.asList(checksum1, checksum2, checksum3, checksum4));

        ChecksumUtils.removeAllChecksumWithMethod(checksums, Checksum.SMILE_MI, Checksum.SMILE);
        Assert.assertEquals(1, checksums.size());
        Assert.assertTrue(checksums.contains(checksum4));
    }
}
