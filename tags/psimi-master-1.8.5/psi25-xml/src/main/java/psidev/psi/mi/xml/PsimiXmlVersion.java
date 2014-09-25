/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml;

/**
 * PSI-MI XML Versions.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public enum PsimiXmlVersion {

    VERSION_25_UNDEFINED( 2, 5, 0, "http://psidev.sourceforge.net/mi/rel25/src/MIF25.xsd" ),

    VERSION_253( 2, 5, 3, "http://psidev.sourceforge.net/mi/rel25/src/MIF253.xsd" ),

    VERSION_254( 2, 5, 4, "http://psidev.sourceforge.net/mi/rel25/src/MIF254.xsd" );

    private String url;
    private int level;
    private int major;
    private int minor;

    PsimiXmlVersion( int level, int major, int minor, String url ) {
        this.level = level;
        this.major = major;
        this.minor = minor;
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public String getUrl() {
        return url;
    }

    public static PsimiXmlVersion valueOfVersion(String version) {
        if ("2.5.4".equals(version)) {
            return VERSION_254;
        } else if ("2.5.3".equals(version)) {
            return VERSION_253;
        } else if ("2.5".equals(version)) {
            return VERSION_25_UNDEFINED;
        } else {
            throw new IllegalArgumentException("Unexpected PSI-MI XML version: "+version);
        }
    }
}
