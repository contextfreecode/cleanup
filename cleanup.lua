Resource = {}

function Resource:open(name)
    print(string.format("open %s", name))
    local result = {name = name}
    setmetatable(result, self)
    self.__index = self
    return result
end

function Resource:close()
    print(string.format("close %s", self.name))
end

function Resource:__close(err)
    -- print(err)
    self:close()
end

function prep_out(out_name, prep_names)
    local writer = Resource:open("out")
    local ok, err = pcall(function()
        for _, name in ipairs(prep_names) do
            local reader <close> = Resource:open(name)
            -- error("WELCOME TO YOUR DOOM!!")
            print(string.format("use %s", name))
        end
    end)
    if ok then
        return writer
    else
        writer:close()
        error(err, 0)
    end
end

function main()
    local writer <close> = prep_out("out", {"a", "b"})
    print("use out")
end

main()
