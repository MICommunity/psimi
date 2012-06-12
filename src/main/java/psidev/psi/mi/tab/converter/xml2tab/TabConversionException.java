/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

/**
 * When an error occurs during TAB convertion.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class TabConversionException extends Exception {
    public TabConversionException( String s ) {
        super(s);
    }

    public TabConversionException( String s, Exception e ) {
        super(s,e);
    }
}