package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The InteractionWriter can write interactions in a datasource (files, database)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public interface InteractionWriter<T extends Interaction> {

    /**
     * Initialise the context of the InteractionWriter given a map of options
     * @param options : the options given by the user
     */
    public void initialiseContext(Map<String, Object> options);

    /**
     * Method to call before starting to write  but after initialising the context
     * @throws MIIOException : if cannot start writing
     */
    public void start() throws MIIOException;

    /**
     * Method to call when we reach the end
     * @throws MIIOException : if cannot end
     */
    public void end() throws MIIOException;

    /**
     * Writes an interaction
     * @param interaction : the interaction to write
     * @throws MIIOException : if cannot write
     */
    public void write(T interaction) throws MIIOException;

    /**
     * Writes a collection of Interaction objects
     * @param interactions : the interactions to write
     * @throws MIIOException : if cannot write
     */
    public void write(Collection<? extends T> interactions) throws MIIOException;

    /**
     * Writes Interaction objects using iterator
     * @param interactions : the iterator of interactions to write
     * @throws MIIOException : if cannot write
     */
    public void write(Iterator<? extends T> interactions) throws MIIOException;

    /**
     * Flushes the writer (commit or write on disk)
     * @throws MIIOException : if cannot flush
     */
    public void flush() throws MIIOException;

    /**
     * Closes the InteractionWriter. It will flushes before closing.
     * It will close any provided outputStream and writer.
     * @throws MIIOException : if cannot close
     */
    public void close() throws MIIOException;

    /**
     * This method will reset the writer from all loaded options.
     * The interaction writer will be back to what is was before the initialiseContext was called.
     * To re-use the interaction writer after calling the reset() method, the data source needs to be re-initialised with
     * initialiseContext(Map<String, Object> options).
     * Any provided ouputStream or writer will not be closed and will have to be closed separately.
     * @throws MIIOException : if cannot reset
     */
    public void reset() throws MIIOException;
}
