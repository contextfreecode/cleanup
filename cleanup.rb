class Resource
  def initialize(name)
    puts "open #{name}"
    @name = name
    if block_given?
      begin
        yield self
      ensure
        self.close
      end
    end
  end

  def close
    puts "close #{@name}"
  end
end

def main()
  # writer = prep_out("out", prep_names: ["a", "b"])
  prep_out("out", prep_names: ["a", "b"]) do |writer|
    puts "use out"
  end
end

def prep_out(out_name, prep_names:)
  writer = Resource.new(out_name)
  begin
    prep_names.each do |name|
      Resource.new(name) do |reader|
        puts "use #{name}"
      end
    end
    # block_given? ? (yield writer) : (return writer)
    if block_given?
      yield writer
    else
      return writer
    end
  ensure
    writer.close if block_given?
  end
  # puts "Hi!"
rescue
  writer.close
  raise
# else
#   puts "Hi!"
end

main()
