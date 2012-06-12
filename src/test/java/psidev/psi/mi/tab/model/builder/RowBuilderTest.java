package psidev.psi.mi.tab.model.builder;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * RowBuilder Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since TODO artifact version
 * @version $Id$
 */
public class RowBuilderTest {

    @Test
    public void createRow() throws Exception {

        RowBuilder builder = new RowBuilder( new MitabDocumentDefinition() );
        final Row row =
                builder.createRow( "uniprotkb:P41004\tuniprotkb:O74469\tuniprotkb:cut3(gene name)\t-\tuniprotkb:smc4|uniprotkb:SPBC146.03c\tuniprotkb:SPCC1739.07\tMI:0018(2 hybrid)|MI:0096(pull down)|MI:0006(anti bait coip)\t-\tpubmed:15148393\ttaxid:4896(schpo)\ttaxid:4896(schpo)\tMI:0218(physical interaction)\tMI:0469(intact)\tintact:EBI-1149490|intact:EBI-1149519|intact:EBI-1149550\t-" );
        Assert.assertNotNull( row );
        Assert.assertEquals( 15, row.getColumnCount() );
        
        Assert.assertEquals(0, row.getColumnByIndex( 3 ).getFields().size() );



    }
}
