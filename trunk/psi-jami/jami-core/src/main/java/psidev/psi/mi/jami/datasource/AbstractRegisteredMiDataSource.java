package psidev.psi.mi.jami.datasource;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;

/**
 * Abstract class for RegisteredMiDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public abstract class AbstractRegisteredMiDataSource<T extends MIDataSource> implements RegisteredMiDataSource<T> {

    /**
     * The MIDataSource class that will be instantiated
     */
    private Class<? extends T> dataSourceClass;

    /**
     * The supported options by this MIDataSource
     */
    protected Map<String, Object> supportedOptions;

    public AbstractRegisteredMiDataSource(Class<? extends T> dataSourceClass, Map<String, Object> supportedOptions){
        if (dataSourceClass == null){
            throw new IllegalArgumentException("The data source Class cannot be null");
        }
        this.dataSourceClass = dataSourceClass;
        this.supportedOptions = supportedOptions != null ? supportedOptions : Collections.EMPTY_MAP;
    }

    /**
     * By default, all the options in the given options should be in the supportedOptions of this
     * registeredDataSource.
     * @param options
     * @return
     */
    public boolean areSupportedOptions(Map<String, Object> options) {
        // no required options
        if (options == null || options.isEmpty()){
            return true;
        }

        // check if required options are supported
        for (Map.Entry<String, Object> entry : options.entrySet()){
            if (supportedOptions.containsKey(entry.getKey())){
                if (!supportedOptions.get(entry.getKey()).equals(entry.getValue())){
                    return false;
                }
            }
            else {
                return false;
            }
        }

        return true;
    }

    public abstract T instantiateNewDataSource(Map<String, Object> options) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    public Class<? extends T> getMIDataSourceClass() {
        return dataSourceClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisteredMIFileDataSource)) return false;

        RegisteredMiDataSource that = (RegisteredMiDataSource) o;

        if (!dataSourceClass.equals(that.getMIDataSourceClass())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dataSourceClass.hashCode();
    }
}
