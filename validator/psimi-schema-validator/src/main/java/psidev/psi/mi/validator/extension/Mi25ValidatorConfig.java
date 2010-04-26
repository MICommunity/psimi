package psidev.psi.mi.validator.extension;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class Mi25ValidatorConfig {

    private String lineSeparator;

    private String InteractionDetectionMethod2ParticipantIdentificationMethod = "/InteractionDetectionMethod2ParticipantIdentificationMethod.tsv";

    public Mi25ValidatorConfig() {
        lineSeparator = System.getProperty( "line.separator" );
    }
}
