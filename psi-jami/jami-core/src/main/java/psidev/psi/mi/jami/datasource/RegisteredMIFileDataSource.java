package psidev.psi.mi.jami.datasource;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * A RegisteredMIFileDataSource contains all the necessary information about a datasource
 * that can be used for the MIDataSourceFactory.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class RegisteredMIFileDataSource extends AbstractRegisteredMiDataSource<MIFileDataSource>{

    public final static String SOURCE_OPTION = "file_data_source";

    public RegisteredMIFileDataSource(Class<? extends MIFileDataSource> dataSourceClass, Map<String, Object> supportedOptions){
         super(dataSourceClass, supportedOptions);
    }

    /**
     *
     * @param requiredOptions
     * @return true if the required options are not supported or recognized by this MIdataSource
     */
    public boolean areSupportedOptions(Map<String,Object> requiredOptions) {

        if (requiredOptions != null){
            // we have a give file data source in the given options so the options are supported
            if (requiredOptions.containsKey(SOURCE_OPTION) && requiredOptions.get(SOURCE_OPTION) != null){
                Object o = requiredOptions.get(SOURCE_OPTION);
                requiredOptions.remove(SOURCE_OPTION);

                boolean supports = super.areSupportedOptions(requiredOptions);

                requiredOptions.put(SOURCE_OPTION, o);
                return supports;
            }
        }

        return false;
    }

    @Override
    /**
     *
     */
    public MIFileDataSource instantiateNewDataSource(Map<String, Object> options) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!areSupportedOptions(options)){
            StringBuffer buffer = new StringBuffer();
            buffer.append("We cannot instantiate a MIFileDataSource with the given rewuired options. The supported options are ");
            for (Map.Entry<String, Object> opt : supportedOptions.entrySet()){
                buffer.append("\n").append(opt.getKey()).append(": ").append(opt.getValue());
            }
            buffer.append("\n").append("An option with key ").append(SOURCE_OPTION).append(" is also expected and should contain the file or inputstream which contains the data.");
            throw new IllegalArgumentException(buffer.toString());
        }

        MIFileDataSource dataSource = getMIDataSourceClass().newInstance();
        dataSource.initialiseContext(options);

        return dataSource;
    }

    /**
     * Instantiate a new MIFileDataSource from a file and a map of required options.
     * This method requires the MIFileDataSource to take a File as a constructor argument
     * @param file : the file containing MI data
     * @param requiredOptions : required options
     * @return the instantiated MIDataSource
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public MIFileDataSource instantiateNewDataSource(File file, Map<String,Object> requiredOptions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!super.areSupportedOptions(requiredOptions)){
            StringBuffer buffer = new StringBuffer();
            buffer.append("We cannot instantiate a MIFileDataSource with the given rewuired options. The supported options are ");
            for (Map.Entry<String, Object> options : supportedOptions.entrySet()){
                buffer.append("\n").append(options.getKey()).append(": ").append(options.getValue());
            }
            throw new IllegalArgumentException(buffer.toString());
        }

        MIFileDataSource dataSource = getMIDataSourceClass().getConstructor(File.class).newInstance(file);
        dataSource.initialiseContext(requiredOptions);

        return dataSource;
    }

    /**
     * Instantiate a new MIFileDataSource from an inputStream and a map of required options
     * This method requires the MIFileDataSource to take an InputStream as a constructor argument
     * @param stream : the stream containing MI data
     * @param requiredOptions : required options
     * @return the instantiated MIDataSource
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public MIFileDataSource instantiateNewDataSource(InputStream stream, Map<String,Object> requiredOptions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!super.areSupportedOptions(requiredOptions)){
            StringBuffer buffer = new StringBuffer();
            buffer.append("We cannot instantiate a MIFileDataSource with the given rewuired options. The supported options are ");
            for (Map.Entry<String, Object> options : supportedOptions.entrySet()){
                buffer.append("\n").append(options.getKey()).append(": ").append(options.getValue());
            }
            throw new IllegalArgumentException(buffer.toString());
        }

        MIFileDataSource dataSource = getMIDataSourceClass().getConstructor(InputStream.class).newInstance(stream);
        dataSource.initialiseContext(requiredOptions);

        return dataSource;
    }
}
