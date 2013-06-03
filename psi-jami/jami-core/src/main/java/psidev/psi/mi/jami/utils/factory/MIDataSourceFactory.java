package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.datasource.MIDataSource;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.datasource.RegisteredMIFileDataSource;
import psidev.psi.mi.jami.datasource.RegisteredMiDataSource;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A factory to create data sources.
 *
 * The datasource have to be registered first and then the factory can create the proper datasource depending on the options
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MIDataSourceFactory {

    private static volatile MIDataSourceFactory instance;

    private Set<RegisteredMiDataSource> registeredDataSources;

    private MIDataSourceFactory(){
        registeredDataSources = new HashSet<RegisteredMiDataSource>();
    }

    public static MIDataSourceFactory getInstance() {
        if (instance == null){
            instance = new MIDataSourceFactory();
        }
        return instance;
    }

    public MIDataSource getMIDataSourceWith(Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (RegisteredMiDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions)){
                return dataSource.instantiateNewDataSource(requiredOptions);
            }
        }

        return null;
    }

    public MIFileDataSource getMIFileDataSourceFrom(File file, Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (requiredOptions == null){
            requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(RegisteredMIFileDataSource.SOURCE_OPTION, file);
        }

        for (RegisteredMiDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions) && dataSource.getMIDataSourceClass().isAssignableFrom(MIFileDataSource.class)){
                 return (MIFileDataSource) dataSource.instantiateNewDataSource(requiredOptions);
            }
        }

        return null;
    }

    public MIFileDataSource getMIFileDataSourceFrom(InputStream stream, Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (requiredOptions == null){
            requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(RegisteredMIFileDataSource.SOURCE_OPTION, stream);
        }

        for (RegisteredMiDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions) && dataSource.getMIDataSourceClass().isAssignableFrom(MIFileDataSource.class)){
                return (MIFileDataSource) dataSource.instantiateNewDataSource(requiredOptions);
            }
        }

        return null;
    }

    /**
     * Register a file data source with supported options.
     * @param dataSourceClass
     * @param supportedOptions
     * @return true if the file data source has been registered
     */
    public synchronized boolean registerFileDataSource(Class<? extends MIFileDataSource> dataSourceClass, Map<String,Object> supportedOptions){
        RegisteredMiDataSource registeredDataSource = new RegisteredMIFileDataSource(dataSourceClass, supportedOptions);
        return registeredDataSources.add(registeredDataSource);
    }

    public synchronized boolean registerDataSource(RegisteredMiDataSource dataSourceToRegister){
        if (dataSourceToRegister == null){
            return false;
        }
        return registeredDataSources.add(dataSourceToRegister);
    }

    public synchronized void clearRegisteredDataSources(){
        registeredDataSources.clear();
    }
}
