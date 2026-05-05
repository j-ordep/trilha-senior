use std::fs;
use std::io::ErrorKind;

/* 
    match fs::copy(source, destination) {
        Ok(bytes) =>  println!("Copied {} bytes successfully", bytes),
        Err(e) => eprintln!("Error copying file: {}", e),
    }
*/

fn main() {
    let source = "src/main.rs";
    let destination = "src/main_copy.rs";

    match fs::copy(source, destination) {
        Ok(bytes) =>  println!("Copied {} bytes successfully", bytes),
        Err(e) => {
            match e.kind() {
                ErrorKind::NotFound => eprintln!("Source file not found: {}", source),
                ErrorKind::PermissionDenied => eprintln!("Permission denied when copying to: {}", destination),
                _ => eprintln!("Error: {:?}", e),
            }
        }
    }

}
