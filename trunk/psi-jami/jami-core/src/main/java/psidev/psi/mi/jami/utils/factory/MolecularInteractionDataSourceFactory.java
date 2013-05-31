package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.datasource.MIDataSource;
import psidev.psi.mi.jami.datasource.RegisteredMIDataSource;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * A factory to create data sources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MolecularInteractionDataSourceFactory {

    private static Set<RegisteredMIDataSource> registeredDataSources = new HashSet<RegisteredMIDataSource>();

    public static MIDataSource getMolecularInteractionDataSourceFrom(File file, Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (RegisteredMIDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions)){
                 return dataSource.instantiateNewDataSource(file, requiredOptions);
            }
        }

        return null;
    }

    public static MIDataSource getMolecularInteractionDataSourceFrom(InputStream stream, Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (RegisteredMIDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions)){
                return dataSource.instantiateNewDataSource(stream, requiredOptions);
            }
        }

        return null;
    }

    /**
     * Register a data source with supported options.
     * @param dataSourceClass
     * @param supportedOptions
     * @return true if the data source has been registered
     */
    public static boolean registerDataSource(Class<? extends MIDataSource> dataSourceClass, Map<String,Object> supportedOptions){
        RegisteredMIDataSource registeredDataSource = new RegisteredMIDataSource(dataSourceClass, supportedOptions);
        return registeredDataSources.add(registeredDataSource);
    }

    public static void clearRegisteredDataSources(){
        registeredDataSources.clear();
    }
}
