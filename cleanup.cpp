#include <iostream>
#include <stdexcept>
#include <vector>

struct Resource {
  Resource(const std::string& name_): name{name_} {
    std::cout << "open " << name << std::endl;
  }

  ~Resource() {
    std::cout << "close " << name << std::endl;
  }

  std::string name;
};

auto prep_out(
  const std::string& out_name,
  const std::vector<std::string>& prep_names
) -> Resource {
  // See https://en.cppreference.com/w/cpp/language/copy_elision
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
