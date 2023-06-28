package com.ezraloan.automation.repository;

import com.ezraloan.automation.entity.LendingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LendingRequestRepository extends JpaRepository<LendingRequest, Long> {
    //@Query("SELECT s FROM lending_request s WHERE s.id = ?1")
    Optional<List<LendingRequest>> findByid(Long id);
}