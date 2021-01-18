package com.warehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
public class ContainArticle {

    public ContainArticle(){}
    private int amount_of;
    private int art_id;

    public int getArt_id() {
        return art_id;
    }

    public void setArt_id(int art_id) {
        this.art_id = art_id;
    }

    public int getAmount_of() {
        return amount_of;
    }

    public void setAmount_of(int amount_of) {
        this.amount_of = amount_of;
    }

}
