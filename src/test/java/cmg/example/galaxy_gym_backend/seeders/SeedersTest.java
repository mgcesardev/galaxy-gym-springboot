package cmg.example.galaxy_gym_backend.seeders;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cmg.example.galaxy_gym_backend.models.*;
import cmg.example.galaxy_gym_backend.repositories.*;

class SeedersTest {

    @Test
    void testRoleSeeder() throws Exception {
        RoleRepository repo = mock(RoleRepository.class);
        RoleSeeder seeder = new RoleSeeder(repo);

        // Skip path
        when(repo.count()).thenReturn(5L);
        seeder.run();
        verify(repo, never()).saveAll(any());

        // Seeding path
        when(repo.count()).thenReturn(0L);
        seeder.run();
        verify(repo, times(1)).saveAll(any());
    }

    @Test
    void testUserSeeder() throws Exception {
        UserRepository userRepo = mock(UserRepository.class);
        RoleRepository roleRepo = mock(RoleRepository.class);
        UserSeeder seeder = new UserSeeder(userRepo, roleRepo);

        // Skip path
        when(userRepo.count()).thenReturn(10L);
        seeder.run();
        verify(userRepo, never()).saveAll(any());

        // Seeding path
        when(userRepo.count()).thenReturn(0L);
        
        Role adminRole = new Role(1L, Role.RoleType.ROLE_ADMIN, "Admin");
        Role trainerRole = new Role(2L, Role.RoleType.ROLE_TRAINER, "Trainer");
        Role memberRole = new Role(3L, Role.RoleType.ROLE_MEMBER, "Member");
        List<Role> roles = List.of(adminRole, trainerRole, memberRole);
        when(roleRepo.findAll()).thenReturn(roles);

        seeder.run();
        verify(userRepo, times(1)).saveAll(any());
    }

