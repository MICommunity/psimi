/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

/**
 * Simple cross reference.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class CrossReferenceImpl implements CrossReference {

	/**
	 * Generated with IntelliJ plugin generateSerialVersionUID.
	 * To keep things consistent, please use the same thing.
	 */
	private static final long serialVersionUID = 3681849842863471840L;

	/**
	 * Database name.
	 */
	private String database;

	/**
	 * The identifier.
	 */
	private String identifier;

	/**
	 * Optional piece of text.
	 */
	private String text;

	//////////////////////////
	// Constructor

	public CrossReferenceImpl() {
	}

	public CrossReferenceImpl(String database, String identifier) {
		setIdentifier(identifier);
		setDatabase(database);
	}

	public CrossReferenceImpl(String database, String identifier, String text) {
		this(database, identifier);
		setText(text);
	}

	/////////////////////////
	// Getters and Setters

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		if (database == null) {
			throw new IllegalArgumentException("You must give a non null database.");
		}
		database = database.trim();
		if (database.length() == 0) {
			throw new IllegalArgumentException("You must give a non empty database.");
		}

		this.database = database;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		if (identifier == null) {
			throw new IllegalArgumentException("You must give a non null identifier.");
		}
		identifier = identifier.trim();
		if (identifier.length() == 0) {
			throw new IllegalArgumentException("You must give a non empty identifier.");
		}

		this.identifier = identifier;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text != null) {
			// ignore empty string
			text = text.trim();
			if (text.length() == 0) {
				text = null;
			}
		}
		this.text = text;
	}

	public boolean hasText() {
		return (text != null && text.trim().length() > 0);
	}

	//////////////////////////
	// Object's override

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("CrossReference");
		sb.append("{database='").append(database).append('\'');
		sb.append(", identifier='").append(identifier).append('\'');
		if (text != null) {
			sb.append(", text='").append(text).append('\'');
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

		if (!database.equals(that.database)) {
			return false;
		}
		if (!identifier.equals(that.identifier)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = database.hashCode();
		result = 29 * result + identifier.hashCode();
		return result;
	}
}