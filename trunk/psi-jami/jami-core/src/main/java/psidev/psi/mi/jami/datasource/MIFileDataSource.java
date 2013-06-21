package psidev.psi.mi.jami.datasource;

import java.util.Collection;

/**
 * Interface for molecular interaction datasources coming from a file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public interface MIFileDataSource extends MIDataSource{

    /**
     * In a MIFileDataSource, the dataSourceErrors are FileSourceErrors.
     * The collection cannot be null. If the MIFileDataSource does not have any fileSourceErrors, the method should return an empty collection
     * @return the collection of FileSourceErrors
     */
    public Collection<FileSourceError> getDataSourceErrors();

    /**
     * Validate the syntax of this MIFileDataSource.
     * It returns true if the file syntax is valid, false otherwise.
     * When the file syntax is invalid, the syntax errors are stored in the dataSourceErrors of this MIDataSource
     */
    public boolean validateSyntax();
}
