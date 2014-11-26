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

/**-
   * The data structure representing a diff is a Linked list of Diff objects:
   * {Diff(Operation.DELETE, "Hello"), Diff(Operation.INSERT, "Goodbye"),
   *  Diff(Operation.EQUAL, " world.")}
   * which means: delete "Hello", add "Goodbye" and keep " world."
   */
  public enum Operation {
    DELETE(diff_match_patch.Operation.DELETE),
    INSERT(diff_match_patch.Operation.INSERT),
    EQUAL(diff_match_patch.Operation.EQUAL);

    private diff_match_patch.Operation diffOperation;

    Operation(diff_match_patch.Operation operation) {
        this.diffOperation = operation;
    }

    public diff_match_patch.Operation getDiffOperation() {
        return diffOperation;
    }

    static Operation valueOfDiffOperation(diff_match_patch.Operation diffOperation) {
        switch (diffOperation) {
            case DELETE:
                return DELETE;
            case INSERT:
                return INSERT;
            case EQUAL:
                return EQUAL;
        }
        return null;
    }
}
