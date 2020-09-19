import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

class Cleanup {
    public static void main(String[] args) throws Exception {
        try (var writer = new Cleanup().prepOut("out.txt", "a.txt", "b.txt")) {
            writer.write("done\n");
        }
    }

    Writer prepOut(String outName, String... preludeNames) throws Exception {
        var writer = new BufferedWriter(new FileWriter(outName));
        try {
            var buffer = new char[8192];
            for (var name: preludeNames) {
                try (var reader = new BufferedReader(new FileReader(name))) {
                    int count;
                    while ((count = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, count);
                    }
                }
            }
        } catch (Exception e) {
            writer.close();
            throw e;
        }
        return writer;
    }
}
