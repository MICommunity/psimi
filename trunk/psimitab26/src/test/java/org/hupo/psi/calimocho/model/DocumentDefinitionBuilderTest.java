package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.AbstractCalimochoTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * DocumentDefinitionBuilder Tester.
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DocumentDefinitionBuilderTest extends AbstractCalimochoTest {
    @Test
    public void columnOrderingByPosition() throws Exception {
        final ColumnBasedDocumentDefinition documentDefinition = buildGeneListDefinition();
        final ArrayList<ColumnDefinition> columnDefinitionList = new ArrayList<ColumnDefinition>( documentDefinition.getColumns() );

        Assert.assertEquals( Integer.valueOf(0), columnDefinitionList.get( 0 ).getPosition() );
        Assert.assertEquals( Integer.valueOf(1), columnDefinitionList.get( 1 ).getPosition() );
    }
}
