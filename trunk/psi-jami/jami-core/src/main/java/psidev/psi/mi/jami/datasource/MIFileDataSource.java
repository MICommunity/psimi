package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.listener.MIFileParserListener;

/**
 * Interface for molecular interaction datasources coming from a file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public interface MIFileDataSource extends MIDataSource{

    /**
     * The MIFileParserListener can be null if not initialised
     * @return the file parser listener that listen to the different parsing events
     */
    public MIFileParserListener getFileParserListener();

    /**
     * Validate the syntax of this MIFileDataSource.
     * It returns true if the file syntax is valid, false otherwise.
     * When the file syntax is invalid, the syntax errors are fired and should be retrieved using a proper MIFileParserListener
     * @throws MIIOException
     */
    public boolean validateSyntax() throws MIIOException;

    /**
     * Validate the syntax of this MIFileDataSource and uses the provided MIFileParserListener to listen to the events.
     * The provided listener will be set as the MIFileParserListener of this datasource
     * @param listener
     * @return true if the file syntax is valid
     * @throws MIIOException
     */
    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException;
}
