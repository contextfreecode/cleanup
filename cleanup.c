#include <stdio.h>

typedef struct Resource {
  const char* name;
} Resource;

typedef enum {
  Error_None,
  Error_Bad,
  Error_WelcomeToYourDoom,
} Error;

typedef struct ResourceResult {
  Error error;
  Resource value;
} ResourceResult;

ResourceResult Resource_open(const char* name) {
  printf("open %s\n", name);
  return (ResourceResult){
    .error = Error_None,
    .value = (Resource){.name = name},
  };
}

void Resource_close(Resource* resource) {
  printf("close %s\n", resource->name);
}

typedef struct StringSpan {
  const char** items;
  size_t length;
} StringSpan;

ResourceResult prep_out(const char* out_name, StringSpan prep_names) {
  Error error = Error_None;
  ResourceResult writer_result = Resource_open(out_name);
  if (writer_result.error) {
    error = writer_result.error;
    goto fail;
  }
  for (size_t index = 0; index < prep_names.length; index += 1) {
    ResourceResult reader_result =
      Resource_open(prep_names.items[index]);
    if (reader_result.error) {
      error = reader_result.error;
      goto close_writer_result;
    }
    // error = Error_WelcomeToYourDoom;
    // goto close_reader_result;
    printf("use %s\n", prep_names.items[index]);
close_reader_result:
    Resource_close(&reader_result.value);
    if (error) goto close_writer_result;
  }
  return writer_result;
  // Only happens on fail.
close_writer_result:
  Resource_close(&writer_result.value);
fail:
  return (ResourceResult){.error = error};
}

int main() {
  Error error = Error_None;
  ResourceResult writer_result = prep_out(
    "out",
    (StringSpan){.items = (const char*[]){"a", "b"}, .length = 2}
  );
  if (writer_result.error) {
    error = writer_result.error;
    goto fail;
  }
  printf("use out\n");
close_writer_result:
  Resource_close(&writer_result.value);
  if (error) goto fail;
  return 0;
fail:
  printf("error %d\n", error);
  return error;
}
