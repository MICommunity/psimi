package org.hupo.psi.tab;

import org.hupo.psi.calimocho.io.DefaultRowReader;
import org.hupo.psi.calimocho.io.RowReader;
import org.hupo.psi.calimocho.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.tab.util.MitabDocumentDefinitionFactory;

import java.io.InputStream;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Training
 * Date: 24/03/11
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public class Playground
{

    public static void main(String[] args) throws Exception {
        InputStream stream = Playground.class.getResourceAsStream("/META-INF/mitab26/14726512.txt");


        ColumnBasedDocumentDefinition mitabDocDefinition = MitabDocumentDefinitionFactory.mitab26();

        RowReader reader = new DefaultRowReader(mitabDocDefinition);

        for (Row row : reader.read(stream)) {
            System.out.println("--------------------------------\nNEW ROW\n-----------------------------------");
            Collection<ColumnDefinition> columns = mitabDocDefinition.getColumns();

            for (ColumnDefinition col : columns) {
                System.out.println(col.getKey());

//                Collection<Field> fields = row.getFields(col);
//
//                for (Field field : fields) {
//                    System.out.println("\t"+field);
//                }

            }
        }
    }

}
