package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * JPA entity: RuleName - MySQL table: rule_name.
 *
 * @author SimonC.
 * @version 1.0
 */
@Entity(name = "ruleName")
@Table(name = "rule_name")
public class RuleName {
    // DONE: Map columns in data table RULENAME with corresponding java fields

    @Id
    @SequenceGenerator(
            name = "rule_name_sequence",
            sequenceName = "rule_name_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rule_name_sequence"
    )
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Column(name = "json")
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Column(name = "template")
    private String template;

    @NotBlank(message = "Sql is mandatory")
    @Column(name = "sql_str")
    private String sqlStr;

    @NotBlank(message = "Sql Part is mandatory")
    @Column(name = "sql_part")
    private String sqlPart;

    public RuleName() {
    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleName)) return false;
        RuleName ruleName = (RuleName) o;
        return id.equals(ruleName.id) && name.equals(ruleName.name) && description.equals(ruleName.description) && json.equals(ruleName.json) && template.equals(ruleName.template) && sqlStr.equals(ruleName.sqlStr) && sqlPart.equals(ruleName.sqlPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, json, template, sqlStr, sqlPart);
    }
}
