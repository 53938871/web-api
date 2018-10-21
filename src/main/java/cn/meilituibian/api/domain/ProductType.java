package cn.meilituibian.api.domain;

import java.io.Serializable;

public class ProductType implements Serializable {
    private Long id;
    private Long cat;
    private String name;
    private String icon;
    private int status;
    public ProductType(){}
    public ProductType(Long cat, String name, String icon) {
        this.cat = cat;
        this.name = name;
        this.icon = icon;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
