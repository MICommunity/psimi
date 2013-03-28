package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.datasource.MolecularInteractionDataSource;
import psidev.psi.mi.jami.datasource.RegisteredDataSource;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * A factory to create data sources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MolecularInteractionDataSourceFactory {

    private static Set<RegisteredDataSource> registeredDataSources = new HashSet<RegisteredDataSource>();

    public static MolecularInteractionDataSource getMolecularInteractionDataSourceFrom(File file, Map<String,Object> requiredOptions){

        for (RegisteredDataSource dataSource : registeredDataSources){
            if (dataSource.areSupportedOptions(requiredOptions)){
                 return dataSource.instantiateNewDataSource(file, requiredOptions);
            }
        }

        return null;
    }

    public MolecularInteractionDataSource getMolecularInteractionDataSourceFrom(InputStream stream, Map<String,Object> requiredOptions){

        for (RegisteredDataSource dataSource : registeredDataSources){
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
    public static boolean registerDataSource(Class<? extends MolecularInteractionDataSource> dataSourceClass, Map<String,Object> supportedOptions){
        RegisteredDataSource registeredDataSource = new RegisteredDataSource(dataSourceClass, supportedOptions);
        return registeredDataSources.add(registeredDataSource);
    }

    public static void clearRegisteredDataSources(){
        registeredDataSources.clear();
    }
}
