package br.com.alura.LiterAlura.repository.book;

import br.com.alura.LiterAlura.models.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByLanguagesContainsIgnoreCase(String language);
}
