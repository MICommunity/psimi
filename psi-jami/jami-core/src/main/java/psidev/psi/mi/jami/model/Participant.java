package psidev.psi.mi.jami.model;

/**
 * Participant identified in an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Participant<I extends Interaction, F extends Feature> extends Entity<F> {

    public static final String UNSPECIFIED_ROLE = "unspecified role";
    public static final String UNSPECIFIED_ROLE_MI = "MI:0499";
    public static final String PUTATIVE_SELF_ROLE = "putative self";
    public static final String PUTATIVE_SELF_ROLE_MI = "MI:0898";
    public static final String SELF_ROLE = "self";
    public static final String SELF_ROLE_MI = "MI:0503";
    public static final String BAIT_ROLE = "bait";
    public static final String BAIT_ROLE_MI = "MI:0496";
    public static final String FLUORESCENCE_DONOR_ROLE = "fluorescence donor";
    public static final String FLUORESCENCE_DONOR_ROLE_MI = "MI:0583";
    public static final String SUPPRESSOR_GENE_ROLE = "suppressor gene";
    public static final String SUPPRESSOR_GENE_ROLE_MI = "MI:0581";
    public static final String ENZYME_ROLE_MI = "MI:0501";
    public static final String ENZYME_ROLE = "enzyme";
    public static final String DONOR_ROLE_MI = "MI:0918";
    public static final String DONOR_ROLE = "donor";
    public static final String ELECTRON_DONOR_ROLE_MI = "MI:0579";
    public static final String ELECTRON_DONOR_ROLE = "electron donor";
    public static final String PHOSPHATE_DONOR_ROLE_MI = "MI:0842";
    public static final String PHOSPHATE_DONOR_ROLE = "phosphate donor";
    public static final String PHOTON_DONOR_ROLE_MI = "MI:1084";
    public static final String PHOTON_DONOR_ROLE = "photon donor";

    /**
     * Sets the Interaction and add the new Participant to its list of Participants.
     * If the give interaction is null, it will remove the Participant from the previous interaction it was attached to
     * @param interaction : interaction
     */
    public void setInteractionAndAddParticipant(I interaction);

    /**
     * The interaction in which the participant is involved.
     * It can be null if the participant is not attached to any interactions. It can happen if the participant has been removed from an interaction and is now invalid.
     * @return the interaction
     */
    public I getInteraction();

    /**
     * Sets the interaction.
     * @param interaction : interaction
     */
    public void setInteraction(I interaction);
}
