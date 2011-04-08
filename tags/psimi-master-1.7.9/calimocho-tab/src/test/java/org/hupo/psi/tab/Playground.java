package org.hupo.psi.tab;

import org.hupo.psi.calimocho.io.DocumentConverter;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.model.definition.PlainDocumentDefinition;
import org.hupo.psi.tab.io.DefaultRowReader;
import org.hupo.psi.tab.io.RowReader;
import org.hupo.psi.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.tab.model.ColumnDefinition;
import org.hupo.psi.tab.util.MitabDocumentDefinitionFactory;

import java.io.InputStream;
import java.util.Collection;

public class Playground {

    public static void main(String[] args) throws Exception {
        InputStream stream = Playground.class.getResourceAsStream("/META-INF/mitab26/14726512.txt");

        ColumnBasedDocumentDefinition mitabDefinition = MitabDocumentDefinitionFactory.mitab26();
        PlainDocumentDefinition plainDefinition = new PlainDocumentDefinition();

        DocumentConverter converter = new DocumentConverter( mitabDefinition, plainDefinition );
        converter.convert( stream, System.out );

    }

}
