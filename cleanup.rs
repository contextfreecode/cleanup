use std::error::Error;

type Try<Value> = Result<Value, Box<dyn Error>>;

#[allow(dead_code)]
fn make_error<Value>(error: &str) -> Try<Value> {
    use std::io::{Error as IoError, ErrorKind};
    Err(IoError::new(ErrorKind::Other, error).into())
}

struct Resource {
    name: String,
}

impl Resource {
    fn new(name: &str) -> Try<Resource> {
        println!("open {}", name);
        Ok(Resource { name: name.into() })
    }
}

impl Drop for Resource {
    fn drop(&mut self) {
        println!("close {}", self.name);
    }
}

fn prep_out(out_name: &str, prep_names: &[&str]) -> Try<Resource> {
    let writer = Resource::new(out_name)?;
    for name in prep_names {
        let _reader = Resource::new(name)?;
        // if true {
        //     return make_error("WELCOME TO YOUR DOOM!!");
        // }
        println!("use {}", name);
    }
    Ok(writer)
}

fn main() -> Try<()> {
    let _writer = prep_out("out", &["a", "b"])?;
    println!("use out");
    Ok(())
}
