package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.Complex;

/**
 * A Data source of biological complexes.
 * It gives full access to all the complexes using Iterator or the full collection.
 * It can also give information about the size of the dataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 */

public interface ComplexSource extends InteractionSource<Complex> {
}
