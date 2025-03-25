package com.example.ManyToMany.service;


import com.example.ManyToMany.entity.Author;
import com.example.ManyToMany.entity.Book;
import com.example.ManyToMany.repository.AuthorRepository;
import com.example.ManyToMany.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author addBookToAuthor(Long authorId, Long bookId) {
        Optional<Author> authorOpt = authorRepository.findById(authorId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (authorOpt.isPresent() && bookOpt.isPresent()) {
            Author author = authorOpt.get();
            Book book = bookOpt.get();
            author.getBooks().add(book);
            book.getAuthors().add(author); //dono entities ke beech bidirectional relationship maintain karti hai
            authorRepository.save(author);
            bookRepository.save(book);
            return author;
        } else {
            throw new RuntimeException("Author or Book not found");
        }
    }

}
