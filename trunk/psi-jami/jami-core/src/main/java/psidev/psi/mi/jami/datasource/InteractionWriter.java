package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.DataSourceWriterException;
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
     * Writes an interaction
     * @param interaction
     */
    public void write(T interaction) throws DataSourceWriterException;

    /**
     * Writes a collection of Interaction objects
     * @param interactions
     */
    public void write(Collection<T> interactions) throws DataSourceWriterException;

    /**
     * Writes Interaction objects using iterator
     * @param interactions
     */
    public void write(Iterator<T> interactions) throws DataSourceWriterException;

    /**
     * Flushes the writer (commit or write on disk)
     */
    public void flush() throws DataSourceWriterException;

    /**
     * Closes the InteractionWriter. It will flushes before closing.
     * It will close any provided outputStream and writer.
     */
    public void close() throws DataSourceWriterException;

    /**
     * This method will clear the writer from all loaded options.
     * The interaction writer will be back to what is was before the initialiseContext was called.
     * To re-use the interaction writer after calling the clear() method, the data source needs to be re-initialised with
     * initialiseContext(Map<String, Object> options).
     * Any provided ouputStream or writer will not be closed and will have to be closed separately.
     */
    public void clear();
}
