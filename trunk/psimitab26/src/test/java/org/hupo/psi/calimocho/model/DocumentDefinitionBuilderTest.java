package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.AbstractCalimochoTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * DocumentDefinitionBuilder Tester.
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DocumentDefinitionBuilderTest extends AbstractCalimochoTest {
    @Test
    public void columnOrderingByPosition() throws Exception {
        final DocumentDefinition documentDefinition = buildGeneListDefinition();
        final ArrayList<ColumnDefinition> columnDefinitionList = new ArrayList<ColumnDefinition>( documentDefinition.getColumns() );

        Assert.assertEquals( 1, columnDefinitionList.get( 0 ).getPosition() );
        Assert.assertEquals( 2, columnDefinitionList.get( 1 ).getPosition() );
    }
}
