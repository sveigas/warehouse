package com.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ContainingArticleRoot {

    public List<ContainArticle> getArticle() {
        return article;
    }

    public void setArticle(List<ContainArticle> article) {
        this.article = article;
    }

    @JsonProperty("contain_articles")
    public List<ContainArticle> article;
    public ContainingArticleRoot() {}
}
