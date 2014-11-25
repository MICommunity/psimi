package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.tab.extension.MitabBinaryInteractionEvidence;
import psidev.psi.mi.jami.tab.extension.MitabCvTerm;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * An extension of MitabLineParser that returns binary interactions evidences only.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/07/13</pre>
 */

public class BinaryEvidenceLineParser extends AbstractInteractionEvidenceLineParser<BinaryInteractionEvidence>{
    public BinaryEvidenceLineParser(InputStream stream) {
        super(stream);
    }

    public BinaryEvidenceLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public BinaryEvidenceLineParser(Reader stream) {
        super(stream);
    }

    public BinaryEvidenceLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    protected MitabBinaryInteractionEvidence createInteraction() {
        return new MitabBinaryInteractionEvidence();
    }

    @Override
    protected void initialiseExpansionMethod(Collection<MitabCvTerm> expansion, BinaryInteractionEvidence interaction) {
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
}
