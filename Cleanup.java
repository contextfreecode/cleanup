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
                try (var reader = new Resource(name)) {
                    // if (true) throw new Error("WELCOME TO YOUR DOOM!!");
                    System.out.printf("use %s%n", name);
                }
            }
        } catch (Throwable e) {
            writer.close();
            throw e;
        }
        return writer;
    }

    void run() {
        try (var writer = prepOut("out", Arrays.asList("a", "b"))) {
            System.out.printf("use out%n");
        }
    }

    public static void main(String[] args) {
        new Cleanup().run();
    }
}
