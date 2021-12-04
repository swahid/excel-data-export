package io.github.swahid.northend.service;

import io.github.swahid.northend.entity.NorthEnd;
import io.github.swahid.northend.repository.NorthEndRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public List<NorthEnd> findAll(Date startDate, Date endDate) throws Exception{

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)){
            return northEndRepo.findAllByItemsNotAndDailyDateBetween("", startDate, endDate, getSorting());
        }
        return northEndRepo.findAllByItemsNot("",getSorting());
    }

    private Sort getSorting(){
        return Sort.by(Sort.Direction.ASC,  "dailyDate", "id");
    }

    @Transactional
    public void deleteByFileName(String fileName) throws Exception{
        if (Objects.nonNull(fileName)){
            List<NorthEnd> northEndList = northEndRepo.findAllByFileName(fileName);
            northEndRepo.deleteAll(northEndList);

        }else{
            northEndRepo.deleteAll();
        }
    }


}
