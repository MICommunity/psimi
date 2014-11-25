package psidev.psi.mi.jami.mitab.extension.datasource;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.tab.extension.datasource.MitabBinaryEvidenceStreamSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit tester for MitabEvidenceStreamSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/07/13</pre>
 */

public class MitabBinaryEvidenceStreamSourceTest {

    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource(MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void validate_invalid_file(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource(MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void iterate_valid_file(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource(MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        iterator = dataSource.getInteractionsIterator();
        i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void iterate_invalid_file(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource(MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        iterator = dataSource.getInteractionsIterator();
        i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource(MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        dataSource.close();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabBinaryEvidenceStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        dataSource.close();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource(new File(MitabBinaryEvidenceStreamSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, new File(MitabBinaryEvidenceStreamSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    @Ignore
    public void test_playground(){
        MitabBinaryEvidenceStreamSource dataSource = new MitabBinaryEvidenceStreamSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, new File("/home/marine/Desktop/general/intact/intact.txt"));
        dataSource.initialiseContext(options);
        System.out.print("start "+System.currentTimeMillis());

        Iterator<BinaryInteractionEvidence> iterator = dataSource.getInteractionsIterator();
        int count = 0;
        while (iterator.hasNext()){
            iterator.next();
            count++;
        }
        System.out.println(" "+ count + " interactions");
        System.out.print("end " + System.currentTimeMillis());
        dataSource.close();
    }
}
