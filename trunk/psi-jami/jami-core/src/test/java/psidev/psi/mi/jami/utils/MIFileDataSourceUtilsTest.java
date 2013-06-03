package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.datasource.FileSourceError;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Unit tester for MIFileDataSourceUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class MIFileDataSourceUtilsTest {

    @Test
    public void test_retrieve_errors_empty_list_or_no_error_type(){

        Assert.assertTrue(MIFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(Collections.EMPTY_LIST, "error_type").isEmpty());
        Assert.assertTrue(MIFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(Arrays.asList(new FileSourceError("error type", "error message", null)), null).isEmpty());
    }

    @Test
    public void test_retrieve_errors_with_specific_error_type(){

        FileSourceError error1 = new FileSourceError("type1", "message1", null);
        FileSourceError error2 = new FileSourceError("type1", "message2", null);
        FileSourceError error3 = new FileSourceError("type2", "message3", null);
        FileSourceError error4 = new FileSourceError("type3", "message4", null);

        List<FileSourceError> errors = Arrays.asList(error1, error2, error3, error4);

        Collection<FileSourceError> selected = MIFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorType(errors, "type1");

        Assert.assertEquals(2, selected.size());
        Assert.assertTrue(selected.contains(error1));
        Assert.assertTrue(selected.contains(error2));
    }

    @Test
    public void test_retrieve_errors_with_several_specific_error_types(){

        FileSourceError error1 = new FileSourceError("type1", "message1", null);
        FileSourceError error2 = new FileSourceError("type1", "message2", null);
        FileSourceError error3 = new FileSourceError("type2", "message3", null);
        FileSourceError error4 = new FileSourceError("type3", "message4", null);

        List<FileSourceError> errors = Arrays.asList(error1, error2, error3, error4);

        Collection<FileSourceError> selected = MIFileDataSourceUtils.collectAllDataSourceErrorsHavingErrorTypes(errors, new String[] {"type1", "type2"});

        Assert.assertEquals(3, selected.size());
        Assert.assertTrue(selected.contains(error1));
        Assert.assertTrue(selected.contains(error2));
        Assert.assertTrue(selected.contains(error3));
    }
}
