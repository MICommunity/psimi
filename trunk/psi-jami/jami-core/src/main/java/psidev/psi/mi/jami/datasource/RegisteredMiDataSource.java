package psidev.psi.mi.jami.datasource;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * A RegisteredMIDataSource contains all the necessary information about a datasource
 * that can be used for the MIDataSourceFactory.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public interface RegisteredMiDataSource<T extends MIDataSource> {

    @Override
    /**
     * The equals is expected to be overriden so two RegisteredMiDataSource with the same MiDataSource class are always equals
     */
    public boolean equals(Object o);

    @Override
    /**
     * The hashcode method is expected to be overriden so two RegisteredMiDataSource with the same MiDataSource class are always equals
     */
    public int hashCode();

    /**
     * Method to know if a registered data source can support the given options
     * @param options
     * @return false if the required options are not supported or recognized, true if all the given options are supported
     */
    public boolean areSupportedOptions(Map<String,Object> options);

    /**
     * Creates a new MIDatasource from the given options
     * @param options
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public T instantiateNewDataSource(Map<String,Object> options) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    /**
     * Get the class of the MIDataSource that can be instantiated by this RegisteredMIDataSource
     * @return
     */
    public Class<? extends T> getMIDataSourceClass();
}
