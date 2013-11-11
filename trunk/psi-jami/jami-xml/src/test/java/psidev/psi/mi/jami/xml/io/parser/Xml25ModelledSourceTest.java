package psidev.psi.mi.jami.xml.io.parser;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit tester for Xml25ModelledSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25ModelledSourceTest {
    @Test(expected = IllegalStateException.class)
    public void test_validate_fails_not_initialised(){
        Xml25ModelledSource dataSource = new Xml25ModelledSource();
        dataSource.validateSyntax();
    }

    @Test(expected = IllegalStateException.class)
    public void test_iterate_fails_not_initialised(){
        Xml25ModelledSource dataSource = new Xml25ModelledSource();
        dataSource.getInteractionsIterator();
    }

    @Test
    public void validate_valid_file(){
        Xml25ModelledSource dataSource = new Xml25ModelledSource(Xml25ModelledSourceTest.class.getResourceAsStream("/samples/21703451.xml"));
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource = new Xml25ModelledSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, Xml25ModelledSourceTest.class.getResourceAsStream("/samples/21703451.xml"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());

        dataSource.close();
    }

    @Test
    public void validate_invalid_file(){
        Xml25ModelledSource dataSource = new Xml25ModelledSource(Xml25ModelledSourceTest.class.getResourceAsStream("/samples/empty.xml"));
        Assert.assertFalse(dataSource.validateSyntax());

        dataSource = new Xml25ModelledSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, Xml25ModelledSourceTest.class.getResourceAsStream("/samples/empty.xml"));
        dataSource.initialiseContext(options);
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void parse_valid_file() throws IOException {
        Xml25ModelledSource dataSource = new Xml25ModelledSource(new File(Xml25ModelledSourceTest.class.getResource("/samples/10049915.xml").getFile()));
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new Xml25ModelledSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, new File(Xml25ModelledSourceTest.class.getResource("/samples/10049915.xml").getFile()));
        dataSource.initialiseContext(options);
        iterator = dataSource.getInteractionsIterator();
        i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void iterate_invalid_file(){
        Xml25ModelledSource dataSource = new Xml25ModelledSource(new File(Xml25ModelledSourceTest.class.getResource("/samples/empty.xml").getFile()));
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();

        dataSource = new Xml25ModelledSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, new File(Xml25ModelledSourceTest.class.getResource("/samples/empty.xml").getFile()));
        dataSource.initialiseContext(options);
        iterator = dataSource.getInteractionsIterator();
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit(){
        Xml25ModelledSource dataSource = new Xml25ModelledSource(Xml25ModelledSourceTest.class.getResourceAsStream("/samples/21703451.xml"));
        Assert.assertTrue(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
    }

    @Test(expected = RuntimeException.class)
    public void test_validate_datasource_impossible_to_reinit_2(){

        Xml25ModelledSource dataSource = new Xml25ModelledSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, Xml25ModelledSourceTest.class.getResourceAsStream("/samples/21703451.xml"));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
    }

    @Test
    public void test_validate_datasource_reinit() throws IOException {
        Xml25ModelledSource dataSource = new Xml25ModelledSource(new File(Xml25ModelledSourceTest.class.getResource("/samples/10049915.xml").getFile()));
        Assert.assertTrue(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
        Assert.assertTrue(dataSource.validateSyntax());
        dataSource.close();
    }

    @Test
    public void test_validate_datasource_reinit_2(){

        Xml25ModelledSource dataSource = new Xml25ModelledSource();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MIDataSourceFactory.INPUT_OPTION_KEY, new File(Xml25ModelledSourceTest.class.getResource("/samples/10049915.xml").getFile()));
        dataSource.initialiseContext(options);
        Assert.assertTrue(dataSource.validateSyntax());
        Iterator<ModelledInteraction> iterator = dataSource.getInteractionsIterator();
        Interaction i1 = iterator.next();
        Assert.assertNotNull(i1);
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(dataSource.validateSyntax());
    }
}
