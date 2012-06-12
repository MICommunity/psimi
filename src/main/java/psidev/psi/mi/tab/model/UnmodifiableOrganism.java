package psidev.psi.mi.tab.model;

import java.util.Collection;
import java.util.Collections;

/**
 * InteractionDetectionMethod that does not authorize users to modify them.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class UnmodifiableOrganism implements Organism {

    private Organism organism;

    ////////////////////
    // Constructor

    public UnmodifiableOrganism( Organism organism ) {
        if( organism == null ) {
            throw new IllegalArgumentException( "You must give a non null organism" );
        }
        this.organism = organism;
    }

    ///////////////////
    // Getters

    public Collection<CrossReference> getIdentifiers() {
        return Collections.unmodifiableCollection( organism.getIdentifiers() );
    }

    public String getTaxid() {
        return organism.getTaxid();
    }

    ///////////////////
    // Setters

    public void addIdentifier( CrossReference ref ) {
        throw new ForbiddenOperationException( "If you need to alter Organism, you should disable Organism pooling." );
    }

    public void setIdentifiers( Collection<CrossReference> identifiers ) {
        organism.setIdentifiers( identifiers );
    }
}