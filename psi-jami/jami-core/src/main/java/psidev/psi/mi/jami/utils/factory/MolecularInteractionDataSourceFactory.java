package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.datasource.MIDataSource;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.datasource.RegisteredMIFileDataSource;
import psidev.psi.mi.jami.datasource.RegisteredMiDataSource;

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

    private static Set<RegisteredMiDataSource> registeredDataSources = new HashSet<RegisteredMiDataSource>();

    public static MIDataSource getMolecularInteractionDataSourceFrom(File file, Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (RegisteredMiDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions)){
                 return dataSource.instantiateNewDataSource(requiredOptions);
            }
        }

        return null;
    }

    public static MIDataSource getMolecularInteractionDataSourceFrom(InputStream stream, Map<String,Object> requiredOptions) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (RegisteredMiDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions)){
                return dataSource.instantiateNewDataSource(requiredOptions);
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
    public static boolean registerFileDataSource(Class<? extends MIFileDataSource> dataSourceClass, Map<String,Object> supportedOptions){
        RegisteredMiDataSource registeredDataSource = new RegisteredMIFileDataSource(dataSourceClass, supportedOptions);
        return registeredDataSources.add(registeredDataSource);
    }

    public static boolean registerDataSource(RegisteredMiDataSource dataSourceToRegister){
        if (dataSourceToRegister == null){
            return false;
        }
        return registeredDataSources.add(dataSourceToRegister);
    }

    public static void clearRegisteredDataSources(){
        registeredDataSources.clear();
    }
}
