package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.MembershipPlan;

public interface MembershipPlanService {
    public Page<MembershipPlan> findAll(Pageable pageable);

    public MembershipPlan findById(Long id);

    public MembershipPlan save(MembershipPlan model);

    Page<MembershipPlan> buscarPorNombre(String nombre, Pageable pageable);

    void delete(Long id);
}
