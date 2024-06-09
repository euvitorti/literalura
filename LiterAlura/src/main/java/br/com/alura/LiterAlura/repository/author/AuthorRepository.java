
package br.com.alura.LiterAlura.repository.author;

import br.com.alura.LiterAlura.models.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
