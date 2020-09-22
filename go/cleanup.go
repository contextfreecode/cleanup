package main

import (
	"fmt"
	"log"
)

type Resource struct {
	Name string
}

func Open(name string) (*Resource, error) {
	fmt.Printf("open %s\n", name)
	return &Resource{Name: name}, nil
}

func (resource *Resource) Close() {
	fmt.Printf("close %s\n", resource.Name)
}

func PrepOut(outName string, prepNames []string) (writer *Resource, err error) {
	writer, err = Open(outName)
	if err != nil {
		return
	}
	defer func() {
		if err != nil {
			writer.Close()
			writer = nil
		}
	}()
	for _, name := range prepNames {
		var reader *Resource
		reader, err = Open(name)
		if err != nil {
			return
		}
		defer reader.Close()
		// if true {
		// 	err = errors.New("WELCOME TO YOUR DOOM!!")
		// 	return
		// }
		fmt.Printf("use %s\n", name)
	}
	return
}

func main() {
	writer, err := PrepOut("out", []string{"a", "b"})
	if err != nil {
		log.Fatal(err)
	}
	defer writer.Close()
	fmt.Printf("use out\n")
}
