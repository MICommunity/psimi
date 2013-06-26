package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.MitabModelledBinaryInteraction;
import psidev.psi.mi.jami.tab.io.writer.Mitab25ModelledInteractionWriter;

/**
 * Unit tester for Mitab25ModelledInteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab25ModelledInteractionWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        Assert.assertEquals(MitabVersion.v2_5, binaryWriter.getVersion());
        Assert.assertFalse(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() throws DataSourceWriterException {
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() throws DataSourceWriterException {
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        binaryWriter.initialiseContext(null);
    }
}
