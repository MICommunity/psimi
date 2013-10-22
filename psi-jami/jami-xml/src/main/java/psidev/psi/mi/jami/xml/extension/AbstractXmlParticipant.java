package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Abstract class for XmlParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlParticipant<I extends Interaction, F extends Feature> extends AbstractXmlEntity<F> implements Participant<I,F>{

    private I interaction;

    public AbstractXmlParticipant() {
        super();
    }

    public AbstractXmlParticipant(Interactor interactor) {
        super(interactor);
    }

    public AbstractXmlParticipant(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public AbstractXmlParticipant(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public AbstractXmlParticipant(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public void setInteractionAndAddParticipant(I interaction) {

        if (this.interaction != null){
            this.interaction.removeParticipant(this);
        }

        if (interaction != null){
            interaction.addParticipant(this);
        }
    }

    public I getInteraction() {
        return this.interaction;
    }

    public void setInteraction(I interaction) {
        this.interaction = interaction;
    }
}
