package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.listener.MIFileParserListener;

import java.util.regex.Pattern;

/**
 * Interface for MIDataSource coming from a file.
 *
 * These dataSources need to be closed when they are not used anymore.
 *
 * A MIFileParserListener can be provided to listen to parsing events.
 *
 * A MIFileDataSource provides a method to know if the file syntax is valid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public interface MIFileDataSource extends MIDataSource{

    public static final String FILE_URI_PREFIX = "file://";
    public static final Pattern URL_PREFIX_REGEXP = Pattern.compile("\\w+?://");

    /**
     * The MIFileParserListener can be null if not initialised
     * @return the file parser listener that listen to the different parsing events
     */
    public MIFileParserListener getFileParserListener();

    public void setFileParserListener(MIFileParserListener listener);

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
     * @param listener : the listener for parsing events
     * @return true if the file syntax is valid
     * @throws MIIOException : if some severe syntax errors are found in the file and it stops the parsing
     */
    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException;
}
