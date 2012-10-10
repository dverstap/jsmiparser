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
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JVar;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiNotificationType;
import org.jsmiparser.smi.SmiObjectType;
import org.jsmiparser.smi.SmiOidValue;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiTrapType;
import org.jsmiparser.smi.SmiVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ModuleBuilder.class);

    private final CodeBuilderSettings settings;
    private final SmiModule module;
    private final JCodeModel codeModel;

    private JDefinedClass definedClass;
    private JFieldVar nameField;
    private JFieldVar oidStrField;
    private JFieldVar berTagValueField;

    public ModuleBuilder(CodeBuilderSettings settings, SmiModule module, JCodeModel codeModel) {
        this.settings = settings;
        this.module = module;
        this.codeModel = codeModel;
    }

    public JDefinedClass build() {
        JPackage _package = determinePackage();
        if (module.getVariables().isEmpty()) {
            // can't generate an enum without constants ...
            return null;
        }

        try {
            definedClass = _package._enum(module.getCodeId());
        } catch (JClassAlreadyExistsException e) {
            throw new RuntimeException(e.getExistingClass().fullName() + " is already defined in the code model.", e);
        }

        // TODO add a package-info.html, ideally with the complete, hyperlinked mib
        addJavadocs();
        addInterfaces();
        addEnumConstants();
        addFields();
        addConstructor();
        addGetters();
        addToString();

        return definedClass;
    }

    // TODO should probably move into SmiCodeNamingStrategy?
    protected JPackage determinePackage() {
        return codeModel._package(settings.getPackageName() + "." + module.getCodeId().toLowerCase());
    }


    protected void addJavadocs() {
        // TODO Add @link back to all the places where this is used ...
    }

    protected void addInterfaces() {
        // TODO add configurable interface
        //definedClass._implements(Serializable.class);
    }

    protected void addEnumConstants() {
        for (SmiOidValue v : module.getOidValues()) {
            if (v.getId() != null) {
                addOidValue(v);
            }
        }
    }

    private void addOidValue(SmiOidValue v) {
        SmiPrimitiveType primitiveType = determinePrimitiveType(v);

        // -1 for variables that are not scalars or columns:
        byte berValue = primitiveType != null ? primitiveType.getVarBindField().getBerTagValue() : -1;
        JEnumConstant ec = definedClass.enumConstant(v.getCodeId())
                .arg(JExpr.lit(v.getId()))
                .arg(JExpr.lit(v.getOidStr()))
                .arg(JExpr.lit(berValue));
        ec.javadoc().append("<pre>" + v.getId() + ": " + v.getOidStr() + "</pre>\n\n");
        if (primitiveType != null) {
            ec.javadoc().append("<pre>Type:" + primitiveType + "<pre>\n\n");
        }
        if (v instanceof SmiObjectType) {
            SmiObjectType objectType = (SmiObjectType) v;
            ec.javadoc().append("<pre>" + objectType.getDescription() + "</pre>");

        }
        if (v instanceof SmiNotificationType) {
        	SmiNotificationType notificationType = (SmiNotificationType) v;
        	ec.javadoc().append("<pre>" + notificationType.getDescription() + "</pre>");
        }

        // TODO add link to generated enum type, if applicable
    }

    protected SmiPrimitiveType determinePrimitiveType(SmiOidValue v) {
        if (v instanceof SmiVariable) {
            SmiVariable var = (SmiVariable) v;
            return var.getPrimitiveType();
        }
        return null;
    }

    protected void addFields() {
        nameField = definedClass.field(JMod.PRIVATE | JMod.FINAL, String.class, "name");
        oidStrField = definedClass.field(JMod.PRIVATE | JMod.FINAL, String.class, "oidStr");
        berTagValueField = definedClass.field(JMod.PRIVATE | JMod.FINAL, Byte.TYPE, "berTagValue");
    }

    protected void addConstructor() {
        JMethod constructor = definedClass.constructor(JMod.PRIVATE);
        JVar nameParam = constructor.param(String.class, "n");
        JVar oidStrParam = constructor.param(String.class, "os");
        JVar berTagValueParam = constructor.param(Integer.TYPE, "btv");
        constructor.body()
                .assign(nameField, nameParam)
                .assign(oidStrField, oidStrParam)
                .assign(berTagValueField, JExpr.cast(definedClass.owner().BYTE, berTagValueParam));
    }

    protected void addGetters() {
        addNameGetter();
        addOidStrGetter();
        addBerTagValueGetter();
    }

    protected void addNameGetter() {
        definedClass.method(JMod.PUBLIC, String.class, "getName")
                .body()._return(nameField);
    }

    // I prefer to expose the OID as a String, because that's immutable
    // The method is named getOidStr and not getOid, so gradle closures can add an additional method with that name,
    // for instance to return an SNMP4J OID instance. Since that OID class is mutable, it would be best to
    // return a new instance every time.
    protected void addOidStrGetter() {
        definedClass.method(JMod.PUBLIC, String.class, "getOidStr")
                .body()._return(oidStrField);
    }

    protected void addBerTagValueGetter() {
        definedClass.method(JMod.PUBLIC, Byte.TYPE, "getBerTagValue")
                .body()._return(berTagValueField);
    }

    protected void addToString() {
        definedClass.method(JMod.PUBLIC, String.class, "toString")
                .body()._return(nameField.plus(JExpr.lit(": ")).plus(oidStrField));
    }

}
