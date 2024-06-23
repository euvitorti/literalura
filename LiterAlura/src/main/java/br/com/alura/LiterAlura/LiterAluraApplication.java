package br.com.alura.LiterAlura;

import br.com.alura.LiterAlura.main.Principal;
import br.com.alura.LiterAlura.repository.author.AuthorRepository;
import br.com.alura.LiterAlura.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(authorRepository, bookRepository);
        principal.menu();
	}
}
