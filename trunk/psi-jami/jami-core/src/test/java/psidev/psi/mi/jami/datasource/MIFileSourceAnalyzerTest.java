package psidev.psi.mi.jami.datasource;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unit tester for MIFileSourceAnalyzer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class MIFileSourceAnalyzerTest {

    private MIFileSourceAnalyzer analyzer = new MIFileSourceAnalyzer();

    @Test
    public void test_recognize_xml_file_with_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.xml").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_file_without_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_xml_no_extension").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_file_starting_with_blank_lines() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_blank_lines.xml").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_file_starting_no_encoding() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_encoding.xml").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_inputStream() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.xml").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.psi25_xml, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_xml_inputStream_starting_with_blank_lines() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_blank_lines.xml").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.psi25_xml, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_xml_inputStream_starting_no_encoding() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_encoding.xml").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.psi25_xml, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_mitab_file_with_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.txt").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.mitab, sourceType);
    }

    @Test
    public void test_recognize_mitab_file_without_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_mitab_no_extension").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.mitab, sourceType);
    }

    @Test
    public void test_recognize_mitab_file_starting_with_blank_lines() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_content_with_blank_line.txt").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.mitab, sourceType);
    }

    @Test
    public void test_recognize_mitab_file_starting_no_title() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_title.txt").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.mitab, sourceType);
    }

    @Test
    public void test_do_not_recognize_empty_file() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_empty.txt").getFile());

        MIFileSourceType sourceType = analyzer.getMolecularInteractionSourceFor(file);

        Assert.assertEquals(MIFileSourceType.other, sourceType);
    }

    @Test
    public void test_recognize_mitab_inputStream() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.txt").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.mitab, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_mitab_inputStream_starting_with_blank_lines() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_content_with_blank_line.txt").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.mitab, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_mitab_inputStream_starting_no_title() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_title.txt").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.mitab, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_do_not_recognize_empty_inputStream() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_empty.txt").openStream();

        OpenedInputStream openedStream = analyzer.getMolecularInteractionSourceFor(stream);

        Assert.assertEquals(MIFileSourceType.other, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }
}
