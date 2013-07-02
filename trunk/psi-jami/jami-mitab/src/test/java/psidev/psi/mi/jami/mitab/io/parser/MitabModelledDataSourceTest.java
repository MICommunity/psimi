package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.tab.io.parser.MitabModelledDataSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit tester for MitabModelledDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/07/13</pre>
 */

public class MitabModelledDataSourceTest {

    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        MitabModelledDataSource dataSource = new MitabModelledDataSource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        MitabModelledDataSource dataSource = new MitabModelledDataSource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        MitabModelledDataSource dataSource = new MitabModelledDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new MitabModelledDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());
    }

    @Test
    public void validate_invalid_file(){
        MitabModelledDataSource dataSource = new MitabModelledDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new MitabModelledDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
    }

    @Test
    public void iterate_valid_file(){
        MitabModelledDataSource dataSource = new MitabModelledDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        ModelledInteraction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        ModelledInteraction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new MitabModelledDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
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
        MitabModelledDataSource dataSource = new MitabModelledDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        ModelledInteraction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        ModelledInteraction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new MitabModelledDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
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
        MitabModelledDataSource dataSource = new MitabModelledDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        MitabModelledDataSource dataSource = new MitabModelledDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        MitabModelledDataSource dataSource = new MitabModelledDataSource(new File(MitabModelledDataSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        ModelledInteraction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        ModelledInteraction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        MitabModelledDataSource dataSource = new MitabModelledDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_FILE_OPTION_KEY, new File(MitabModelledDataSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        ModelledInteraction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        ModelledInteraction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
    }
}
