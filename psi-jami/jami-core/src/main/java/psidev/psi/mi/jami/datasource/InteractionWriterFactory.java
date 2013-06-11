package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.DataSourceWriterException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A factory for InteractionDataSourceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class InteractionWriterFactory {

    private static final InteractionWriterFactory instance = new InteractionWriterFactory();

    private Map<Class<? extends InteractionDataSourceWriter>, Map<String, Object>> registeredDataSourceWriters;

    public static final String OUTPUT_FILE_OPTION_KEY = "output_file_key";
    public static final String OUTPUT_STREAM_OPTION_KEY = "output_stream_key";
    public static final String WRITER_OPTION_KEY = "ouput_writer_key";
    public static final String COMPLEX_EXPANSION_OPTION_KEY = "complex_expansion_key";

    private InteractionWriterFactory(){
        registeredDataSourceWriters = new ConcurrentHashMap<Class<? extends InteractionDataSourceWriter>, Map<String, Object>>();
    }

    public static InteractionWriterFactory getInstance() {
        return instance;
    }

    /**
     * Register a datasource writer with options in this factory
     * @param dataSourceClass
     * @param supportedOptions
     * @return
     */
    public void registerDataSourceWriter(Class<? extends InteractionDataSourceWriter> dataSourceClass, Map<String,Object> supportedOptions){
        if (dataSourceClass == null){
            throw new IllegalArgumentException("Cannot register a InteractionDataSourceWriter without a interactionDataSourceWriterClass");
        }

        registeredDataSourceWriters.put(dataSourceClass, supportedOptions != null ? supportedOptions : new ConcurrentHashMap<String, Object>());
    }

    /**
     * Remove the interactionDataSourceWriter from this factory
     * @param dataSourceClass
     */
    public void removeDataSourceWriter(Class<? extends InteractionDataSourceWriter> dataSourceClass){
        registeredDataSourceWriters.remove(dataSourceClass);
    }

    /**
     * Clear all the registered writers from this factory
     */
    public void clearRegisteredDataSourceWriters(){
        registeredDataSourceWriters.clear();
    }

    private InteractionDataSourceWriter instantiateNewDataSource(Class<? extends InteractionDataSourceWriter> dataSourceClass, Map<String, Object> options) throws IllegalAccessException, InstantiationException, DataSourceWriterException {

        InteractionDataSourceWriter dataSource = dataSourceClass.newInstance();
        dataSource.initialiseContext(options);

        return dataSource;
    }
}
