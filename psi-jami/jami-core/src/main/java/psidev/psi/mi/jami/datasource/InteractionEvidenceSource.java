package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.util.Iterator;

/**
 * MI data source of InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public interface InteractionEvidenceSource extends InteractionSource {

    public Iterator<InteractionEvidence> getInteractionsIterator() throws MIIOException;
}
