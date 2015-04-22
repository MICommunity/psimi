

# Introduction #

The aim of JAMI is to provide a single java library and framework which unifies the standard formats such as PSI-MI XML and PSI-MITAB.

The JAMI model interfaces are abstracted from both formats to hide the complexity/requirements of each format. The development of softwares and tools on top of this framewrok simplifies the integration and support of the two standard formats.

It avoids endless conversions to different formats and avoid code/unit test duplicate as the code becomes more modular.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jamiIntroduction.png' />

# JAMI data model #

## Interactions ##

In the JAMI data model, we distinguish several types of interactions:
  * **Interaction Evidences**: experimental description of an interaction in a specific experiment
  * **Modelled interactions**: abstract interactions which can be supported by experimental evidences (interaction evidences). Modelled interactions are abstracted from any experimental details apart from a list of interaction evidences
  * **Complexes**: A more specific modelled interaction which can be considered as a biological entity and then can interact with other molecules

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_interactions.png' />

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIInteraction.png' />

### Participants ###

An interaction is composed of one to many participants. Each participant is wrapping an interactor which is always a biological entity and as such, does not contain any experimental details and is independent of the interaction context.

In the opposite, the participant does not live outside the interaction context. It has several properties such as features which are dependent on the interaction context.

A participant can be experimental or modelled.

A participant pool is a specific case/extension of participant. It is composed of several participant candidates, each of them can refer to an interactor and features. This enable the complex case of interactor set where we don't know which interactor in the pool interact but one of them does. As each interactor may have different sequences, a participant candidate can also have features.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIParticipant.png' />

### Features ###

Features are always attached to a participant which is always attached to an interaction. They are very dependent on the context of the interaction.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIFeature.png' />

## Binary Interactions ##

Binary interactions are an extension of Interaction and can only be composed of two participants. As for the Interaction, you can have several types of Binary Interactions:
  * **Binary Interaction Evidences**: experimental description of an interaction in a specific experiment
  * **Modelled Binary interactions**: abstract interactions which can be supported by experimental evidences (interaction evidences). Modelled interactions are abstracted

When visualising interactions in a graph or network, it is often necessary to break n-ary interactions into n-1 binary interactions.

In the JAMI data model, default complex expansion methods are provided to break n-ary interactions into several binary pairs (this could be extended if new methods are wanted):

  * **Spoke Expansion**
  * **Matrix Expansion**
  * **Bipartite Expansion**

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_binary.png' />

## Interaction Evidences ##

Interaction evidences are experimental interactions with the full details of the experiment.

An interaction evidence is always attached to one experiment.

The participants are participant evidences with experimental description such as participant identification method, experimental role, experimental preparation, parameters, confidences etc.

The participant features can be experimental but also binding sites. Experimental features can have feature detection method and parameters.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_interaction_evidence.png' />

### Publication and experiment ###

A publication can have one to many experiments. It also has a source/institution which is the organisation which curated the interactions described in this publication.

Each experiment have one interaction detection method and may have some confidence(s). Experimental can also descrive variable parameters in case of dynamic interactions.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIPublication.png' />

### Dynamic interactions and variable parameters ###

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_dynamic_interactions.png' />

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIVariableParameter.png' />

## Modelled Interactions ##

Modelled interactions are abstracted from any experimental details.

They can point to a list of interaction evidences for the experimental details.

The participants are modelled participants and do not contain any experimental details.

The participant features are biological features such as binding sites, etc. They do not contain any experimental details.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_modelled_interaction.png' />

### Cooperative Effects ###

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMICooperativeEffect.png' />

## Biological Complexes ##

Biological Complexes are an extension of Modelled Interactions and do not have any experimental details. As they are biological entities, they can interact with other molecules and can be re-used a participants of another interaction.

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_complex.png' />

## Interactor ##

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIInteractor.png' />

## Organism ##

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMIOrganism.png' />

## Simple data objects ##

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/JAMISimpleTypes.png' />

## Controlled vocabulary terms ##

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/MICvTerm.png' />

# JAMI datasources/writers #

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_datasources.png' />

# JAMI modules #

<img width='800' src='http://psimi.googlecode.com/svn/wiki/images/jami_existing_modeules.png' />