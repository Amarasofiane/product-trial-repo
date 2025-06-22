package com.product.trial.backend.entity;

import jakarta.persistence.*;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Product {
    @Id
    private Long id;

    private String code;
    private String name;
    private String description;
    private String image;
    private Double price;
    private String category;
    
    @JsonProperty(defaultValue = "0") 
    private Integer quantity = 0;  
    
    private Long  createdAt;
    private Long  updatedAt;

    private Long shellId;
    private String internalReference;
    private String inventoryStatus;
    private Integer rating;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long  getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long  createdAt) { this.createdAt = createdAt; }

    public Long  getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long  updatedAt) { this.updatedAt = updatedAt; }

    public Long getShellId() { return shellId; }
    public void setShellId(Long shellId) { this.shellId = shellId; }

    public String getInternalReference() { return internalReference; }
    public void setInternalReference(String internalReference) { this.internalReference = internalReference; }

    public String getInventoryStatus() { return inventoryStatus; }
    public void setInventoryStatus(String inventoryStatus) { this.inventoryStatus = inventoryStatus; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    
    public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@PrePersist
    public void prePersist() {
        Long now = System.currentTimeMillis();
        this.createdAt = now; 
        this.updatedAt = now; 
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = System.currentTimeMillis(); 
    }
}
