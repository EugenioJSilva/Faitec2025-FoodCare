package br.com.faitec.foodcare.implementation.service.donation;

import br.com.faitec.foodcare.domain.Donation;
import br.com.faitec.foodcare.port.dao.donation.DonationDao;
import br.com.faitec.foodcare.port.service.donation.DonationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {
    private final DonationDao donationDao;

    public DonationServiceImpl(DonationDao donationDao) {
        this.donationDao = donationDao;
    }

    @Override
    public int create(Donation entity) {
        int invalidResponse = -1;

        if (entity == null) {
            return invalidResponse;
        }
        if (entity.getDonationDate() == null || entity.getDonationDate().isEmpty()) {
            return invalidResponse;
        }
        if (entity.getUserId() <= 0) {
            return invalidResponse;
        }
        
        final int id = donationDao.create(entity);
        return id;
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            return;
        }

        donationDao.delete(id);
    }

    @Override
    public Donation findById(int id) {
        if (id < 0) {
            return null;
        }

        Donation entity = donationDao.findByid(id);
        return entity;
    }

    @Override
    public List<Donation> findAll() {
        final List<Donation> entities = donationDao.findAll();
        return entities;
    }

    @Override
    public void update(int id, Donation entity) {
        if (id != entity.getId()) {
            return;
        }

        Donation donation = findById(id);

        if (donation == null) {
            return;
        }

        donationDao.update(id, entity);
    }

    @Override
    public List<Donation> findByUserId(int userId) {
        if (userId < 0) {
            return List.of();
        }
        return donationDao.findByUserId(userId);
    }
}