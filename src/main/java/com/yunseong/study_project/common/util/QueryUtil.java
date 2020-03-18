package com.yunseong.study_project.common.util;

import org.springframework.data.domain.Pageable;

public class QueryUtil {

    public static String pageableQuery(Pageable pageable) {
        String query = "";
        if (pageable != null) {
            query += "?";
            if (pageable.getPageNumber() > -1) {
                query += "number=" + pageable.getPageNumber() + "&";
            }
            if (pageable.getPageSize() > 0) {
                query += "number=" + pageable.getPageSize() + "&";
            }
            if (pageable.getSort() != null) {
                query += "number=" + pageable.getSort() + "&";
            }
            query = query.substring(0, query.length()-1);
        }
        return query;
    }
}
