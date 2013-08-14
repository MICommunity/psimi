package psidev.psi.mi.jami.commons;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unit tester for MIFileAnalyzer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class MIFileSourceAnalyzerTest {

    private MIFileAnalyzer analyzer = new MIFileAnalyzer();

    @Test
    public void test_recognize_xml_file_with_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.xml").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_file_without_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_xml_no_extension").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_file_starting_with_blank_lines() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_blank_lines.xml").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_file_starting_no_encoding() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_encoding.xml").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.psi25_xml, sourceType);
    }

    @Test
    public void test_recognize_xml_inputStream() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.xml").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.psi25_xml, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_xml_inputStream_starting_with_blank_lines() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_blank_lines.xml").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.psi25_xml, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_xml_inputStream_starting_no_encoding() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_encoding.xml").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.psi25_xml, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_mitab_file_with_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.txt").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.mitab, sourceType);
    }

    @Test
    public void test_recognize_mitab_file_without_extension() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_mitab_no_extension").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.mitab, sourceType);
    }

    @Test
    public void test_recognize_mitab_file_starting_with_blank_lines() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_content_with_blank_line.txt").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.mitab, sourceType);
    }

    @Test
    public void test_recognize_mitab_file_starting_no_title() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_title.txt").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.mitab, sourceType);
    }

    @Test
    public void test_do_not_recognize_empty_file() throws IOException {

        File file = new File(MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_empty.txt").getFile());

        MIFileType sourceType = analyzer.identifyMIFileTypeFor(file);

        Assert.assertEquals(MIFileType.other, sourceType);
    }

    @Test
    public void test_recognize_mitab_inputStream() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675.txt").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.mitab, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_mitab_inputStream_starting_with_blank_lines() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_content_with_blank_line.txt").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.mitab, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_recognize_mitab_inputStream_starting_no_title() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_no_title.txt").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.mitab, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    public void test_do_not_recognize_empty_inputStream() throws IOException {

        InputStream stream = MIFileSourceAnalyzerTest.class.getResource("/samples/10075675_empty.txt").openStream();

        OpenedInputStream openedStream = analyzer.extractMIFileTypeAndCopiedInputStream(stream);

        Assert.assertEquals(MIFileType.other, openedStream.getSource());
        Assert.assertNotNull(openedStream.getCopiedStream());

        openedStream.close();
    }

    @Test
    @Ignore
    public void playground() throws IOException {
        // initialise factories
        PsiJami.initialiseAllFactories();

        // reader
        MIDataSourceOptionFactory optionfactory = MIDataSourceOptionFactory.getInstance();
        MIDataSourceFactory dataSourceFactory = MIDataSourceFactory.getInstance();

        InteractionSource interactionSource = dataSourceFactory.
                getInteractionSourceWith(optionfactory.getDefaultOptions(new File("path/to/file")));

        // writer
        MIWriterOptionFactory optionwriterFactory = MIWriterOptionFactory.getInstance();
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        InteractionWriter writer = writerFactory.
                getInteractionWriterWith(optionwriterFactory.getDefaultMitabOptions(new File("path/to/file")));
    }
}
