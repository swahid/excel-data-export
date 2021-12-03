package io.github.swahid.northend.repository;

import io.github.swahid.northend.entity.NorthEnd;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface NorthEndRepo extends JpaRepository<NorthEnd, Serializable> {

    List<NorthEnd> findAllByItemsNot(String items, Sort sort);
}
