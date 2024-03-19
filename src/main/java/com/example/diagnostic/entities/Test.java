package com.example.diagnostic.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
public class Test {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "test_name")
    private String testName;
    @Basic
    @Column(name = "test_description")
    private String testDescription;
    @Basic
    @Column(name = "preparation")
    private String preparation;
    @Basic
    @Column(name = "price")
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", testName='" + testName + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", preparation='" + preparation + '\'' +
                ", price=" + price +
                '}';
    }
}
