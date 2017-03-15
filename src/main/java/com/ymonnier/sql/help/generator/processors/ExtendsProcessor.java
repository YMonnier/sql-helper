package com.ymonnier.sql.help.generator.processors;


import com.ymonnier.sql.help.generator.annotations.Attr;
import com.ymonnier.sql.help.generator.annotations.Extends;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.generator.processors.
 * File ExtendsProcessor.java.
 * Created by Ysee on 14/03/2017 - 23:09.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@SupportedAnnotationTypes(value = "com.ymonnier.sql.help.generator.annotations.Extends")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ExtendsProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Process function...");

        for (Element element : roundEnv.getElementsAnnotatedWith(Extends.class)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Process...");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, element.toString());
            if (element.getKind().isClass())
                generate(element);

        }
        return false;
    }

    private void attributsProcess(Element extendsClass) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "getAttributs");

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "enclosedElements: ");
        extendsClass.getEnclosedElements().forEach(el -> {
            if (el.getKind().isField()) {
                if (el.getAnnotationsByType(Attr.class).length > 0) {

                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "    field: " + el.toString());
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "    field1: " + el.getClass().getName());
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "    field2: " + el.getClass().getCanonicalName());
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "    field3: " + el.asType());
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "    field4: " + el.asType());
                }
                /*
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "    field: " + el.toString());
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "        isAnnotedWithAttr: " + el.getClass().isAnnotationPresent(Attr.class));
                el.getAnnotationMirrors().forEach(ann -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "      ann: " + ann.getClass().getName()));
                for (Attr attr : el.getAnnotationsByType(Attr.class)) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "      attr: " + attr.annotationType().getCanonicalName());
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "      attr: " + Attr.class.getCanonicalName());
                }
                el.getModifiers().forEach(ann -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "      modifier: " + ann.toString()));
                el.getModifiers().forEach(modifier -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "      mod: " + modifier.toString()));
                el.getEnclosedElements().forEach(o -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "      oel: " + o.toString()));
                */
            }
        });

    }

    private void generate(Element sourceClass) {
        String fqn = ((TypeElement) sourceClass).getQualifiedName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(sourceClass).getQualifiedName().toString();
        String typeName = sourceClass.getSimpleName().toString();//.replace(".", "_") + "Manager";
        String fileName = "_" + typeName;

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "generate...");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, fqn);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, packageName);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, typeName);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "pp: " + packageName + "." + fileName + ".java");

        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + fileName, sourceClass);

            Writer writer = file.openWriter();
            PrintWriter pw = new PrintWriter(writer);

            pw.println("package " + packageName + ";");
            pw.println("");
            pw.println("public class " + fileName + " {");
            sourceClass.getEnclosedElements().forEach(attr -> {
                if (attr.getKind().isField()) {
                    if (attr.getAnnotationsByType(Attr.class).length > 0) {
                        pw.println("    public static " + typeName + " findBy" + firstUpperCaseLetter(attr.toString()) + "(" + attr.asType() + " " + attr + ") {");
                        pw.println("        return null;");
                        pw.println("    }");
                        pw.println("    ");
                    }
                }
            });
            pw.println("}");
            pw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String firstUpperCaseLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
