const print = @import("std").debug.print;

const Resource = struct {
    pub fn init(name: []const u8) !Resource {
        print("open {}\n", .{name});
        return Resource{ .name = name };
    }

    pub fn deinit(self: Resource) void {
        print("close {}\n", .{self.name});
    }

    name: []const u8,
};

const Error = error{WelcomeToYourDoom};

fn prepOut(out_name: []const u8, prep_names: []const []const u8) !Resource {
    var writer = try Resource.init(out_name);
    errdefer writer.deinit();
    for (prep_names) |name| {
        var reader = try Resource.init(name);
        defer reader.deinit();
        // if (true) return Error.WelcomeToYourDoom;
        print("use {}\n", .{name});
    }
    return writer;
}

pub fn main() !void {
    var writer = try prepOut("out", &[_][]const u8{ "a", "b" });
    defer writer.deinit();
    print("use out\n", .{});
}
