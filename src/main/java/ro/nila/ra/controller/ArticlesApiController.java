package ro.nila.ra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.nila.ra.dto.ArticleDTO;
import ro.nila.ra.payload.ApiResponse;
import ro.nila.ra.service.article.ArticleService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ArticlesApiController implements ArticleApi {

    private ArticleService articleService;

    public ArticlesApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public ResponseEntity<ApiResponse> getArticles() {
        List<ArticleDTO> articles = articleService.findAll();
        return new ResponseEntity<>
                (new ApiResponse<>(true, articles), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getArticlesForUser(@Valid @PathVariable Long id) {
        List<ArticleDTO> articles = articleService.findArticlesForUser(id);
        return new ResponseEntity<>
                (new ApiResponse<>(true, articles), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ApiResponse> saveOrUpdateArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        ArticleDTO result = articleService.upsert(articleDTO);
        return new ResponseEntity<>
                (new ApiResponse<>(true, result), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> getArticle(@Valid @PathVariable Long id) {
        ArticleDTO article = articleService.findById(id);
        return new ResponseEntity<>
                (new ApiResponse<>(true, article), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteArticle(@Valid @PathVariable Long id) {
        ArticleDTO article = articleService.deleteById(id);
        return new ResponseEntity<>
                (new ApiResponse<>(true, article), HttpStatus.OK);
    }

}
