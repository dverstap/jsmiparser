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
import org.jsmiparser.smi.SmiCodeNamingStrategy;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiModule;
import org.jsmiparser.smi.SmiNamedNumber;
import org.jsmiparser.smi.SmiPrimitiveType;
import org.jsmiparser.smi.SmiType;
import org.jsmiparser.util.FileTestUtil;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.IdToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EnumBuilderTest {

    private JCodeModel codeModel;
    private DefaultCodeBuilderSettings settings;

    @Before
    public void before() {
        codeModel = new JCodeModel();
        settings = new DefaultCodeBuilderSettings();
        settings.setPackageName("mytest");
    }

    @After
    public void after() throws IOException {
        File dir = FileTestUtil.makeBuildSubDir(EnumBuilderTest.class, "jsmiparser-codegen-test");
        codeModel.build(dir, (PrintStream) null);
        codeModel = null;
    }

    @Test
    public void testMyEnum() throws JClassAlreadyExistsException {
        SmiCodeNamingStrategy namingStrategy = new StraightNamingStrategy("");
        SmiType type = new SmiType(new IdToken(null, "MyEnum"), new SmiModule(new SmiMib(null, null), new IdToken(null, "MyModule")), SmiPrimitiveType.ENUM);
        type.getModule().getMib().setCodeNamingStrategy(new StraightNamingStrategy(settings.getPackageName()));
        type.setEnumValues(Arrays.asList(new SmiNamedNumber(new IdToken(null, "enum1"), new BigIntegerToken(1)),
                new SmiNamedNumber(new IdToken(null, "enum2"), new BigIntegerToken(2))));
        JDefinedClass ec = new EnumBuilder(settings, type, codeModel).build();
        assertEquals("MyEnum", ec.name());
        assertEquals(2, getEnumConstantsByName(ec).size());
    }

    @SuppressWarnings("unchecked")
    private Map<String, JEnumConstant> getEnumConstantsByName(JDefinedClass dc) {
        try {
            Field field = JDefinedClass.class.getDeclaredField("enumConstantsByName");
            field.setAccessible(true);
            return (Map<String, JEnumConstant>) field.get(dc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
