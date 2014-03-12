package psidev.psi.mi.jami.model;

/**
 * An participant evidence pool which is a part of an interaction evidence and may contain feature evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/10/13</pre>
 */

public interface ParticipantEvidencePool extends ParticipantPool<InteractionEvidence,FeatureEvidence,ParticipantEvidence>,ParticipantEvidence {
}
