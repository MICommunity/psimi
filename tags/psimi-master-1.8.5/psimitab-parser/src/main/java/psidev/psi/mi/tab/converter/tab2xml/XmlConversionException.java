/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.tab2xml;

/**
 * When an error occurs during TAB convertion.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class XmlConversionException extends Exception {
    public XmlConversionException( String s ) {
        super(s);
    }

    public XmlConversionException( String s, Exception e ) {
        super(s,e);
    }
}