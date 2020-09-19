#include <iostream>

struct Resource {
  Resource(const std::string& name_): name{name_} {
    std::cout << "open " << name << std::endl;
  }

  ~Resource() {
    std::cout << "close " << name << std::endl;
  }

  std::string name;
};

auto main() -> int {
  //
}
