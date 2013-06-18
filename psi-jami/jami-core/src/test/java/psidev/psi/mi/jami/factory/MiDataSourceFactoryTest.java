package psidev.psi.mi.jami.factory;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.datasource.MockExperimentDataSource;
import psidev.psi.mi.jami.datasource.MockInteractionDataSource;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for MiDataSourceFactory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class MiDataSourceFactoryTest {
    @Test(expected = IllegalArgumentException.class)
    public void register_data_source_no_give_class(){

        MIDataSourceFactory.getInstance().registerDataSource(null, Collections.EMPTY_MAP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_missing_file_data_source_options() throws IllegalAccessException, InstantiationException {

        MIDataSourceFactory.getInstance().registerDataSource(MockExperimentDataSource.class, Collections.EMPTY_MAP);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();

        MIDataSourceFactory.getInstance().getMIDataSourceWith(requiredOptions);
    }

    public void test_existing_file_data_source_options() throws IllegalAccessException, InstantiationException {

        MIDataSourceFactory.getInstance().registerDataSource(MockExperimentDataSource.class, Collections.EMPTY_MAP);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();

        Assert.assertNotNull(MIDataSourceFactory.getInstance().getMIFileDataSourceFrom(new ByteArrayInputStream("".getBytes()), requiredOptions));
        Assert.assertTrue(MIDataSourceFactory.getInstance().getMIFileDataSourceFrom(new ByteArrayInputStream("".getBytes()), requiredOptions) instanceof MockExperimentDataSource);
    }

    @Test
    public void test_no_supported_options() throws IllegalAccessException, InstantiationException {
        MIDataSourceFactory.getInstance().registerDataSource(MockInteractionDataSource.class, Collections.EMPTY_MAP);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");

        Assert.assertNull(MIDataSourceFactory.getInstance().getMIDataSourceWith(requiredOptions));
    }

    @Test
    public void test_supports_options() throws IllegalAccessException, InstantiationException {
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");

        MIDataSourceFactory.getInstance().registerDataSource(MockInteractionDataSource.class, possibleOptions);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");

        Assert.assertNotNull(MIDataSourceFactory.getInstance().getMIDataSourceWith(requiredOptions));
    }

    @Test
    public void test_supports_options_more_options_than_required() throws IllegalAccessException, InstantiationException {
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");
        possibleOptions.put("streaming", "true");

        MIDataSourceFactory.getInstance().registerDataSource(MockInteractionDataSource.class, possibleOptions);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");

        Assert.assertNotNull(MIDataSourceFactory.getInstance().getMIDataSourceWith(requiredOptions));
    }

    @Test
    public void test_does_not_support_one_required_option() throws IllegalAccessException, InstantiationException {
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");
        possibleOptions.put("streaming", "false");

        MIDataSourceFactory.getInstance().registerDataSource(MockInteractionDataSource.class, possibleOptions);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");
        requiredOptions.put("streaming", "true");

        Assert.assertNull(MIDataSourceFactory.getInstance().getMIDataSourceWith(requiredOptions));

        Map<String, Object> requiredOptions2 = new HashMap<String, Object>();
        requiredOptions2.put("file_source", "xml");
        requiredOptions2.put("experiment", "true");

        Assert.assertNull(MIDataSourceFactory.getInstance().getMIDataSourceWith(requiredOptions));
    }
}
