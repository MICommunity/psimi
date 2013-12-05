package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.datasource.MIDataSource;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.model.Interaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger("MIDataSourceFactory");

    private Map<Class<? extends MIDataSource>, Map<String, Object>> registeredDataSources;

    private Class interactionSourceClass = InteractionStream.class;

    private MIDataSourceFactory(){
        registeredDataSources = new ConcurrentHashMap<Class<? extends MIDataSource>, Map<String, Object>>();
    }

    public static MIDataSourceFactory getInstance() {
        return instance;
    }

    public MIDataSource getMIDataSourceWith(Map<String,Object> requiredOptions) {

        for (Map.Entry<Class<? extends MIDataSource>, Map<String, Object>> entry : registeredDataSources.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (areSupportedOptions(entry.getValue(), requiredOptions)){
                try {
                    return instantiateNewDataSource(entry.getKey(), requiredOptions);
                } catch (IllegalAccessException e) {
                    logger.warning("We cannot instantiate data source of type " + entry.getKey() + " with the given options.");
                } catch (InstantiationException e) {
                    logger.warning("We cannot instantiate data source of type " + entry.getKey() + " with the given options.");
                }
            }
        }

        return null;
    }

    public <I extends Interaction> InteractionStream<I> getInteractionSourceWith(Map<String,Object> requiredOptions) {

        for (Map.Entry<Class<? extends MIDataSource>, Map<String, Object>> entry : registeredDataSources.entrySet()){
            // we check for a DataSource that can be used with the given options
            if (interactionSourceClass.isAssignableFrom(entry.getKey()) && areSupportedOptions(entry.getValue(), requiredOptions)){
                try {
                    return (InteractionStream) instantiateNewDataSource(entry.getKey(), requiredOptions);
                } catch (IllegalAccessException e) {
                    logger.warning("We cannot instantiate interaction data source of type " + entry.getKey() + " with the given options.");
                } catch (InstantiationException e) {
                    logger.warning("We cannot instantiate interaction data source of type " + entry.getKey() + " with the given options.");
                }
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

    private MIDataSource instantiateNewDataSource(Class<? extends MIDataSource> dataSourceClass, Map<String, Object> options) throws IllegalAccessException, InstantiationException {

        MIDataSource dataSource = dataSourceClass.newInstance();
        dataSource.initialiseContext(options);

        return dataSource;
    }
}
