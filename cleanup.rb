class Resource
  def initialize(name, &block)
    puts "open #{name}"
    @name = name
    @closed = false
    if block_given?
      handle(&block)
    end
  end

  def close
    if not @closed
      puts "close #{@name}"
      @closed = true
    end
  end

  def handle
    begin
      yield self
    ensure
      self.close
    end
  end
end

def prep_out(out_name, prep_names:, &block)
  writer = Resource.new(out_name)
  begin
    prep_names.each do |name|
      Resource.new(name) do |reader|
        # raise "WELCOME TO YOUR DOOM!!"
        puts "use #{name}"
      end
    end
    if block_given?
      writer.handle(&block)
    else
      return writer
    end
  end
  # puts "Hi!"
rescue
  writer.close
  raise
# else
#   puts "Hi!"
end

def main()
  # writer = prep_out("out", prep_names: ["a", "b"])
  # puts "use out"
  # writer.close
  prep_out("out", prep_names: ["a", "b"]) do |writer|
    # raise "No way!"
    puts "use out"
  end
end

main()
