
package br.com.alura.LiterAlura.main;

import br.com.alura.LiterAlura.dto.DataBookDTO;
import br.com.alura.LiterAlura.models.author.Author;
import br.com.alura.LiterAlura.models.book.Book;
import br.com.alura.LiterAlura.repository.author.AuthorRepository;
import br.com.alura.LiterAlura.repository.book.BookRepository;
import br.com.alura.LiterAlura.service.ConnectApi;
import br.com.alura.LiterAlura.service.ConvertData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private ConnectApi connectApi = new ConnectApi();

    private ConvertData convertData = new ConvertData();

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    List<Author> authorList = new ArrayList<>();

    public Principal(){}

    public Principal(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void menu() {
        byte choice = -1;

        while (choice != 0) {
            System.out.println("""
                    Welcome! 🎥
                    ------------------------------------
                    [1] Buscar Livro
                    [2] Buscar Todos Os Autores Registrados
                    [3] Buscar Autor Por Determinado Ano
                    [4] Buscar Todos Os Livros Registrados
                    [5] Buscar Livro POr Determinado Idioma
                    ------------------------------------
                    [0] Sair
                    """);

            choice = scanner.nextByte();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    searchAllAuthor();
                    break;
                case 3:
                    searchAuthorByYear();
                    break;
//                case 4:
//                    searchAllBook();
//                    break;
//                case 5:
//                    searchBookByLanguage();
//                    break;
                case 0:
                    System.out.println("Goodbye👋");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private DataBookDTO getDataBook() {

        System.out.println("Digite o nome do livro: ");
        String booksName = scanner.nextLine();

        var json = connectApi.connect(booksName);

        DataBookDTO dataBookDTO = convertData.getData(json, DataBookDTO.class);
        return dataBookDTO;
    }

    private void searchBook() {
        DataBookDTO data = getDataBook();

        // SALVANDO OS DADOS NO BANCO DE DADOS
        List<Book> books = data.results().stream()
                .map(result -> {
                    Book book = new Book(result);
                    result.authors().forEach(authorDTO -> {
                        Author author = new Author(authorDTO.name(), authorDTO.birth_year(), authorDTO.death_year());
                        author.addBook(book);
                        authorRepository.save(author);
                    });
                    return book;
                })
                .collect(Collectors.toList());

        books.forEach(bookRepository::save);
        books.forEach(System.out::println);
    }

        private void searchAllAuthor() {
            // BUSCANDO AS INFORMAÇÕES DO BANCO DE DADOS
            authorList = authorRepository.findAll();

            List<Author> author = authorList.stream()
                    .sorted(Comparator.comparing(Author::getName))
                    .collect(Collectors.toList());

            author.forEach(System.out::println);
        }

    private void searchAuthorByYear() {
    }

//    private void searchAllBook() {
//    }

//    private void searchBookByLanguage() {
//        System.out.println("Digite o idioma: ");
//        var language = scanner.nextLine();
//
//        var bookLanguage = bookRepository.findByLanguage(language.toLowerCase());
//
//        if (bookLanguage != null) {
//            System.out.println(bookLanguage);
//        } else {
//            System.out.println("Não encontrada.");
//        }
//    }
}

