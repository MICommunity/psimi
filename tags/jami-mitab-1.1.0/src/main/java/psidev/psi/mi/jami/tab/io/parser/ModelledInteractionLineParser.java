package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.tab.extension.MitabModelledInteraction;

import java.io.InputStream;
import java.io.Reader;

/**
 * An extension of MitabLineParser which parses only ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class ModelledInteractionLineParser extends AbstractModelledInteractionLineParser<ModelledInteraction> {

    public ModelledInteractionLineParser(InputStream stream) {
        super(stream);
    }

    public ModelledInteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public ModelledInteractionLineParser(Reader stream) {
        super(stream);
    }

    public ModelledInteractionLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    protected ModelledInteraction createInteraction() {
        return new MitabModelledInteraction();
    }
}