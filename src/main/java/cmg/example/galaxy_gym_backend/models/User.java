package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phone;

    @Column(length = 500)
    private String address;

    private LocalDateTime birthDate;

    private String emergencyContact;

    private String emergencyPhone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double weight;

    private Double height;

    @Column(columnDefinition = "TEXT")
    private String medicalNotes;

    private String profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Membership> memberships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    private Boolean isActive = true;

    private Boolean isVerified = false;

    private String verificationToken;

    private String resetPasswordToken;

    private LocalDateTime resetPasswordExpiry;

    private LocalDateTime lastLogin;

    private Integer loginAttempts = 0;

    private Boolean accountLocked = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Gender {
        MUJER, HOMBRE, OTRO
    }
}