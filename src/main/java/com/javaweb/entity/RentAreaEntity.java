package com.javaweb.entity;

import javax.persistence.*;

@Entity
@Table(name="rentarea")
public class RentAreaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="value")
    private Long value;


    // Thiết lập mối quan hệ
    @ManyToOne
    @JoinColumn(name = "buildingid")
    private BuildingEntity buildingId;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BuildingEntity getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(BuildingEntity buildingId) {
        this.buildingId = buildingId;
    }
}
