package br.com.faitec.foodcare.implementation.service.donation;

import br.com.faitec.foodcare.domain.Donation;
import br.com.faitec.foodcare.port.dao.donation.DonationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceImplTest {

    @Mock
    private DonationDao donationDao;

    @InjectMocks
    private DonationServiceImpl donationService;

    private Donation validDonation;

    @BeforeEach
    void setUp() {
        validDonation = new Donation(1, "2024-01-15", 1, "PENDING");
    }

    @Test
    void shouldCreateDonationSuccessfully() {
        when(donationDao.create(validDonation)).thenReturn(1);

        int result = donationService.create(validDonation);

        assertEquals(1, result);
        verify(donationDao).create(validDonation);
    }

    @Test
    void shouldReturnInvalidResponseWhenCreatingNullDonation() {
        int result = donationService.create(null);

        assertEquals(-1, result);
        verify(donationDao, never()).create(any());
    }

    @Test
    void shouldDeleteDonationSuccessfully() {
        donationService.delete(1);

        verify(donationDao).delete(1);
    }

    @Test
    void shouldFindDonationById() {
        when(donationDao.findByid(1)).thenReturn(validDonation);

        Donation result = donationService.findById(1);

        assertEquals(validDonation, result);
        verify(donationDao).findByid(1);
    }

    @Test
    void shouldFindAllDonations() {
        List<Donation> donations = List.of(validDonation);
        when(donationDao.findAll()).thenReturn(donations);

        List<Donation> result = donationService.findAll();

        assertEquals(donations, result);
        verify(donationDao).findAll();
    }

    @Test
    void shouldUpdateDonationSuccessfully() {
        when(donationDao.findByid(1)).thenReturn(validDonation);

        donationService.update(1, validDonation);

        verify(donationDao).update(1, validDonation);
    }
}