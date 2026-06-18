package SistemRentalKendaraan.domain.repository;

import SistemRentalKendaraan.domain.model.User;

public interface UserRepository {
    User findByUsername(String username);
}