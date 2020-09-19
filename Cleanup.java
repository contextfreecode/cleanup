record Resource(String name) implements AutoCloseable {
    public Resource {
        System.out.printf("open %s%n", name);
    }

    public void close() {
        System.out.printf("close %s%n", name);
    }
}

class Cleanup {
    public static void main(String[] args) throws Exception {
        new Cleanup().run();
    }

    void run() throws Exception {
        var writer = prepOut("out", "a", "b");
        try {
            System.out.printf("use out%n");
        } finally {
            writer.close();
        }
    }

    Resource prepOut(String outName, String... preludeNames) {
        var writer = new Resource(outName);
        try {
            for (var name: preludeNames) {
                var reader = new Resource(name);
                try {
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
}
