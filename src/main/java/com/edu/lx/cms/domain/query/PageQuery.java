package com.edu.lx.cms.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {
    private String userId;
    private Long pageNo;
    private Long pageSize;
    private String sortBy;
    private Boolean isAsc;
    private String ctDelete;
    private String ctMf;
    private String ctName;



}