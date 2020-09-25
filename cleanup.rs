use std::error::Error;

type Try<Value> = Result<Value, Box<dyn Error>>;

#[allow(dead_code)]
fn make_error<E: Into<Box<dyn Error + Send + Sync>>>(error: E) -> Box<dyn Error> {
    std::io::Error::new(std::io::ErrorKind::Other, error).into()
}

struct Resource<'a> {
    name: &'a str,
}

impl<'a> Resource<'a> {
    fn new(name: &'a str) -> Try<Resource> {
        println!("open {}", name);
        Ok(Resource { name })
    }
}

impl<'a> Drop for Resource<'a> {
    fn drop(&mut self) {
        println!("close {}", self.name);
    }
}

fn prep_out<'a>(out_name: &'a str, prep_names: &[&str]) -> Try<Resource<'a>> {
    let writer = Resource::new(out_name)?;
    for name in prep_names {
        let _reader = Resource::new(name)?;
        // if true {
        //     return Err(make_error("WELCOME TO YOUR DOOM!!"));
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
