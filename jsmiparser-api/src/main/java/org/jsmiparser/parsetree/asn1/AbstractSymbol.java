/*
 * Copyright 2005 Nigel Sheridan-Smith, Davy Verstappen.
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
package org.jsmiparser.parsetree.asn1;


/**
 * @author davy
 */
public abstract class AbstractSymbol implements Symbol {

    private ASNModule module_;
    private int line_;
    private int column_;

    protected AbstractSymbol(Context context) {
        super();

        module_ = context.getModule();

        line_ = context.getLine();
        column_ = context.getColumn();
    }

    public int getColumn() {
        return column_;
    }

    public int getLine() {
        return line_;
    }

    public ASNModule getModule() {
        return module_;
    }

    public void setPosition(int line, int column) {
        line_ = line;
        column_ = column;
    }
}
