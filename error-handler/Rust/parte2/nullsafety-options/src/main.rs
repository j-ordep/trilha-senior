use std::collections::HashMap;

struct User {
    name: String,
    email: Option<String>,
}

struct UserRepository {
    users: HashMap<u32, User>,
    next_id: u32,
}

impl UserRepository {
    fn new() -> Self {
        Self {
            users: HashMap::new(),
            next_id: 0,
        }
    }

    fn find_by_id(&self, id: u32) -> Option<&User> {
        self.users.get(&id) // map[key] retorna Option<&Value>
    }

    // and_then é usado para encadear operações que podem falhar, permitindo que você trabalhe com o valor contido em um Option sem precisar lidar com o None explicitamente a cada passo.
    // "tenta encontrar, se encontrar aplique isso"
    // tenta encontrar usuario, se econtrar pegue a referencia do email 

    // função com Option dá null safety porque obriga o código a representar explicitamente a ausência de valor. 
    // Porém, unwrap() ainda pode causar panic se for chamado sobre None. O ideal é usar match, if let, map, and_then, unwrap_or ou unwrap_or_else quando a ausência for esperada.
    fn get_user_email_domain(&self, id: u32) -> Option<String> {
        self.find_by_id(id)
            .and_then(|user|user.email.as_ref())
            .and_then(|email|email.split('@').last())
            .map(|domain|domain.to_string())  
    }

    // por padrão, os tipos primitivos são clonados
    fn create(&mut self, name: String, email: Option<String>) -> u32 {
        self.next_id += 1;
        self.users.insert(self.next_id, User { name, email });
        self.next_id // return
    }
}

fn main() {
    let mut repo = UserRepository::new();
    repo.create("João".to_string(), Some("joao@email.com".to_string()));
    repo.create("Pedro".to_string(), None);

    let user = repo.find_by_id(1);

    // parece if user != null, mas é mais seguro porque o tipo Option obriga a lidar com a ausência de valor
    if let Some(user) = user {
        let email = user.email.as_ref().unwrap(); // as_ref = &Option<String> -> Option<&String>
        println!("User found: {}", user.name);
        println!("Email: {}", email);
    }

    let domain = repo.get_user_email_domain(1);
    println!("{:?}", domain.unwrap_or_else(|| "N/A".to_string()));

    println!("finished");
}
