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
package psidev.psi.mi.jami.enricher.util.diff;

import java.util.LinkedList;
import java.util.List;

/**
 * Compares Strings to find differences. This is kind of a wrapper of the diff_match_patch class
 * created by google.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: DiffCalculator.java 11649 2008-06-24 15:25:14Z baranda $
 */
public class DiffCalculator {

    private diff_match_patch diffMatchPatch;

    public DiffCalculator() {
        diffMatchPatch = new diff_match_patch();
    }

    public List<Diff> calculateDiffs(String str1, String str2) {
        return calculateDiffs(str1, str2, false);
    }

    public List<Diff> calculateDiffs(String str1, String str2, boolean checkLines) {
        if (str1 == null) throw new NullPointerException("String 1 is null");
        if (str2 == null) throw new NullPointerException("String 2 is null");

        final LinkedList<diff_match_patch.Diff> mainDiffs = diffMatchPatch.diff_main(str1, str2, checkLines);

        int globalIndexA = 0;
        int globalIndexB = 0;

        List<Diff> diffs = new LinkedList<Diff>();

        for (diff_match_patch.Diff mainDiff : mainDiffs) {

            int indexA = -1;
            int indexB = -1;

            switch (mainDiff.operation) {
                case EQUAL:
                    indexA = globalIndexA;
                    indexB = globalIndexB;
                    globalIndexA = globalIndexA + mainDiff.text.length();
                    globalIndexB = globalIndexB + mainDiff.text.length();
                    break;
                case INSERT:
                    indexB = globalIndexB;
                    globalIndexB = globalIndexB + mainDiff.text.length();
                    break;
                case DELETE:
                    indexA = globalIndexA;
                    globalIndexA = globalIndexA + mainDiff.text.length();
                    break;
            }

            diffs.add(new Diff(mainDiff, indexA, indexB));
        }

        return diffs;
    }

    public void setTimeOut(float timeout) {
        diffMatchPatch.Diff_Timeout = timeout;
    }

}
