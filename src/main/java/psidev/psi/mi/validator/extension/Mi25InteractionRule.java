package psidev.psi.mi.validator.extension;

import psidev.psi.mi.xml.model.CvType;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * <b> PSI-MI 2.5 Specific Rule </b>.
 * <p/>
 * That class is only meant to be checking on Interactions. </p>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.0
 */
public abstract class Mi25InteractionRule extends ObjectRule<Interaction> {

    //////////////////
    // Constructors

    public Mi25InteractionRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
    }

    ///////////////////////
    // ObjectRule

    public boolean canCheck(Object o) {
       return ( o != null && o instanceof Interaction );
    }

    public abstract Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException;

    ///////////////////////
    // Utility methods

    /**
     * Return the MI ontology
     *
     * @return the ontology
     * @throws ValidatorException
     */
    public OntologyAccess getMiOntology() throws ValidatorException {
        return ontologyManager.getOntologyAccess( "MI" );
    }

    /**
     * Return the MI ontology
     *
     * @return the ontology
     * @throws psidev.psi.tools.validator.ValidatorException
     */
    public Mi25Ontology getMi25Ontology() throws ValidatorException {
        return new Mi25Ontology( ontologyManager.getOntologyAccess( "MI" ) );
    }

    protected String getMiIdentifier( CvType cv ) {

        Collection<DbReference> refs = new ArrayList<DbReference>();
        if( cv.getXref() != null ) {
            refs.add( cv.getXref().getPrimaryRef() );
            refs.addAll( cv.getXref().getSecondaryRef() );

            for ( Iterator<DbReference> iterator = refs.iterator(); iterator.hasNext(); ) {
                DbReference ref = iterator.next();

                if ( "MI:0488".equals( ref.getDbAc() ) || "psi-mi".equals( ref.getDb() ) ) {
                    if ( "MI:0356".equals( ref.getRefTypeAc() ) || "identity".equals( ref.getRefType() ) ) {
                        return ref.getId();
                    }
                }
            }
        }

        return null;
    }
}