package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.*;

import java.util.Iterator;

/**
 * An interaction data source allows to stream the interactors of a given datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public interface StreamingInteractionSource extends MIDataSource {

    public Iterator<? extends Interaction> getInteractionsIterator();
}
