package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.tab.io.parser.LightMitabBinaryDataSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Untit tester for LightMitabDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/07/13</pre>
 */

public class LightMitabBinaryDataSourceTest {

    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new LightMitabBinaryDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource.close();
    }

    @Test
    public void validate_invalid_file(){
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new LightMitabBinaryDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void iterate_valid_file(){
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Iterator<BinaryInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        Interaction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new LightMitabBinaryDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
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
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Iterator<BinaryInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        Interaction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new LightMitabBinaryDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
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
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource(MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteraction> iterator = dataSource.getInteractionsIterator();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledDataSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteraction> iterator = dataSource.getInteractionsIterator();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource(new File(MitabModelledDataSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        Interaction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        LightMitabBinaryDataSource dataSource = new LightMitabBinaryDataSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, new File(MitabModelledDataSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Iterator<BinaryInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertTrue(iterator.hasNext());
        Interaction i2 = iterator.next();
        Assert.assertNotNull(i2);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
    }
}
