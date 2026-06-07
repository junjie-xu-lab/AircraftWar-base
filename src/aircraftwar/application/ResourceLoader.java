package aircraftwar.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class ResourceLoader {

    private ResourceLoader() {
    }

    public static InputStream open(String path) throws IOException {
        String normalized = path.replace("\\", "/");
        if (normalized.startsWith("src/")) {
            normalized = normalized.substring(4);
        }
        if (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }

        InputStream classpathStream = ResourceLoader.class.getResourceAsStream("/" + normalized);
        if (classpathStream != null) {
            return classpathStream;
        }

        File directFile = new File(path);
        if (directFile.exists()) {
            return new FileInputStream(directFile);
        }

        File normalizedFile = new File(normalized);
        if (normalizedFile.exists()) {
            return new FileInputStream(normalizedFile);
        }

        throw new FileNotFoundException("Resource not found: " + path);
    }
}

