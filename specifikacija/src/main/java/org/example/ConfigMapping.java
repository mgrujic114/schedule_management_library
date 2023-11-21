package org.example;

import lombok.Getter;
import lombok.Setter;
//za citanje config fajla
@Getter
@Setter
public class ConfigMapping {
    private Integer index;
    private String custom;
    private String original;

    public ConfigMapping(Integer index, String custom, String original) {
        this.index = index;
        this.custom = custom;
        this.original = original;
    }
}
