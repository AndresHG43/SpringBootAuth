package com.auth.authorization.utils;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Data
public class PageableUtils {
    protected int offset = 0;
    protected int limit = 10;
    protected String sortField = "dateCreated";
    protected Sort.Direction sortDirection = Sort.Direction.ASC;

    private Pageable pageable;

    public Pageable getPageable() {
        pageable = PageRequest.of(offset, limit, sortDirection, sortField);
        return pageable;
    }

    public Pageable getPageable(Map<String, Object> params) {
        if (params == null)
            return PageRequest.of(offset, limit, sortDirection, sortField);
        setPageable(params);
        pageable = PageRequest.of(offset, limit, sortDirection, sortField);
        return pageable;
    }

    private void setPageable(Map<String, Object> params) {
        if (params.containsKey("offset")) {
            offset = Integer.parseInt(params.get("offset").toString());
        }
        if (params.containsKey("limit")) {
            limit = (int) Integer.parseInt(params.get("limit").toString());
        }
        if (params.containsKey("sortField")) {
            sortField = params.get("sortField").toString();
        }
        if (params.containsKey("sortDirection")) {
            String direction = params.get("sortDirection").toString().toUpperCase();
            sortDirection = Sort.Direction.fromString(direction);
        }
    }
}
