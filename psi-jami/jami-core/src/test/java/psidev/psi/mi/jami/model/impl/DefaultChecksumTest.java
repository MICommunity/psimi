package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for Checksum
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultChecksumTest {

    @Test
    public void test_create_checksum_method_and_value() throws Exception {
        Checksum checksum = new DefaultChecksum(CvTermUtils.createRogid(), "xxxxxxxxx");

        Assert.assertEquals(CvTermUtils.createRogid(), checksum.getMethod());
        Assert.assertEquals("xxxxxxxxx", checksum.getValue());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_checksum_no_method() throws Exception {
        Checksum checksum = new DefaultChecksum(null, "xxxxxxxxx");
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_checksum_no_value() throws Exception {
        Checksum checksum = new DefaultChecksum(CvTermUtils.createRogid(), null);
    }
}
