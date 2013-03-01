package psidev.psi.mi.jami.datasource;

import java.util.Map;

/**
 * A molecular interaction  DataSource.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface MolecularInteractionDataSource {

    public void initialiseContext(Map<String, Object> options);
}
