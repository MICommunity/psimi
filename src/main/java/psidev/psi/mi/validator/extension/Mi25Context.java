package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.xml.model.HasId;
import psidev.psi.tools.validator.Context;

import java.util.ArrayList;
import java.util.List;

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

    private int lineNumber = -1;
    private int columnNumber = -1;

    private String objectLabel;

    private int id = -1;

    private List<Mi25Context> associatedContexts;

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


    public List<Mi25Context> getAssociatedContexts() {
        if (associatedContexts == null){
            associatedContexts = new ArrayList<Mi25Context>();
        }
        return associatedContexts;
    }

    public void addAssociatedContext(Mi25Context context) {
        if (context != null){
            getAssociatedContexts().add(context);
        }
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber( int lineNumber ) {
        this.lineNumber = lineNumber;
    }

    public int getId() {
        return id;
    }

    public String getObjectLabel() {
        return objectLabel;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setObjectLabel(String objectLabel) {
        this.objectLabel = objectLabel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void extractFileContextFrom(FileSourceContext element) {
        if (element instanceof HasId){
            HasId hasId = (HasId) element;
            if ( element instanceof InteractionEvidence) {
                this.id = hasId.getId();
                this.objectLabel = "interaction";
            } else if ( element instanceof psidev.psi.mi.jami.model.Interactor) {
                this.id = hasId.getId();
                this.objectLabel = "interactor";
            } else if ( element instanceof ParticipantEvidence) {
                this.id = hasId.getId();
                this.objectLabel = "participant";
            } else if ( element instanceof Experiment) {
                this.id = hasId.getId();
                this.objectLabel = "experiment";
            } else if ( element instanceof FeatureEvidence) {
                this.id = hasId.getId();
                this.objectLabel = "feature";
            }
        }
        else{
            if ( element instanceof InteractionEvidence) {
                this.objectLabel = "interaction";
            } else if ( element instanceof psidev.psi.mi.jami.model.Interactor) {
                this.objectLabel = "interactor";
            } else if ( element instanceof ParticipantEvidence) {
                this.objectLabel = "participant";
            } else if ( element instanceof Experiment) {
                this.objectLabel = "experiment";
            } else if ( element instanceof FeatureEvidence) {
                this.objectLabel = "feature";
            }
        }
        if (element.getSourceLocator() != null){
            setLineNumber(element.getSourceLocator().getLineNumber());
            setColumnNumber(element.getSourceLocator().getLineNumber());
        }
    }

    public void extractFileContextOnlyFrom(FileSourceContext element) {
        if (element instanceof HasId){
            HasId hasId = (HasId) element;
            this.id = hasId.getId();
        }

        if (element.getSourceLocator() != null){
            setLineNumber(element.getSourceLocator().getLineNumber());
            setColumnNumber(element.getSourceLocator().getLineNumber());
        }
    }

    public void extractIdAndLabelFrom(HasId element) {
        if ( element instanceof InteractionEvidence) {
            this.id = element.getId();
            this.objectLabel = "interaction";
        } else if ( element instanceof psidev.psi.mi.jami.model.Interactor) {
            this.id = element.getId();
            this.objectLabel = "interactor";
        } else if ( element instanceof ParticipantEvidence) {
            this.id = element.getId();
            this.objectLabel = "participant";
        } else if ( element instanceof Experiment) {
            this.id = element.getId();
            this.objectLabel = "experiment";
        } else if ( element instanceof FeatureEvidence) {
            this.id = element.getId();
            this.objectLabel = "feature";
        }
    }

    public Mi25Context copy() {
        Mi25Context clone = new Mi25Context( this.getContext() );

        clone.setId(this.getId());
        clone.setObjectLabel(this.getObjectLabel());
        clone.setColumnNumber(this.getColumnNumber());
        clone.setLineNumber(this.getLineNumber());

        return clone;
    }

    ////////////////////////
    // toString

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append( "Context(" );

        if ( lineNumber != -1 ) {
            sb.append( " line: " ).append( lineNumber );
        }
        if ( columnNumber != -1 ) {
            sb.append( " column: " ).append( lineNumber );
        }

        if ( objectLabel != null ) {
            sb.append( " "+objectLabel );
        }

        if ( id != -1 ) {
            sb.append( "[id='" ).append( id ).append( "']" );
        }

        sb.append( " )" );
        return sb.toString();
    }

}