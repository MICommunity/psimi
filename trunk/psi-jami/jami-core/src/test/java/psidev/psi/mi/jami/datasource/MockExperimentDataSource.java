package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.listener.MIFileParserListener;

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
        if (!options.containsKey(MIFileDataSourceOptions.INPUT_OPTION_KEY)){
            throw new IllegalArgumentException("no source option");
        }
        else if (options.get(MIFileDataSourceOptions.INPUT_OPTION_KEY) == null){
            throw new IllegalArgumentException("no source option");
        }
    }

    public void close() {
        // do nothing
    }

    public void reset() {

    }

    public MIFileParserListener getFileParserListener() {
        return null;
    }

    public void setFileParserListener(MIFileParserListener listener) {
        // do nothing
    }

    public boolean validateSyntax() {
        return true;
    }

    public boolean validateSyntax(MIFileParserListener listener) {
        return true;
    }
}
