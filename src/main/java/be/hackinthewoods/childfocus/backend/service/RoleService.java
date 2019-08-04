package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    public Optional<Role> findByAuthority(String authority);

    public Set<Role> findAll();

    Role findUserRole();
}
