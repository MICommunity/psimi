package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.datasource.MIDataSource;
import psidev.psi.mi.jami.datasource.MIFileDataSource;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final MIDataSourceFactory instance = new MIDataSourceFactory();

    private Map<Class<? extends MIDataSource>, Map<String, Object>> registeredDataSources;

    public static final String INPUT_FILE_OPTION_KEY = "input_file_key";
    public static final String INPUT_STREAM_OPTION_KEY = "input_stream_key";
    public static final String READER_OPTION_KEY = "input_reader_key";
    public static final String STREAMING_OPTION_KEY = "streaming_key";
    public static final String INTERACTION_OBJECT_OPTION_KEY = "interaction_object_key";
    public static final String PARSER_LISTENER_OPTION_KEY = "parser_listener_key";
    public static final String INPUT_FORMAT_OPTION_KEY = "input_format_key";

    private MIDataSourceFactory(){
        registeredDataSources = new ConcurrentHashMap<Class<? extends MIDataSource>, Map<String, Object>>();
    }

    public static MIDataSourceFactory getInstance() {
        return instance;
    }

    public MIDataSource getMIDataSourceWith(Map<String,Object> requiredOptions) throws InstantiationException, IllegalAccessException {

        for (Map.Entry<Class<? extends MIDataSource>, Map<String, Object>> entry : registeredDataSources.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (areSupportedOptions(entry.getValue(), requiredOptions)){
                return instantiateNewDataSource(entry.getKey(), entry.getValue());
            }
        }

        return null;
    }

    public MIFileDataSource getMIFileDataSourceFrom(File file, Map<String,Object> requiredOptions) throws InstantiationException, IllegalAccessException {

        if (requiredOptions == null){
            requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(INPUT_FILE_OPTION_KEY, file);
        }

        for (Map.Entry<Class<? extends MIDataSource>, Map<String, Object>> entry : registeredDataSources.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (entry.getKey().isAssignableFrom(MIFileDataSource.class) &&
                    areSupportedOptions(entry.getValue(), requiredOptions)){
                return (MIFileDataSource) instantiateNewDataSource(entry.getKey(), entry.getValue());
            }
        }

        return null;
    }

    public MIFileDataSource getMIFileDataSourceFrom(InputStream stream, Map<String,Object> requiredOptions) throws InstantiationException, IllegalAccessException {

        if (requiredOptions == null){
            requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(INPUT_STREAM_OPTION_KEY, stream);
        }

        for (Map.Entry<Class<? extends MIDataSource>, Map<String, Object>> entry : registeredDataSources.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (entry.getKey().isAssignableFrom(MIFileDataSource.class) &&
                    areSupportedOptions(entry.getValue(), requiredOptions)){
                return (MIFileDataSource) instantiateNewDataSource(entry.getKey(), entry.getValue());
            }
        }

        return null;
    }

    public MIFileDataSource getMIFileDataSourceFrom(Reader reader, Map<String,Object> requiredOptions) throws InstantiationException, IllegalAccessException {

        if (requiredOptions == null){
            requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(READER_OPTION_KEY, reader);
        }

        for (Map.Entry<Class<? extends MIDataSource>, Map<String, Object>> entry : registeredDataSources.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (entry.getKey().isAssignableFrom(MIFileDataSource.class) &&
                    areSupportedOptions(entry.getValue(), requiredOptions)){
                return (MIFileDataSource) instantiateNewDataSource(entry.getKey(), entry.getValue());
            }
        }

        return null;
    }

    /**
     * Register a datasource with options in this factory
     * @param dataSourceClass
     * @param supportedOptions
     * @return
     */
    public void registerDataSource(Class<? extends MIFileDataSource> dataSourceClass, Map<String,Object> supportedOptions){
        if (dataSourceClass == null){
            throw new IllegalArgumentException("Cannot register a MIDataSource without a dataSourceClass");
        }

        registeredDataSources.put(dataSourceClass, supportedOptions != null ? supportedOptions : new ConcurrentHashMap<String, Object>());
    }

    /**
     * Remove the dataSource from this factory
     * @param dataSourceClass
     */
    public void removeDataSource(Class<? extends MIFileDataSource> dataSourceClass){
        registeredDataSources.remove(dataSourceClass);
    }

    /**
     * Clear all the registered datasources from this factory
     */
    public void clearRegisteredDataSources(){
        registeredDataSources.clear();
    }

    /**
     * By default, all the options in the given options should be in the supportedOptions of this
     * registeredDataSource.
     * @param supportedOptions options supported
     * @param options that are required
     * @return
     */
    private boolean areSupportedOptions(Map<String, Object> supportedOptions, Map<String, Object> options) {
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

    private MIDataSource instantiateNewDataSource(Class<? extends MIDataSource> dataSourceClass, Map<String, Object> options) throws IllegalAccessException, InstantiationException {

        MIDataSource dataSource = dataSourceClass.newInstance();
        dataSource.initialiseContext(options);

        return dataSource;
    }
}
