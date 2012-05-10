package psidev.psi.mi.tab.model;

import java.io.Serializable;
import java.util.List;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface BinaryInteraction<T extends Interactor> extends Flippable, Serializable {
    T getInteractorA();

    void setInteractorA( T interactorA );

    T getInteractorB();

    void setInteractorB( T interactorB );

    List<InteractionDetectionMethod> getDetectionMethods();

    void setDetectionMethods( List<InteractionDetectionMethod> detectionMethods );

    List<InteractionType> getInteractionTypes();

    void setInteractionTypes( List<InteractionType> interactionTypes );

    List<CrossReference> getPublications();

    void setPublications( List<CrossReference> publications );

    List<Confidence> getConfidenceValues();

    void setConfidenceValues( List<Confidence> confidenceValues );

    List<Author> getAuthors();

    void setAuthors( List<Author> authors );

    List<CrossReference> getSourceDatabases();

    void setSourceDatabases( List<CrossReference> sourceDatabases );

    List<CrossReference> getInteractionAcs();

    void setInteractionAcs( List<CrossReference> interactionAcs );
    
    int getExpectedColumnCount();
}
