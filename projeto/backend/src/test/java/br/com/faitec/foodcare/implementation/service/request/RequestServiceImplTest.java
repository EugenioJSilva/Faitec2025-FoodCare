package br.com.faitec.foodcare.implementation.service.request;

import br.com.faitec.foodcare.domain.Request;
import br.com.faitec.foodcare.port.dao.request.RequestDao;
import br.com.faitec.foodcare.port.service.basket.BasketManagementService;
import br.com.faitec.foodcare.port.service.request.RequestStockIntegrationService;
import br.com.faitec.foodcare.port.service.user.UserService;
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
class RequestServiceImplTest {

    @Mock
    private RequestDao requestDao;

    @Mock
    private UserService userService;

    @Mock
    private BasketManagementService basketManagementService;

    @Mock
    private RequestStockIntegrationService requestStockIntegrationService;

    @InjectMocks
    private RequestServiceImpl requestService;

    private Request validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new Request(1, "2024-01-15", Request.RequestType.BASIC, Request.BasketType.basic, Request.RequestStatus.PENDING, 1);
    }

    @Test
    void shouldCreateRequestSuccessfully() {
        when(requestDao.create(validRequest)).thenReturn(1);

        int result = requestService.create(validRequest);

        assertEquals(1, result);
        verify(requestDao).create(validRequest);
    }

    @Test
    void shouldReturnInvalidResponseWhenCreatingRequestWithEmptyDate() {
        Request invalidRequest = new Request(1, "", Request.RequestType.BASIC, Request.BasketType.basic, Request.RequestStatus.PENDING, 1);
        
        int result = requestService.create(invalidRequest);
        
        assertEquals(-1, result);
        verify(requestDao, never()).create(any());
    }

    @Test
    void shouldReturnInvalidResponseWhenCreatingRequestWithInvalidUserId() {
        Request invalidRequest = new Request(1, "2024-01-15", Request.RequestType.BASIC, Request.BasketType.basic, Request.RequestStatus.PENDING, 0);
        
        int result = requestService.create(invalidRequest);
        
        assertEquals(-1, result);
        verify(requestDao, never()).create(any());
    }

    @Test
    void shouldReturnInvalidResponseWhenCreatingNullRequest() {
        int result = requestService.create(null);

        assertEquals(-1, result);
        verify(requestDao, never()).create(any());
    }

    @Test
    void shouldDeleteRequestSuccessfully() {
        requestService.delete(1);

        verify(requestDao).delete(1);
    }

    @Test
    void shouldFindRequestById() {
        when(requestDao.findByid(1)).thenReturn(validRequest);

        Request result = requestService.findById(1);

        assertEquals(validRequest, result);
        verify(requestDao).findByid(1);
    }

    @Test
    void shouldFindAllRequests() {
        List<Request> requests = List.of(validRequest);
        when(requestDao.findAll()).thenReturn(requests);

        List<Request> result = requestService.findAll();

        assertEquals(requests, result);
        verify(requestDao).findAll();
    }

    @Test
    void shouldUpdateRequestSuccessfully() {
        when(requestDao.findByid(1)).thenReturn(validRequest);

        requestService.update(1, validRequest);

        verify(requestDao).update(1, validRequest);
    }
}