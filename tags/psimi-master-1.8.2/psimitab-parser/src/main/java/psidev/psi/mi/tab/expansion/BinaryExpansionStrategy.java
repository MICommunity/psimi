/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.expansion;

import psidev.psi.mi.xml.model.ExperimentalRole;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import java.util.Collection;

/**
 * ABstraction of an expansion strategy.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13-Oct-2006</pre>
 */
public abstract class BinaryExpansionStrategy implements ExpansionStrategy {


	private static final String SELF_PSI_REF = "MI:0503";
	private static final String PUTATIVE_SELF_PSI_REF = "MI:0898";

	/**
	 * Determines is the interaction is binary. That is involve exactly two participants.
	 *
	 * @param interaction the interaction to check on.
	 * @return true if the interaction is binary.
	 */
	protected boolean isBinary(Interaction interaction) {

		if (interaction == null) {
			throw new IllegalArgumentException("Interaction must not be null.");
		}

		if (interaction.getParticipants().size() == 2) {
			return true;
		}

		return false;
	}

	/**
	 * Build a new Interaction with no participants but holding a copy of all other attributes of the given interaction.
	 * <br/> WARNING: all attributes of the newly created interaction are the same as the source's (that is same object
	 * instance). This is not an object clone.
	 *
	 * @param source the source interaction.
	 * @return a target interaction which is a copy of the source interaction but the participants or
	 *         inferredInteractions.
	 */
	protected Interaction copyInteraction(Interaction source) {

		Interaction target = new Interaction();

		// clone inner attributes
		target.setId(source.getId());
		target.setImexId(source.getImexId());

		// objects
		target.setNames(source.getNames());
		target.setXref(source.getXref());

		// collections
		target.getConfidences().addAll(source.getConfidences());
		target.getExperiments().addAll(source.getExperiments());
		target.getInteractionTypes().addAll(source.getInteractionTypes());

		// added with mitab 27
		target.setModelled(source.isModelled());
		target.setIntraMolecular(source.isIntraMolecular());
		target.setNegative(source.isNegative());
		target.getParameters().addAll(source.getParameters());
		target.getAttributes().addAll(source.getAttributes());

		target.setAvailability(source.getAvailability());


		return target;
	}

	/**
	 * Builds a new interaction object based the given interaction template.
	 * <br/> Participants are replaced by the two given ones.
	 *
	 * @param interactionTemplate the interaction template.
	 * @param p1                  participant to add to the newly created interaction.
	 * @param p2                  participant to add to the newly created interaction.
	 * @return a new interaction having p1 and p2 as participant.
	 */
	protected Interaction buildInteraction(final Interaction interactionTemplate, Participant p1, Participant p2) {
		// We do not alter the given interaction template but copy it
		Interaction i = copyInteraction(interactionTemplate);

		// add cloned participant
		i.getParticipants().add(p1);
		i.getParticipants().add(p2);

		return i;
	}

	public InteractionCategory findInteractionCategory(Interaction interaction) {

		if (interaction.getParticipants().size() == 1) {
			Participant c = interaction.getParticipants().iterator().next();

			// we have a self interaction but inter molecular
			if (interaction.isIntraMolecular()){
				return InteractionCategory.self_intra_molecular;
			}
			else {
				return InteractionCategory.self_inter_molecular;
			}
		} else if (interaction.getParticipants().size() == 2) {
			return InteractionCategory.binary;
		} else if (interaction.getParticipants().size() > 2) {
			return InteractionCategory.n_ary;
		}

		return null;
	}

	protected boolean containsRole(Collection<ExperimentalRole> experimentalRoles, String[] rolesToFind) {
		if (experimentalRoles != null) {
			for (ExperimentalRole expRole : experimentalRoles) {
				if (expRole.getXref() != null && expRole.getXref().getPrimaryRef()!=null) {
					String ac =  expRole.getXref().getPrimaryRef().getRefTypeAc();
					for (String roleToFind : rolesToFind) {
						if (roleToFind.equals(ac)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}