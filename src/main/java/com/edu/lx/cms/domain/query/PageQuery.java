package com.edu.lx.cms.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {
    private Long pageNo;
    private Long pageSize;
    private String sortBy;
    private String isAsc;
    private String ctId;
    private String ctDelete;
    private String ctMf;
    private String ctName;
    private String matterDelete;
    private String matterId;
    private String matter;

}