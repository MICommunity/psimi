package org.hupo.psi.calimocho.xgmml;

import org.hupo.psi.calimocho.io.DocumentConverter;
import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.util.MitabDocumentDefinitionFactory;

import java.io.*;

public class Playground {

    public static void main(String[] args) throws Exception {
//        InputStream stream = Playground.class.getResourceAsStream("/META-INF/mitab/14726512.mitab26.txt");
        InputStream stream = Playground.class.getResourceAsStream("/META-INF/mitab/brca2.mitab25-ia.txt");

        ColumnBasedDocumentDefinition mitabDefinition = MitabDocumentDefinitionFactory.mitab25Intact();
        DocumentDefinition definition = new XGMMLDocumentDefinition();

        OutputStream os = new FileOutputStream("/tmp/lala.xml");

        DocumentConverter converter = new DocumentConverter( mitabDefinition, definition );
        converter.convert( stream, os );

        os.flush();



        try {
            BufferedReader in = new BufferedReader(new FileReader("/tmp/lala.xml"));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            in.close();
        } catch (IOException e) {
        }

    }

}
