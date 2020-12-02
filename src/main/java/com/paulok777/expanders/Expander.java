package com.paulok777.expanders;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

public class Expander {

    private static final String JAVA_COMMAND = "java ";
    private static final String COMPILATION_EXCEPTION = "Compile failed";
    private static final String RUNTIME_EXCEPTION = "Runtime failed";

    public static void execute(File file) {
        compile(file.getName());
        runCompiledFile(file.getName().substring(0, file.getName().length() - 5));
    }

    private static void compile(String fileToCompile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(System.in, System.out, System.err, fileToCompile);
        if (compilationResult != 0) {
            System.out.println(COMPILATION_EXCEPTION);
            System.exit(1);
        }
    }

    private static void runCompiledFile(String fileToRun) {
        try {
            Process p = Runtime.getRuntime().exec(JAVA_COMMAND + fileToRun);
            InputStream errIn = p.getErrorStream();
            InputStream in = p.getInputStream();
            BufferedReader errorOutput = new BufferedReader(new InputStreamReader(errIn));
            BufferedReader output = new BufferedReader(new InputStreamReader(in));

            String line1;
            String line2 = null;

            while ((line1 = errorOutput.readLine()) != null ||
                    (line2 = output.readLine()) != null) {
                if(line1 != null) System.out.println(line1);
                if(line2 != null) System.out.println(line2);
            }

            errorOutput.close();
            output.close();
        } catch (IOException e) {
            System.out.println(RUNTIME_EXCEPTION);
            System.exit(1);
        }
    }
}
