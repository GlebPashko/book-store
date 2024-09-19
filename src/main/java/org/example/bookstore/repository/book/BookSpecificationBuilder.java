package org.example.bookstore.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.book.BookSearchParameters;
import org.example.bookstore.model.Book;
import org.example.bookstore.repository.SpecificationBuilder;
import org.example.bookstore.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String AUTHOR_FIELD = "author";
    private static final String TITLE_FIELD = "title";

    private final SpecificationProviderManager<Book> specificationProviderManager;

    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);

        if (searchParameters.author() != null && searchParameters.author().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider(AUTHOR_FIELD)
                    .getSpecification(searchParameters.author()));
        }
        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider(TITLE_FIELD)
                    .getSpecification(searchParameters.title()));
        }
        return spec;
    }
}
