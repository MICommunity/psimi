package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.datasource.BinaryInteractionSource;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * A mitab datasource that loads very basic binary interactions and ignore experimental details, source, confidence and experimental details.
 *
 * It loads the full interaction dataset.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class LightMitabBinarySource extends AbstractMitabSource<BinaryInteraction, Participant, Feature> implements BinaryInteractionSource<BinaryInteraction>{
    public LightMitabBinarySource() {
        super(new LightMitabBinaryStreamSource());
    }

    public LightMitabBinarySource(File file) throws IOException {
        super(new LightMitabBinaryStreamSource(file));
    }

    public LightMitabBinarySource(InputStream input) {
        super(new LightMitabBinaryStreamSource(input));
    }

    public LightMitabBinarySource(Reader reader) {
        super(new LightMitabBinaryStreamSource(reader));
    }
}