    @Test
    void testTrainerSeeder() throws Exception {
        TrainerRepository trainerRepo = mock(TrainerRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        RoleRepository roleRepo = mock(RoleRepository.class);
        TrainerSeeder seeder = new TrainerSeeder(trainerRepo, userRepo);

        // Skip path
        when(trainerRepo.count()).thenReturn(5L);
        seeder.run();
        verify(trainerRepo, never()).saveAll(any());

        // Seeding path - empty user list
        when(trainerRepo.count()).thenReturn(0L);
        when(roleRepo.findAll()).thenReturn(Collections.emptyList());
        when(userRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(trainerRepo, times(1)).saveAll(any());

        reset(trainerRepo);

        // Seeding path - active users found
        Role trainerRole = new Role(1L, Role.RoleType.ROLE_TRAINER, "Trainer");
        when(roleRepo.findAll()).thenReturn(List.of(trainerRole));
        
        User user = new User();
        user.setUsername("trainer1");
        user.setRoles(Collections.singleton(trainerRole));
        when(userRepo.findAll()).thenReturn(List.of(user));

        seeder.run();
        verify(trainerRepo, times(1)).saveAll(any());
    }

    @Test
    void testEquipmentSeeder() throws Exception {
        EquipmentRepository repo = mock(EquipmentRepository.class);
        EquipmentSeeder seeder = new EquipmentSeeder(repo);

        // Skip path
        when(repo.count()).thenReturn(5L);
        seeder.run();
        verify(repo, never()).saveAll(any());

        // Seeding path
        when(repo.count()).thenReturn(0L);
        seeder.run();
        verify(repo, times(1)).saveAll(any());
    }

    @Test
    void testExerciseSeeder() throws Exception {
        ExerciseRepository repo = mock(ExerciseRepository.class);
        ExerciseSeeder seeder = new ExerciseSeeder(repo);

        // Skip path
        when(repo.count()).thenReturn(5L);
        seeder.run();
        verify(repo, never()).saveAll(any());

        // Seeding path
        when(repo.count()).thenReturn(0L);
        seeder.run();
        verify(repo, times(1)).saveAll(any());
    }

    @Test
    void testGymClassSeeder() throws Exception {
        GymClassRepository gymClassRepo = mock(GymClassRepository.class);
        TrainerRepository trainerRepo = mock(TrainerRepository.class);
        GymClassSeeder seeder = new GymClassSeeder(gymClassRepo, trainerRepo);

        // Skip path
        when(gymClassRepo.count()).thenReturn(5L);
        seeder.run();
        verify(gymClassRepo, never()).saveAll(any());

        // Seeding path - empty trainers
        when(gymClassRepo.count()).thenReturn(0L);
        when(trainerRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(gymClassRepo, never()).saveAll(any());

        // Seeding path - trainers available
        Trainer trainer = new Trainer();
        when(trainerRepo.findAll()).thenReturn(List.of(trainer));
        seeder.run();
        verify(gymClassRepo, times(1)).saveAll(any());
    }

    @Test
    void testMembershipPlanSeeder() throws Exception {
        MembershipPlanRepository repo = mock(MembershipPlanRepository.class);
        MembershipPlanSeeder seeder = new MembershipPlanSeeder(repo);

        // Skip path
        when(repo.count()).thenReturn(5L);
        seeder.run();
        verify(repo, never()).saveAll(any());

        // Seeding path
        when(repo.count()).thenReturn(0L);
        seeder.run();
        verify(repo, times(1)).saveAll(any());
    }

    @Test
    void testMembershipSeeder() throws Exception {
        MembershipRepository membershipRepo = mock(MembershipRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        MembershipPlanRepository planRepo = mock(MembershipPlanRepository.class);
        MembershipSeeder seeder = new MembershipSeeder(membershipRepo, userRepo, planRepo);

        // Skip path
        when(membershipRepo.count()).thenReturn(5L);
        seeder.run();
        verify(membershipRepo, never()).saveAll(any());

        // Seeding path - empty users or plans
        when(membershipRepo.count()).thenReturn(0L);
        when(userRepo.findAll()).thenReturn(Collections.emptyList());
        when(planRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(membershipRepo, never()).saveAll(any());

        // Seeding path - records available
        User user = new User();
        user.setId(1L);
        user.setUsername("member1");
        when(userRepo.findAll()).thenReturn(List.of(user));

        MembershipPlan plan = new MembershipPlan();
        plan.setId(1L);
        plan.setDurationDays(30);
        plan.setPrice(java.math.BigDecimal.TEN);
        when(planRepo.findAll()).thenReturn(List.of(plan));

        seeder.run();
        verify(membershipRepo, times(1)).saveAll(any());
    }

    @Test
    void testPaymentSeeder() throws Exception {
        PaymentRepository paymentRepo = mock(PaymentRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        MembershipRepository membershipRepo = mock(MembershipRepository.class);
        PaymentSeeder seeder = new PaymentSeeder(paymentRepo, membershipRepo);

        // Skip path
        when(paymentRepo.count()).thenReturn(5L);
        seeder.run();
        verify(paymentRepo, never()).saveAll(any());

        // Seeding path - empty memberships
        when(paymentRepo.count()).thenReturn(0L);
        when(membershipRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(paymentRepo, never()).saveAll(any());

        // Seeding path - memberships available
        Membership m = new Membership();
        m.setId(1L);
        m.setAmountPaid(java.math.BigDecimal.TEN);
        m.setPaymentMethod(Membership.PaymentMethod.CASH);
        User user = new User();
        user.setId(1L);
        m.setUser(user);

        when(membershipRepo.findAll()).thenReturn(List.of(m));
        seeder.run();
        verify(paymentRepo, times(1)).saveAll(any());
    }

    @Test
    void testReservationSeeder() throws Exception {
        ReservationRepository reservationRepo = mock(ReservationRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        GymClassRepository gymClassRepo = mock(GymClassRepository.class);
        ReservationSeeder seeder = new ReservationSeeder(reservationRepo, gymClassRepo, userRepo);

        // Skip path
        when(reservationRepo.count()).thenReturn(5L);
        seeder.run();
        verify(reservationRepo, never()).saveAll(any());

        // Seeding path - empty users or classes
        when(reservationRepo.count()).thenReturn(0L);
        when(userRepo.findAll()).thenReturn(Collections.emptyList());
        when(gymClassRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(reservationRepo, never()).saveAll(any());

        // Seeding path - records available
        User user = new User();
        user.setId(1L);
        user.setUsername("member1");
        when(userRepo.findAll()).thenReturn(List.of(user));

        GymClass gymClass1 = new GymClass();
        gymClass1.setId(1L);
        GymClass gymClass2 = new GymClass();
        gymClass2.setId(2L);
        when(gymClassRepo.findAll()).thenReturn(List.of(gymClass1, gymClass2));

        seeder.run();
        verify(reservationRepo, times(1)).saveAll(any());
    }

    @Test
    void testAttendanceSeeder() throws Exception {
        AttendanceRepository attendanceRepo = mock(AttendanceRepository.class);
        MembershipRepository membershipRepo = mock(MembershipRepository.class);
        AttendanceSeeder seeder = new AttendanceSeeder(attendanceRepo, membershipRepo);

        // Skip path
        when(attendanceRepo.count()).thenReturn(5L);
        seeder.run();
        verify(attendanceRepo, never()).saveAll(any());

        // Seeding path - empty memberships
        when(attendanceRepo.count()).thenReturn(0L);
        when(membershipRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(attendanceRepo, never()).saveAll(any());

        // Seeding path - memberships available
        Membership m = new Membership();
        m.setId(1L);
        User user = new User();
        user.setId(1L);
        m.setUser(user);

        when(membershipRepo.findAll()).thenReturn(List.of(m));
        seeder.run();
        verify(attendanceRepo, times(1)).saveAll(any());
    }

    @Test
    void testRoutineExerciseSeeder() throws Exception {
        RoutineExerciseRepository routineExerciseRepo = mock(RoutineExerciseRepository.class);
        WorkoutRoutineRepository workoutRoutineRepo = mock(WorkoutRoutineRepository.class);
        ExerciseRepository exerciseRepo = mock(ExerciseRepository.class);
        RoutineExerciseSeeder seeder = new RoutineExerciseSeeder(routineExerciseRepo, workoutRoutineRepo, exerciseRepo);

        // Skip path
        when(routineExerciseRepo.count()).thenReturn(5L);
        seeder.run();
        verify(routineExerciseRepo, never()).saveAll(any());

        // Seeding path - empty routines or exercises
        when(routineExerciseRepo.count()).thenReturn(0L);
        when(workoutRoutineRepo.findAll()).thenReturn(Collections.emptyList());
        when(exerciseRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(routineExerciseRepo, never()).saveAll(any());

        // Seeding path - records available
        WorkoutRoutine routine = new WorkoutRoutine();
        routine.setId(1L);
        when(workoutRoutineRepo.findAll()).thenReturn(List.of(routine));

        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Squat");
        when(exerciseRepo.findAll()).thenReturn(List.of(exercise));

        seeder.run();
        verify(routineExerciseRepo, times(1)).saveAll(any());
    }

    @Test
    void testNotificationSeeder() throws Exception {
        NotificationRepository notificationRepo = mock(NotificationRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        NotificationSeeder seeder = new NotificationSeeder(notificationRepo, userRepo);

        // Skip path
        when(notificationRepo.count()).thenReturn(5L);
        seeder.run();
        verify(notificationRepo, never()).saveAll(any());

        // Seeding path - empty users
        when(notificationRepo.count()).thenReturn(0L);
        when(userRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(notificationRepo, never()).saveAll(any());

        // Seeding path - users available
        User user = new User();
        user.setId(1L);
        user.setUsername("member1");
        when(userRepo.findAll()).thenReturn(List.of(user));

        seeder.run();
        verify(notificationRepo, times(1)).saveAll(any());
    }

    @Test
    void testWorkoutRoutineSeeder() throws Exception {
        WorkoutRoutineRepository workoutRoutineRepo = mock(WorkoutRoutineRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        TrainerRepository trainerRepo = mock(TrainerRepository.class);
        WorkoutRoutineSeeder seeder = new WorkoutRoutineSeeder(workoutRoutineRepo, trainerRepo);

        // Skip path
        when(workoutRoutineRepo.count()).thenReturn(5L);
        seeder.run();
        verify(workoutRoutineRepo, never()).saveAll(any());

        // Seeding path - empty users or trainers
        when(workoutRoutineRepo.count()).thenReturn(0L);
        when(userRepo.findAll()).thenReturn(Collections.emptyList());
        when(trainerRepo.findAll()).thenReturn(Collections.emptyList());
        seeder.run();
        verify(workoutRoutineRepo, never()).saveAll(any());

        // Seeding path - records available
        User user = new User();
        user.setId(1L);
        when(userRepo.findAll()).thenReturn(List.of(user));

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        when(trainerRepo.findAll()).thenReturn(List.of(trainer));

        seeder.run();
        verify(workoutRoutineRepo, times(1)).saveAll(any());
    }
}
