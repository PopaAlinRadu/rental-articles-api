package ro.nila.ra.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.nila.ra.model.Account;
import ro.nila.ra.model.Article;
import ro.nila.ra.model.view.Views;
import ro.nila.ra.payload.ApiResponse;
import ro.nila.ra.security.AccountPrincipal;
import ro.nila.ra.service.AccountService;
import ro.nila.ra.service.ArticleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;
    private AccountService accountService;

    public ArticleController(ArticleService articleService,
                             AccountService accountService) {
        this.articleService = articleService;
        this.accountService = accountService;
    }

    /**
     * Method that will return all Articles from DB and their owners(Account)
     *
     * @return A List with all Articles
     */
    @GetMapping
    @JsonView(Views.WithAccount.class)
    public ResponseEntity getArticles() {
        return ResponseEntity.
                ok(new ApiResponse(true, articleService.findAll(), null));
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
        return ResponseEntity.
                ok(new ApiResponse(true, articleService.findArticleForUser(id), null));
    }

    /**
     * Method that will return an Article based on its ID
     *
     * @param id - Article ID
     * @return
     */
    @GetMapping("/{id}")
    @JsonView(Views.WithAccount.class)
    public ResponseEntity getArticle(@Valid @PathVariable Long id){
        if (!articleService.findById(id).isPresent()){
            return new ResponseEntity<>(
                    new ApiResponse(false, HttpStatus.NOT_FOUND.getReasonPhrase(), null),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity
                .ok(new ApiResponse(true, articleService.findById(id), null));
    }

    /**
     * Method that will delete an Article based on its ID
     *
     * @param id - Article ID
     * @return
     */
    @DeleteMapping("/{id}")
    @JsonView(Views.WithoutAccount.class)
    public ResponseEntity deleteArticle(@Valid @PathVariable Long id){
        return ResponseEntity.
                ok(new ApiResponse(true, articleService.deleteById(id), null));
    }

    /**
     * Method that will Save a Article into DB and link it to the Account that is logged in
     *
     * @param article - Object that will be saved
     * @return the Article saved and the Account that saved it
     */
    @PostMapping
    @JsonView(Views.WithoutAccount.class)
    public ResponseEntity saveArticle(@Valid @RequestBody Article article){
        Long accountId = ((AccountPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Account account = new Account();
        account.setId(accountId);
        article.setAccount(account);
        Article result = articleService.save(article);
        return ResponseEntity
                .ok(new ApiResponse(true, result, null));
    }

}
