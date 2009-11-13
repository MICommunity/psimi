package psidev.psi.mi.xml.xmlindex;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean allowing to store the result of the indexing of an XML file.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlFileIndex {

    private File file;

    private Map<Integer, InputStreamRange> experimentId2position = new HashMap<Integer, InputStreamRange>();
    private Map<Integer, InputStreamRange> interactorId2position = new HashMap<Integer, InputStreamRange>();
    private Map<Integer, InputStreamRange> interactionId2position = new HashMap<Integer, InputStreamRange>();
    private Map<Integer, InputStreamRange> participantId2position = new HashMap<Integer, InputStreamRange>();
    private Map<Integer, InputStreamRange> featureId2position = new HashMap<Integer, InputStreamRange>();

    //////////////////
    // Constructors

    public PsimiXmlFileIndex( File file ) {
        if ( file == null ) {
            throw new IllegalArgumentException();
        }
        this.file = file;
    }

    ///////////////////////////
    // experiments

    public Map<Integer, InputStreamRange> getExperimentId2position() {
        return Collections.unmodifiableMap( experimentId2position );
    }

    public void addExperiment( int experimentId, InputStreamRange position ) {
        experimentId2position.put( experimentId, position );
    }

    public InputStreamRange getExperimentPosition( int experimentId ) {
        return experimentId2position.get( experimentId );
    }

    public int getExperimentCount() {
        return experimentId2position.size();
    }

    ///////////////////////////
    // interactor

    public Map<Integer, InputStreamRange> getInteractorId2position() {
        return Collections.unmodifiableMap( interactorId2position );
    }

    public void addInteractor( int interactorId, InputStreamRange position ) {
        interactorId2position.put( interactorId, position );
    }

    public InputStreamRange getInteractorPosition( int interactorId ) {
        return interactorId2position.get( interactorId );
    }

    public int getInteractorCount() {
        return interactorId2position.size();
    }

    ///////////////////////////
    // interaction

    public Map<Integer, InputStreamRange> getInteractionId2position() {
        return Collections.unmodifiableMap( interactionId2position );
    }

    public void addInteraction( int interactionId, InputStreamRange position ) {
        interactionId2position.put( interactionId, position );
    }

    public InputStreamRange getInteractionPosition( int interactionId ) {
        return interactionId2position.get( interactionId );
    }

    public int getInteractionCount() {
        return interactionId2position.size();
    }

    ///////////////////////////
    // participant

    public Map<Integer, InputStreamRange> getParticipantId2position() {
        return Collections.unmodifiableMap( participantId2position );
    }

    public void addParticipant( int participantId, InputStreamRange position ) {
        participantId2position.put( participantId, position );
    }

    public InputStreamRange getParticipantPosition( int participantId ) {
        return participantId2position.get( participantId );
    }

    public int getParticipantCount() {
        return participantId2position.size();
    }

    ///////////////////////////
    // feature

    public Map<Integer, InputStreamRange> getFeatureId2position() {
        return Collections.unmodifiableMap( featureId2position );
    }

    public void addFeature( int featureId, InputStreamRange position ) {
        featureId2position.put( featureId, position );
    }

    public InputStreamRange getFeaturePosition( int featureId ) {
        return featureId2position.get( featureId );
    }

    public int getFeatureCount() {
        return featureId2position.size();
    }
}