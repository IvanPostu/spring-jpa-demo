package com.ipostu.demo11.inheritance.domain.repositories;

import com.ipostu.demo11.inheritance.domain.joined.ElectricGuitar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricGuitarRepository extends JpaRepository<ElectricGuitar, Long> {
}
