package psidev.psi.mi.tab.model;

import java.io.Serializable;
import java.util.List;
import java.util.List;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */

public interface Organism extends Serializable {
    /**
     * The default database in case it is not specified.
     */
    String DEFAULT_DATABASE = "taxid";

    void addIdentifier( CrossReference ref );

    List<CrossReference> getIdentifiers();

    void setIdentifiers( List<CrossReference> identifiers );

    String getTaxid();
}
