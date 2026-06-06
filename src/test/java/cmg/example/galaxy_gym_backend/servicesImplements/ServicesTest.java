package cmg.example.galaxy_gym_backend.servicesImplements;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cmg.example.galaxy_gym_backend.models.*;
import cmg.example.galaxy_gym_backend.repositories.*;

class ServicesTest {

    @Test
    void testAttendanceServiceImpl() {
        AttendanceRepository repo = mock(AttendanceRepository.class);
        AttendanceServiceImpl service = new AttendanceServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Attendance> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Attendance model = new Attendance();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testEquipmentServiceImpl() {
        EquipmentRepository repo = mock(EquipmentRepository.class);
        EquipmentServiceImpl service = new EquipmentServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Equipment> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Equipment model = new Equipment();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("name", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("name", pageable));
    }

    @Test
    void testExerciseServiceImpl() {
        ExerciseRepository repo = mock(ExerciseRepository.class);
        ExerciseServiceImpl service = new ExerciseServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Exercise> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Exercise model = new Exercise();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("name", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("name", pageable));
    }

    @Test
    void testGymClassServiceImpl() {
        GymClassRepository repo = mock(GymClassRepository.class);
        GymClassServiceImpl service = new GymClassServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<GymClass> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        GymClass model = new GymClass();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("name", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("name", pageable));
    }

    @Test
    void testMembershipPlanServiceImpl() {
        MembershipPlanRepository repo = mock(MembershipPlanRepository.class);
        MembershipPlanServiceImpl service = new MembershipPlanServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<MembershipPlan> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        MembershipPlan model = new MembershipPlan();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("name", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("name", pageable));
    }

    @Test
    void testMembershipServiceImpl() {
        MembershipRepository repo = mock(MembershipRepository.class);
        MembershipServiceImpl service = new MembershipServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Membership> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Membership model = new Membership();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testNotificationServiceImpl() {
        NotificationRepository repo = mock(NotificationRepository.class);
        NotificationServiceImpl service = new NotificationServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Notification> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Notification model = new Notification();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorTitulo("title", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorTitulo("title", pageable));
    }

    @Test
    void testPaymentServiceImpl() {
        PaymentRepository repo = mock(PaymentRepository.class);
        PaymentServiceImpl service = new PaymentServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Payment> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Payment model = new Payment();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testReservationServiceImpl() {
        ReservationRepository repo = mock(ReservationRepository.class);
        ReservationServiceImpl service = new ReservationServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Reservation> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Reservation model = new Reservation();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testRoleServiceImpl() {
        RoleRepository repo = mock(RoleRepository.class);
        RoleServiceImpl service = new RoleServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Role> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Role model = new Role();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("ROLE_ADMIN", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("ROLE_ADMIN", pageable));
    }

    @Test
    void testRoutineExerciseServiceImpl() {
        RoutineExerciseRepository repo = mock(RoutineExerciseRepository.class);
        RoutineExerciseServiceImpl service = new RoutineExerciseServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<RoutineExercise> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        RoutineExercise model = new RoutineExercise();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testTrainerServiceImpl() {
        TrainerRepository repo = mock(TrainerRepository.class);
        TrainerServiceImpl service = new TrainerServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Trainer> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        Trainer model = new Trainer();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testUserServiceImpl() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        User model = new User();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("name", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("name", pageable));

        when(repo.buscarPorCorreo("email", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorCorreo("email", pageable));
    }

    @Test
    void testWorkoutRoutineServiceImpl() {
        WorkoutRoutineRepository repo = mock(WorkoutRoutineRepository.class);
        WorkoutRoutineServiceImpl service = new WorkoutRoutineServiceImpl(repo);

        Pageable pageable = PageRequest.of(0, 10);
        Page<WorkoutRoutine> page = new PageImpl<>(Collections.emptyList());
        when(repo.findAll(pageable)).thenReturn(page);
        assertEquals(page, service.findAll(pageable));

        WorkoutRoutine model = new WorkoutRoutine();
        when(repo.findById(1L)).thenReturn(Optional.of(model));
        assertEquals(model, service.findById(1L));

        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.findById(2L));

        when(repo.save(model)).thenReturn(model);
        assertEquals(model, service.save(model));

        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);

        when(repo.buscarPorNombre("name", pageable)).thenReturn(page);
        assertEquals(page, service.buscarPorNombre("name", pageable));
    }
}
