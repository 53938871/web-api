package cn.meilituibian.api.domain;

import java.io.Serializable;

public class ProductType implements Serializable {
    private Long cat;
    private String name;
    public ProductType(){}
    public ProductType(Long cat, String name) {
        this.cat = cat;
        this.name = name;
    }

    public Long getCat() {
        return cat;
    }

    public void setCat(Long cat) {
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
