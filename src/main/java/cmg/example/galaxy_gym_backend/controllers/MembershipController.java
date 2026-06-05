package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.Membership;
import cmg.example.galaxy_gym_backend.services.MembershipService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public Page<Membership> findAll(Pageable pageable) {
        return membershipService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Membership findById(@PathVariable Long id) {
        return membershipService.findById(id);
    }

    @PostMapping
    public Membership save(@RequestBody Membership model) {
        return membershipService.save(model);
    }

    @PatchMapping("/{id}")
    public Membership update(@PathVariable Long id, @RequestBody Membership model) {
        Membership existing = membershipService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return membershipService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        membershipService.delete(id);
    }

}
