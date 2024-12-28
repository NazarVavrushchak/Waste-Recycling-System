package com.ecowaste.recycling.repository;

import com.ecowaste.recycling.entity.RecyclingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingPointRepo extends JpaRepository<RecyclingPoint , Long> {
    List<RecyclingPoint> findByWasteType(String wasteType);
}