package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.MitabBinaryInteraction;
import psidev.psi.mi.jami.tab.extension.MitabCvTerm;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * An extension of MitabLineParser that returns simple binary interactions only.
 *
 * It ignore properties of BinaryInteractionEvidence and ModelledBinaryInteraction
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/07/13</pre>
 */

public class BinaryLineParser extends AbstractLightInteractionLineParser<BinaryInteraction>{
    public BinaryLineParser(InputStream stream) {
        super(stream);
    }

    public BinaryLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public BinaryLineParser(Reader stream) {
        super(stream);
    }

    public BinaryLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    protected MitabBinaryInteraction createInteraction() {
        return new MitabBinaryInteraction();
    }

    @Override
    protected void initialiseExpansionMethod(Collection<MitabCvTerm> expansion, BinaryInteraction interaction) {
        if (expansion.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermsFound(expansion, expansion.iterator().next(), expansion.size()+" complex expansions found. Only the first one will be loaded.");
            }
            interaction.setComplexExpansion(expansion.iterator().next());
        }
        else if (!expansion.isEmpty()){
            interaction.setComplexExpansion(expansion.iterator().next());
        }
    }

    @Override
    protected void addParticipant(Participant participant, BinaryInteraction interaction) {
        if (interaction.getParticipantA() != null){
            interaction.setParticipantB(participant);
        }
        else {
            interaction.setParticipantA(participant);
        }
    }
}
