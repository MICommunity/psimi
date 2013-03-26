package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.datasource.DataSourceError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Utility class for Molecular interaction datasources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class MolecularInteractionDataSourceUtils {

    /**
     * Collect all data source errors having same error type
     * @param errors
     * @param errorType
     * @return
     */
    public static Collection<DataSourceError> collectAllDataSourceErrorsHavingErrorType(Collection<? extends DataSourceError> errors, String errorType){

        if (errors == null || errors.isEmpty() || errorType == null){
            return Collections.EMPTY_LIST;
        }
        Collection<DataSourceError> filteredErrors = new ArrayList<DataSourceError>(errors);

        for (DataSourceError err : errors){
            if (err.getLabel() != null && err.getLabel().equalsIgnoreCase(errorType)){
                filteredErrors.add(err);
            }
        }

        return filteredErrors;
    }
}
