package cmg.example.galaxy_gym_backend.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ModelsTest {

    @Test
    void testAttendance() {
        Attendance a1 = new Attendance();
        Attendance a2 = new Attendance(1L, new User(), new Membership(), LocalDateTime.now(), LocalDateTime.now(), Attendance.AttendanceType.GYM_ACCESS, "Notes", "127.0.0.1", "Mobile", LocalDateTime.now());
        
        a1.setId(10L);
        a1.setUser(a2.getUser());
        a1.setMembership(a2.getMembership());
        // Do NOT set checkIn here to cover null branch in onCreate
        a1.setCheckOut(a2.getCheckOut());
        a1.setType(a2.getType());
        a1.setNotes(a2.getNotes());
        a1.setIpAddress(a2.getIpAddress());
        a1.setDeviceInfo(a2.getDeviceInfo());
        a1.setCreatedAt(a2.getCreatedAt());

        assertEquals(10L, a1.getId());
        assertEquals(a2.getUser(), a1.getUser());
        assertEquals(a2.getMembership(), a1.getMembership());
        assertEquals(a2.getCheckOut(), a1.getCheckOut());
        assertEquals(a2.getType(), a1.getType());
        assertEquals(a2.getNotes(), a1.getNotes());
        assertEquals(a2.getIpAddress(), a1.getIpAddress());
        assertEquals(a2.getDeviceInfo(), a1.getDeviceInfo());
        assertEquals(a2.getCreatedAt(), a1.getCreatedAt());

        a1.onCreate();
        assertNotNull(a1.getCreatedAt());
        assertNotNull(a1.getCheckIn());

        // Test non-null branch of checkIn in onCreate
        Attendance a3 = new Attendance();
        LocalDateTime testCheckIn = LocalDateTime.now().minusDays(1);
        a3.setCheckIn(testCheckIn);
        a3.onCreate();
        assertEquals(testCheckIn, a3.getCheckIn());

        assertNotNull(a1.toString());
        assertEquals(a1, a1);
        assertNotEquals(a1, a2);
        assertNotNull(a1.hashCode());
    }

    @Test
    void testEquipment() {
        Equipment e1 = new Equipment();
        Equipment e2 = new Equipment(1L, "Treadmill", "Serial123", "BrandX", "ModelY", "Description", Equipment.EquipmentCategory.CARDIO, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), Equipment.EquipmentStatus.AVAILABLE, "Gym A", 999.99, "url", "Notes", LocalDateTime.now(), LocalDateTime.now());

        e1.setId(10L);
        e1.setName(e2.getName());
        e1.setBrand(e2.getBrand());
        e1.setModel(e2.getModel());
        e1.setSerialNumber(e2.getSerialNumber());
        e1.setDescription(e2.getDescription());
        e1.setCategory(e2.getCategory());
        e1.setStatus(e2.getStatus());
        e1.setPurchaseDate(e2.getPurchaseDate());
        e1.setLastMaintenanceDate(e2.getLastMaintenanceDate());
        e1.setNextMaintenanceDate(e2.getNextMaintenanceDate());
        e1.setMaintenanceNotes(e2.getMaintenanceNotes());
        e1.setLocation(e2.getLocation());
        e1.setPrice(e2.getPrice());
        e1.setImageUrl(e2.getImageUrl());
        e1.setCreatedAt(e2.getCreatedAt());
        e1.setUpdatedAt(e2.getUpdatedAt());

        assertEquals(10L, e1.getId());
        assertEquals(e2.getName(), e1.getName());
        assertEquals(e2.getBrand(), e1.getBrand());
        assertEquals(e2.getModel(), e1.getModel());
        assertEquals(e2.getSerialNumber(), e1.getSerialNumber());
        assertEquals(e2.getDescription(), e1.getDescription());
        assertEquals(e2.getCategory(), e1.getCategory());
        assertEquals(e2.getStatus(), e1.getStatus());
        assertEquals(e2.getPurchaseDate(), e1.getPurchaseDate());
        assertEquals(e2.getLastMaintenanceDate(), e1.getLastMaintenanceDate());
        assertEquals(e2.getNextMaintenanceDate(), e1.getNextMaintenanceDate());
        assertEquals(e2.getMaintenanceNotes(), e1.getMaintenanceNotes());
        assertEquals(e2.getLocation(), e1.getLocation());
        assertEquals(e2.getPrice(), e1.getPrice());
        assertEquals(e2.getImageUrl(), e1.getImageUrl());
        assertEquals(e2.getCreatedAt(), e1.getCreatedAt());
        assertEquals(e2.getUpdatedAt(), e1.getUpdatedAt());

        e1.onCreate();
        e1.onUpdate();
        assertNotNull(e1.getCreatedAt());
        assertNotNull(e1.getUpdatedAt());

        assertNotNull(e1.toString());
        assertEquals(e1, e1);
        assertNotEquals(e1, e2);
        assertNotNull(e1.hashCode());
    }

    @Test
    void testExercise() {
        Exercise ex1 = new Exercise();
        Exercise ex2 = new Exercise(1L, "Squat", "Squat desc", Exercise.MuscleGroup.QUADRICEPS, Exercise.MuscleGroup.GLUTES, Exercise.ExerciseType.STRENGTH, Exercise.DifficultyLevel.BEGINNER, "videoUrl", "imageUrl", 10, true, "instructions", "safetyTips", LocalDateTime.now(), LocalDateTime.now());

        ex1.setId(10L);
        ex1.setName(ex2.getName());
        ex1.setDescription(ex2.getDescription());
        ex1.setPrimaryMuscle(ex2.getPrimaryMuscle());
        ex1.setSecondaryMuscle(ex2.getSecondaryMuscle());
        ex1.setType(ex2.getType());
        ex1.setDifficulty(ex2.getDifficulty());
        ex1.setVideoUrl(ex2.getVideoUrl());
        ex1.setImageUrl(ex2.getImageUrl());
        ex1.setCaloriesBurnedPerMinute(ex2.getCaloriesBurnedPerMinute());
        ex1.setRequiresEquipment(ex2.getRequiresEquipment());
        ex1.setInstructions(ex2.getInstructions());
        ex1.setSafetyTips(ex2.getSafetyTips());
        ex1.setCreatedAt(ex2.getCreatedAt());
        ex1.setUpdatedAt(ex2.getUpdatedAt());

        assertEquals(10L, ex1.getId());
        assertEquals(ex2.getName(), ex1.getName());
        assertEquals(ex2.getDescription(), ex1.getDescription());
        assertEquals(ex2.getPrimaryMuscle(), ex1.getPrimaryMuscle());
        assertEquals(ex2.getSecondaryMuscle(), ex1.getSecondaryMuscle());
        assertEquals(ex2.getType(), ex1.getType());
        assertEquals(ex2.getDifficulty(), ex1.getDifficulty());
        assertEquals(ex2.getVideoUrl(), ex1.getVideoUrl());
        assertEquals(ex2.getImageUrl(), ex1.getImageUrl());
        assertEquals(ex2.getCaloriesBurnedPerMinute(), ex1.getCaloriesBurnedPerMinute());
        assertEquals(ex2.getRequiresEquipment(), ex1.getRequiresEquipment());
        assertEquals(ex2.getInstructions(), ex1.getInstructions());
        assertEquals(ex2.getSafetyTips(), ex1.getSafetyTips());
        assertEquals(ex2.getCreatedAt(), ex1.getCreatedAt());
        assertEquals(ex2.getUpdatedAt(), ex1.getUpdatedAt());

        ex1.onCreate();
        ex1.onUpdate();
        assertNotNull(ex1.getCreatedAt());
        assertNotNull(ex1.getUpdatedAt());

        assertNotNull(ex1.toString());
        assertEquals(ex1, ex1);
        assertNotEquals(ex1, ex2);
        assertNotNull(ex1.hashCode());
    }

    @Test
    void testGymClass() {
        GymClass gc1 = new GymClass();
        GymClass gc2 = new GymClass(1L, "Yoga", "Relaxing yoga", new Trainer(), GymClass.ClassType.YOGA, LocalDateTime.now(), LocalDateTime.now(), 20, 0, "Room A", Exercise.DifficultyLevel.BEGINNER, 10.0, true, "pattern", GymClass.ClassStatus.SCHEDULED, new HashSet<>(), LocalDateTime.now(), LocalDateTime.now());

        gc1.setId(10L);
        gc1.setName(gc2.getName());
        gc1.setDescription(gc2.getDescription());
        gc1.setTrainer(gc2.getTrainer());
        gc1.setType(gc2.getType());
        gc1.setStartTime(gc2.getStartTime());
        gc1.setEndTime(gc2.getEndTime());
        gc1.setMaxCapacity(gc2.getMaxCapacity());
        gc1.setCurrentEnrollment(gc2.getCurrentEnrollment());
        gc1.setLocation(gc2.getLocation());
        gc1.setDifficulty(gc2.getDifficulty());
        gc1.setPrice(gc2.getPrice());
        gc1.setIsRecurring(gc2.getIsRecurring());
        gc1.setRecurrencePattern(gc2.getRecurrencePattern());
        gc1.setStatus(gc2.getStatus());
        gc1.setReservations(gc2.getReservations());
        gc1.setCreatedAt(gc2.getCreatedAt());
        gc1.setUpdatedAt(gc2.getUpdatedAt());

        assertEquals(10L, gc1.getId());
        assertEquals(gc2.getName(), gc1.getName());
        assertEquals(gc2.getDescription(), gc1.getDescription());
        assertEquals(gc2.getTrainer(), gc1.getTrainer());
        assertEquals(gc2.getType(), gc1.getType());
        assertEquals(gc2.getStartTime(), gc1.getStartTime());
        assertEquals(gc2.getEndTime(), gc1.getEndTime());
        assertEquals(gc2.getMaxCapacity(), gc1.getMaxCapacity());
        assertEquals(gc2.getCurrentEnrollment(), gc1.getCurrentEnrollment());
        assertEquals(gc2.getLocation(), gc1.getLocation());
        assertEquals(gc2.getDifficulty(), gc1.getDifficulty());
        assertEquals(gc2.getPrice(), gc1.getPrice());
        assertEquals(gc2.getIsRecurring(), gc1.getIsRecurring());
        assertEquals(gc2.getRecurrencePattern(), gc1.getRecurrencePattern());
        assertEquals(gc2.getStatus(), gc1.getStatus());
        assertEquals(gc2.getReservations(), gc1.getReservations());
        assertEquals(gc2.getCreatedAt(), gc1.getCreatedAt());
        assertEquals(gc2.getUpdatedAt(), gc1.getUpdatedAt());

        gc1.onCreate();
        gc1.onUpdate();
        assertNotNull(gc1.getCreatedAt());
        assertNotNull(gc1.getUpdatedAt());

        assertNotNull(gc1.toString());
        assertEquals(gc1, gc1);
        assertNotEquals(gc1, gc2);
        assertNotNull(gc1.hashCode());
    }

    @Test
    void testMembership() {
        Membership m1 = new Membership();
        Membership m2 = new Membership(1L, new User(), new MembershipPlan(), "GYM-12345", LocalDate.now(), LocalDate.now(), Membership.MembershipStatus.ACTIVE, BigDecimal.TEN, Membership.PaymentMethod.CASH, 0, true, "notes", LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());

        m1.setId(10L);
        m1.setUser(m2.getUser());
        m1.setPlan(m2.getPlan());
        m1.setMembershipNumber(m2.getMembershipNumber());
        m1.setStartDate(m2.getStartDate());
        m1.setEndDate(m2.getEndDate());
        m1.setStatus(m2.getStatus());
        m1.setAmountPaid(m2.getAmountPaid());
        m1.setPaymentMethod(m2.getPaymentMethod());
        m1.setClassesAttended(m2.getClassesAttended());
        m1.setAutoRenew(m2.getAutoRenew());
        m1.setNotes(m2.getNotes());
        m1.setPayments(m2.getPayments());
        m1.setCreatedAt(m2.getCreatedAt());
        m1.setUpdatedAt(m2.getUpdatedAt());

        assertEquals(10L, m1.getId());
        assertEquals(m2.getUser(), m1.getUser());
        assertEquals(m2.getPlan(), m1.getPlan());
        assertEquals(m2.getMembershipNumber(), m1.getMembershipNumber());
        assertEquals(m2.getStartDate(), m1.getStartDate());
        assertEquals(m2.getEndDate(), m1.getEndDate());
        assertEquals(m2.getStatus(), m1.getStatus());
        assertEquals(m2.getAmountPaid(), m1.getAmountPaid());
        assertEquals(m2.getPaymentMethod(), m1.getPaymentMethod());
        assertEquals(m2.getClassesAttended(), m1.getClassesAttended());
        assertEquals(m2.getAutoRenew(), m1.getAutoRenew());
        assertEquals(m2.getNotes(), m1.getNotes());
        assertEquals(m2.getPayments(), m1.getPayments());
        assertEquals(m2.getCreatedAt(), m1.getCreatedAt());
        assertEquals(m2.getUpdatedAt(), m1.getUpdatedAt());

        m1.onCreate();
        m1.onUpdate();
        assertNotNull(m1.getCreatedAt());
        assertNotNull(m1.getUpdatedAt());
        assertNotNull(m1.getMembershipNumber());

        assertNotNull(m1.toString());
        assertEquals(m1, m1);
        assertNotEquals(m1, m2);
        assertNotNull(m1.hashCode());
    }

    @Test
    void testMembershipPlan() {
        MembershipPlan mp1 = new MembershipPlan();
        MembershipPlan mp2 = new MembershipPlan(1L, "Monthly", "Plan desc", 30, BigDecimal.TEN, BigDecimal.ONE, 3, true, true, true, MembershipPlan.PlanStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now());

        mp1.setId(10L);
        mp1.setName(mp2.getName());
        mp1.setDescription(mp2.getDescription());
        mp1.setDurationDays(mp2.getDurationDays());
        mp1.setPrice(mp2.getPrice());
        mp1.setDiscount(mp2.getDiscount());
        mp1.setMaxClassesPerWeek(mp2.getMaxClassesPerWeek());
        mp1.setIncludesTrainer(mp2.getIncludesTrainer());
        mp1.setIncludesNutritionPlan(mp2.getIncludesNutritionPlan());
        mp1.setIncludesParking(mp2.getIncludesParking());
        mp1.setStatus(mp2.getStatus());
        mp1.setCreatedAt(mp2.getCreatedAt());
        mp1.setUpdatedAt(mp2.getUpdatedAt());

        assertEquals(10L, mp1.getId());
        assertEquals(mp2.getName(), mp1.getName());
        assertEquals(mp2.getDescription(), mp1.getDescription());
        assertEquals(mp2.getDurationDays(), mp1.getDurationDays());
        assertEquals(mp2.getPrice(), mp1.getPrice());
        assertEquals(mp2.getDiscount(), mp1.getDiscount());
        assertEquals(mp2.getMaxClassesPerWeek(), mp1.getMaxClassesPerWeek());
        assertEquals(mp2.getIncludesTrainer(), mp1.getIncludesTrainer());
        assertEquals(mp2.getIncludesNutritionPlan(), mp1.getIncludesNutritionPlan());
        assertEquals(mp2.getIncludesParking(), mp1.getIncludesParking());
        assertEquals(mp2.getStatus(), mp1.getStatus());
        assertEquals(mp2.getCreatedAt(), mp1.getCreatedAt());
        assertEquals(mp2.getUpdatedAt(), mp1.getUpdatedAt());

        mp1.onCreate();
        mp1.onUpdate();
        assertNotNull(mp1.getCreatedAt());
        assertNotNull(mp1.getUpdatedAt());

        assertNotNull(mp1.toString());
        assertEquals(mp1, mp1);
        assertNotEquals(mp1, mp2);
        assertNotNull(mp1.hashCode());
    }

    @Test
    void testNotification() {
        Notification n1 = new Notification();
        Notification n2 = new Notification(1L, new User(), "Title", "Message", Notification.NotificationType.GENERAL, false, LocalDateTime.now(), "link", "icon", Notification.NotificationPriority.MEDIUM, LocalDateTime.now(), LocalDateTime.now());

        n1.setId(10L);
        n1.setUser(n2.getUser());
        n1.setTitle(n2.getTitle());
        n1.setMessage(n2.getMessage());
        n1.setType(n2.getType());
        n1.setIsRead(n2.getIsRead());
        n1.setReadAt(n2.getReadAt());
        n1.setLink(n2.getLink());
        n1.setIcon(n2.getIcon());
        n1.setPriority(n2.getPriority());
        n1.setCreatedAt(n2.getCreatedAt());
        n1.setExpiresAt(n2.getExpiresAt());

        assertEquals(10L, n1.getId());
        assertEquals(n2.getUser(), n1.getUser());
        assertEquals(n2.getTitle(), n1.getTitle());
        assertEquals(n2.getMessage(), n1.getMessage());
        assertEquals(n2.getType(), n1.getType());
        assertEquals(n2.getIsRead(), n1.getIsRead());
        assertEquals(n2.getReadAt(), n1.getReadAt());
        assertEquals(n2.getLink(), n1.getLink());
        assertEquals(n2.getIcon(), n1.getIcon());
        assertEquals(n2.getPriority(), n1.getPriority());
        assertEquals(n2.getCreatedAt(), n1.getCreatedAt());
        assertEquals(n2.getExpiresAt(), n1.getExpiresAt());

        n1.onCreate();
        assertNotNull(n1.getCreatedAt());

        assertNotNull(n1.toString());
        assertEquals(n1, n1);
        assertNotEquals(n1, n2);
        assertNotNull(n1.hashCode());
    }

    @Test
    void testPayment() {
        Payment p1 = new Payment();
        Payment p2 = new Payment(1L, new Membership(), new User(), BigDecimal.TEN, LocalDateTime.now(), Payment.PaymentStatus.COMPLETED, Membership.PaymentMethod.CASH, "transactionId", "receiptNumber", "description", "paymentProof", LocalDateTime.now());

        p1.setId(10L);
        p1.setMembership(p2.getMembership());
        p1.setUser(p2.getUser());
        p1.setAmount(p2.getAmount());
        // Do NOT set paymentDate and receiptNumber here to cover null branches in onCreate
        p1.setStatus(p2.getStatus());
        p1.setMethod(p2.getMethod());
        p1.setTransactionId(p2.getTransactionId());
        p1.setDescription(p2.getDescription());
        p1.setPaymentProof(p2.getPaymentProof());
        p1.setCreatedAt(p2.getCreatedAt());

        assertEquals(10L, p1.getId());
        assertEquals(p2.getMembership(), p1.getMembership());
        assertEquals(p2.getUser(), p1.getUser());
        assertEquals(p2.getAmount(), p1.getAmount());
        assertEquals(p2.getStatus(), p1.getStatus());
        assertEquals(p2.getMethod(), p1.getMethod());
        assertEquals(p2.getTransactionId(), p1.getTransactionId());
        assertEquals(p2.getDescription(), p1.getDescription());
        assertEquals(p2.getPaymentProof(), p1.getPaymentProof());
        assertEquals(p2.getCreatedAt(), p1.getCreatedAt());

        p1.onCreate();
        assertNotNull(p1.getCreatedAt());
        assertNotNull(p1.getPaymentDate());
        assertNotNull(p1.getReceiptNumber());

        // Test non-null branches in onCreate
        Payment p3 = new Payment();
        LocalDateTime testPaymentDate = LocalDateTime.now().minusDays(1);
        p3.setPaymentDate(testPaymentDate);
        p3.setReceiptNumber("REC-TEST-123");
        p3.onCreate();
        assertEquals(testPaymentDate, p3.getPaymentDate());
        assertEquals("REC-TEST-123", p3.getReceiptNumber());

        assertNotNull(p1.toString());
        assertEquals(p1, p1);
        assertNotEquals(p1, p2);
        assertNotNull(p1.hashCode());
    }

    @Test
    void testReservation() {
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation(1L, new User(), new GymClass(), LocalDateTime.now(), Reservation.ReservationStatus.CONFIRMED, Reservation.AttendanceStatus.ATTENDED, "notes", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

        r1.setId(10L);
        r1.setUser(r2.getUser());
        r1.setClassEntity(r2.getClassEntity());
        // Do NOT set reservationDate here to cover null branch in onCreate
        r1.setStatus(r2.getStatus());
        r1.setAttendanceStatus(r2.getAttendanceStatus());
        r1.setNotes(r2.getNotes());
        r1.setCheckInTime(r2.getCheckInTime());
        r1.setCreatedAt(r2.getCreatedAt());
        r1.setUpdatedAt(r2.getUpdatedAt());

        assertEquals(10L, r1.getId());
        assertEquals(r2.getUser(), r1.getUser());
        assertEquals(r2.getClassEntity(), r1.getClassEntity());
        assertEquals(r2.getStatus(), r1.getStatus());
        assertEquals(r2.getAttendanceStatus(), r1.getAttendanceStatus());
        assertEquals(r2.getNotes(), r1.getNotes());
        assertEquals(r2.getCheckInTime(), r1.getCheckInTime());
        assertEquals(r2.getCreatedAt(), r1.getCreatedAt());
        assertEquals(r2.getUpdatedAt(), r1.getUpdatedAt());

        r1.onCreate();
        r1.onUpdate();
        assertNotNull(r1.getCreatedAt());
        assertNotNull(r1.getUpdatedAt());
        assertNotNull(r1.getReservationDate());

        // Test non-null branch of reservationDate in onCreate
        Reservation r3 = new Reservation();
        LocalDateTime testReservationDate = LocalDateTime.now().minusDays(1);
        r3.setReservationDate(testReservationDate);
        r3.onCreate();
        assertEquals(testReservationDate, r3.getReservationDate());

        assertNotNull(r1.toString());
        assertEquals(r1, r1);
        assertNotEquals(r1, r2);
        assertNotNull(r1.hashCode());
    }

    @Test
    void testRole() {
        Role rl1 = new Role();
        Role rl2 = new Role(1L, Role.RoleType.ROLE_ADMIN, "Admin desc");

        rl1.setId(10L);
        rl1.setName(rl2.getName());
        rl1.setDescription(rl2.getDescription());

        assertEquals(10L, rl1.getId());
        assertEquals(rl2.getName(), rl1.getName());
        assertEquals(rl2.getDescription(), rl1.getDescription());

        assertNotNull(rl1.toString());
        assertEquals(rl1, rl1);
        assertNotEquals(rl1, rl2);
        assertNotNull(rl1.hashCode());
    }

    @Test
    void testRoutineExercise() {
        RoutineExercise re1 = new RoutineExercise();
        RoutineExercise re2 = new RoutineExercise(1L, new WorkoutRoutine(), new Exercise(), 3, 12, 60, 20.0, "15m", "Notes", 1, RoutineExercise.IntensityLevel.MEDIUM);

        re1.setId(10L);
        re1.setRoutine(re2.getRoutine());
        re1.setExercise(re2.getExercise());
        re1.setSets(re2.getSets());
        re1.setReps(re2.getReps());
        re1.setRestSeconds(re2.getRestSeconds());
        re1.setWeight(re2.getWeight());
        re1.setDuration(re2.getDuration());
        re1.setNotes(re2.getNotes());
        re1.setOrderIndex(re2.getOrderIndex());
        re1.setIntensity(re2.getIntensity());

        assertEquals(10L, re1.getId());
        assertEquals(re2.getRoutine(), re1.getRoutine());
        assertEquals(re2.getExercise(), re1.getExercise());
        assertEquals(re2.getSets(), re1.getSets());
        assertEquals(re2.getReps(), re1.getReps());
        assertEquals(re2.getRestSeconds(), re1.getRestSeconds());
        assertEquals(re2.getWeight(), re1.getWeight());
        assertEquals(re2.getDuration(), re1.getDuration());
        assertEquals(re2.getNotes(), re1.getNotes());
        assertEquals(re2.getOrderIndex(), re1.getOrderIndex());
        assertEquals(re2.getIntensity(), re1.getIntensity());

        assertNotNull(re1.toString());
        assertEquals(re1, re1);
        assertNotEquals(re1, re2);
        assertNotNull(re1.hashCode());
    }

    @Test
    void testTrainer() {
        Trainer t1 = new Trainer();
        Trainer t2 = new Trainer(1L, new User(), "Specialization", "Certifications", 5, "Bio", BigDecimal.TEN, Trainer.TrainerStatus.ACTIVE, "Schedule", 10, 4.5, "pic", LocalDateTime.now(), LocalDateTime.now(), new HashSet<>(), new HashSet<>());

        t1.setId(10L);
        t1.setUser(t2.getUser());
        t1.setSpecialization(t2.getSpecialization());
        t1.setCertifications(t2.getCertifications());
        t1.setYearsOfExperience(t2.getYearsOfExperience());
        t1.setBiography(t2.getBiography());
        t1.setHourlyRate(t2.getHourlyRate());
        t1.setStatus(t2.getStatus());
        t1.setSchedule(t2.getSchedule());
        t1.setMaxClients(t2.getMaxClients());
        t1.setRating(t2.getRating());
        t1.setProfilePicture(t2.getProfilePicture());
        t1.setCreatedAt(t2.getCreatedAt());
        t1.setUpdatedAt(t2.getUpdatedAt());
        t1.setClasses(t2.getClasses());
        t1.setRoutines(t2.getRoutines());

        assertEquals(10L, t1.getId());
        assertEquals(t2.getUser(), t1.getUser());
        assertEquals(t2.getSpecialization(), t1.getSpecialization());
        assertEquals(t2.getCertifications(), t1.getCertifications());
        assertEquals(t2.getYearsOfExperience(), t1.getYearsOfExperience());
        assertEquals(t2.getBiography(), t1.getBiography());
        assertEquals(t2.getHourlyRate(), t1.getHourlyRate());
        assertEquals(t2.getStatus(), t1.getStatus());
        assertEquals(t2.getSchedule(), t1.getSchedule());
        assertEquals(t2.getMaxClients(), t1.getMaxClients());
        assertEquals(t2.getRating(), t1.getRating());
        assertEquals(t2.getProfilePicture(), t1.getProfilePicture());
        assertEquals(t2.getCreatedAt(), t1.getCreatedAt());
        assertEquals(t2.getUpdatedAt(), t1.getUpdatedAt());
        assertEquals(t2.getClasses(), t1.getClasses());
        assertEquals(t2.getRoutines(), t1.getRoutines());

        t1.onCreate();
        t1.onUpdate();
        assertNotNull(t1.getCreatedAt());
        assertNotNull(t1.getUpdatedAt());

        assertNotNull(t1.toString());
        assertEquals(t1, t1);
        assertNotEquals(t1, t2);
        assertNotNull(t1.hashCode());
    }

    @Test
    void testUser() {
        User u1 = new User();
        User u2 = new User(1L, "username", "email@user.com", "pass", "John", "Doe", "12345678", "Address", LocalDateTime.now(), "Emergency", "911", User.Gender.MUJER, 70.0, 1.75, "Notes", "pic", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true, false, "token", "reset", LocalDateTime.now(), LocalDateTime.now(), 0, false, LocalDateTime.now(), LocalDateTime.now());

        u1.setId(10L);
        u1.setUsername(u2.getUsername());
        u1.setEmail(u2.getEmail());
        u1.setPassword(u2.getPassword());
        u1.setFirstName(u2.getFirstName());
        u1.setLastName(u2.getLastName());
        u1.setPhone(u2.getPhone());
        u1.setAddress(u2.getAddress());
        u1.setBirthDate(u2.getBirthDate());
        u1.setEmergencyContact(u2.getEmergencyContact());
        u1.setEmergencyPhone(u2.getEmergencyPhone());
        u1.setGender(u2.getGender());
        u1.setWeight(u2.getWeight());
        u1.setHeight(u2.getHeight());
        u1.setMedicalNotes(u2.getMedicalNotes());
        u1.setProfilePicture(u2.getProfilePicture());
        u1.setRoles(u2.getRoles());
        u1.setMemberships(u2.getMemberships());
        u1.setAttendances(u2.getAttendances());
        u1.setReservations(u2.getReservations());
        u1.setNotifications(u2.getNotifications());
        u1.setIsActive(u2.getIsActive());
        u1.setIsVerified(u2.getIsVerified());
        u1.setVerificationToken(u2.getVerificationToken());
        u1.setResetPasswordToken(u2.getResetPasswordToken());
        u1.setResetPasswordExpiry(u2.getResetPasswordExpiry());
        u1.setLastLogin(u2.getLastLogin());
        u1.setLoginAttempts(u2.getLoginAttempts());
        u1.setAccountLocked(u2.getAccountLocked());
        u1.setCreatedAt(u2.getCreatedAt());
        u1.setUpdatedAt(u2.getUpdatedAt());

        assertEquals(10L, u1.getId());
        assertEquals(u2.getUsername(), u1.getUsername());
        assertEquals(u2.getEmail(), u1.getEmail());
        assertEquals(u2.getPassword(), u1.getPassword());
        assertEquals(u2.getFirstName(), u1.getFirstName());
        assertEquals(u2.getLastName(), u1.getLastName());
        assertEquals(u2.getPhone(), u1.getPhone());
        assertEquals(u2.getAddress(), u1.getAddress());
        assertEquals(u2.getBirthDate(), u1.getBirthDate());
        assertEquals(u2.getEmergencyContact(), u1.getEmergencyContact());
        assertEquals(u2.getEmergencyPhone(), u1.getEmergencyPhone());
        assertEquals(u2.getGender(), u1.getGender());
        assertEquals(u2.getWeight(), u1.getWeight());
        assertEquals(u2.getHeight(), u1.getHeight());
        assertEquals(u2.getMedicalNotes(), u1.getMedicalNotes());
        assertEquals(u2.getProfilePicture(), u1.getProfilePicture());
        assertEquals(u2.getRoles(), u1.getRoles());
        assertEquals(u2.getMemberships(), u1.getMemberships());
        assertEquals(u2.getAttendances(), u1.getAttendances());
        assertEquals(u2.getReservations(), u1.getReservations());
        assertEquals(u2.getNotifications(), u1.getNotifications());
        assertEquals(u2.getIsActive(), u1.getIsActive());
        assertEquals(u2.getIsVerified(), u1.getIsVerified());
        assertEquals(u2.getVerificationToken(), u1.getVerificationToken());
        assertEquals(u2.getResetPasswordToken(), u1.getResetPasswordToken());
        assertEquals(u2.getResetPasswordExpiry(), u1.getResetPasswordExpiry());
        assertEquals(u2.getLastLogin(), u1.getLastLogin());
        assertEquals(u2.getLoginAttempts(), u1.getLoginAttempts());
        assertEquals(u2.getAccountLocked(), u1.getAccountLocked());
        assertEquals(u2.getCreatedAt(), u1.getCreatedAt());
        assertEquals(u2.getUpdatedAt(), u1.getUpdatedAt());

        u1.onCreate();
        u1.onUpdate();
        assertNotNull(u1.getCreatedAt());
        assertNotNull(u1.getUpdatedAt());

        assertNotNull(u1.toString());
        assertEquals(u1, u1);
        assertNotEquals(u1, u2);
        assertNotNull(u1.hashCode());
    }

    @Test
    void testWorkoutRoutine() {
        WorkoutRoutine wr1 = new WorkoutRoutine();
        WorkoutRoutine wr2 = new WorkoutRoutine(1L, "RoutineName", "Description", new User(), new Trainer(), WorkoutRoutine.RoutineType.STRENGTH_TRAINING, Exercise.DifficultyLevel.BEGINNER, 45, 300, 90, new HashSet<>(), "Notes", true, false, 5, 4.5, LocalDateTime.now(), LocalDateTime.now());

        wr1.setId(10L);
        wr1.setName(wr2.getName());
        wr1.setDescription(wr2.getDescription());
        wr1.setCreatedBy(wr2.getCreatedBy());
        wr1.setTrainer(wr2.getTrainer());
        wr1.setType(wr2.getType());
        wr1.setDifficulty(wr2.getDifficulty());
        wr1.setDurationMinutes(wr2.getDurationMinutes());
        wr1.setEstimatedCaloriesBurn(wr2.getEstimatedCaloriesBurn());
        wr1.setRestBetweenSetsSeconds(wr2.getRestBetweenSetsSeconds());
        wr1.setExercises(wr2.getExercises());
        wr1.setNotes(wr2.getNotes());
        wr1.setIsPublic(wr2.getIsPublic());
        wr1.setIsTemplate(wr2.getIsTemplate());
        wr1.setPopularity(wr2.getPopularity());
        wr1.setRating(wr2.getRating());
        wr1.setCreatedAt(wr2.getCreatedAt());
        wr1.setUpdatedAt(wr2.getUpdatedAt());

        assertEquals(10L, wr1.getId());
        assertEquals(wr2.getName(), wr1.getName());
        assertEquals(wr2.getDescription(), wr1.getDescription());
        assertEquals(wr2.getCreatedBy(), wr1.getCreatedBy());
        assertEquals(wr2.getTrainer(), wr1.getTrainer());
        assertEquals(wr2.getType(), wr1.getType());
        assertEquals(wr2.getDifficulty(), wr1.getDifficulty());
        assertEquals(wr2.getDurationMinutes(), wr1.getDurationMinutes());
        assertEquals(wr2.getEstimatedCaloriesBurn(), wr1.getEstimatedCaloriesBurn());
        assertEquals(wr2.getRestBetweenSetsSeconds(), wr1.getRestBetweenSetsSeconds());
        assertEquals(wr2.getExercises(), wr1.getExercises());
        assertEquals(wr2.getNotes(), wr1.getNotes());
        assertEquals(wr2.getIsPublic(), wr1.getIsPublic());
        assertEquals(wr2.getIsTemplate(), wr1.getIsTemplate());
        assertEquals(wr2.getPopularity(), wr1.getPopularity());
        assertEquals(wr2.getRating(), wr1.getRating());
        assertEquals(wr2.getCreatedAt(), wr1.getCreatedAt());
        assertEquals(wr2.getUpdatedAt(), wr1.getUpdatedAt());

        wr1.onCreate();
        wr1.onUpdate();
        assertNotNull(wr1.getCreatedAt());
        assertNotNull(wr1.getUpdatedAt());

        assertNotNull(wr1.toString());
        assertEquals(wr1, wr1);
        assertNotEquals(wr1, wr2);
        assertNotNull(wr1.hashCode());
    }
}
