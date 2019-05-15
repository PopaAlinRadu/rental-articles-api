package ro.nila.ra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.nila.ra.dto.ArticleDTO;

import javax.validation.Valid;

@RequestMapping("/articles")
public interface ArticleApi {

    /**
     * Method that will return all Articles from DB and their owners(Account)
     *
     * @return A List with all Articles
     */
    @GetMapping
    ResponseEntity getArticles();

    /**
     * Method that will return all Articles for a Account based on Account ID
     *
     * @param id - Account ID
     * @return List of Articles of a User
     */
    @GetMapping("/user/{id}")
    ResponseEntity getArticlesForUser(@Valid @PathVariable Long id);

    /**
     * Method that will Save a Article into DB and link it to the Account that is logged in
     *
     * @param article - Object that will be saved
     * @return the Article saved and the Account that saved it
     */
    @PostMapping
    ResponseEntity saveOrUpdateArticle(@Valid @RequestBody ArticleDTO articleDTO);

    /**
     * Method that will return an Article based on its ID
     *
     * @param id - Article ID
     * @return Article Object
     */
    @GetMapping("/{id}")
    ResponseEntity getArticle(@Valid @PathVariable Long id);

    /**
     * Method that will delete an Article based on its ID
     *
     * @param id - Article ID
     * @return Deleted Article
     */
    @DeleteMapping("/{id}")
    ResponseEntity deleteArticle(@Valid @PathVariable Long id);
}
