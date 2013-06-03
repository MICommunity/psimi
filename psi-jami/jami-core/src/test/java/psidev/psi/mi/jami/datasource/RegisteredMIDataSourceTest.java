package psidev.psi.mi.jami.datasource;

import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for RegisteredMIFileDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class RegisteredMIDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void create_data_source_no_give_class(){

        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(null, Collections.EMPTY_MAP);
    }

    @Test
    public void test_missing_file_data_source_options(){
        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(MIFileDataSource.class, Collections.EMPTY_MAP);

        Assert.assertFalse(registeredSource.areSupportedOptions(null));
        Assert.assertFalse(registeredSource.areSupportedOptions(Collections.EMPTY_MAP));

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put(RegisteredMIFileDataSource.SOURCE_OPTION, new ByteArrayInputStream("".getBytes()));
        Assert.assertTrue(registeredSource.areSupportedOptions(requiredOptions));
    }

    @Test
    public void test_no_supported_options(){
        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(MIFileDataSource.class, Collections.EMPTY_MAP);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");

        Assert.assertFalse(registeredSource.areSupportedOptions(requiredOptions));
    }

    @Test
    public void test_supports_options(){
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");

        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(MIFileDataSource.class, possibleOptions);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");
        requiredOptions.put(RegisteredMIFileDataSource.SOURCE_OPTION, new ByteArrayInputStream("".getBytes()));

        Assert.assertTrue(registeredSource.areSupportedOptions(requiredOptions));
    }

    @Test
    public void test_supports_options_more_options_than_required(){
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");
        possibleOptions.put("streaming", "true");

        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(MIFileDataSource.class, possibleOptions);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");
        requiredOptions.put(RegisteredMIFileDataSource.SOURCE_OPTION, new ByteArrayInputStream("".getBytes()));

        Assert.assertTrue(registeredSource.areSupportedOptions(requiredOptions));
    }

    @Test
    public void test_does_not_support_one_required_option(){
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");
        possibleOptions.put("streaming", "false");

        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(MIFileDataSource.class, possibleOptions);

        Map<String, Object> requiredOptions = new HashMap<String, Object>();
        requiredOptions.put("file_source", "xml");
        requiredOptions.put("streaming", "true");
        requiredOptions.put(RegisteredMIFileDataSource.SOURCE_OPTION, new ByteArrayInputStream("".getBytes()));

        Assert.assertFalse(registeredSource.areSupportedOptions(requiredOptions));

        Map<String, Object> requiredOptions2 = new HashMap<String, Object>();
        requiredOptions2.put("file_source", "xml");
        requiredOptions2.put("experiment", "true");
        requiredOptions2.put(RegisteredMIFileDataSource.SOURCE_OPTION, new ByteArrayInputStream("".getBytes()));

        Assert.assertFalse(registeredSource.areSupportedOptions(requiredOptions2));
    }

    @Test
    public void test_equal_data_source(){
        Map<String, Object> possibleOptions = new HashMap<String, Object>();
        possibleOptions.put("file_source", "xml");
        possibleOptions.put("streaming", "true");
        RegisteredMIFileDataSource registeredSource = new RegisteredMIFileDataSource(MockExperimentDataSource.class, possibleOptions);
        RegisteredMIFileDataSource registeredSource2 = new RegisteredMIFileDataSource(MockExperimentDataSource.class, Collections.EMPTY_MAP);
        RegisteredMIFileDataSource registeredSource3 = new RegisteredMIFileDataSource(MockInteractionDataSource.class, Collections.EMPTY_MAP);

        Assert.assertEquals(registeredSource, registeredSource2);
        Assert.assertNotSame(registeredSource, registeredSource3);
        Assert.assertNotSame(registeredSource2, registeredSource3);
        Assert.assertEquals(registeredSource.hashCode(), registeredSource2.hashCode());
        Assert.assertNotSame(registeredSource.hashCode(), registeredSource3.hashCode());
        Assert.assertNotSame(registeredSource2.hashCode(), registeredSource3.hashCode());
    }
}
