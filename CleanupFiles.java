import java.io.*;
import java.util.*;

class CleanupFiles {
    public static void main(String[] args) throws Exception {
        new CleanupFiles().run();
    }

    void run() throws Exception {
        var writer = prepOut("out", Arrays.asList("a", "b"));
        try {
            writer.write("done\n");
        } finally {
            writer.close();
        }
    }

    Writer prepOut(String outName, Iterable<String> prepNames)
            throws Exception {
        var writer = new BufferedWriter(new FileWriter(outName));
        try {
            var buffer = new char[8192];
            for (var name: prepNames) {
                var reader = new BufferedReader(new FileReader(name));
                try {
                    int count;
                    while ((count = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, count);
                    }
                } finally {
                    reader.close();
                }
            }
        } catch (Exception e) {
            writer.close();
            throw e;
        }
        return writer;
    }
}
