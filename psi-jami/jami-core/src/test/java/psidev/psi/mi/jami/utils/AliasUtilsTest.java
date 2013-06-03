package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.impl.DefaultAlias;

/**
 * Unit tester for AliasUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class AliasUtilsTest {

    @Test
    public void test_alias_null_does_not_have_alias_type(){

        Assert.assertFalse(AliasUtils.doesAliasHaveType(null, "MI:xxxx", "synonym"));
        Assert.assertFalse(AliasUtils.doesAliasHaveType(new DefaultAlias("bla"), null, null));
    }

    @Test
    public void test_alias_with_type_null(){

        Assert.assertFalse(AliasUtils.doesAliasHaveType(new DefaultAlias("test name"), "MI:xxxx", "synonym"));
    }
}
