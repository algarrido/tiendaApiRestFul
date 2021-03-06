/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.apirestfulshop.services;

import com.service.apirestfulshop.exceptions.RecordNotFoundException;
import com.service.apirestfulshop.model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.service.apirestfulshop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alfon
 */
@Service
public class ClientService {
    @Autowired
    ClientRepository repository;

    public List<Client> getAllClient() {
        List<Client> clientList = repository.findAll();

        if (clientList.size() > 0) {
            return clientList;
        } else {
            return new ArrayList<Client>();
        }
    }

    public Client getClientById(Long id) throws RecordNotFoundException {
        Optional<Client> client = repository.findById(id);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RecordNotFoundException("No client record exist for given id", id);
        }
    }

    public Client createClient(Client entity) {
        entity = repository.save(entity);
        return entity;
    }

    public Client UpdateClient(Client entity) throws RecordNotFoundException {

        if (entity.getId() != null) {
            Optional<Client> client = repository.findById(entity.getId());

            if (client.isPresent()) {
                Client newEntity = client.get();
                //newEntity.setId(entity.getId());
                newEntity.setName(entity.getName());
                newEntity.setAge(entity.getAge());
                newEntity.setPhone(entity.getPhone());
                newEntity.setOrdersSet(entity.getOrdersSet());

                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                throw new RecordNotFoundException("Client not found", entity.getId());
            }
        } else {
            throw new RecordNotFoundException("No id of client given", 0l);
        }
    }

    public void deleteClientById(Long id) throws RecordNotFoundException {
        Optional<Client> client = repository.findById(id);

        if (client.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No client record exist for given id", id);
        }
    }

    public List<Client> getClientByName(String name) {
        List<Client> clientList = repository.getByName(name);

        if (clientList.size() > 0) {
            return clientList;
        } else {
            return new ArrayList<Client>();
        }
    }
}
