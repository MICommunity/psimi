package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.ExperimentalEntityPool;

/**
 * Listener for changes in an experimental entity pool
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface ExperimentalEntityPoolChangeListener extends EntityPoolChangeListener<ExperimentalEntityPool>, ExperimentalEntityChangeListener<ExperimentalEntityPool> {

}
