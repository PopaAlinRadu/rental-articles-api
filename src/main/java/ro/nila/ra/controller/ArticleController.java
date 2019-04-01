package ro.nila.ra.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import ro.nila.ra.exceptions.ResourceNotFoundException;
import ro.nila.ra.model.Account;
import ro.nila.ra.model.Article;
import ro.nila.ra.model.view.Views;
import ro.nila.ra.payload.ApiResponse;
import ro.nila.ra.security.AccountPrincipal;
import ro.nila.ra.service.ArticleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Method that will return all Articles from DB and their owners(Account)
     *
     * @return A List with all Articles
     */
    @GetMapping
    @JsonView(Views.WithAccount.class)
    public ResponseEntity getArticles() {
        List<Article> articles = articleService.findAll();
        if (!ObjectUtils.isEmpty(articles)) {
            return ResponseEntity.
                    ok(new ApiResponse<>(true, articleService.findAll(), null));
        } else
            return new ResponseEntity<>(
                    new ApiResponse<>
                            (false, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase()),
                    HttpStatus.NOT_FOUND);
    }

    /**
     * Method that will return all Articles for a Account based on Account ID
     *
     * @param id - Account ID
     * @return List of Articles of a User
     */
    @GetMapping("/user/{id}")
    @JsonView(Views.WithoutAccount.class)
    public ResponseEntity getArticlesForUser(@Valid @PathVariable Long id) {
        try {
            List<Article> articles = articleService.findArticlesForUser(id);
            return ResponseEntity.
                    ok(new ApiResponse<>(true, articles, null));
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(
                    new ApiResponse<>(false,
                            new ResourceNotFoundException(e.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()),
                    HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Method that will return an Article based on its ID
     *
     * @param id - Article ID
     * @return
     */
    @GetMapping("/{id}")
    @JsonView(Views.WithAccount.class)
    public ResponseEntity getArticle(@Valid @PathVariable Long id) {
        if (!articleService.findById(id).isPresent()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, HttpStatus.NOT_FOUND.getReasonPhrase(), null),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity
                .ok(new ApiResponse<>(true, articleService.findById(id), null));
    }

    /**
     * Method that will delete an Article based on its ID
     *
     * @param id - Article ID
     * @return
     */
    @DeleteMapping("/{id}")
    @JsonView(Views.WithoutAccount.class)
    public ResponseEntity deleteArticle(@Valid @PathVariable Long id) {
        try {
            Article article = articleService.deleteById(id);
            return ResponseEntity.
                    ok(new ApiResponse<>(true, article, null));
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(
                    new ApiResponse<>(false,
                            new ResourceNotFoundException(e.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()),
                    HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method that will Save a Article into DB and link it to the Account that is logged in
     *
     * @param article - Object that will be saved
     * @return the Article saved and the Account that saved it
     */
    @PostMapping
    @JsonView(Views.WithoutAccount.class)
    public ResponseEntity saveArticle(@Valid @RequestBody Article article) {
        Long accountId = ((AccountPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Account account = new Account();
        account.setId(accountId);
        article.setAccount(account);
        Article result = articleService.save(article);
        return ResponseEntity
                .ok(new ApiResponse<>(true, result, null));
    }

}
