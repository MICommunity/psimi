package psidev.psi.mi.jami.datasource;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A RegisteredMIDataSource contains all the necessary information about a datasource
 * that can be used for the MolecularInteractionDataSourceFactory.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class RegisteredMIDataSource {

    /**
     * The MIDataSource class that will be instantiated
     */
    private Class<? extends MIDataSource> dataSourceClass;

    /**
     * The supported options by this MIDataSource
     */
    private Map<String, Object> supportedOptions;
    private static final Logger log = Logger.getLogger("RegisteredMIDataSource");

    public RegisteredMIDataSource(Class<? extends MIDataSource> dataSourceClass, Map<String, Object> supportedOptions){
         if (dataSourceClass == null){
             throw new IllegalArgumentException("The data source Class cannot be null");
         }
        this.dataSourceClass = dataSourceClass;
        this.supportedOptions = supportedOptions != null ? supportedOptions : Collections.EMPTY_MAP;
    }

    /**
     *
     * @param requiredOptions
     * @return true if the required options are not supported or recognized by this MIdataSource
     */
    public boolean areSupportedOptions(Map<String,Object> requiredOptions) {

        // no required options
        if (requiredOptions == null){
            return true;
        }

        // check if required options are supported
        for (Map.Entry<String, Object> entry : requiredOptions.entrySet()){
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

    /**
     * Instantiate a new MIDataSource from a file and a map of required options
     * @param file : the file containing MI data
     * @param requiredOptions : required options
     * @return the instantiated MIDataSource
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public MIDataSource instantiateNewDataSource(File file, Map<String,Object> requiredOptions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MIDataSource dataSource = dataSourceClass.getConstructor(File.class).newInstance(file);
        dataSource.initialiseContext(requiredOptions);

        return dataSource;
    }

    /**
     * Instantiate a new MIDataSource from an inputStream and a map of required options
     * @param stream : the stream containing MI data
     * @param requiredOptions : required options
     * @return the instantiated MIDataSource
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public MIDataSource instantiateNewDataSource(InputStream stream, Map<String,Object> requiredOptions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MIDataSource dataSource = dataSourceClass.getConstructor(InputStream.class).newInstance(stream);
        dataSource.initialiseContext(requiredOptions);

        return dataSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisteredMIDataSource)) return false;

        RegisteredMIDataSource that = (RegisteredMIDataSource) o;

        if (!dataSourceClass.equals(that.dataSourceClass)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dataSourceClass.hashCode();
    }
}
