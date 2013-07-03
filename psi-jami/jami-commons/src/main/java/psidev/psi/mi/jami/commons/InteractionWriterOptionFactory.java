package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.listener.MIFileParserListener;

import java.util.HashMap;
import java.util.Map;

/**
 * The factory to get options for the InteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class InteractionWriterOptionFactory {

    private static final InteractionWriterOptionFactory instance = new InteractionWriterOptionFactory();

    private InteractionWriterOptionFactory(){
    }

    public static InteractionWriterOptionFactory getInstance() {
        return instance;
    }

    public Map<String, Object> getMitabOptions(InteractionObjectCategory objectCategory, boolean streaming, MIFileParserListener listener){
        Map<String, Object> options = new HashMap<String, Object>();

        options.put(MIDataSourceFactory.INTERACTION_OBJECT_OPTION_KEY, objectCategory != null ? objectCategory : InteractionObjectCategory.evidence);
        options.put(MIDataSourceFactory.STREAMING_OPTION_KEY, streaming);
        if (listener != null){
            options.put(MIDataSourceFactory.PARSER_LISTENER_OPTION_KEY, listener);
        }

        return options;
    }
}
