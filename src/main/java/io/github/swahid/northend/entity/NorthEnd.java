package io.github.swahid.northend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "northend")
public class NorthEnd implements Serializable {


    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "items")
    private String items;

    @Column(name = "sheet_name")
    private String sheetName;

    @Column(name = "order1")
    private Integer order1;

    @Column(name = "delivery1")
    private Integer delivery1;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "daily_date")
    @Temporal(value = TemporalType.DATE)
    private Date dailyDate;

}
