package psidev.psi.mi.jami.datasource;

/**
 * This enum is giving topics for different parsing error types
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public enum FileSourceParsingError {

    invalid_syntax, multiple_experimental_roles, multiple_host_organisms, multiple_experiments, multiple_expressed_in,
    multiple_participant_identification_methods, multiple_interaction_types, missing_cv, clustered_content

}
