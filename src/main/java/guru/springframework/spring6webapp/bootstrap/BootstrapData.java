package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //region Authors
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");
        //endregion

        //region Books
        Book ddd = new Book();
        ddd.setTitle("Domain Driven Development");
        ddd.setIsbn("123456");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("4567890");
        //endregion

        //region Publishers
        Publisher penguin = new Publisher();
        penguin.setPublisherName("Penguin");
        penguin.setCity("Amsterdam");
        penguin.setAddress("Next door");
        penguin.setZip("1234 HJ");
        penguin.setState("Noord Holland");
        //endregion

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);

        Author rodSaved = authorRepository.save(rod);
        Book noEJBSaved = bookRepository.save(noEJB);

        ericSaved.getBooks().add(dddSaved);
        dddSaved.getAuthors().add(ericSaved);

        rodSaved.getBooks().add(noEJBSaved);
        noEJBSaved.getAuthors().add((rodSaved));

        Publisher penguinSaved = publisherRepository.save(penguin);

        noEJBSaved.setPublisher(penguinSaved);
        dddSaved.setPublisher(penguinSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);
        bookRepository.save(ddd);
        bookRepository.save(noEJB);


        System.out.println("In Bootstrap");
        System.out.println("Authors: " + authorRepository.count());
        System.out.println("Books: " + bookRepository.count());
        System.out.println("Publishers: " + publisherRepository.count());
    }
}
