/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

/**
 * Simple cross reference.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class CrossReferenceImpl extends DefaultXref implements CrossReference {

	/**
	 * Generated with IntelliJ plugin generateSerialVersionUID.
	 * To keep things consistent, please use the same thing.
	 */
	private static final long serialVersionUID = 3681849842863471840L;

	//////////////////////////
	// Constructor

	public CrossReferenceImpl() {
        super(new DefaultCvTerm("unknown"),"unknown");
	}

	public CrossReferenceImpl(String database, String identifier) {
		super(new DefaultCvTerm(database), identifier);
	}

	public CrossReferenceImpl(String database, String identifier, String text) {
		this(database, identifier);
		setText(text);
	}

	/////////////////////////
	// Getters and Setters

	public String getDatabaseName() {
		return this.database.getShortName();
	}

	public void setDatabaseName(String database) {
		if (database == null) {
			throw new IllegalArgumentException("You must give a non null database.");
		}
		database = database.trim();
		if (database.length() == 0) {
			throw new IllegalArgumentException("You must give a non empty database.");
		}

		this.database = new DefaultCvTerm(database);
	}

	public String getIdentifier() {
		return id;
	}

	public void setIdentifier(String identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException("You must give a non null identifier.");
		}
		identifier = identifier.trim();
		if (identifier.length() == 0) {
			throw new IllegalArgumentException("You must give a non empty identifier.");
		}

		this.id = identifier;
	}

	public String getText() {
		return qualifier != null ? qualifier.getShortName() : null;
	}

	public void setText(String text) {
		if (text != null) {
			// ignore empty string
			text = text.trim();
			if (text.length() == 0) {
				text = null;
			}
            this.qualifier = new DefaultCvTerm(text);
		}
        else {
            this.qualifier = null;
        }
	}

	public boolean hasText() {
		return (qualifier != null && qualifier.getShortName().trim().length() > 0);
	}

	//////////////////////////
	// Object's override

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("CrossReference");
		sb.append("{database='").append(database).append('\'');
		sb.append(", identifier='").append(id).append('\'');
		if (qualifier != null) {
			sb.append(", text='").append(qualifier.getShortName()).append('\'');
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final CrossReferenceImpl that = (CrossReferenceImpl) o;

		if (!database.getShortName().equals(that.database.getShortName())) {
			return false;
		}
		if (!id.equals(that.id)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = database.getShortName().hashCode();
		result = 29 * result + id.hashCode();
		return result;
	}
}