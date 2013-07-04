package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.tab.extension.MitabInteractionEvidence;

import java.io.InputStream;
import java.io.Reader;

/**
 * An extension of MitabLineParser that returns interactions evidences only.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class InteractionEvidenceLineParser extends AbstractInteractionEvidenceLineParser<InteractionEvidence> {

    public InteractionEvidenceLineParser(InputStream stream) {
        super(stream);
    }

    public InteractionEvidenceLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public InteractionEvidenceLineParser(Reader stream) {
        super(stream);
    }

    public InteractionEvidenceLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    protected InteractionEvidence createInteraction() {
        return new MitabInteractionEvidence();
    }
}
