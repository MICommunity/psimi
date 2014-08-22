package psidev.psi.mi.jami.model;

/**
 * Flag to determine the curation depth.
 * - IMEx: curation following IMEx standards
 * - MIMIx: curation following Minimum Information for Molecular Interactions
 * - undefined : no curation standards defined
 * - rapid_curation : rapid curation
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/12</pre>
 */

public enum CurationDepth {

    IMEx, MIMIx, rapid_curation, undefined
}
