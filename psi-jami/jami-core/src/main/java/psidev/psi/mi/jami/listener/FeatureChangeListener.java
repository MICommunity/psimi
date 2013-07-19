package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface FeatureChangeListener extends EventListener{

    public void onShortNameUpdate(Feature feature, String oldShortName);

    public void onFullNameUpdate(Feature feature, String oldFullName);

    public void onInterproUpdate(Feature feature, String oldInterpro);

    public void onTypeUpdate(Feature feature , CvTerm oldType);

    public void onInteractionEffectUpdate(Feature feature , CvTerm oldInteractionEffect);

    public void onInteractionDependencyUpdate(Feature feature , CvTerm oldInteractionDependency);

    public void onParticipantUpdate(Feature feature , Participant participant);

    public void onAddedIdentifier(Feature feature, Xref added);

    public void onRemovedIdentifier(Feature feature, Xref removed);

    public void onAddedXref(Feature feature, Xref added);

    public void onRemovedXref(Feature feature, Xref removed);

    public void onAddedAnnotation(Feature feature, Annotation added);

    public void onRemovedAnnotation(Feature feature, Annotation removed);

    public void onAddedRange(Feature feature, Range added);

    public void onRemovedRange(Feature feature, Range removed);


}
