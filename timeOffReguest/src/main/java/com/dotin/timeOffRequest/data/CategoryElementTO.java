package com.dotin.timeOffRequest.data;

import javax.persistence.*;

@Entity
public class CategoryElementTO {

    private Long id;
    private Integer code;
    private String name;
    private CategoryTO category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryTO.class, optional = false)
    @JoinColumn(name = "id")
    public CategoryTO getCategory() {
        return category;
    }

    public void setCategory(CategoryTO categoryTO) {
        this.category = categoryTO;
    }
}
