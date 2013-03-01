package psidev.psi.mi.jami.datasource;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A RegisteredDataSource contains all the necessary information about a datasource that can be used for the DataSourceFactory.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class RegisteredDataSource {

    private Class<? extends MolecularInteractionDataSource> dataSourceClass;
    private Map<String, Object> supportedOptions;
    private static final Logger log = Logger.getLogger("RegisteredDataSource");

    public RegisteredDataSource(Class<? extends MolecularInteractionDataSource> dataSourceClass, Map<String, Object> supportedOptions){
         if (dataSourceClass == null){
             throw new IllegalArgumentException("The data source Class cannot be null");
         }
        this.dataSourceClass = dataSourceClass;
        this.supportedOptions = supportedOptions != null ? supportedOptions : Collections.EMPTY_MAP;
    }

    public boolean areSupportedOptions(Map<String,Object> requiredOptions) {
        if (requiredOptions == null){
            return false;
        }

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

    public MolecularInteractionDataSource instantiateNewDataSource(File file, Map<String,Object> requiredOptions){
        MolecularInteractionDataSource dataSource = null;
        try {
            dataSource = dataSourceClass.getConstructor(File.class).newInstance(file);
            dataSource.initialiseContext(requiredOptions);
        } catch (InstantiationException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName());
        } catch (IllegalAccessException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName());
        } catch (NoSuchMethodException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName() + " because does not have a constructor with a file.");
        } catch (InvocationTargetException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName() + " because does not have a constructor with a files.");
        }

        return dataSource;
    }

    public MolecularInteractionDataSource instantiateNewDataSource(InputStream stream, Map<String,Object> requiredOptions){
        MolecularInteractionDataSource dataSource = null;
        try {
            dataSource = dataSourceClass.getConstructor(InputStream.class).newInstance(stream);
            dataSource.initialiseContext(requiredOptions);
        } catch (InstantiationException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName());
        } catch (IllegalAccessException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName());
        } catch (NoSuchMethodException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName() + " because does not have a constructor with a file.");
        } catch (InvocationTargetException e) {
            log.severe("Impossible to instantiate a new " + dataSourceClass.getCanonicalName() + " because does not have a constructor with a files.");
        }

        return dataSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisteredDataSource)) return false;

        RegisteredDataSource that = (RegisteredDataSource) o;

        if (!dataSourceClass.equals(that.dataSourceClass)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dataSourceClass.hashCode();
    }
}
