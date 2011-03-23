package org.hupo.psi.tab.io;

import org.hupo.psi.calimocho.io.FormatConverter;
import org.hupo.psi.tab.util.MitabDocumentDefinitionFactory;
import org.junit.Test;

import java.io.InputStream;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class FormatConverterTest {

    @Test
    public void convert() throws Exception {
        InputStream is = FormatConverterTest.class.getResourceAsStream("/META-INF/mitab25/P51578-oneline.intact_extended.txt");

        FormatConverter converter = new FormatConverter( MitabDocumentDefinitionFactory.mitab25(), MitabDocumentDefinitionFactory.mitab26());
        converter.convert(is, System.out);
    }
}
