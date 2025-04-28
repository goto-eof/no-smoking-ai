package com.andreidodu.server.repository;


import com.andreidodu.server.entity.User;

import java.util.Optional;

public interface UserRepository extends TransactionalRepository<User, Long> {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);
}
