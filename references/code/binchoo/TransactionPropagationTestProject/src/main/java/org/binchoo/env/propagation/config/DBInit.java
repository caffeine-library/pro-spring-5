package org.binchoo.env.propagation.config;

import org.binchoo.env.propagation.entities.SimpleData;
import org.binchoo.env.propagation.repos.SimpleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DBInit {

    @Autowired
    public SimpleDataRepository repository;

    @PostConstruct
    void initDB() {
        SimpleData data = new SimpleData();
        repository.save(data);
    }
}
