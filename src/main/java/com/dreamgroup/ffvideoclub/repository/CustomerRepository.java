package com.dreamgroup.ffvideoclub.repository;

import com.dreamgroup.ffvideoclub.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByUsername(String userName);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdIsNot(String userName, Long id);

    boolean existsByEmailAndIdIsNot(String email, Long id);
}
