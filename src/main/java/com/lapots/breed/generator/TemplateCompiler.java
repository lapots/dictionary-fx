package com.lapots.breed.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

// singleton
public class TemplateCompiler {

    private Configuration configuration;

    public TemplateCompiler() {
        configuration = new Configuration(Configuration.VERSION_2_3_27);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/");
    }

    public void compileAndOutput(String templateFile, Map<String, Object> parameters) {
        try {
            Template template = configuration.getTemplate(templateFile);
            try(Writer out = new OutputStreamWriter(System.out)) {
                template.process(parameters, out);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void compileToFile(String templateFile, Map<String, Object> parameters, String path) {
        try {
            Template template = configuration.getTemplate(templateFile);
            try(Writer out = new FileWriter(path)) {
                template.process(parameters, out);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String compile(String templateFile, Map<String, Object> parameters) {
        try {
            Template template = configuration.getTemplate(templateFile);
            try(Writer out = new StringWriter()) {
                template.process(parameters, out);
                return out.toString();
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return "";
        }
    }
}
