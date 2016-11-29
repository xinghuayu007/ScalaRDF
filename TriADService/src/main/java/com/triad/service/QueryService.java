package com.triad.service;

import com.triad.dataobject.Query;
import com.triad.tools.ErrorCode;

/**
 * Created by zhuoying on 2015/10/1.
 */
public interface QueryService {
    ErrorCode executeQuery(Query query);
}
