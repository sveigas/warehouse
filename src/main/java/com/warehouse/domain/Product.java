package com.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Product {

    @Id
    private String name;

    @ElementCollection
    @Fetch(FetchMode.SUBSELECT)
    public List<ContainArticle> contain_articles;

    public Product(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContainArticle> getContain_articles() {
        return contain_articles;
    }

    public void setContain_articles(List<ContainArticle> contain_articles) {
        this.contain_articles = contain_articles;
    }
}
