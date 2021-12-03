package io.github.swahid.northend.service;

import io.github.swahid.northend.entity.NorthEnd;
import io.github.swahid.northend.repository.NorthEndRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NorthendService {

    @Autowired
    private NorthEndRepo northEndRepo;

    @Transactional
    public Object save(NorthEnd entity){
        northEndRepo.save(entity);
        return entity;
    }

    @Transactional
    public Object save(List<NorthEnd> entity){
        System.out.println("save entity size: " + entity);
        northEndRepo.saveAll(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public List<NorthEnd> findAll(){
        return northEndRepo.findAllByItemsNot("",Sort.by(Sort.Direction.ASC,  "dailyDate", "id"));
    }
}
