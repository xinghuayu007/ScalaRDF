package com.triad.service.impl;

import com.triad.dataobject.Query;
import com.triad.service.QueryService;
import com.triad.tools.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by zhuoying on 2015/10/1.
 */
public class QueryServiceImpl implements QueryService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ErrorCode executeQuery(Query query) {
        if(query==null || StringUtils.isEmpty(query.getRequest()))
            return ErrorCode.EMPTY_QUERY;
        Socket client = null;
        try {
            StringBuffer establishSb = new StringBuffer();
            establishSb.append("[QUERY_SERVICE] MASTER ").append(query.getMaster()).append(" PORT ").append(query.getPort());
            logger.debug(establishSb.toString());

            String masterNode = query.getMaster();
            if(StringUtils.isEmpty(masterNode))
                return ErrorCode.NO_MASTER;
            client = new Socket(query.getMaster(), query.getPort());
            //sending query request
            Writer writer = new OutputStreamWriter(client.getOutputStream());
            String size = query.getRequest().length()+"";
            for(int i=0;i<32-(query.getRequest().length()+"").length();i++){
                size +=" ";
            }
            writer.write(size,0,32);
            writer.flush();
            int off = 0;
            int maxSize = query.getRequest().length();
            while(off<maxSize){
                int len;
                if(off+1024<maxSize){
                    len = 1024;
                }
                else
                    len = maxSize - off;
                writer.write(query.getRequest(),off,len);
                off+=len;
                writer.flush();
            }
            if(query.getRequest().equals("quit"))
                return ErrorCode.SHUTTING_DOWN;
            //receiving result
            Reader reader = new InputStreamReader(client.getInputStream());
            char result[] = new char[1024];
            StringBuilder sb = new StringBuilder();
            int len;
            while((len=reader.read(result,0,1024))!=-1){
                sb.append(result,0,len);
            }
            reader.close();
            writer.close();
            String response = sb.toString();
            query.setResponse(response);
        }
        catch (IOException e){
            logger.error("[QUERY_SERVICE] socket IO exception",e);
            return ErrorCode.SOCKET_ERROR;
        }
        finally {
            try {
                client.close();
            }
            catch (Exception e){
                logger.error("[QUERY_SERVICE] ",e);
            }
        }
        return ErrorCode.SUCCESS;
    }


}
