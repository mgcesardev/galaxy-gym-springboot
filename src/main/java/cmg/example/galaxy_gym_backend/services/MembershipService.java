package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Membership;

public interface MembershipService {
    public Page<Membership> findAll(Pageable pageable);

    public Membership findById(Long id);

    public Membership save(Membership model);

    Page<Membership> buscarPorNombre(String nombre, Pageable pageable);

}
