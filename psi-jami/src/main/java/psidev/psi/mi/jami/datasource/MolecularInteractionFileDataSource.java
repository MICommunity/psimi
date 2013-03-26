package psidev.psi.mi.jami.datasource;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

/**
 * Interface for molecular interaction datasources coming from a file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public interface MolecularInteractionFileDataSource {

    public Collection<FileSourceError> getDataSourceErrors();

    public void open(File file);
    public void open(InputStream stream);
    public void close();

    public void validateFileSyntax();
}
