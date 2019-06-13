package dns.analyse.controller.network;

import dns.analyse.dao.mapper.DomainNetWorkDAO;
import dns.analyse.dao.model.DomainAnalysePO;
import dns.analyse.dao.model.DomainNetWorkPO;
import dns.analyse.model.DeleteNodeVO;
import dns.analyse.model.DomainNetworkVO;
import dns.analyse.service.IDnsDomainDependenceService;
import dns.analyse.service.IHandleNetworkNode;
import dns.analyse.service.tools.LogAnnotation;
import dns.analyse.service.tools.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.antlr.op.Del;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @email jryyufeng@126.com
 * @author:Jry
 * @date: 2019/5/14
 * @time: 16:28
 */
@Slf4j
@Controller
@RequestMapping("/admin/network/api")
public class DomainNetworkController {
    private static final Logger LOGGER = LogManager.getLogger(DomainNetworkController.class.getName());

    @Autowired
    DomainNetWorkDAO domainNetWorkDAO;
    @Autowired
    RedisCacheManager redisCacheManager;
    @Autowired
    IHandleNetworkNode handleNetworkNode;
    @Autowired
    IDnsDomainDependenceService dnsDomainDependenceService;

    @GetMapping("/list")
    @ResponseBody
    public DomainNetworkVO getList(@RequestParam(defaultValue = "graph_chain_100") String chartName, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
        List<DomainNetWorkPO> pos = domainNetWorkDAO.queryPageByCharts(Arrays.asList(chartName.split(";")), pageNum, pageSize);
        //加缓存
        Integer total = dnsDomainDependenceService.getNetWorkChartNum(chartName);
        return DomainNetworkVO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .pos(pos)
                .totalCount(total)
                .build();
    }

    @GetMapping("/deleteNode")
    @ResponseBody
    public DeleteNodeVO deleteNode(Integer pattern, double percent, String attribute, String chartNmae, String kind2) {
        List<DomainAnalysePO> pos = handleNetworkNode.getMcss(kind2);
        List<String> vertexs = handleNetworkNode.getVertexByPercent(pattern, percent, attribute, chartNmae, kind2);
        List<String> res = handleNetworkNode.computeInvalid(pos, vertexs);
        return DeleteNodeVO.builder()
                .allNum(res.get(0))
                .deleteNum(res.get(1))
                .proportion(res.get(2))
                .build();
    }

    /**
     * 初始化HTTP头.
     *
     * @return HttpHeaders
     */
    public HttpHeaders initHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html",
                Charset.forName("utf-8"));
        headers.setContentType(mediaType);
        return headers;
    }

    @RequestMapping("/handleFile")
    @ResponseBody
    public ResponseEntity<DeleteNodeVO> handleFile(@RequestParam("myfile") MultipartFile myfile, @RequestParam("kind2") String kind2, HttpSession session) {
        String sourceName = myfile.getOriginalFilename();
        String fileType = sourceName.substring(sourceName.lastIndexOf("."));
        if (myfile.isEmpty()) {
            return new ResponseEntity<DeleteNodeVO>(
                    DeleteNodeVO.builder()
                            .message("文件不能为空")
                            .deleteNum("0")
                            .proportion("0")
                            .build(), initHttpHeaders(), HttpStatus.OK);
        }
        if (!".txt".equals(fileType.toLowerCase())) {
            return new ResponseEntity<DeleteNodeVO>(
                    DeleteNodeVO.builder()
                            .message("文件只支持txt类型")
                            .deleteNum("0")
                            .proportion("0")
                            .build(), initHttpHeaders(), HttpStatus.OK);
        }
        String classpath = session.getServletContext().getRealPath("/");
        //String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        //System.out.println(classpath);

        // 存放文件临时路径
        String base = classpath+"/upload";  //获取文件上传的路径，在webapp下的upload中
        File file = new File(base);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 讲文件上传到临时目录
        String path = base + File.separator + sourceName;
        File upload = new File(path);
        try {
            myfile.transferTo(upload);
        } catch (IOException e) {
            log.error("文件保存失败");
            return new ResponseEntity<DeleteNodeVO>(
                    DeleteNodeVO.builder()
                            .message("文件保存失败")
                            .deleteNum("0")
                            .proportion("0")
                            .build(), initHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 解析文件
        BufferedReader br = null;
        FileReader reader = null;
        try {
            reader = new FileReader(upload);
            br = new BufferedReader(reader);
            String line = "";
            List<String> vertexs = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                vertexs.add(line);
                //System.out.println(line);
            }
            List<DomainAnalysePO> pos = handleNetworkNode.getMcss(kind2);
            List<String> res = handleNetworkNode.computeInvalid(pos,vertexs);
            return new ResponseEntity<DeleteNodeVO>(
                    DeleteNodeVO.builder()
                            .allNum(res.get(0))
                            .deleteNum(res.get(1))
                            .proportion(res.get(2))
                            .build(), initHttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return new ResponseEntity<DeleteNodeVO>(
                    DeleteNodeVO.builder()
                            .message("上传文件失败")
                            .deleteNum("0")
                            .proportion("0")
                            .build(), initHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
                if (br != null) {
                    br.close();
                    br = null;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
