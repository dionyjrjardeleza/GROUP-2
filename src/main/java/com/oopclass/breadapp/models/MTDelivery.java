package com.oopclass.breadapp.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "delivery")
public class MTDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private String customer_name;
    private String rider_name;
    private String contact_number;
    private String address;
    private LocalDate delivery_schedule;

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

    public String getRiderName() {
        return rider_name;
    }

    public void setRiderName(String rider_name) {
        this.rider_name = rider_name;
    }

    public String getContactNumber() {
        return contact_number;
    }

    public void setContactNumber(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDeliverySchedule() {
        return delivery_schedule;
    }

    public void setDeliverySchedule(LocalDate delivery_schedule) {
        this.delivery_schedule = delivery_schedule;
    }

    @Override
    public String toString() {
        return "Mightytea [id=" + id + ", customerName=" + customer_name + "]";
    }

}
