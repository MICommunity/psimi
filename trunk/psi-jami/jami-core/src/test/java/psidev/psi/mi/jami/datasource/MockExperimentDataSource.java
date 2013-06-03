package psidev.psi.mi.jami.datasource;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * A MockExperimentDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class MockExperimentDataSource implements MIFileDataSource {

    public void initialiseContext(Map<String, Object> options) {
        // do nothing
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return Collections.EMPTY_LIST;
    }

    public void open() {
        // do nothing
    }

    public void close() {
        // do nothing
    }

    public boolean validateFileSyntax() {
        return true;
    }
}
