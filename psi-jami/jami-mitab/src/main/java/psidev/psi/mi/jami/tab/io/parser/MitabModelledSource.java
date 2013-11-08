package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.ModelledInteractionSource;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * A mitab datasource that loads modelled interactions and ignore experimental details
 * It will load the full interaction dataset.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class MitabModelledSource extends AbstractMitabSource<ModelledInteraction, ModelledParticipant, ModelledFeature> implements ModelledInteractionSource<ModelledInteraction>{
    public MitabModelledSource() {
        super(new MitabModelledStreamSource());
    }

    public MitabModelledSource(File file) throws IOException {
        super(new MitabModelledStreamSource(file));
    }

    public MitabModelledSource(InputStream input) {
        super(new MitabModelledStreamSource(input));
    }

    public MitabModelledSource(Reader reader) {
        super(new MitabModelledStreamSource(reader));
    }
}
