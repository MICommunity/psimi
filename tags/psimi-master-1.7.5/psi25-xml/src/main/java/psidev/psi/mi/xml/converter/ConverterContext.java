/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter;

import psidev.psi.mi.xml.converter.config.PsimiXmlConverterConfig;


/**
 * Makes the configuration available to the current thread (through ThreadLocal).
 *
 * @author Arnaud Ceol
 * @version $Id$
 */
public class ConverterContext extends Exception {


	private PsimiXmlConverterConfig converterConfig;


	public static ConverterContext getInstance() {
        return instance.get();
    }

    private static ThreadLocal<ConverterContext> instance = new ThreadLocal<ConverterContext>() {
        @Override
        protected ConverterContext initialValue() {
            return new ConverterContext();
        }
    };
   
	
    public PsimiXmlConverterConfig getConverterConfig() {
		return converterConfig;
	}

	public void setConverterConfig(PsimiXmlConverterConfig converterConfig) {
		this.converterConfig = converterConfig;
	}
    
}