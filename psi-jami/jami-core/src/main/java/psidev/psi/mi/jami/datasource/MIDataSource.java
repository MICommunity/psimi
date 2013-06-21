package psidev.psi.mi.jami.datasource;

import java.util.Collection;
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
     * The collection of DataSourceErrors which have been collected in this MiDataSource.
     * This collection cannot be null. If the MIDataSource does not have any errors, it should return an empty collection.
     * @return the collection of DataSourceErrors
     */
    public Collection<? extends DataSourceError> getDataSourceErrors();

    /**
     * This method close the file data source (or inputStream)
     */
    public void close();
}
