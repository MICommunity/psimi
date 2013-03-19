package psidev.psi.mi.validator.extension;

import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.validator.Context;

/**
 * <b> PSI-MI 2.5 Specific Context </b>.
 * <p/>
 *
 * @author Matthias Oesterheld
 * @version $Id$
 * @since 04.01.2006; 15:56:04
 */
public class Mi25Context extends Context {

    //////////////////////////
    // Instance variable

    private long lineNumber = -1;

    private int interactionId = -1;
    private int experimentId = -1;
    private int participantId = -1;
    private int interactorId = -1;
    private int featureId = -1;

    //////////////////
    // Constructors

    public Mi25Context( String context ) {
        super( context );
    }

    public Mi25Context() {
        super( null );
    }

    ///////////////////////////
    // Getters and Setters

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber( long lineNumber ) {
        this.lineNumber = lineNumber;
    }

    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId( int interactionId ) {
        this.interactionId = interactionId;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId( int experimentId ) {
        this.experimentId = experimentId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId( int participantId ) {
        this.participantId = participantId;
    }

    public int getInteractorId() {
        return interactorId;
    }

    public void setInteractorId( int interactorId ) {
        this.interactorId = interactorId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId( int featureId ) {
        this.featureId = featureId;
    }

    public void setId( Object element ) {
        if ( element instanceof Interaction ) {
            Interaction i = ( Interaction ) element;
            this.setInteractionId( i.getId() );
        } else if ( element instanceof Interactor ) {
            Interactor i = ( Interactor ) element;
            this.setInteractorId( i.getId() );
        } else if ( element instanceof Participant ) {
            Participant p = ( Participant ) element;
            this.setParticipantId( p.getId() );
        } else if ( element instanceof ExperimentDescription ) {
            ExperimentDescription e = ( ExperimentDescription ) element;
            this.setExperimentId( e.getId() );
        } else if ( element instanceof Feature ) {
            Feature f = ( Feature ) element;
            this.setFeatureId( f.getId() );
        }
    }

    public Mi25Context copy() {
        Mi25Context clone = new Mi25Context( this.getContext() );

        clone.setExperimentId( this.getExperimentId() );
        clone.setInteractionId( this.getInteractionId() );
        clone.setInteractorId( this.getInteractorId() );
        clone.setParticipantId( this.getParticipantId() );
        clone.setFeatureId( this.getFeatureId() );
        clone.setLineNumber( this.getLineNumber() );

        return clone;
    }

    ////////////////////////
    // toString

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append( "Context(" );

        if ( lineNumber != -1 ) {
            sb.append( " line:" ).append( lineNumber );
        }

        if ( interactionId != -1 ) {
            sb.append( " interaction[id='" ).append( interactionId ).append( "']" );
        }

        if ( experimentId != -1 ) {
            sb.append( " experimentDescription[id='" ).append( experimentId ).append( "']" );
        }

        if ( participantId != -1 ) {
            sb.append( " participant[id='" ).append( participantId ).append( "']" );
        }

        if ( interactorId != -1 ) {
            sb.append( " interactor[id='" ).append( interactorId ).append( "']" );
        }

        if ( featureId != -1 ) {
            sb.append( " feature[id='" ).append( featureId ).append( "']" );
        }

        sb.append( " )" );
        return sb.toString();
    }

}