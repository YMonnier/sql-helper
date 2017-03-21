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
        for (Element element : roundEnv.getElementsAnnotatedWith(Extends.class)) {
            if (element.getKind().isClass())
                generate(element);

        }
        return true;
    }

    private void generate(Element sourceClass) {
        String fqn = ((TypeElement) sourceClass).getQualifiedName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(sourceClass).getQualifiedName().toString();
        String typeName = sourceClass.getSimpleName().toString();//.replace(".", "_") + "Manager";
        String fileName = "_" + typeName;


        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + fileName, sourceClass);

            Writer writer = file.openWriter();
            PrintWriter pw = new PrintWriter(writer);

            pw.println("package " + packageName + ";");
            pw.println("");
            pw.println("import com.ymonnier.sql.help.service.CrudService;");
            pw.println("import com.ymonnier.sql.help.service.CrudServiceBean;");
            pw.println("import com.ymonnier.sql.help.service.QueryBuilder;");
            pw.println("import javax.persistence.EntityManager;");
            pw.println("import java.util.List;");
            pw.println("import java.util.Map;");

            pw.println("");
            pw.println("public class " + fileName + " {");
            pw.println("    ");
            pw.println("    private final CrudServiceBean<" + typeName + "> service = new CrudServiceBean<" + typeName + ">();");
            pw.println("    ");
            pw.println("    public " + fileName + "() {");
            pw.println("    }");
            pw.println("    ");
            sourceClass.getEnclosedElements().forEach(attr -> {
                if (attr.getKind().isField()) {
                    if (attr.getAnnotationsByType(Attr.class).length > 0) {
                        pw.println("    public " + typeName + " findBy" + firstUpperCaseLetter(attr.toString()) + "(" + attr.asType() + " " + attr + ") {");
                        pw.println("        return null;");
                        pw.println("    }");
                        pw.println("    ");
                    }
                }
            });

            pw.println("    public EntityManager getEntityManager() {");
            pw.println("        return service.entityManager;");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public MyEntity save(" + typeName + " object) {");
            pw.println("        return service.save(object);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public MyEntity update(" + typeName + " object) {");
            pw.println("        return service.update(object);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public void delete(" + typeName + " object) {");
            pw.println("        service.delete(object);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public List findWithNamedQuery(String queryName) {");
            pw.println("        return service.findWithNamedQuery(queryName);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public List findWithNamedQuery(String queryName, int limit) {");
            pw.println("        return service.findWithNamedQuery(queryName, limit);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public List findWithNamedQuery(String queryName, Map parameters) {");
            pw.println("        return service.findWithNamedQuery(queryName, parameters, 0);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {");
            pw.println("        return service.findWithNamedQuery(namedQueryName, parameters, resultLimit);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public void close() {");
            pw.println("        service.close();");
            pw.println("    }");
            pw.println("    ");

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

    /*private void attributsProcess(Element extendsClass) {
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
            }
        });
    }*/
}
