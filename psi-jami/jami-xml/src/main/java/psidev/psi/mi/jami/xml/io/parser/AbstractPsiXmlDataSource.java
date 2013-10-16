package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlIdReference;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract class for psiXml data source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public abstract class AbstractPsiXmlDataSource<T extends Interaction> implements MIFileDataSource, InteractionSource, PsiXmlParserListener {

    private boolean isInitialised = false;
    private AbstractPsiXml25Parser<T> parser;
    private MIFileParserListener defaultParserListener;
    private PsiXmlParserListener parserListener;

    public AbstractPsiXmlDataSource(){
    }

    public AbstractPsiXmlDataSource(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractPsiXmlDataSource(InputStream input) {

        initialiseInputStream(input);
        isInitialised = true;
    }

    public AbstractPsiXmlDataSource(Reader reader) {

        initialiseReader(reader);
        isInitialised = true;
    }

    public AbstractPsiXmlDataSource(URL url) {

        initialiseURL(url);
        isInitialised = true;
    }

    @Override
    public <I extends Interaction> Iterator<I> getInteractionsIterator() throws MIIOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MIFileParserListener getFileParserListener() {
        return this.defaultParserListener;
    }

    @Override
    public boolean validateSyntax() throws MIIOException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws MIIOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reset() throws MIIOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUnresolvedReference(XmlIdReference ref, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSyntaxWarning(FileSourceContext context, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSeveralCvTermsFound(Collection<? extends CvTerm> terms, FileSourceContext context, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSeveralHostOrganismFound(Collection<? extends Organism> organisms, FileSourceContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected abstract void initialiseXmlParser(Reader reader);

    protected abstract void initialiseXmlParser(File file);

    protected abstract void initialiseXmlParser(InputStream input);

    protected abstract void initialiseXmlParser(URL url);

    protected void setXmlFileParserListener(PsiXmlParserListener listener) {
        this.parserListener = listener;
        this.defaultParserListener = listener;
    }

    protected void setMIFileParserListener(MIFileParserListener listener) {
        if (listener instanceof PsiXmlParserListener){
            setXmlFileParserListener((PsiXmlParserListener) listener);
        }
        else{
            this.parserListener = null;
            this.defaultParserListener = listener;
        }
    }

    private void initialiseReader(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }

        initialiseXmlParser(reader);
    }

    private void initialiseInputStream(InputStream input) {
        if (input == null){
            throw new IllegalArgumentException("The input stream cannot be null.");
        }

        initialiseXmlParser(input);
    }

    private void initialiseFile(File file)  {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read the file "+file.getAbsolutePath());
        }

        initialiseXmlParser(file);
    }

    private void initialiseURL(URL url)  {
        if (url == null){
            throw new IllegalArgumentException("The url cannot be null.");
        }

        initialiseXmlParser(url);
    }
}
