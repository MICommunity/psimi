package psidev.psi.mi.jami.datasource;

import java.util.Map;

/**
 * A molecular interaction  DataSource.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface MIDataSource {

    /**
     * Initialise the context of the MIDataSource given a map of options
     * @param options
     */
    public void initialiseContext(Map<String, Object> options);

    /**
     * This method close the file data source (or inputStream)
     */
    public void close();

    /**
     * To know if a MIDataSource supports a given option (name and value)
     * @param optionKey : the name of the option
     * @param optionValue : the value of this option
     * @return true if the data source supports the options
     */
    public boolean supportsOption(String optionKey, Object optionValue);
}
