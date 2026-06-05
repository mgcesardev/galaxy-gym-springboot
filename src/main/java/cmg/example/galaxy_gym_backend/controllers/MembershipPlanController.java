package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.MembershipPlan;    
import cmg.example.galaxy_gym_backend.services.MembershipPlanService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/membership-plans")
public class MembershipPlanController {

    private final MembershipPlanService membershipPlanService;

    public MembershipPlanController(MembershipPlanService membershipPlanService) {
        this.membershipPlanService = membershipPlanService;
    }

    @GetMapping
    public Page<MembershipPlan> findAll(Pageable pageable) {
        return membershipPlanService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public MembershipPlan findById(@PathVariable Long id) {
        return membershipPlanService.findById(id);
    }

    @PostMapping
    public MembershipPlan save(@RequestBody MembershipPlan model) {
        return membershipPlanService.save(model);
    }

    @PatchMapping("/{id}")
    public MembershipPlan update(@PathVariable Long id, @RequestBody MembershipPlan model) {
        MembershipPlan existing = membershipPlanService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return membershipPlanService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        membershipPlanService.delete(id);
    }

}
