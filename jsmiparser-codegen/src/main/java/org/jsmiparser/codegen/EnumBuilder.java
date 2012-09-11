/*
 * Copyright 2012 Davy Verstappen.
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
package org.jsmiparser.codegen;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JVar;
import org.jsmiparser.smi.SmiNamedNumber;
import org.jsmiparser.smi.SmiType;

import java.util.Iterator;

public class EnumBuilder {

    protected final CodeBuilderSettings settings;
    protected final SmiType type;
    protected final String typeName;
    protected final JCodeModel codeModel;

    protected JDefinedClass definedClass;
    protected JFieldVar nameField;
    protected JFieldVar valueField;

    public EnumBuilder(CodeBuilderSettings settings, SmiType type, JCodeModel codeModel) {
        this(settings, type, type.getId(), codeModel);
    }

    public EnumBuilder(CodeBuilderSettings settings, SmiType type, String typeName, JCodeModel codeModel) {
        this.settings = settings;
        this.type = type;
        this.typeName = typeName;
        this.codeModel = codeModel;
    }

    public JDefinedClass build() {
        JPackage _package = determinePackage();

        try {
            definedClass = _package._enum(typeName);
        } catch (JClassAlreadyExistsException e) {
            throw new RuntimeException(e.getExistingClass().fullName() + " is already defined in the code model.", e);
        }

        addJavadocs();
        addInterfaces();
        addEnumConstants();
        addFields();
        addConstructor();
        addNameGetter();
        addValueGetter();
        // TODO add static findValue method: use a case statement, the compiler should be able to convert that into a binary search
        // alternative: use the holder pattern to lazily and thread-safely initialize (and perhaps expose, but read-only) a data structure
        addToString();

        return definedClass;
    }

    protected JPackage determinePackage() {
        // Different modules can contain enums with the same name (sometimes with the same enums, sometimes not),
        // therefore the module name is included in the package name.
        return codeModel._package(settings.getPackageName() + "." + type.getModule().getCodeId().toLowerCase());
    }

    // TODO Add @link back to all the places where this is used ...
    // TODO make sure it's clear whether this is for an enum or for a bits type.
    protected void addJavadocs() {
        JDocComment c = definedClass.javadoc();
        c.add("<pre>\n");
        for (Iterator<SmiNamedNumber> iterator = type.getNamedNumbers().iterator(); iterator.hasNext(); ) {
            SmiNamedNumber namedNumber = iterator.next();
            c.add(namedNumber.toString());
            if (iterator.hasNext()) {
                c.add(",");
            }
            c.add("\n");
        }
        c.add("</pre>\n");
    }

    protected void addInterfaces() {
        // TODO add configurable interface
        //definedClass._implements(Serializable.class);
    }

    protected void addEnumConstants() {
        // TODO optionally add an ILLEGAL_VALUE constant, which could be returned from the findValue() method
        for (SmiNamedNumber namedNumber : type.getNamedNumbers()) {
            JEnumConstant ec = definedClass.enumConstant(namedNumber.getCodeId());
            ec.arg(JExpr.lit(namedNumber.getId()));
            ec.arg(JExpr.lit(namedNumber.getValue().intValue()));
            ec.javadoc().add("<pre>" + namedNumber.toString() + "</pre>");
        }
    }

    protected void addFields() {
        nameField = definedClass.field(JMod.PRIVATE | JMod.FINAL, String.class, "name");
        valueField = definedClass.field(JMod.PRIVATE | JMod.FINAL, Integer.TYPE, "value");
    }

    protected void addConstructor() {
        JMethod constructor = definedClass.constructor(JMod.PRIVATE);
        JVar nameParam = constructor.param(String.class, "n");
        JVar valueParam = constructor.param(Integer.TYPE, "v");
        constructor.body()
                .assign(nameField, nameParam)
                .assign(valueField, valueParam);
    }

    protected void addNameGetter() {
        definedClass.method(JMod.PUBLIC, String.class, "getName")
                .body()._return(nameField);
    }

    protected void addValueGetter() {
        definedClass.method(JMod.PUBLIC, Integer.TYPE, "getValue")
                .body()._return(valueField);
    }

    protected void addToString() {
        definedClass.method(JMod.PUBLIC, String.class, "toString")
                .body()._return(nameField.plus(JExpr.lit("(")).plus(valueField).plus(JExpr.lit(")")));
    }

}
