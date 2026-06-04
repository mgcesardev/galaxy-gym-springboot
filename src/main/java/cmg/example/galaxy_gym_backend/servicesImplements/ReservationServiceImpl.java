package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Reservation;
import cmg.example.galaxy_gym_backend.repositories.ReservationRepository;
import cmg.example.galaxy_gym_backend.services.ReservationService;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findAll(Pageable pageable) {
        try {
            return reservationRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las reservaciones", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        try {
            return reservationRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar reservación con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Reservation save(Reservation model) {
        try {
            return reservationRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar reservación: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar reservación con id " + id + ": " + e.getMessage());
        }
    }


}
