package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.model.extension.factory.PsiXmlDataSourceFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Generic class for PSI-XML full datasource.
 *
 * This datasource loads all the interactions from a PSI-XML file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class DefaultPsiXmlSource extends DefaultPsiXmlStreamSource implements PsiXmlSource {

    public DefaultPsiXmlSource(){
        super();
    }

    @Override
    protected void initialiseDelegate(Map<String, Object> options, PsiXmlDataSourceFactory factory, InteractionCategory category, ComplexType type) {
        super.setDelegate(factory.createPsiXmlDataSource(category, type, false));
        getDelegate().initialiseContext(options);
    }

    public Collection<Interaction> getInteractions() throws MIIOException {
        if (getDelegate() == null){
            throw new IllegalStateException("The PSI-XML interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return getDelegate().getInteractions();
    }

    public long getNumberOfInteractions() {
        if (getDelegate() == null){
            throw new IllegalStateException("The PSI-XML interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return getDelegate().getNumberOfInteractions();
    }

    @Override
    protected PsiXmlSource getDelegate() {
        return (PsiXmlSource)super.getDelegate();
    }
}
