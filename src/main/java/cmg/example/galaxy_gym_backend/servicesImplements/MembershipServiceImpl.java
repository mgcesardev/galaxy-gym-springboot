package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Membership;
import cmg.example.galaxy_gym_backend.repositories.MembershipRepository;
import cmg.example.galaxy_gym_backend.services.MembershipService;

@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Membership> findAll(Pageable pageable) {
        try {
            return membershipRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las membresías", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Membership findById(Long id) {
        try {
            return membershipRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar membresía con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Membership save(Membership model) {
        try {
            return membershipRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar membresía: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            membershipRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar membresía con id " + id + ": " + e.getMessage());
        }
    }


}
