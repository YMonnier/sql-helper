package com.ymonnier.sql.help.generator.processors;


import com.ymonnier.sql.help.generator.annotations.Attr;
import com.ymonnier.sql.help.generator.annotations.Extends;
import com.ymonnier.sql.help.service.QueryBuilder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.persistence.Query;
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
        roundEnv.getElementsAnnotatedWith(Extends.class)
                .stream()
                .filter(element -> element.getKind().isClass())
                .forEach(this::generate);
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
            pw.println("import javax.persistence.Query;");
            pw.println("import java.util.List;");
            pw.println("import java.util.Map;");


            pw.println("");
            pw.println("public class " + fileName + " {");
            pw.println("    ");
            pw.println("    private final CrudServiceBean<" + typeName + "> service;");
            pw.println("    ");
            pw.println("    public " + fileName + "() {");
            pw.println("        service = new CrudServiceBean<" + typeName + ">();");
            pw.println("    }");
            pw.println("    ");

            pw.println("    public " + fileName + "(EntityManager em) {");
            pw.println("        service = new CrudServiceBean<" + typeName + ">(em);");
            pw.println("    }");
            pw.println("    ");


            pw.println("    public EntityManager getEntityManager() {");
            pw.println("        return service.entityManager;");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public " + typeName + " save(" + typeName + " object) {");
            pw.println("        return service.save(object);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public " + typeName + " update(" + typeName + " object) {");
            pw.println("        return service.update(object);");
            pw.println("    }");
            pw.println("    ");

            //pw.println("    @Override");
            pw.println("    public void delete(" + typeName + " object) {");
            pw.println("        service.delete(object);");
            pw.println("    }");
            pw.println("    ");

            sourceClass.getEnclosedElements().forEach(attr -> {
                if (attr.getKind().isField()) {
                    if (attr.getAnnotationsByType(Attr.class).length > 0) {
                        pw.println("    public QueryBuilder<" + typeName + "> findBy" + firstUpperCaseLetter(attr.toString()) + "(" + attr.asType() + " " + attr + ") {");
                        pw.println("        return find(\"" + attr.toString() + "\", " + attr.toString() + ");");
                        pw.println("    }");
                        pw.println("    ");
                    }
                }
            });

            pw.println("    public QueryBuilder<" + typeName + "> findWithNamedQuery(String namedQueryName) {");
            pw.println("        return service.findWithNamedQuery(namedQueryName);");
            pw.println("    }");
            pw.println("    ");

            pw.println("    public QueryBuilder<" + typeName + "> findWithQuery(String query) {");
            pw.println("        return service.findWithQuery(query);");
            pw.println("    }");
            pw.println("    ");

            pw.println("    private QueryBuilder<" + typeName + "> find(String name, Object value) {");
            pw.println("        return service.findWithQuery(\"SELECT o FROM " + typeName + " o where o.\" + name + \" = :\" + name + \"\")");
            pw.println("                      .where(name, value);");
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
}
