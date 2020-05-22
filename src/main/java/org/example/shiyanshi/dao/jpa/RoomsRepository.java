package org.example.shiyanshi.dao.jpa;


import org.example.shiyanshi.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomsRepository extends JpaRepository<Rooms, String> {
}