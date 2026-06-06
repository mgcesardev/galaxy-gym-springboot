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
        return membershipPlanRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipPlan findById(Long id) {
        return membershipPlanRepository.findById(id).orElse(null);
    }

    @Override
    public MembershipPlan save(MembershipPlan model) {
        return membershipPlanRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        membershipPlanRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipPlan> buscarPorNombre(String nombre, Pageable pageable) {
        return membershipPlanRepository.buscarPorNombre(nombre, pageable);
    }

}
