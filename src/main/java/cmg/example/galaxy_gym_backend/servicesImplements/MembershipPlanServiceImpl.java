package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.MembershipPlan;
import cmg.example.galaxy_gym_backend.repositories.MembershipPlanRepository;
import cmg.example.galaxy_gym_backend.services.MembershipPlanService;

@Service
@Transactional
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;

    public MembershipPlanServiceImpl(MembershipPlanRepository membershipPlanRepository) {
        this.membershipPlanRepository = membershipPlanRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipPlan> findAll(Pageable pageable) {
        try {
            return membershipPlanRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los planes de membresía", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipPlan findById(Long id) {
        try {
            return membershipPlanRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar plan de membresía con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public MembershipPlan save(MembershipPlan model) {
        try {
            return membershipPlanRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar plan de membresía: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            membershipPlanRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar plan de membresía con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipPlan> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return membershipPlanRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar plan de membresía por nombre: " + e.getMessage());
        }
    }

}
