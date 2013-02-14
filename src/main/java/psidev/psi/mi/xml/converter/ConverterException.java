/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter;

/**
 * Exception thrown by the Converter.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18-Jun-2006</pre>
 */
public class ConverterException extends Exception {

    public ConverterException( String message ) {
        super( message );
    }

    public ConverterException( String message, Throwable cause ) {
        super( message, cause );
    }
}