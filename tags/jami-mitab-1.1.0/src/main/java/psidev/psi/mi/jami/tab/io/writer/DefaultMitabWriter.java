package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.factory.MitabWriterFactory;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabDataSourceOptions;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabWriterOptions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Generic writer for MITAB
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public class DefaultMitabWriter implements InteractionWriter {

    private InteractionWriter delegate;

    public void initialiseContext(Map options) {
        MitabWriterFactory factory = MitabWriterFactory.getInstance();
        InteractionCategory category = InteractionCategory.mixed;
        ComplexType type = ComplexType.n_ary;
        MitabVersion version = MitabVersion.v2_7;
        boolean extended = false;

        if (options == null || !options.containsKey(MitabWriterOptions.OUTPUT_OPTION_KEY)){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(MitabWriterOptions.INTERACTION_CATEGORY_OPTION_KEY)){
            Object value = options.get(MitabDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY);
            if (value instanceof InteractionCategory){
                category = (InteractionCategory)value;
            }
        }
        if (options.containsKey(MitabWriterOptions.COMPLEX_TYPE_OPTION_KEY)){
            Object value = options.get(MitabDataSourceOptions.COMPLEX_TYPE_OPTION_KEY);
            if (value instanceof ComplexType){
                type = (ComplexType)value;
            }
        }
        if (options.containsKey(MitabWriterOptions.MITAB_VERSION_OPTION)){
            Object value = options.get(MitabWriterOptions.MITAB_VERSION_OPTION);
            if (value instanceof MitabVersion){
                version = (MitabVersion)value;
            }
        }
        if (options.containsKey(MitabWriterOptions.MITAB_EXTENDED_OPTION)){
            Object value = options.get(MitabWriterOptions.MITAB_EXTENDED_OPTION);
            if (value != null){
                extended = (Boolean)value;
            }
        }

        this.delegate = factory.createMitabWriter(category, type, version, extended);
        this.delegate.initialiseContext(options);
    }

    public void start() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.start();
    }

    public void end() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.end();
    }

    public void write(Interaction interaction) throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.write(interaction);
    }

    public void write(Collection interactions) throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.write(interactions);
    }

    public void write(Iterator interactions) throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.write(interactions);
    }

    public void flush() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.flush();
    }

    public void close() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.close();
    }

    public void reset() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction writer has not been initialised. The options for the Mitab interaction writer " +
                    "should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        this.delegate.reset();
    }

    protected InteractionWriter getDelegate() {
        return delegate;
    }

    protected void setDelegate(InteractionWriter delegate) {
        this.delegate = delegate;
    }
}
