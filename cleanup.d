import std.stdio;

class Resource {
    this(string name) {
        writefln("open %s", name);
        this.name = name;
    }

    void close() {
        writefln("close %s", name);
    }

    string name;
}

Resource prepOut(string outName, string[] prepNames) {
    auto writer = new Resource(outName);
    scope(failure) writer.close();
    foreach (name; prepNames) {
        auto reader = new Resource(name);
        scope(exit) reader.close();
        // scope(success) writeln("Hi!");
        // throw new Error("WELCOME TO YOUR DOOM!!");
        writefln("use %s", name);
    }
    return writer;
}

void main() {
    auto writer = prepOut("out", ["a", "b"]);
    scope(exit) writer.close();
    writeln("use out");
}
