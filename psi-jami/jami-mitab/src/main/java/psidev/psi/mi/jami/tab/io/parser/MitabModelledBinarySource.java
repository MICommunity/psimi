package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.datasource.ModelledBinaryInteractionStream;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * A mitab datasource that loads modelled binary interactions and ignore experimental details.
 * It will load the full interaction dataset
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class MitabModelledBinarySource  extends AbstractMitabSource<ModelledBinaryInteraction, ModelledParticipant, ModelledFeature> implements ModelledBinaryInteractionStream {
    public MitabModelledBinarySource() {
        super(new MitabModelledBinaryStreamSource());
    }

    public MitabModelledBinarySource(File file) throws IOException {
        super(new MitabModelledBinaryStreamSource(file));
    }

    public MitabModelledBinarySource(InputStream input) {
        super(new MitabModelledBinaryStreamSource(input));
    }

    public MitabModelledBinarySource(Reader reader) {
        super(new MitabModelledBinaryStreamSource(reader));
    }
}
