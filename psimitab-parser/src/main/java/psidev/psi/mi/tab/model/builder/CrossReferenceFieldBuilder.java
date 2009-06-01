/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.model.builder;

/**
 * Cross reference bulder.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class CrossReferenceFieldBuilder extends AbstractFieldBuilder {

    private static final String MI_PREFIX = "MI";

    /**
     * <p>Creates a MitabField object using a String. The format has to be:</p>
     *
     * <pre>
     *     A:B(C)
     *     A:B
     *     B
     * </pre>
     *
     * <p>If there are reserved character in A, B or C, they have to be escaped, surrounded by quotes. If
     * there are quotes in the value, quotes have to be escaped. Examples:</p>
     *
     * <pre>
     *     A:"B(B:B)B"(C)
     *     "A:A\"AAAA\". A":BBBB
     * </pre>
     *
     * @param str
     * @return
     */
    public Field createField(String str) {

        str = removeLineReturn(str);

        String[] groups = ParseUtils.quoteAwareSplit(str, new char[] {':', '(', ')'}, true);

        // some exception handling
        if (groups.length == 0 || groups.length > 3) {
            throw new IllegalFormatException("String cannot be parsed to create a Field (check the syntax): "+str);
        }

        // create the Field object, using the groups.
        // we have -> A:B(C)
        //    if we only have one group, we consider it to be B;
        //    if we have two groups, we have A and B
        //    and three groups is A, B and C
        Field field = null;

        switch (groups.length) {
            case 1:
                field = new Field(groups[0]);
                break;
            case 2:
                field = new Field(groups[0], groups[1]);
                break;
            case 3:
                field = new Field(groups[0], groups[1], groups[2]);
                break;
        }

        if( field != null &&
            field.getDescription() == null && field.getType() == null && String.valueOf( Column.EMPTY_COLUMN ).equals( field.getValue() )) {
            // this is an empty field,
            field = null;
        }

        // correct MI:0012(blah) to psi-mi:"MI:0012"(blah)
        if (field != null) {
            field = fixPsimiFieldfNecessary(field);
        }

        return field;
    }

    protected Field fixPsimiFieldfNecessary(Field field) {
        if ( MI_PREFIX.equals(field.getType())) {
            String identifier = field.getValue();

            return new Field("psi-mi", MI_PREFIX + ":" + identifier, field.getDescription());
        }

        return field;
    }
}
