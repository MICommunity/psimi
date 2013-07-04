package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.DataSourceWriterException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * A factory for InteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class InteractionWriterFactory {

    private static final InteractionWriterFactory instance = new InteractionWriterFactory();
    private static final Logger logger = Logger.getLogger("InteractionWriterFactory");

    private Map<Class<? extends InteractionWriter>, Map<String, Object>> registeredWriters;

    public static final String OUTPUT_OPTION_KEY = "output_key";
    public static final String COMPLEX_EXPANSION_OPTION_KEY = "complex_expansion_key";
    public static final String OUTPUT_FORMAT_OPTION_KEY = "output_format_key";

    private InteractionWriterFactory(){
        registeredWriters = new ConcurrentHashMap<Class<? extends InteractionWriter>, Map<String, Object>>();
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
    public void registerDataSourceWriter(Class<? extends InteractionWriter> dataSourceClass, Map<String,Object> supportedOptions){
        if (dataSourceClass == null){
            throw new IllegalArgumentException("Cannot register a InteractionWriter without a interactionDataSourceWriterClass");
        }

        registeredWriters.put(dataSourceClass, supportedOptions != null ? supportedOptions : new ConcurrentHashMap<String, Object>());
    }

    /**
     * Remove the interactionDataSourceWriter from this factory
     * @param dataSourceClass
     */
    public void removeDataSourceWriter(Class<? extends InteractionWriter> dataSourceClass){
        registeredWriters.remove(dataSourceClass);
    }

    /**
     * Clear all the registered writers from this factory
     */
    public void clearRegisteredDataSourceWriters(){
        registeredWriters.clear();
    }

    public InteractionWriter getInteractionWriterWith(Map<String,Object> requiredOptions) {

        for (Map.Entry<Class<? extends InteractionWriter>, Map<String, Object>> entry : registeredWriters.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (areSupportedOptions(entry.getValue(), requiredOptions)){
                try {
                    return instantiateNewWriter(entry.getKey(), entry.getValue());
                } catch (IllegalAccessException e) {
                    logger.warning("We cannot instantiate interaction writer of type " + entry.getKey() + " with the given options.");
                } catch (InstantiationException e) {
                    logger.warning("We cannot instantiate interaction writer of type " + entry.getKey() + " with the given options.");
                } catch (DataSourceWriterException e) {
                    logger.warning("We cannot instantiate interaction writer of type " + entry.getKey() + " with the given options.");
                }
            }
        }

        return null;
    }

    private InteractionWriter instantiateNewWriter(Class<? extends InteractionWriter> dataSourceClass, Map<String, Object> options) throws IllegalAccessException, InstantiationException, DataSourceWriterException {

        InteractionWriter dataSource = dataSourceClass.newInstance();
        dataSource.initialiseContext(options);

        return dataSource;
    }

    /**
     * By default, all the options in the given options should be in the supportedOptions of this
     * registeredDataSourceWriter.
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
                Object result = supportedOptions.get(entry.getKey());
                if ( result != null && !result.equals(entry.getValue())){
                    return false;
                }
            }
            else {
                return false;
            }
        }

        return true;
    }
}
