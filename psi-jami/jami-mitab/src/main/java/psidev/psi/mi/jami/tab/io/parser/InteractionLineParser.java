package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.MitabInteraction;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * An extension of MitabLineParser that returns simple interactions only.
 *
 * It ignore properties of InteractionEvidence and ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class InteractionLineParser extends AbstractLightInteractionLineParser<Interaction> {

    public InteractionLineParser(InputStream stream) {
        super(stream);
    }

    public InteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public InteractionLineParser(Reader stream) {
        super(stream);
    }

    public InteractionLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    protected void addParticipant(Participant participant, Interaction interaction) {
        ((Collection<Participant>)interaction.getParticipants()).add(participant);
    }

    @Override
    protected Interaction createInteraction() {
        return new MitabInteraction();
    }
}