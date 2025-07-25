package br.com.faitec.foodcare.implementation.service.request;

import br.com.faitec.foodcare.domain.Request;
import br.com.faitec.foodcare.port.dao.request.RequestDao;
import br.com.faitec.foodcare.port.service.request.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestDao requestDao;

    public RequestServiceImpl(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public int create(Request entity) {
        int invalidResponse = -1;

        if (entity == null) {
            return invalidResponse;
        }
        if (entity.getRequestDate() == null || entity.getRequestDate().isEmpty()) {
            return invalidResponse;
        }
        if (entity.getRequestType() == null) {
            return invalidResponse;
        }
        if (entity.getStatus() == null) {
            return invalidResponse;
        }
        if (entity.getUserId() <= 0) {
            return invalidResponse;
        }
        
        final int id = requestDao.create(entity);
        return id;
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            return;
        }

        requestDao.delete(id);
    }

    @Override
    public Request findById(int id) {
        if (id < 0) {
            return null;
        }

        Request entity = requestDao.findByid(id);
        return entity;
    }

    @Override
    public List<Request> findAll() {
        final List<Request> entities = requestDao.findAll();
        return entities;
    }

    @Override
    public void update(int id, Request entity) {
        if (id != entity.getId()) {
            return;
        }

        Request request = findById(id);

        if (request == null) {
            return;
        }

        requestDao.update(id, entity);
    }

    @Override
    public List<Request> findByUserId(int userId) {
        if (userId < 0) {
            return List.of();
        }
        return requestDao.findByUserId(userId);
    }

    @Override
    public List<Request> findByStatus(Request.RequestStatus status) {
        if (status == null) {
            return List.of();
        }
        return requestDao.findByStatus(status);
    }

    @Override
    public List<Request> findByRequestType(Request.RequestType requestType) {
        if (requestType == null) {
            return List.of();
        }
        return requestDao.findByRequestType(requestType);
    }

    @Override
    public boolean updateStatus(int id, Request.RequestStatus newStatus) {
        if (id < 0 || newStatus == null) {
            return false;
        }
        
        Request request = findById(id);
        if (request == null) {
            return false;
        }
        
        return requestDao.updateStatus(id, newStatus);
    }
}