from contextlib import AbstractContextManager
from typing import Iterable


class Resource(AbstractContextManager):
    def __init__(self, name: str):
        print(f"open {name}")
        self.closed = False
        self.name = name

    # def __enter__(self):
    #     print(f"enter {self.name}")
    #     return self

    # def __del__(self):
    #     print(f"del {self.name}")
    #     self.close()

    def __exit__(self, *exc):
        self.close()

    def close(self):
        if not self.closed:
            print(f"close {self.name}")
            self.closed = True


def main():
    with prep_out("out", prep_names=["a", "b"]) as writer:
        print("use out")


def prep_out(out_name: str, *, prep_names: Iterable[str]) -> Resource:
    writer = Resource(out_name)
    try:
        for name in prep_names:
            with Resource(name) as reader:
                # raise Exception("WELCOME TO YOUR DOOM!!")
                print(f"use {name}")
        return writer
    except:
        writer.close()
        raise
    # else:
    #     return writer


if __name__ == "__main__":
    main()
