package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.AbstractEntry;
import psidev.psi.mi.jami.xml.AbstractEntrySet;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract class for a PSI-XML 2.5 datasource that loads the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public abstract class AbstractPsiXml25Source<T extends Interaction> extends AbstractPsiXml25Stream<T> implements InteractionSource<T>{
    private Collection<T> loadedInteractions;

    protected AbstractPsiXml25Source() {
    }

    protected AbstractPsiXml25Source(File file) {
        super(file);
    }

    protected AbstractPsiXml25Source(InputStream input) {
        super(input);
    }

    protected AbstractPsiXml25Source(Reader reader) {
        super(reader);
    }

    protected AbstractPsiXml25Source(URL url) {
        super(url);
    }

    @Override
    public Collection<T> getInteractions() throws MIIOException {
        if (!isInitialised()){
            throw new IllegalStateException("The PsiXml interaction datasource has not been initialised. The options for the Psi xml 2.5 interaction datasource should contains at least "+ MIDataSourceFactory.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        // reset parser if possible
        try {
            if (getParser().hasFinished() && loadedInteractions == null){
                reInit();
                initialiseLoadedInteractions();
            }
            else if (loadedInteractions == null){
                initialiseLoadedInteractions();
            }
        } catch (PsiXmlParserException e) {
            if (getFileParserListener() != null){
                getFileParserListener().onInvalidSyntax(new DefaultFileSourceContext(e.getLocator()), e);
            }
            else{
                throw new MIIOException("Cannot know if the parser has finished.", e);
            }
        }
        return loadedInteractions;
    }

    @Override
    public long getNumberOfInteractions() {
        return getInteractions().size();
    }

    @Override
    public Iterator<T> getInteractionsIterator() throws MIIOException {
        return getInteractions().iterator();
    }

    @Override
    public void close() throws MIIOException{
        super.close();
        this.loadedInteractions = null;
    }

    @Override
    public void reset() throws MIIOException{
        super.reset();
        this.loadedInteractions = null;
    }

    @Override
    protected Iterator<T> createXmlIterator() {
        return getInteractions().iterator();
    }

    private void initialiseLoadedInteractions() throws PsiXmlParserException {
        this.loadedInteractions = new ArrayList<T>();
        AbstractEntrySet<AbstractEntry<T>> entrySet = ((FullPsiXml25Parser<T>)getParser()).getEntrySet();
        for (AbstractEntry<T> entry : entrySet.getEntries()){
            this.loadedInteractions.addAll(entry.getInteractions());
        }
    }
}
