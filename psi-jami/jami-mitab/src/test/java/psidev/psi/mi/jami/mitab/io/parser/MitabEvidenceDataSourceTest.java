package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.tab.io.parser.MitabEvidenceDataSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit tester for MitabEvidenceDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/07/13</pre>
 */

public class MitabEvidenceDataSourceTest {

    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource(MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());
    }

    @Test
    public void validate_invalid_file(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource(MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
    }

    @Test
    public void iterate_valid_file(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource(MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        iterator = dataSource.getInteractionsIterator();
        i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
    }

    @Test
    public void iterate_invalid_file(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource(MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        iterator = dataSource.getInteractionsIterator();
        i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource(MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabEvidenceDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource(new File(MitabEvidenceDataSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_FILE_OPTION_KEY, new File(MitabEvidenceDataSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
        InteractionEvidence i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        InteractionEvidence i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
    }

    @Test
    @Ignore
    public void test_playground(){
        MitabEvidenceDataSource dataSource = new MitabEvidenceDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_FILE_OPTION_KEY, new File("/home/marine/Desktop/general/intact/intact.txt"));
        dataSource.initialiseContext(options);
        System.out.print("start "+System.currentTimeMillis());

        Iterator<InteractionEvidence> iterator = dataSource.getInteractionsIterator();
        int count = 0;
        while (iterator.hasNext()){
            iterator.next();
            count++;
        }
        System.out.println(" "+ count + " interactions");
        System.out.print("end " + System.currentTimeMillis());
    }
}
