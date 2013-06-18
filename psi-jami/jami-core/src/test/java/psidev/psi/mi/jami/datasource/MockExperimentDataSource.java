package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.factory.MIDataSourceFactory;

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
        if (!options.containsKey(MIDataSourceFactory.INPUT_FILE_OPTION_KEY)){
            throw new IllegalArgumentException("no source option");
        }
        else if (options.get(MIDataSourceFactory.INPUT_FILE_OPTION_KEY) == null){
            throw new IllegalArgumentException("no source option");
        }
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
