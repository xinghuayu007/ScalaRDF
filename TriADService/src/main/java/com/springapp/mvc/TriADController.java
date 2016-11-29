package com.springapp.mvc;

import com.triad.dataobject.ClusterServer;
import com.triad.dataobject.Query;
import com.triad.service.QueryService;
import com.triad.service.RegisterService;
import com.triad.tools.ErrorCode;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class TriADController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    QueryService queryService;

    @Resource
    RegisterService registerService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView QueryPage(@ModelAttribute("query") Query query,ModelMap modelMap,HttpServletRequest request){
        List<ClusterServer> masterList = registerService.getCluster();
        String selectHost = SelectOptionsToView(masterList);
        modelMap.addAttribute("selectHost",selectHost);
        return new ModelAndView("query", "query", new Query());
    }

    @RequestMapping(value="/queryexecute",method = RequestMethod.POST)
    public ModelAndView QueryResult(@ModelAttribute("query")Query query,ModelMap modelMap,HttpServletRequest request){
        List<ClusterServer> masterList = registerService.getCluster();
        String selectHost = SelectOptionsToView(masterList);
        modelMap.addAttribute("selectHost", selectHost);
        try {
            int port = MatchPort(query.getMaster());
            query.setPort(port);
        }
        catch (Exception e){
            logger.error("[TRIAD_CONTROLLER]",e);
        }
        ErrorCode errorCode = queryService.executeQuery(query);
        request.getSession().setAttribute("errorCode", errorCode.getCode());
        modelMap.addAttribute("queryString",query.getRequest());
        if(errorCode.getCode() == ErrorCode.SUCCESS.getCode()){
            //present query result when successfully execute the query
            String result = query.getResponse();
            String[] resultArray = result.split("\n");
            int length = resultArray.length;
            int count = 1;
            if(length%200 == 0)
                count = length/200;
            else
                count = length/200+1;
            JSONArray resultJson = new JSONArray();
            for(int i=0;i<count;i++){
                String newLine = "";
                for(int j=0+i*200;j<(i+1)*200&j<length;j++){
                    newLine+=resultArray[j]+"\n";
                }
                if(i==0&&count>200){
                    result = newLine;
                }
                resultJson.add(i,newLine);
            }
            modelMap.addAttribute("queryResult",result);
            modelMap.addAttribute("resultJson",resultJson);
            String pageStr = ResultPageToView(count);
            modelMap.addAttribute("resultPage",pageStr);
        }
        return new ModelAndView("query",modelMap);
    }

    @RequestMapping(value = {"/","/cluster"},method = RequestMethod.GET)
    public ModelAndView HomePage(ModelMap modelMap){
        List<ClusterServer> serverList = registerService.getCluster();
        modelMap.addAttribute("mRecordData",RegisterToView(serverList));
        return new ModelAndView("home");
    }

    @ResponseBody
    @RequestMapping(value="/lubmQuery",method = RequestMethod.GET)
    public String UseLUBMQuery(@RequestParam(required = false) String option,ModelMap modelMap,HttpServletRequest request){
        try {
            if (!option.equals("None")) {
                String queryStr = request.getSession().getAttribute(option).toString();
                return queryStr;
            }
        }
        catch (Exception e){
            logger.error("[TRIAD_CONTROLLER] ",e);
        }
        return "";
    }

    @RequestMapping(value="/uploadQuery",method = RequestMethod.POST)
    public ModelAndView UploadQuery(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request,ModelMap modelMap){
        try {
            if (!file.isEmpty()) {
                List<ClusterServer> masterList = registerService.getCluster();
                String selectHost = SelectOptionsToView(masterList);
                modelMap.addAttribute("selectHost", selectHost);
                InputStream in = file.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String queryStr = "";
                while((line = reader.readLine())!=null){
                    queryStr += line;
                }
                Query query = new Query();
                query.setRequest(queryStr);
                modelMap.addAttribute("queryString",queryStr);
                return new ModelAndView("query");
            }
        }
        catch (Exception e){
            logger.error("[UPLOADCONTROLLER] Exception ",e);
        }
        return new ModelAndView("query");
    }

    protected String RegisterToView(List<ClusterServer> serverList){
        StringBuffer sb = new StringBuffer();
        for(ClusterServer server:serverList){
            sb.append("addRecordData(\"clusterTable\",");
            //sb.append("\"").append(server.getRegState().getMessage()).append("\",");
            sb.append("\"").append(server.getAddr()).append("\",");
            //sb.append("\"").append(server.getName()).append("\",");
            sb.append("\"").append(server.getPort()).append("\",");
            sb.append("\"").append(server.getRole()).append("\");");
        }
        return sb.toString();
    }

    protected String ResultPageToView(int count){
        StringBuffer sb = new StringBuffer();
        sb.append("setResultPage(\"").append(count).append("\");");
        return sb.toString();
    }

    protected int MatchPort(String host) throws Exception{
        List<ClusterServer> masterList = registerService.getCluster();
        for(ClusterServer master:masterList){
            if(master.getAddr().equals(host))
                return master.getPort();
        }
        throw new Exception("No matching master found!");
    }

    protected String SelectOptionsToView(List<ClusterServer> masterList){
        StringBuffer sb = new StringBuffer();
        for(ClusterServer master:masterList) {
            sb.append("addMasterAddress(");
            sb.append("\"").append(master.getAddr()).append("\",");
            //sb.append("\"").append(master.getName()).append("\");");
        }
        return sb.toString();
    }

    protected void mockResult(ModelMap modelMap){
        JSONArray resultJson = new JSONArray();
        for(int i=0;i<1000;i++){
            resultJson.add(i,"abc");
        }
        modelMap.addAttribute("resultJson",resultJson);
        modelMap.addAttribute("resultPage",ResultPageToView(50));
    }

    protected void LoadLubmQuries(HttpServletRequest request){
        try {
            String absPath = request.getSession().getServletContext().getRealPath("/");
            for (int i = 0; i < 7; i++) {
                Object query = request.getSession().getAttribute("Q"+(i+1));
                if (query == null || query.toString().equals("")) {
                    String path = absPath + "/Quries/Q" + (i+1);
                    File file = new File(path);
                    FileInputStream in = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    String queryStr = "";
                    while((line = reader.readLine())!=null){
                        queryStr += line;
                    }
                    request.getSession().setAttribute("Q"+(i+1),queryStr);
                }
            }
        }
        catch (Exception e){
            logger.error("[TRIAD_CONTROLLER] ",e);
        }
    }
}
