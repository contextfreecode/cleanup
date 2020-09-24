using System;
using System.Collections.Generic;

class Resource: IDisposable {
    public Resource(string name) {
        Console.WriteLine($"open {name}");
        this.name = name;
    }

    public void Dispose() {
        Console.WriteLine($"close {name}");
    }

    string name;
}

class Cleanup {

    static void Main(string[] args) {
        new Cleanup().Run();
    }

    void Run() {
        using (var writer = PrepOut("out", new List<string>{"a", "b"})) {
            Console.WriteLine("use out");
        }
    }

    Resource PrepOut(string outName, IEnumerable<string> prepNames) {
        var writer = new Resource(outName);
        try {
            foreach (var name in prepNames) {
                using (var reader = new Resource(name)) {
                    // throw new Exception("WELCOME TO YOUR DOOM!!");
                    Console.WriteLine($"use {name}");
                }
            }
        } catch {
            writer.Dispose();
            throw;
        }
        return writer;
    }

}
