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
        return membershipRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Membership findById(Long id) {
        return membershipRepository.findById(id).orElse(null);
    }

    @Override
    public Membership save(Membership model) {
        return membershipRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        membershipRepository.deleteById(id);
    }

}
