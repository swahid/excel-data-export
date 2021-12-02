package io.github.swahid.northend.repository;

import io.github.swahid.northend.entity.NorthEnd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface NorthEndRepo extends JpaRepository<NorthEnd, Serializable> {
}
