import java.util.*;

class Resource implements AutoCloseable {
    public Resource(String name) {
        System.out.printf("open %s%n", name);
        this.name = name;
    }

    public void close() {
        System.out.printf("close %s%n", name);
    }

    String name;
}

class Cleanup {
    Resource prepOut(String outName, Iterable<String> prepNames) {
        var writer = new Resource(outName);
        try {
            for (var name: prepNames) {
                var reader = new Resource(name);
                try {
                    // if (true) throw new Error("WELCOME TO YOUR DOOM!!");
                    System.out.printf("use %s%n", name);
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

    void run() {
        var writer = prepOut("out", Arrays.asList("a", "b"));
        try {
            System.out.printf("use out%n");
        } finally {
            writer.close();
        }
    }

    public static void main(String[] args) {
        new Cleanup().run();
    }
}
