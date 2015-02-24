package psidev.psi.mi.validator.extension;

import psidev.psi.mi.jami.datasource.FileSourceLocator;
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
public class MiContext extends Context {

    //////////////////////////
    // Instance variable

    private String objectLabel;

    private FileSourceLocator locator;

    private List<MiContext> associatedContexts;

    //////////////////
    // Constructors

    public MiContext(String context) {
        super( context );
    }

    public MiContext() {
        super( null );
    }

    public MiContext(FileSourceLocator locator) {
        super( locator != null ? locator.toString() : null);
        this.locator = locator;
    }

    ///////////////////////////
    // Getters and Setters


    public List<MiContext> getAssociatedContexts() {
        if (associatedContexts == null){
            associatedContexts = new ArrayList<MiContext>();
        }
        return associatedContexts;
    }

    public void addAssociatedContext(MiContext context) {
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

    public MiContext copy() {
        MiContext clone = new MiContext( this.getContext() );

        clone.setLocator(this.getLocator());
        clone.setObjectLabel(this.getObjectLabel());
        clone.getAssociatedContexts().addAll(this.getAssociatedContexts());

        return clone;
    }

    public boolean isContextWithAnchor(){
        if (objectLabel == null){
            return false;
        }
        return (objectLabel.equals("interaction") || objectLabel.equals("experiment") ||
                objectLabel.equals("participant") || objectLabel.equals("feature")
        || objectLabel.equals("interactor"));
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
            sb.append( "[" ).append( locator.toString()).append( "']" );
        }

        sb.append( " )" );
        return sb.toString();
    }
}