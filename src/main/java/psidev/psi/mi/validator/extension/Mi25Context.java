package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
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

    private String objectLabel;

    private FileSourceLocator locator;

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
        return locator != null ? locator.getLineNumber() : -1;
    }

    public String getObjectLabel() {
        return objectLabel;
    }

    public void setObjectLabel(String objectLabel) {
        this.objectLabel = objectLabel;
    }

    public int getColumnNumber() {
        return locator != null ? locator.getLineNumber() : -1;
    }

    public FileSourceLocator getLocator() {
        return locator;
    }

    public void setLocator(FileSourceLocator locator) {
        this.locator = locator;
    }

    public void extractFileContextFrom(FileSourceContext element) {
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

        this.locator = element.getSourceLocator();
    }

    public void extractFileContextOnlyFrom(FileSourceContext element) {
        this.locator = element.getSourceLocator();
    }

    public void extractLabelFrom(Object element) {
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

    public Mi25Context copy() {
        Mi25Context clone = new Mi25Context( this.getContext() );

        clone.setLocator(this.getLocator());
        clone.setObjectLabel(this.getObjectLabel());
        clone.getAssociatedContexts().addAll(this.getAssociatedContexts());

        return clone;
    }

    ////////////////////////
    // toString

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append( "Context(" );

        if ( objectLabel != null ) {
            sb.append( " "+objectLabel );
        }

        if ( locator != null ) {
            sb.append( "[" ).append( locator.getLocationDescription() ).append( "']" );
        }

        sb.append( " )" );
        return sb.toString();
    }

}