package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.Complex;

/**
 * A Data source of biological complexes giving only a stream of complexes.
 * It is not possible to get a full collection of complexes.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 */

public interface ComplexStream extends InteractionStream<Complex>, InteractorStream<Complex> {

}
