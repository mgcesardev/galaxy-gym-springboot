package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Reservation;

public interface ReservationService {
    public Page<Reservation> findAll(Pageable pageable);

    public Reservation findById(Long id);

    public Reservation save(Reservation model);

    Page<Reservation> buscarPorNombre(String nombre, Pageable pageable);

}
