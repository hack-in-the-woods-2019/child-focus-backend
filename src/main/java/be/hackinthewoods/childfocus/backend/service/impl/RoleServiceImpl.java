package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.Role;
import be.hackinthewoods.childfocus.backend.repository.RoleRepository;
import be.hackinthewoods.childfocus.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }

    @Override
    public Set<Role> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Role findUserRole() {
        return findByAuthority("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("The user role must exist in database."));
    }
}
