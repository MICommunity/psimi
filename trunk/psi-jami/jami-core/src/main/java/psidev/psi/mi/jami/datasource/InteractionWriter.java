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
     * @param options
     */
    public void initialiseContext(Map<String, Object> options);

    /**
     * Method to call when starting to write
     * @throws MIIOException
     */
    public void start() throws MIIOException;

    /**
     * Method to call when we reach the end
     * @throws MIIOException
     */
    public void end() throws MIIOException;

    /**
     * Writes an interaction
     * @param interaction
     * @throws MIIOException
     */
    public void write(T interaction) throws MIIOException;

    /**
     * Writes a collection of Interaction objects
     * @param interactions
     * @throws MIIOException
     */
    public void write(Collection<T> interactions) throws MIIOException;

    /**
     * Writes Interaction objects using iterator
     * @param interactions
     * @throws MIIOException
     */
    public void write(Iterator<T> interactions) throws MIIOException;

    /**
     * Flushes the writer (commit or write on disk)
     * @throws MIIOException
     */
    public void flush() throws MIIOException;

    /**
     * Closes the InteractionWriter. It will flushes before closing.
     * It will close any provided outputStream and writer.
     * @throws MIIOException
     */
    public void close() throws MIIOException;

    /**
     * This method will reset the writer from all loaded options.
     * The interaction writer will be back to what is was before the initialiseContext was called.
     * To re-use the interaction writer after calling the reset() method, the data source needs to be re-initialised with
     * initialiseContext(Map<String, Object> options).
     * Any provided ouputStream or writer will not be closed and will have to be closed separately.
     * @throws MIIOException
     */
    public void reset() throws MIIOException;
}
