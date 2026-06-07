import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RunAircraftWar {

    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("JDK 11 or newer is required. A JRE alone cannot compile this project.");
            return;
        }

        Path outputDir = Paths.get("build", "classes");
        Files.createDirectories(outputDir);

        List<File> sourceFiles = new ArrayList<>();
        try (var stream = Files.walk(Paths.get("src", "edu"))) {
            stream.filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> sourceFiles.add(path.toFile()));
        }

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFiles);
            List<String> options = List.of("-encoding", "UTF-8", "-d", outputDir.toString());
            Boolean success = compiler.getTask(null, fileManager, null, options, null, compilationUnits).call();
            if (!Boolean.TRUE.equals(success)) {
                System.err.println("Compilation failed.");
                return;
            }
        }

        URL[] classPath = {
                outputDir.toUri().toURL(),
                Paths.get("src").toUri().toURL()
        };
        URLClassLoader classLoader = new URLClassLoader(classPath);
        Thread.currentThread().setContextClassLoader(classLoader);
        Class<?> mainClass = classLoader.loadClass("edu.hitsz.application.Main");
        Method main = mainClass.getMethod("main", String[].class);
        main.invoke(null, (Object) new String[0]);
    }
}
