package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class V3__BookMigrationUtil extends BaseJavaMigration {

    private static final String BOOK_NAME = "BookName";
    private static final String BOOK_AUTHOR = "BookAuthor";
    private static final String BOOK_PUBLISHED_DATE = "BookPublishedDate";
    private static final String BOOK_THUMBNAIL_URL = "BookThumbnailUrl";
    private static final String BOOK_SHORT_DESCRIPTION = "BookShortDescription";
    private static final String BOOK_ISBN = "BookIsbn";
    private static final String BOOK_CATEGORY = "Bookcategory";
    private RestTemplate restTemplate;

    public V3__BookMigrationUtil() {
        this.restTemplate = new RestTemplate();
    }

    public V3__BookMigrationUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void migrate(Context context) throws Exception {
        List<Map<String, String>> books = fetchBooksFromApi();
        insertIntoDatabase(context, books);
    }

    private void insertIntoDatabase(Context context, List<Map<String, String>> books) throws ParseException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
        if(books != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            for(Map<String, String> book: books) {
                String name = book.get(BOOK_NAME);
                String author = book.get(BOOK_AUTHOR);
                String coverPicture = book.get(BOOK_THUMBNAIL_URL);
                String releaseDateString = book.get(BOOK_PUBLISHED_DATE);
                String category = book.get(BOOK_CATEGORY);
                String shortDescription = book.get(BOOK_SHORT_DESCRIPTION);
                String isbn = book.get(BOOK_ISBN);

                java.sql.Date releaseDate = null;
                if(releaseDateString != null) {
                    releaseDate = new java.sql.Date(sdf.parse(releaseDateString).getTime());
                }
                jdbcTemplate.update(
                        "insert into book(" +
                                "BookName, " +
                                "coverpicture, " +
                                "author, " +
                                "short_description, " +
                                "isbn, " +
                                "category, " +
                                "releaseDate )" +
                                " values(?, ?, ?, ?, ?, ?, ?)",
                        name,
                        coverPicture,
                        author,
                        shortDescription,
                        isbn,
                        category,
                        releaseDate);
            }
        }
    }

    private List<Map<String, String>> fetchBooksFromApi() throws IOException {
        List<Map<String, String>> books = new ArrayList<>();

        Properties properties = new Properties();
        String propFileName = "application.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        properties.load(inputStream);

        String url = (String) properties.get("isbn_books_api_url");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        List<Map<String, Object>> responseObjects =  this.restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List>() {}).getBody();

        for(Map<String, Object> responseObject: responseObjects) {
            Map<String, String> book = new HashMap<>();
            book.put(BOOK_NAME, (String) responseObject.get("title"));
            List<String> authors = (List<String>) responseObject.get("authors");
            List<String> categories = (List<String>) responseObject.get("categories");
            book.put(BOOK_AUTHOR, authors != null ? authors.get(0) : null);
            book.put(BOOK_SHORT_DESCRIPTION,(String) responseObject.get("shortDescription"));
            book.put(BOOK_ISBN, (String) responseObject.get("isbn"));
            book.put(BOOK_CATEGORY, categories != null && !categories.isEmpty() ? categories.get(0) : null);

            Map<String, String> publishedDateMap = (Map<String, String>) responseObject.get("publishedDate");
            if(publishedDateMap != null) {
                book.put(BOOK_PUBLISHED_DATE, publishedDateMap.get("$date"));
            }
            book.put(BOOK_THUMBNAIL_URL, (String) responseObject.get("thumbnailUrl"));
            books.add(book);
        }
        return books;
    }
}