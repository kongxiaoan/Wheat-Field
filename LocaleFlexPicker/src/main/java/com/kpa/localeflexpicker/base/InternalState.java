package com.kpa.localeflexpicker.base;

/**
 * @author: kpa
 * @date: 2024/2/29
 * @description:
 */
public class InternalState {
    private int code;
    private String name;
    private int priority;
    private String spell;
    private String indexName;
    private String headerSpell;
    private String cnExtra;

    public InternalState() {
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String getSpell() {
        return spell;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getHeaderSpell() {
        return headerSpell;
    }

    public String getCnExtra() {
        return cnExtra;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setHeaderSpell(String headerSpell) {
        this.headerSpell = headerSpell;
    }

    public void setCnExtra(String cnExtra) {
        this.cnExtra = cnExtra;
    }
}
