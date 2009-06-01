package psidev.psi.mi.validator.client.gui.view.validator;

/**
 * Define the different state of a validator.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17-Jan-2006</pre>
 */
public enum ValidationStatus {

    NOT_STARTED( "Not Started" ),
    IN_PROGRESS( "In progress" ),
    FAILED( "Failed" ),
    COMPLETED( "Completed" );

    private String name;

    ValidationStatus( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}