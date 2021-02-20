package com.oopclass.breadapp.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Entity
@Table(name = "cancelation")
public class Cancelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private String customer_name;
    private String description;
    private LocalDate cancelation;
    private LocalDate dateApprove;
    private String answer;   

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCancelation() {
        return cancelation;
    }

    public void setCancelation(LocalDate cancelation) {
        this.cancelation = cancelation;
    }

    public LocalDate getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(LocalDate dateApprove) {
        this.dateApprove = dateApprove;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
       
    
    @Override
    public String toString() {
        return "Cancelation [id=" + id + ", customerName=" + customer_name + "]";
    }
}