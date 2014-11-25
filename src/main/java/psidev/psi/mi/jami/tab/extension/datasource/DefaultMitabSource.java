package psidev.psi.mi.jami.tab.extension.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.extension.factory.MitabDataSourceFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract class for an InteractionSource coming from a MITAB file.
 *
 * This datasource provides interaction iterator and collection
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class DefaultMitabSource extends DefaultMitabStreamSource implements MitabSource{

    public DefaultMitabSource(){
        super();
    }

    @Override
    protected void initialiseDelegate(Map<String, Object> options, MitabDataSourceFactory factory, InteractionCategory category, ComplexType type) {
        super.setDelegate(factory.createMitabDataSource(category, type, false));
        getDelegate().initialiseContext(options);
    }

    public Collection<Interaction> getInteractions() throws MIIOException {
        if (getDelegate() == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return getDelegate().getInteractions();
    }

    public long getNumberOfInteractions() {
        if (getDelegate() == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return getDelegate().getNumberOfInteractions();
    }

    @Override
    protected MitabSource getDelegate() {
        return (MitabSource)super.getDelegate();
    }
}
