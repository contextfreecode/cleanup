#include <iostream>
#include <stdexcept>
#include <vector>

struct Resource {
  Resource(const std::string& name_): name{name_} {
    std::cout << "open " << name << std::endl;
  }

  Resource(Resource&& other):
    name{std::move(other.name)},
    closed{other.closed}
  {
    other.closed = true;
  }

  ~Resource() {
    if (!closed) {
      std::cout << "close " << name << std::endl;
      closed = true;
    }
  }

  std::string name;
  bool closed = false;
};

auto prep_out(
  const std::string& out_name,
  const std::vector<std::string>& prep_names
) -> Resource {
  auto writer = Resource{out_name};
  for (auto& name: prep_names) {
    auto reader = Resource{name};
    // throw std::runtime_error("WELCOME TO YOUR DOOM!!");
    std::cout << "use " << name << std::endl;
  }
  return writer;
}

auto main() -> int {
  try {
    auto writer = prep_out("out", {"a", "b"});
    std::cout << "use out" << std::endl;
  } catch (std::exception& e) {
    std::cout << "error " << e.what() << std::endl;
    return 1;
  }
}
