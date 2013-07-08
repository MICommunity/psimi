package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.listener.MIFileParserListener;

import java.util.Map;

/**
 * A MockInteractionDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class MockInteractionDataSource implements MIFileDataSource {

    public void initialiseContext(Map<String, Object> options) {
        // do nothing
    }

    public void close() {
        // do nothing
    }

    public void clear() {
    }

    public MIFileParserListener getFileParserListener() {
        return null;
    }

    public void setMIFileParserListener(MIFileParserListener listener) {
        //do nothing
    }

    public boolean validateSyntax() {
        return true;
    }

    public boolean validateSyntax(MIFileParserListener listener) {
        return true;
    }
}
