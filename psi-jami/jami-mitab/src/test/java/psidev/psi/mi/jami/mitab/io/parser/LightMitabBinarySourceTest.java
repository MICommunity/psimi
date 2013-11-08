package psidev.psi.mi.jami.mitab.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.tab.io.parser.LightMitabBinarySource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Untit tester for LightMitabBinarySource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/07/13</pre>
 */

public class LightMitabBinarySourceTest {

    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource(MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new LightMitabBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource.close();
    }

    @Test
    public void validate_invalid_file(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource(MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new LightMitabBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void parse_valid_file(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource(MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<BinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        dataSource.close();

        dataSource = new LightMitabBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line.txt"));
        dataSource.initialiseContext(options);
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        dataSource.close();
    }

    @Test
    public void iterate_invalid_file(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource(MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<BinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        dataSource.close();

        dataSource = new LightMitabBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        dataSource.close();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit(){
        LightMitabBinarySource dataSource = new LightMitabBinarySource(MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        Assert.assertFalse(dataSource.validateSyntax());
        Collection<BinaryInteraction> iterator = dataSource.getInteractions();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        LightMitabBinarySource dataSource = new LightMitabBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, MitabModelledStreamSourceTest.class.getResourceAsStream("/samples/mitab27_line_too_many_columns.txt"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Collection<BinaryInteraction> iterator = dataSource.getInteractions();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        LightMitabBinarySource dataSource = new LightMitabBinarySource(new File(MitabModelledStreamSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        Assert.assertFalse(dataSource.validateSyntax());
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<BinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        LightMitabBinarySource dataSource = new LightMitabBinarySource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, new File(MitabModelledStreamSourceTest.class.getResource("/samples/mitab27_line_too_many_columns.txt").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        Assert.assertEquals(2, dataSource.getNumberOfInteractions());
        Collection<BinaryInteraction> interactions = dataSource.getInteractions();
        Assert.assertEquals(2, interactions.size());
        Assert.assertFalse(dataSource.validateSyntax());
    }
}
