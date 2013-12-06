package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.datasource.MIFileDataSourceOptions;
import psidev.psi.mi.jami.tab.io.parser.MitabModelledBinarySource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for MitabModelledBinarySource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/07/13</pre>
 */

public class MitabModelledBinarySourceTest {

    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource(MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new MitabModelledBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void validate_invalid_file(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource(MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new MitabModelledBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void iterate_valid_file(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource(MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<ModelledBinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new MitabModelledBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void iterate_invalid_file(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource(MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<ModelledBinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new MitabModelledBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit(){
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource(MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());
        Collection<ModelledBinaryInteraction> interactions = dataSource.getInteractions();
    }
    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        MitabModelledBinarySource dataSource = new MitabModelledBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, MitabModelledBinarySourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Collection<ModelledBinaryInteraction> interactions = dataSource.getInteractions();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        MitabModelledBinarySource dataSource = new MitabModelledBinarySource(new File(MitabModelledBinarySourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        Assert.assertFalse(dataSource.validateSyntax());
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<ModelledBinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        MitabModelledBinarySource dataSource = new MitabModelledBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIFileDataSourceOptions.INPUT_OPTION_KEY, new File(MitabModelledBinarySourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<ModelledBinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }
}
